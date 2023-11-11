package christmas.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MenuTest {

    @ParameterizedTest
    @MethodSource("findByMenuNameParameters")
    void 메뉴_이름을_입력하면_이름이_일치하는_메뉴를_리턴한다(String menuName, Menu expectedValue) {
        Menu actualValue = Menu.findByName(menuName);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    static Stream<Arguments> findByMenuNameParameters() {
        return Stream.of(
                Arguments.of(Menu.초코케이크.name(), Menu.초코케이크),
                Arguments.of(Menu.해산물파스타.name(), Menu.해산물파스타),
                Arguments.of(Menu.제로콜라.name(), Menu.제로콜라)
        );
    }

    @ParameterizedTest
    @MethodSource("isExistMenuNameParameters")
    void 존재하는_메뉴_이름을_입력하면_true를_리턴한다(String menuName) {
        boolean actualValue = Menu.isExistMenu(menuName);

        Assertions.assertEquals(true, actualValue);
    }

    static Stream<Arguments> isExistMenuNameParameters() {
        return Stream.of(
                Arguments.of(Menu.초코케이크.name()),
                Arguments.of(Menu.해산물파스타.name()),
                Arguments.of(Menu.제로콜라.name())
        );
    }

    @ParameterizedTest
    @MethodSource("notExistMenuNameParameters")
    void 존재하지_않는_메뉴_이름을_입력하면_false를_리턴한다(String menuName) {
        boolean actualValue = Menu.isExistMenu(menuName);

        Assertions.assertEquals(false, actualValue);
    }

    static Stream<Arguments> notExistMenuNameParameters() {
        return Stream.of(
                Arguments.of("츄코케이크"),
                Arguments.of("해물파스타"),
                Arguments.of("제로사이다")
        );
    }
}
