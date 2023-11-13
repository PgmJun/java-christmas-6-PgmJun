package christmas.domain;

import java.util.Optional;

public class Benefits {
    private final Discount discount;
    private final Optional<GiftMenu> giftMenu;

    public Benefits(Discount discount, Optional<GiftMenu> giftMenu) {
        this.discount = discount;
        this.giftMenu = giftMenu;
    }

    public int getTotalBenefitPrice() {
        int totalDiscountPrice = discount.getTotalDiscountPrice();
        if (giftMenu.isPresent()) {
            totalDiscountPrice += giftMenu.get().getTotalPrice();
        }

        return totalDiscountPrice;
    }

    public int getTotalDiscountPrice() {
        return discount.getTotalDiscountPrice();
    }
}
