package christmas.domain.menu;

import java.util.Arrays;

public enum Menu {
    양송이수프(6_000, MenuType.APPETIZER), 타파스(5_500, MenuType.APPETIZER), 시저샐러드(8_000, MenuType.APPETIZER),
    티본스테이크(55_000, MenuType.MAIN), 바비큐립(54_000, MenuType.MAIN), 해산물파스타(35_000, MenuType.MAIN), 크리스마스파스타(25_000, MenuType.MAIN),
    초코케이크(15_000, MenuType.DESSERT), 아이스크림(5_000, MenuType.DESSERT),
    제로콜라(3_000, MenuType.DRINK), 레드와인(60_000, MenuType.DRINK), 샴페인(25_000, MenuType.DRINK)
    ;

    private final int price;
    private final MenuType type;

    Menu(int price, MenuType type) {
        this.price = price;
        this.type = type;
    }

    public MenuType getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public static boolean isExistMenu(String name) {
        return Arrays.stream(Menu.values())
                .anyMatch(menu -> menu.name().equals(name));
    }

    public static Menu findByName(String name) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.name().equals(name))
                .findFirst()
                .get();
    }
}
