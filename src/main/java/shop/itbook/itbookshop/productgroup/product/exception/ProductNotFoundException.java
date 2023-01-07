package shop.itbook.itbookshop.productgroup.product.exception;

/**
 * @author 이하늬
 * @since 1.0
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("no product of id : " + id);
    }
}
