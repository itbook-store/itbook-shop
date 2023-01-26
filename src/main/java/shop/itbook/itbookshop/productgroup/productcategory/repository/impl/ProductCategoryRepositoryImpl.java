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
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepositoryCustom;
import shop.itbook.itbookshop.productgroup.productcategory.entity.ProductCategory;
import shop.itbook.itbookshop.productgroup.productcategory.entity.QProductCategory;
import shop.itbook.itbookshop.productgroup.productcategory.repository.ProductCategoryRepositoryCustom;

/**
 * @author 이하늬
 * @since 1.0
 */
public class ProductCategoryRepositoryImpl extends QuerydslRepositorySupport
    implements ProductCategoryRepositoryCustom {

    public ProductCategoryRepositoryImpl() {
        super(ProductCategory.class);
    }

    @Override
    public List<ProductDetailsResponseDto> getProductListWithCategoryNo(Integer categoryNo) {
        QProductCategory qProductCategory = QProductCategory.productCategory;
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<ProductDetailsResponseDto> productList =
            from(qBook)
                .rightJoin(qBook.product, qProduct)
                .rightJoin(qProductCategory.product, qProduct)
                .select(Projections.constructor(ProductDetailsResponseDto.class,
                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                    qProduct.detailsDescription, qProduct.isSelled, qProduct.isDeleted,
                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                    qBook.ebookUrl, qBook.publisherName, qBook.authorName))
                .where(qProductCategory.category.categoryNo.eq(categoryNo));

        return productList.fetch();
    }

    @Override
    public List<CategoryDetailsResponseDto> getCategoryListWithProductNo(Long productNo) {
        QProductCategory qProductCategory = QProductCategory.productCategory;
        QCategory qCategory = QCategory.category;

        JPQLQuery<CategoryDetailsResponseDto> categoryList =
            from(qProductCategory)
                .innerJoin(qProductCategory.category, qCategory)
                .select(Projections.constructor(CategoryDetailsResponseDto.class,
                    qCategory.categoryNo, qCategory.categoryName, qCategory.isHidden,
                    qCategory.level, qCategory.parentCategory.categoryNo,
                    qCategory.parentCategory.categoryName,
                    qCategory.parentCategory.isHidden, qCategory.parentCategory.level))
                .where(qProductCategory.product.productNo.eq(productNo));

        return categoryList.fetch();
    }
}
