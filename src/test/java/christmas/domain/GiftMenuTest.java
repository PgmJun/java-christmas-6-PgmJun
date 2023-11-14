package christmas.domain;

import christmas.domain.menu.Menu;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class GiftMenuTest {

    @ParameterizedTest
    @ValueSource(ints = {10000, 50000, 90000, 119999})
    void 할인_전_총_결제_금액이_120000원이_넘지_않으면_증정_메뉴를_제공하지_않는다(int totalPriceBeforeDiscount) {
        Optional<GiftMenu> giftMenu = GiftMenu.receive(totalPriceBeforeDiscount);

        Assertions.assertTrue(giftMenu.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {120000,200000,300000,1000000})
    void 할인_전_총_결제_금액이_120000원을_넘으면_증정_메뉴를_제공한다(int totalPriceBeforeDiscount) {
        Optional<GiftMenu> giftMenu = GiftMenu.receive(totalPriceBeforeDiscount);

        Assertions.assertTrue(giftMenu.isPresent());
    }

    @ParameterizedTest
    @ValueSource(ints = {120000,200000,300000,1000000})
    void 증정품은_샴페인을_1개_지급한다(int totalPriceBeforeDiscount) {
        Optional<GiftMenu> giftMenu = GiftMenu.receive(totalPriceBeforeDiscount);

        Assertions.assertEquals(Menu.샴페인, giftMenu.get().getMenu());
        Assertions.assertEquals(1, giftMenu.get().getAmount());
    }
}
