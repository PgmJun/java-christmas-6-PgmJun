package christmas.view.converter;

import christmas.domain.Menu;
import christmas.domain.OrderMenu;
import christmas.domain.OrderMenus;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChristmasEventMessageConverterTest {

    @ParameterizedTest
    @MethodSource("validOrderMenuMessageParameters")
    void 입력받은_메뉴_순서대로_주문_메뉴_안내_메시지_생성(int amount1, int amount2, int amount3, String expectedValue) {
        ChristmasEventMessageConverter messageConverter = new ChristmasEventMessageConverter();
        List<OrderMenu> orderMenus = List.of(new OrderMenu(Menu.초코케이크.name(), amount1), new OrderMenu(Menu.바비큐립.name(), amount2),
                new OrderMenu(Menu.해산물파스타.name(), amount3));

        String actualValue = messageConverter.convertOrderMenuInfoMessage(new OrderMenus(orderMenus));
        Assertions.assertEquals(expectedValue, actualValue);
    }

    static Stream<Arguments> validOrderMenuMessageParameters() {
        return Stream.of(
                Arguments.of(10, 9, 1, "<주문 메뉴>\n초코케이크 10개\n바비큐립 9개\n해산물파스타 1개\n"),
                Arguments.of(10, 4, 1, "<주문 메뉴>\n초코케이크 10개\n바비큐립 4개\n해산물파스타 1개\n"),
                Arguments.of(1, 1, 1, "<주문 메뉴>\n초코케이크 1개\n바비큐립 1개\n해산물파스타 1개\n")
        );
    }

    @ParameterizedTest
    @MethodSource("validTotalMenuPriceBeforeDiscountParameters")
    void 입력받은_메뉴_금액에_맞는_할인_전_총주문_금액_안내_메시지_생성(int amount1, int amount2, int amount3, String expectedValue) {
        ChristmasEventMessageConverter messageConverter = new ChristmasEventMessageConverter();
        List<OrderMenu> orderMenus = List.of(new OrderMenu(Menu.초코케이크.name(), amount1), new OrderMenu(Menu.바비큐립.name(), amount2),
                new OrderMenu(Menu.해산물파스타.name(), amount3));

        String actualValue = messageConverter.convertTotalPriceBeforeDiscountInfoMessage(new OrderMenus(orderMenus).calculateTotalPrice());
        Assertions.assertEquals(expectedValue, actualValue);
    }

    static Stream<Arguments> validTotalMenuPriceBeforeDiscountParameters() {
        return Stream.of(
                Arguments.of(1, 1, 1, "<할인 전 총주문 금액>\n104,000원\n"),
                Arguments.of(2, 1, 1, "<할인 전 총주문 금액>\n119,000원\n"),
                Arguments.of(2, 1, 2, "<할인 전 총주문 금액>\n154,000원\n")
        );
    }
}
