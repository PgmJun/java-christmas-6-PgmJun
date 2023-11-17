package christmas.view.dto;

import christmas.domain.OrderMenu;

public class OrderMenuDto {
    private final String menuName;
    private final int amount;

    public OrderMenuDto(String menuName, int amount) {
        this.menuName = menuName;
        this.amount = amount;
    }

    public OrderMenu toOrderMenu() {
        return new OrderMenu(menuName, amount);
    }
}
