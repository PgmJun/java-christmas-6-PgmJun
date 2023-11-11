package christmas.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class OrderMenuTest {

    @ParameterizedTest
    @ValueSource(strings = {"양성이수프", "콜리", "맛돌이", "타피스"})
    void 메뉴판에_존재하지_않는_메뉴인_경우_예외_발생(String menuName) {
        Assertions.assertThatThrownBy(() -> new OrderMenu(menuName, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"양성이수프", "콜리", "맛돌이", "타피스"})
    void 메뉴_개수가_0인_경우_예외_발생(String menuName) {
        Assertions.assertThatThrownBy(() -> new OrderMenu(menuName, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }
}
