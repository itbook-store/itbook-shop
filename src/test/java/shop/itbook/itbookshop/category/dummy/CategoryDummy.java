package shop.itbook.itbookshop.category.dummy;

import shop.itbook.itbookshop.category.entity.Category;

public class CategoryDummy {

    public static Category getCategoryHiddenBook() {
        Category category = new Category("도서", true);
        return category;
    }

    public static Category getCategoryNoHiddenBook() {
        Category category = new Category("도서", false);
        return category;
    }

    public static Category getCategoryHiddenStuff() {
        Category category = new Category("잡화", true);
        return category;
    }

    public static Category getCategoryNoHiddenStuff() {
        Category category = new Category("잡화", false);
        return category;
    }


}
