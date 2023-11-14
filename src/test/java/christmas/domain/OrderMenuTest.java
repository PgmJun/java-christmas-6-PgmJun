package christmas.domain;

import christmas.global.message.ErrorMessage;
import java.util.NoSuchElementException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class OrderMenuTest {

    @ParameterizedTest
    @ValueSource(strings = {"양성이수프", "콜리", "맛돌이", "타피스"})
    void 메뉴판에_존재하지_않는_메뉴인_경우_예외_발생(String menuName) {
        Assertions.assertThatThrownBy(() -> new OrderMenu(menuName, 1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining(ErrorMessage.INVALID_ORDER.getText());
    }

    @ParameterizedTest
    @ValueSource(strings = {"양송이수프", "제로콜라", "바비큐립", "타파스"})
    void 메뉴_개수가_0인_경우_예외_발생(String menuName) {
        Assertions.assertThatThrownBy(() -> new OrderMenu(menuName, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.INVALID_ORDER.getText());
    }
}
