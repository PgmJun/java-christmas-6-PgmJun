package christmas.domain;

import christmas.domain.menu.Menu;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DiscountTest {

    @ParameterizedTest
    @MethodSource("noneDiscountParameters")
    void 입력된_주문_메뉴의_가격이_10000원을_넘지_않으면_할인받지_못한다(Menu menu, int amount) {
        ReservationDate reservationDate = new ReservationDate(25);
        OrderMenus orderMenus = new OrderMenus(List.of(new OrderMenu(menu.name(), amount)));

        Discount discount = Discount.calculateFrom(orderMenus, reservationDate);

        Assertions.assertEquals(0, discount.getTotalDiscountPrice());
    }

    static Stream<Arguments> noneDiscountParameters() {
        return Stream.of(
                Arguments.of(Menu.양송이수프, 1),
                Arguments.of(Menu.시저샐러드, 1),
                Arguments.of(Menu.아이스크림, 1),
                Arguments.of(Menu.타파스, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("dDayDiscountParameters")
    void 입력된_주문_메뉴의_가격이_10000원을_넘고_1일에서_25일_사이면_디데이할인이_적용된다(int date, int dDayDiscountPrice) {
        ReservationDate reservationDate = new ReservationDate(date);
        OrderMenus orderMenus = new OrderMenus(List.of(new OrderMenu(Menu.양송이수프.name(), 2)));

        Discount discount = Discount.calculateFrom(orderMenus, reservationDate);

        int actualValue = discount.getDdayDiscountPrice();
        Assertions.assertEquals(dDayDiscountPrice, actualValue);
    }

    static Stream<Arguments> dDayDiscountParameters() {
        return Stream.of(
                Arguments.of(1, 1000),
                Arguments.of(3, 1200),
                Arguments.of(10, 1900),
                Arguments.of(17, 2600),
                Arguments.of(24, 3300),
                Arguments.of(25, 3400)
        );
    }

    @ParameterizedTest
    @MethodSource("weekdayDiscountParameters")
    void 입력된_주문_메뉴의_가격이_10000원을_넘고_예약일이_평일이면_디저트_개수_당_2023원이_할인된다(Menu dessertMenu, int date, int amount,
                                                                  int expectedValue) {
        ReservationDate reservationDate = new ReservationDate(date);
        OrderMenus orderMenus = new OrderMenus(List.of(new OrderMenu(dessertMenu.name(), amount)));

        Discount discount = Discount.calculateFrom(orderMenus, reservationDate);

        int actualValue = discount.getDayOfWeekDiscountPrice();
        Assertions.assertEquals(expectedValue, actualValue);
    }

    static Stream<Arguments> weekdayDiscountParameters() {
        return Stream.of(
                Arguments.of(Menu.초코케이크, 4, 1, 2023),
                Arguments.of(Menu.아이스크림, 11, 2, 4046),
                Arguments.of(Menu.초코케이크, 11, 5, 10115),
                Arguments.of(Menu.아이스크림, 11, 5, 10115)
        );
    }

    @ParameterizedTest
    @MethodSource("weekendDiscountParameters")
    void 입력된_주문_메뉴의_가격이_10000원을_넘고_예약일이_주말이면_메인_메뉴_개수_당_2023원이_할인된다(Menu mainMenu, int date, int amount,
                                                                    int expectedValue) {
        ReservationDate reservationDate = new ReservationDate(date);
        OrderMenus orderMenus = new OrderMenus(List.of(new OrderMenu(mainMenu.name(), amount)));

        Discount discount = Discount.calculateFrom(orderMenus, reservationDate);
        int actualValue = discount.getDayOfWeekDiscountPrice();
        Assertions.assertEquals(expectedValue, actualValue);
    }

    static Stream<Arguments> weekendDiscountParameters() {
        return Stream.of(
                Arguments.of(Menu.바비큐립, 1, 1, 2023),
                Arguments.of(Menu.크리스마스파스타, 2, 2, 4046),
                Arguments.of(Menu.해산물파스타, 8, 5, 10115),
                Arguments.of(Menu.티본스테이크, 9, 5, 10115)
        );
    }

    @ParameterizedTest
    @MethodSource("specialDiscountParameters")
    void 입력된_주문_메뉴의_가격이_10000원을_넘고_특별일에_해당하면_1000원이_할인된다(int specialDate, Menu menu, int specialDiscountPrice) {
        ReservationDate reservationDate = new ReservationDate(specialDate);
        OrderMenus orderMenus = new OrderMenus(List.of(new OrderMenu(menu.name(), 1)));

        Discount discount = Discount.calculateFrom(orderMenus, reservationDate);

        int actualValue = discount.getSpecialDiscountPrice();
        Assertions.assertEquals(specialDiscountPrice, actualValue);
    }

    static Stream<Arguments> specialDiscountParameters() {
        return Stream.of(
                Arguments.of(3, Menu.바비큐립, 1000),
                Arguments.of(10, Menu.초코케이크, 1000),
                Arguments.of(17, Menu.해산물파스타, 1000),
                Arguments.of(24, Menu.티본스테이크, 1000),
                Arguments.of(25, Menu.티본스테이크, 1000),
                Arguments.of(31, Menu.티본스테이크, 1000)
        );
    }
}
