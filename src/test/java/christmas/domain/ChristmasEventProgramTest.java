package christmas.domain;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import christmas.Application;
import org.junit.jupiter.api.Test;

public class ChristmasEventProgramTest extends NsTest {

    @Test
    void 혜택_예제_실행시_텍스트_정상적으로_출력하는지_검증() {
        assertSimpleTest(() -> {
            run("3", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1");
            assertThat(output()).contains("""
                    12월 3일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!
                                                
                    <주문 메뉴>
                    티본스테이크 1개
                    바비큐립 1개
                    초코케이크 2개
                    제로콜라 1개
                                                
                    <할인 전 총주문 금액>
                    142,000원                
                                        
                    <증정 메뉴>
                    샴페인 1개

                    <혜택 내역>
                    크리스마스 디데이 할인: -1,200원
                    평일 할인 : -4,046원
                    특별 할인: -1,000원
                    증정 이벤트: -25,000원

                    <총혜택 금액>
                    -31,246원

                    <할인 후 예상 결제 금액>
                    135,754원

                    <12월 이벤트 배지>
                    산타"""
            );
        });
    }

    @Test
    void 미혜택_예제_실행시_텍스트_정상적으로_출력하는지_검증() {
        assertSimpleTest(() -> {
            run("26", "타파스-1,제로콜라-1");
            assertThat(output()).contains("""
                    12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!
                                                  
                    <주문 메뉴>
                    타파스 1개
                    제로콜라 1개
                                                  
                    <할인 전 총주문 금액>
                    8,500원
                                                  
                    <증정 메뉴>
                    없음
                                                  
                    <혜택 내역>
                    없음
                                                  
                    <총혜택 금액>
                    0원
                                                  
                    <할인 후 예상 결제 금액>
                    8,500원
                                                  
                    <12월 이벤트 배지>
                    없음"""
            );
        });
    }

    @Test
    void 혜택_및_배지_검증1() {
        assertSimpleTest(() -> {
            run("25", "해산물파스타-2,레드와인-1,초코케이크-2");
            assertThat(output()).contains("""
                    12월 25일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!
                                                
                    <주문 메뉴>
                    해산물파스타 2개
                    레드와인 1개
                    초코케이크 2개
                                               
                    <할인 전 총주문 금액>
                    160,000원                
                                        
                    <증정 메뉴>
                    샴페인 1개

                    <혜택 내역>
                    크리스마스 디데이 할인: -3,400원
                    평일 할인 : -4,046원
                    특별 할인: -1,000원
                    증정 이벤트: -25,000원
                                                
                    <총혜택 금액>
                    -33,446원

                    <할인 후 예상 결제 금액>
                    151,554원

                    <12월 이벤트 배지>
                    산타"""
            );
        });
    }

    @Test
    void 혜택_및_배지_검증2() {
        assertSimpleTest(() -> {
            run("23", "해산물파스타-2");
            assertThat(output()).contains("""
                    12월 23일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!
                                                
                    <주문 메뉴>
                    해산물파스타 2개
                                                
                    <할인 전 총주문 금액>
                    70,000원
                                                
                    <증정 메뉴>
                    없음
                                                
                    <혜택 내역>
                    크리스마스 디데이 할인: -3,200원
                    주말 할인 : -4,046원
                                                
                    <총혜택 금액>
                    -7,246원
                                                
                    <할인 후 예상 결제 금액>
                    62,754원
                                                
                    <12월 이벤트 배지>
                    별"""
            );
        });
    }

    @Test
    void 혜택_및_배지_검증3() {
        assertSimpleTest(() -> {
            run("23", "크리스마스파스타-4");
            assertThat(output()).contains("""
                    12월 23일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!
                                                
                    <주문 메뉴>
                    크리스마스파스타 4개
                                                
                    <할인 전 총주문 금액>
                    100,000원
                                                
                    <증정 메뉴>
                    없음
                                                
                    <혜택 내역>
                    크리스마스 디데이 할인: -3,200원
                    주말 할인 : -8,092원
                                                
                    <총혜택 금액>
                    -11,292원
                                                
                    <할인 후 예상 결제 금액>
                    88,708원
                                                
                    <12월 이벤트 배지>
                    트리"""
            );
        });
    }

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}
