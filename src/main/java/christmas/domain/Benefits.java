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

    public boolean isDdayDiscountApplied() {
        return discount.getDdayDiscountPrice() > 0;
    }

    public boolean isDayOfWeekDiscountApplied() {
        return discount.getDayOfWeekDiscountPrice() > 0;
    }

    public boolean isSpecialDiscountApplied() {
        return discount.getSpecialDiscountPrice() > 0;
    }

    public int getDdayDiscountPrice() {
        return discount.getDdayDiscountPrice();
    }

    public int getDayOfWeekDiscountPrice() {
        return discount.getDayOfWeekDiscountPrice();
    }

    public int getSpecialDiscountPrice() {
        return discount.getSpecialDiscountPrice();
    }

    public int getGiftMenuPrice() {
        if (giftMenu.isPresent()) {
            return giftMenu.get().getTotalPrice();
        }
        return 0;
    }

    public boolean isBeneficiary() {
        return (isExistGiftMenu() || isDiscountApplied());
    }

    public boolean isExistGiftMenu() {
        return giftMenu.isPresent();
    }

    private boolean isDiscountApplied() {
        return discount.getTotalDiscountPrice() != 0;
    }
}
