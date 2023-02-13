package shop.itbook.itbookshop.productgroup.product.service.salesstatus;

/**
 * @author 이하늬
 * @since 1.0
 */
public enum ProductSortingCriteriaEnum {

    COMPLETED("완료건"),
    CANCELED("취소건"),
    TOTAL_SALES("매출합계"),
    SALES_AMOUNT("판매금액");

    private String message;

    ProductSortingCriteriaEnum(String message) {
        this.message = message;
    }

    public static ProductSortingCriteriaEnum fromString(String string) {
        switch (string) {
            case "완료건":
                return COMPLETED;
            case "취소건":
                return CANCELED;
            case "매출합계":
                return TOTAL_SALES;
            case "판매금액":
                return SALES_AMOUNT;
        }
        return null;
    }
}
