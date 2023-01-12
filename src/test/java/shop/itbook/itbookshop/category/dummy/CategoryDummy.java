package shop.itbook.itbookshop.category.dummy;

import shop.itbook.itbookshop.category.entity.Category;

public class CategoryDummy {

    public static Category getCategoryHiddenBook() {

        return Category.builder()
            .categoryName("도서")
            .isHidden(true)
            .level(0)
            .sequence(1)
            .build();
    }

    public static Category getCategoryNoHiddenBook() {

        return Category.builder()
            .categoryName("도서")
            .isHidden(false)
            .level(0)
            .sequence(1)
            .build();
    }

    public static Category getCategoryHiddenStuff() {

        return Category.builder()
            .categoryName("잡화")
            .isHidden(true)
            .level(0)
            .sequence(1)
            .build();
    }

    public static Category getCategoryNoHiddenStuff() {

        return Category.builder()
            .categoryName("잡화")
            .isHidden(false)
            .level(0)
            .sequence(1)
            .build();
    }


}
