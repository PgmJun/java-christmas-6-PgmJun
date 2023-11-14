package christmas.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BadgeTest {

    @ParameterizedTest
    @MethodSource("validBadgeIssueParameters")
    void 금액에_따라_올바른_뱃지를_리턴한다(int benefitPrice, Badge expectedValue) {
        Badge actualValue = Badge.issue(benefitPrice);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    static Stream<Arguments> validBadgeIssueParameters() {
        return Stream.of(
                Arguments.of(-3000, Badge.NONE),
                Arguments.of(0, Badge.NONE),
                Arguments.of(4000, Badge.NONE),
                Arguments.of(7000, Badge.STAR),
                Arguments.of(11000, Badge.TREE),
                Arguments.of(23000, Badge.SANTA)
        );
    }

}
