package christmas.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DiscountTest {

    @Test
    void 입력된_주문_메뉴의_가격이_10000원을_넘지_않으면_할인받지_못한다() {
        ReservationDate reservationDate = new ReservationDate(25);
        OrderMenus orderMenus = new OrderMenus(List.of(new OrderMenu(Menu.양송이수프.name(), 1)));

        Discount discount = Discount.calculateFrom(orderMenus, reservationDate);

        Assertions.assertEquals(0, discount.getTotalDiscountPrice());
    }

    @Test
    void 입력된_주문_메뉴의_가격이_10000원을_넘고_1일에서_25일_사이면_디데이할인이_적용된다() {
        ReservationDate reservationDate = new ReservationDate(13);
        OrderMenus orderMenus = new OrderMenus(List.of(new OrderMenu(Menu.양송이수프.name(), 2)));

        Discount discount = Discount.calculateFrom(orderMenus, reservationDate);

        int expectedValue = 2200;
        int actualValue = discount.getDdayDiscountPrice();
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @ParameterizedTest
    @MethodSource("dayOfWeekDiscountParameters")
    void 입력된_주문_메뉴의_가격이_10000원을_넘고_예약알이_평일이면_디저트_개수_당_2023원이_할인된다(int date, int amount, int expectedValue) {
        ReservationDate reservationDate = new ReservationDate(date);
        OrderMenus orderMenus = new OrderMenus(List.of(new OrderMenu(Menu.초코케이크.name(), amount)));

        Discount discount = Discount.calculateFrom(orderMenus, reservationDate);

        int actualValue = discount.getDayOfWeekDiscountPrice();
        Assertions.assertEquals(expectedValue, actualValue);
    }
    static Stream<Arguments> dayOfWeekDiscountParameters() {
        return Stream.of(
                Arguments.of(4, 1, 2023),
                Arguments.of(11, 2, 4046),
                Arguments.of(18, 5, 10115)
        );
    }

    @Test
    void 입력된_주문_메뉴의_가격이_10000원을_넘고_특별일에_해당하면_1000원이_할인된다() {
        ReservationDate reservationDate = new ReservationDate(3);
        OrderMenus orderMenus = new OrderMenus(List.of(new OrderMenu(Menu.초코케이크.name(), 1)));

        Discount discount = Discount.calculateFrom(orderMenus, reservationDate);

        int expectedValue = 1000;
        int actualValue = discount.getSpecialDiscountPrice();
        Assertions.assertEquals(expectedValue, actualValue);
    }
}
