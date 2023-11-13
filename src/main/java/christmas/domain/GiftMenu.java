package christmas.domain;

import java.util.Optional;

public class GiftMenu extends OrderMenu{
    private static final int GIFT_STANDARD_PRICE = 120_000;
    private static final Menu GIFT_MENU = Menu.샴페인;
    private static final int GIFT_AMOUNT = 1;

    public GiftMenu(String name, int amount) {
        super(name, amount);
    }

    public static Optional<GiftMenu> receive(int totalPrice) {
        Optional<GiftMenu> giftMenu = Optional.empty();
        if (totalPrice > GIFT_STANDARD_PRICE) {
            giftMenu = Optional.of(new GiftMenu(GIFT_MENU.name(), GIFT_AMOUNT));
        }

        return giftMenu;
    }
}
