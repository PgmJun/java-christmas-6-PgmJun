package christmas;

import christmas.domain.OrderMenu;
import christmas.domain.OrderMenus;
import christmas.domain.ReservationDate;
import christmas.view.InputView;
import christmas.view.OutputView;
import christmas.view.converter.ChristmasEventMessageConverter;
import christmas.view.dto.OrderMenuDto;
import java.util.List;

public class Application {
    static InputView inputView = new InputView();
    static OutputView outputView = new OutputView();
    static ChristmasEventMessageConverter messageConverter = new ChristmasEventMessageConverter();

    public static void main(String[] args) {
        reserveVisitDate();
        OrderMenus orderMenus = orderMenus();
        printOrderResult(orderMenus);
    }

    private static void printOrderResult(OrderMenus orderMenus) {
        outputView.println("12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n");
        printOrderMenus(orderMenus);
        printTotalPriceBeforeDiscount(orderMenus);
    }

    private static void printOrderMenus(OrderMenus orderMenus) {
        outputView.println(messageConverter.convertOrderMenuMessage(orderMenus));
    }

    private static void printTotalPriceBeforeDiscount(OrderMenus orderMenus) {
        outputView.println(messageConverter.convertTotalPriceBeforeDiscountInfoMessage(orderMenus.calculateTotalPrice()));
    }

    private static ReservationDate reserveVisitDate() {
        while (true) {
            try {
                int date = inputView.readReservationDate();
                return new ReservationDate(date);
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception);
            }
        }
    }

    private static OrderMenus orderMenus() {
        while(true) {
            try {
                List<OrderMenuDto> orderMenuDtos = inputView.readOrderMenus();
                List<OrderMenu> orderMenus = orderMenuDtos.stream()
                        .map(OrderMenuDto::toOrderMenu)
                        .toList();
                return new OrderMenus(orderMenus);
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception);
            }
        }
    }
}
