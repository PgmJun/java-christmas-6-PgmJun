package christmas.domain;

import christmas.domain.menu.Menu;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BenefitsTest {

    @ParameterizedTest
    @MethodSource("benefitsParameters")
    void 증정_상품과_할인_금액을_토대로_총_혜택_금액을_계산한다(int date, Menu menu, int amount, int expectedValue) {
        ReservationDate reservationDate = new ReservationDate(date);
        OrderMenus orderMenus = new OrderMenus(List.of(new OrderMenu(menu.name(), amount)));

        Discount discount = Discount.calculateFrom(orderMenus, reservationDate);
        Optional<GiftMenu> giftMenu = GiftMenu.receive(orderMenus.calculateTotalPrice());

        Benefits benefits = new Benefits(discount, giftMenu);

        Assertions.assertEquals(expectedValue, benefits.getTotalBenefitPrice());
    }

    static Stream<Arguments> benefitsParameters() {
        return Stream.of(
                Arguments.of(13, Menu.시저샐러드, 1, 0),
                Arguments.of(13, Menu.바비큐립, 10, 27200),
                Arguments.of(25, Menu.초코케이크, 10, 49630),
                Arguments.of(23, Menu.해산물파스타, 10, 48430)
        );
    }

    @ParameterizedTest
    @MethodSource("calculateGiftMenuParameters")
    void 증정_상품_가격을_계산한다(int date, Menu menu, int amount, int giftMenuPrice) {
        ReservationDate reservationDate = new ReservationDate(date);
        OrderMenus orderMenus = new OrderMenus(List.of(new OrderMenu(menu.name(), amount)));

        Discount discount = Discount.calculateFrom(orderMenus, reservationDate);
        Optional<GiftMenu> giftMenu = GiftMenu.receive(orderMenus.calculateTotalPrice());

        Benefits benefits = new Benefits(discount, giftMenu);

        Assertions.assertEquals(giftMenuPrice, benefits.getGiftMenuPrice());
    }

    static Stream<Arguments> calculateGiftMenuParameters() {
        return Stream.of(
                Arguments.of(13, Menu.시저샐러드, 1, 0),
                Arguments.of(13, Menu.바비큐립, 10, Menu.샴페인.getPrice()),
                Arguments.of(25, Menu.초코케이크, 10, Menu.샴페인.getPrice()),
                Arguments.of(23, Menu.해산물파스타, 10, Menu.샴페인.getPrice())
        );
    }

    @ParameterizedTest
    @MethodSource("validBeneficiaryParameters")
    void 적용되는_혜택이_1개라도_존재하는지_확인한다(int date, Menu menu, int amount, boolean isBeneficiary) {
        ReservationDate reservationDate = new ReservationDate(date);
        OrderMenus orderMenus = new OrderMenus(List.of(new OrderMenu(menu.name(), amount)));

        Discount discount = Discount.calculateFrom(orderMenus, reservationDate);
        Optional<GiftMenu> giftMenu = GiftMenu.receive(orderMenus.calculateTotalPrice());

        Benefits benefits = new Benefits(discount, giftMenu);

        Assertions.assertEquals(isBeneficiary, benefits.isBeneficiary());
    }

    static Stream<Arguments> validBeneficiaryParameters() {
        return Stream.of(
                Arguments.of(13, Menu.시저샐러드, 1, false),
                Arguments.of(13, Menu.바비큐립, 10, true),
                Arguments.of(25, Menu.초코케이크, 10, true),
                Arguments.of(23, Menu.해산물파스타, 10, true)
        );
    }

    @ParameterizedTest
    @MethodSource("validExistGiftMenuParameters")
    void 증정_상품이_존재하는지_확인한다(int date, Menu menu, int amount, boolean isExistGiftMenu) {
        ReservationDate reservationDate = new ReservationDate(date);
        OrderMenus orderMenus = new OrderMenus(List.of(new OrderMenu(menu.name(), amount)));

        Discount discount = Discount.calculateFrom(orderMenus, reservationDate);
        Optional<GiftMenu> giftMenu = GiftMenu.receive(orderMenus.calculateTotalPrice());

        Benefits benefits = new Benefits(discount, giftMenu);

        Assertions.assertEquals(isExistGiftMenu, benefits.isExistGiftMenu());
    }

    static Stream<Arguments> validExistGiftMenuParameters() {
        return Stream.of(
                Arguments.of(13, Menu.시저샐러드, 1, false),
                Arguments.of(13, Menu.바비큐립, 10, true),
                Arguments.of(25, Menu.초코케이크, 10, true),
                Arguments.of(23, Menu.해산물파스타, 2, false)
        );
    }
}
