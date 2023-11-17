package christmas.domain;

import christmas.domain.menu.Menu;
import christmas.domain.menu.MenuType;
import christmas.global.message.ErrorMessage;
import java.util.NoSuchElementException;

public class OrderMenu {
    private final Menu menu;
    private final int amount;

    public OrderMenu(String name, int amount) {
        validate(name, amount);
        this.menu = Menu.findByName(name);
        this.amount = amount;
    }

    private void validate(String name, int amount) {
        validateMenuName(name);
        validateIsAmountZero(amount);
    }

    private void validateIsAmountZero(int amount) {
        if (amount == 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER.getText());
        }
    }

    private void validateMenuName(String name) {
        if (!Menu.isExistMenu(name)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER.getText());
        }
    }

    public int getAmount() {
        return amount;
    }

    public Menu getMenu() {
        return menu;
    }

    public MenuType getMenuType() {
        return menu.getType();
    }

    public int getTotalPrice() {
        return menu.getPrice() * amount;
    }

    public String getMenuName() {
        return menu.name();
    }
}
