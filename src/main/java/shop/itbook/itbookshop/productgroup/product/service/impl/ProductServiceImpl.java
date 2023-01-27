package shop.itbook.itbookshop.productgroup.product.service.impl;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.book.service.adminapi.BookService;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;
import shop.itbook.itbookshop.productgroup.product.fileservice.FileService;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.product.transfer.ProductTransfer;
import shop.itbook.itbookshop.productgroup.productcategory.service.ProductCategoryService;

/**
 * ProductService 인터페이스를 구현한 상품 Service 클래스입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final FileService fileService;
    private final BookService bookService;
    private final ProductCategoryService productCategoryService;
    @Value("${object.storage.folder-path.thumbnail}")
    private String folderPathThumbnail;

    @Value("${object.storage.folder-path.ebook}")
    private String folderPathEbook;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Long addProduct(ProductBookRequestDto requestDto,
                           MultipartFile thumbnails, MultipartFile ebook) {
        uploadAndSetFile(requestDto, thumbnails, ebook);

        Product product = ProductTransfer.dtoToEntityAdd(this.toProductRequestDto(requestDto));
        productRepository.save(product);

        Category parentCategory =
            productCategoryService.addProductCategory(product, requestDto.getCategoryNoList());

        Long productNo = product.getProductNo();
        if (parentCategory.getCategoryName().equals("도서")) {
            bookService.addBook(bookService.toBookRequestDto(requestDto), productNo);
        }

        return productNo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void modifyProduct(Long productNo, ProductBookRequestDto requestDto,
                              MultipartFile thumbnails, MultipartFile ebook) {
        uploadAndSetFile(requestDto, thumbnails, ebook);

        Product product = updateProduct(requestDto, productNo);
        productRepository.save(product);

        Category parentCategory =
            productCategoryService.modifyProductCategory(product, requestDto.getCategoryNoList());

        if (parentCategory.getCategoryName().equals("도서")) {
            bookService.modifyBook(bookService.toBookRequestDto(requestDto), productNo);
        }

    }

    private void uploadAndSetFile(ProductBookRequestDto requestDto, MultipartFile thumbnails,
                                  MultipartFile ebook) {
        String thumbnailUrl = fileService.uploadFile(thumbnails, folderPathThumbnail);
        requestDto.setFileThumbnailsUrl(thumbnailUrl);

        if (!Objects.isNull(ebook)) {
            String ebookUrl = fileService.uploadFile(ebook, folderPathEbook);
            requestDto.setFileEbookUrl(ebookUrl);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removeProduct(Long productNo) {
        productCategoryService.removeProductCategory(productNo);
        productRepository.deleteById(productNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product findProductEntity(Long productNo) {
        return productRepository.findById(productNo)
            .orElseThrow(ProductNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductDetailsResponseDto> findProductList() {
        return productRepository.findProductList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDetailsResponseDto findProduct(Long productNo) {
        ProductDetailsResponseDto productResponseDto = productRepository.findProduct(productNo);
        return productResponseDto;
    }

    private AddProductRequestDto toProductRequestDto(ProductBookRequestDto requestDto) {
        return AddProductRequestDto.builder()
            .productName(requestDto.getProductName())
            .simpleDescription(requestDto.getSimpleDescription())
            .detailsDescription(requestDto.getDetailsDescription())
            .stock(requestDto.getStock())
            .isExposed(requestDto.getIsExposed())
            .isForceSoldOut(requestDto.getIsForceSoldOut())
            .fixedPrice(requestDto.getFixedPrice())
            .increasePointPercent(requestDto.getIncreasePointPercent())
            .discountPercent(requestDto.getDiscountPercent())
            .rawPrice(requestDto.getRawPrice())
            .fileThumbnailsUrl(requestDto.getFileThumbnailsUrl())
            .build();
    }

    /**
     * 상품 번호로 상품을 찾아 해당 상품 정보를 수정해주는 메서드입니다.
     *
     * @param requestDto 상품 수정을 위한 정보를 담은 dto 객체입니다.
     * @param productNo  수정해야 할 상품 번호입니다.
     * @return 수정 완료된 상품을 반환합니다.
     */
    private Product updateProduct(ProductBookRequestDto requestDto, Long productNo) {
        Product product = this.findProductEntity(productNo);

        product.setName(requestDto.getProductName());
        product.setSimpleDescription(requestDto.getSimpleDescription());
        product.setDetailsDescription(requestDto.getDetailsDescription());
        product.setStock(requestDto.getStock());
        product.setIsExposed(requestDto.getIsExposed());
        product.setIsForceSoldOut(requestDto.getIsForceSoldOut());
        product.setThumbnailUrl(requestDto.getFileThumbnailsUrl());
        product.setFixedPrice(requestDto.getFixedPrice());
        product.setIncreasePointPercent(requestDto.getIncreasePointPercent());
        product.setDiscountPercent(requestDto.getDiscountPercent());
        product.setRawPrice(requestDto.getRawPrice());

        return product;
    }
}
