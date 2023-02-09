package shop.itbook.itbookshop.productgroup.product.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.exception.NotSellableProductException;
import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;
import shop.itbook.itbookshop.fileservice.FileService;
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
    public Long addProduct(ProductRequestDto requestDto, MultipartFile thumbnails) {
        uploadAndSetFile(requestDto, thumbnails);

        Product product = productRepository.save(ProductTransfer.dtoToEntityAdd(requestDto));

        productCategoryService.addProductCategory(product, requestDto.getCategoryNoList());

        return product.getProductNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void modifyProduct(Long productNo, ProductRequestDto requestDto,
                              MultipartFile thumbnails) {
        uploadAndSetFile(requestDto, thumbnails);

        Product product = updateProduct(requestDto, productNo);
        productRepository.save(product);

        Category parentCategory =
            productCategoryService.modifyProductCategory(product, requestDto.getCategoryNoList());

    }

    private void uploadAndSetFile(ProductRequestDto requestDto, MultipartFile thumbnails) {
        String thumbnailUrl = fileService.uploadFile(thumbnails, folderPathThumbnail);
        requestDto.setFileThumbnailsUrl(thumbnailUrl);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removeProduct(Long productNo) {
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
    public Page<ProductDetailsResponseDto> findProductList(Pageable pageable, boolean isAdmin) {

        Page<ProductDetailsResponseDto> productList;

        if (isAdmin) {
            productList = productRepository.findProductListAdmin(pageable);
        } else {
            productList = productRepository.findProductListUser(pageable);
        }

        setExtraFieldsForList(productList);
        return productList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findProductListByProductNoListForUser(Pageable pageable,
                                                                                 List<Long> productNoList) {

        List<Long> productNoListRemovedNull = productNoList.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        Page<ProductDetailsResponseDto> productListByProductNoList =
            productRepository.findProductListByProductNoListForUser(pageable,
                productNoListRemovedNull);
        setExtraFieldsForList(productListByProductNoList);

        return productListByProductNoList;
    }

    @Override
    public Page<ProductDetailsResponseDto> findProductListByProductNoListForAdmin(Pageable pageable,
                                                                                  List<Long> productNoList) {

        List<Long> productNoListRemovedNull = productNoList.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        Page<ProductDetailsResponseDto> productListByProductNoList =
            productRepository.findProductListByProductNoListForAdmin(pageable,
                productNoListRemovedNull);
        setExtraFieldsForList(productListByProductNoList);

        return productListByProductNoList;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDetailsResponseDto findProduct(Long productNo) {

        ProductDetailsResponseDto product =
            productRepository.findProductDetails(productNo)
                .orElseThrow(ProductNotFoundException::new);
        setExtraFields(product);
        return product;
    }

    /**
     * 상품 번호로 상품을 찾아 해당 상품 정보를 수정해주는 메서드입니다.
     *
     * @param requestDto 상품 수정을 위한 정보를 담은 dto 객체입니다.
     * @param productNo  수정해야 할 상품 번호입니다.
     * @return 수정 완료된 상품을 반환합니다.
     */
    private Product updateProduct(ProductRequestDto requestDto, Long productNo) {
        Product product = this.findProductEntity(productNo);

        product.setName(requestDto.getProductName());
        product.setSimpleDescription(requestDto.getSimpleDescription());
        product.setDetailsDescription(requestDto.getDetailsDescription());
        product.setStock(requestDto.getStock());
        product.setIsSelled(requestDto.getIsSelled());
        product.setIsForceSoldOut(requestDto.getIsForceSoldOut());
        product.setThumbnailUrl(requestDto.getFileThumbnailsUrl());
        product.setFixedPrice(requestDto.getFixedPrice());
        product.setIncreasePointPercent(requestDto.getIncreasePointPercent());
        product.setDiscountPercent(requestDto.getDiscountPercent());
        product.setRawPrice(requestDto.getRawPrice());
        product.setIsPointApplyingBasedSellingPrice(
            requestDto.getIsPointApplyingBasedSellingPrice());
        product.setIsSubscription(requestDto.getIsSubscription());
        return product;
    }

    public static void setExtraFieldsForList(Page<ProductDetailsResponseDto> productList) {
        for (ProductDetailsResponseDto product : productList) {
            setExtraFields(product);
        }
    }

    public static void setExtraFields(ProductDetailsResponseDto product) {
        product.setSelledPrice(
            (long) (product.getFixedPrice() * ((100 - product.getDiscountPercent()) * 0.01)));
        String fileThumbnailsUrl = product.getFileThumbnailsUrl();
        product.setThumbnailsName(
            fileThumbnailsUrl.substring(fileThumbnailsUrl.lastIndexOf("/") + 1));
    }

    @Override
    public ProductRequestDto toProductRequestDto(ProductBookRequestDto requestDto) {
        return ProductRequestDto.builder()
            .productName(requestDto.getProductName())
            .categoryNoList(requestDto.getCategoryNoList())
            .simpleDescription(requestDto.getSimpleDescription())
            .detailsDescription(requestDto.getDetailsDescription())
            .stock(requestDto.getStock())
            .isSelled(requestDto.getIsSelled())
            .isForceSoldOut(requestDto.getIsForceSoldOut())
            .fixedPrice(requestDto.getFixedPrice())
            .increasePointPercent(requestDto.getIncreasePointPercent())
            .discountPercent(requestDto.getDiscountPercent())
            .rawPrice(requestDto.getRawPrice())
            .isSubscription(requestDto.getIsSubscription())
            .isPointApplying(requestDto.getIsPointApplying())
            .isPointApplyingBasedSellingPrice(requestDto.getIsPointApplyingBasedSellingPrice())
            .build();
    }

    /**
     * 모든 제품이 팔 수 있는 상품인지 검사 합니다.
     *
     * @param productNoList 검사할 상품들의 번호 리스트.
     * @param productCnt    검사할 상품들의 각각 구매할 개수 리스트.
     * @return 1개라도 팔 수 없는 상품이 있다면 false 를 반환합니다.
     * @author 정재원 *
     */
    @Override
    public void checkSellProductList(List<Long> productNoList, List<Integer> productCnt) {

        Queue<Integer> productCntQueue = new LinkedList<>(productCnt);

        productNoList.forEach(productNo -> {
            if (!canSellProduct(productNo, productCntQueue.poll())) {
                throw new NotSellableProductException(productNo);
            }
        });
    }

    /**
     * 팔 수 있는 상품인지 검사합니다.
     *
     * @param productNo  검사할 상품의 번호
     * @param productCnt 검사할 상품의 구매할 개수
     * @return 팔 수 있으면 true, 팔 수 없으면 false
     * @author 정재원 *
     */
    @Override
    public boolean canSellProduct(Long productNo, Integer productCnt) {

        Product product = findProductEntity(productNo);

        return product.getIsSelled() || !product.getIsForceSoldOut()
            || (product.getStock() - productCnt >= 0);
    }


}
