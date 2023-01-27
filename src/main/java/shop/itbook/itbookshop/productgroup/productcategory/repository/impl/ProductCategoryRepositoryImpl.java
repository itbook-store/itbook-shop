package shop.itbook.itbookshop.productgroup.productcategory.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.book.entity.QBook;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.category.entity.QCategory;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;
import shop.itbook.itbookshop.productgroup.productcategory.entity.ProductCategory;
import shop.itbook.itbookshop.productgroup.productcategory.entity.QProductCategory;
import shop.itbook.itbookshop.productgroup.productcategory.repository.ProductCategoryRepositoryCustom;

/**
 * ProductCategoryRepositoryCustom을 구현한 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class ProductCategoryRepositoryImpl extends QuerydslRepositorySupport
    implements ProductCategoryRepositoryCustom {

    public ProductCategoryRepositoryImpl() {
        super(ProductCategory.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductDetailsResponseDto> getProductListWithCategoryNo(Integer categoryNo) {
        QProductCategory qProductCategory = QProductCategory.productCategory;
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;
        QCategory qCategory = QCategory.category;

        JPQLQuery<ProductDetailsResponseDto> productList =
            from(qProductCategory)
                .innerJoin(qProductCategory.category, qCategory)
                .innerJoin(qProductCategory.product, qProduct)
                .leftJoin(qProduct.book, qBook)
                .select(Projections.constructor(ProductDetailsResponseDto.class,
                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                    qProduct.detailsDescription, qProduct.isExposed, qProduct.isForceSoldOut,
                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                    qBook.ebookUrl, qBook.publisherName, qBook.authorName))
                .where(qCategory.categoryNo.eq(categoryNo));

        return productList.fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryDetailsResponseDto> getCategoryListWithProductNo(Long productNo) {
        QProductCategory qProductCategory = QProductCategory.productCategory;
        QCategory qCategory = QCategory.category;
        QProduct qProduct = QProduct.product;
        QCategory qParentCategory = new QCategory("parentCategory");

        JPQLQuery<CategoryDetailsResponseDto> categoryList =
            from(qProductCategory)
                .innerJoin(qProductCategory.product, qProduct)
                .innerJoin(qProductCategory.category, qCategory)
                .innerJoin(qCategory.parentCategory, qParentCategory)
                .select(Projections.constructor(CategoryDetailsResponseDto.class,
                    qCategory.categoryNo, qCategory.categoryName, qCategory.isHidden,
                    qCategory.level, qCategory.sequence, qParentCategory.categoryNo,
                    qParentCategory.categoryName,
                    qParentCategory.isHidden, qParentCategory.level,
                    qParentCategory.sequence))
                .where(qProductCategory.product.productNo.eq(productNo));

        return categoryList.fetch();
    }
}
