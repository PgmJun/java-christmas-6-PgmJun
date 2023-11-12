package christmas.domain;

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
        if(amount == 0) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    private void validateMenuName(String name) {
        if(!Menu.isExistMenu(name)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    public int getAmount() {
        return amount;
    }

    public Menu getMenu() {
        return menu;
    }

    public String getMenuName() {
        return menu.name();
    }
}
