package christmas.domain;

import christmas.global.message.ErrorMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class OrderMenusTest {

    @ParameterizedTest
    @MethodSource("invalidOrderMenuAmountParameters")
    void 총메뉴_주문_개수가_20개보다_많은_경우_예외발생(int amount1, int amount2, int amount3) {
        List<OrderMenu> orderMenus = List.of(new OrderMenu(Menu.초코케이크.name(), amount1), new OrderMenu(Menu.바비큐립.name(), amount2),
                new OrderMenu(Menu.해산물파스타.name(), amount3));

        Assertions.assertThatThrownBy(() -> new OrderMenus(orderMenus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.INVALID_ORDER.getText());
    }
    static Stream<Arguments> invalidOrderMenuAmountParameters() {
        return Stream.of(
                Arguments.of(10, 10, 1),
                Arguments.of(10, 30, 1),
                Arguments.of(10, 30, 40)
        );
    }

    @ParameterizedTest
    @MethodSource("duplicationMenuParameters")
    void 중복_메뉴_입력시_예외발생(String menuName1, String menuName2, String menuName3) {
        List<OrderMenu> orderMenus = List.of(new OrderMenu(menuName1, 1), new OrderMenu(menuName2, 1), new OrderMenu(menuName3, 1));

        Assertions.assertThatThrownBy(() -> new OrderMenus(orderMenus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.INVALID_ORDER.getText());
    }

    static Stream<Arguments> duplicationMenuParameters() {
        return Stream.of(
                Arguments.of(Menu.초코케이크.name(), Menu.초코케이크.name(), Menu.바비큐립.name()),
                Arguments.of(Menu.해산물파스타.name(), Menu.해산물파스타.name(), Menu.바비큐립.name()),
                Arguments.of(Menu.제로콜라.name(), Menu.제로콜라.name(), Menu.제로콜라.name())
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"제로콜라", "샴페인", "레드와인"})
    void 음료만_한개_주문시_예외_발생(String drinkMenu) {
        List<OrderMenu> orderMenus = List.of(new OrderMenu(drinkMenu, 1));

        Assertions.assertThatThrownBy(() -> new OrderMenus(orderMenus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.INVALID_ORDER.getText());
    }

    @ParameterizedTest
    @ValueSource(strings = {"제로콜라,샴페인", "샴페인,레드와인", "레드와인,제로콜라,샴페인"})
    void 음료만_여러개_주문시_예외_발생(String drinkMenus) {
        List<OrderMenu> orderMenus = new ArrayList<>();
        for (String drinkMenu : drinkMenus.split(",")) {
            orderMenus.add(new OrderMenu(drinkMenu, 1));
        }

        Assertions.assertThatThrownBy(() -> new OrderMenus(orderMenus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.INVALID_ORDER.getText());
    }

    @ParameterizedTest
    @MethodSource("validOrderMenuAmountParameters")
    void 메뉴_개수가_1개_이상_20개_이하인_경우_정상적으로_객체_생성(int amount1, int amount2, int amount3) {
        List<OrderMenu> orderMenus = List.of(new OrderMenu(Menu.초코케이크.name(), amount1), new OrderMenu(Menu.바비큐립.name(), amount2),
                new OrderMenu(Menu.해산물파스타.name(), amount3));

        new OrderMenus(orderMenus);
    }

    static Stream<Arguments> validOrderMenuAmountParameters() {
        return Stream.of(
                Arguments.of(10, 9, 1),
                Arguments.of(10, 4, 1),
                Arguments.of(1, 1, 1)
        );
    }
}
