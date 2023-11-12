package christmas;

import christmas.domain.Menu;
import christmas.domain.OrderMenu;
import christmas.domain.OrderMenus;
import christmas.domain.ReservationDate;
import christmas.view.InputView;
import christmas.view.OutputView;
import christmas.view.converter.ChristmasEventMessageConverter;
import christmas.view.dto.OrderMenuDto;
import java.util.List;
import java.util.Optional;

public class Application {
    static InputView inputView = new InputView();
    static OutputView outputView = new OutputView();
    static ChristmasEventMessageConverter messageConverter = new ChristmasEventMessageConverter();

    public static void main(String[] args) {
        ReservationDate reservationDate = reserveVisitDate();
        OrderMenus orderMenus = orderMenus();
        printOrderResult(orderMenus, reservationDate);
    }

    private static void printOrderResult(OrderMenus orderMenus, ReservationDate reservationDate) {
        outputView.println("12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n");
        printOrderMenusInfo(orderMenus);
        printTotalPriceBeforeDiscountInfo(orderMenus);
        printGiftMenuInfo(receiveGiftMenuWhenOverStandardPrice(orderMenus, 120_000));
    }

    private static void printOrderMenusInfo(OrderMenus orderMenus) {
        outputView.println(messageConverter.convertOrderMenuInfoMessage(orderMenus));
    }

    private static void printTotalPriceBeforeDiscountInfo(OrderMenus orderMenus) {
        outputView.println(messageConverter.convertTotalPriceBeforeDiscountInfoMessage(orderMenus.calculateTotalPrice()));
    }

    private static void printGiftMenuInfo(Optional<OrderMenu> giftMenu) {
        outputView.println(messageConverter.convertGiftMenuInfoMessage(giftMenu));
    }

    private static Optional<OrderMenu> receiveGiftMenuWhenOverStandardPrice(OrderMenus orderMenus, int standardPrice) {
        Optional<OrderMenu> giftMenu = Optional.empty();
        if(orderMenus.calculateTotalPrice() > standardPrice) {
            giftMenu = Optional.of(new OrderMenu(Menu.샴페인.name(), 1));
            orderMenus.receiveGiftMenu(giftMenu.get());
        }
        return giftMenu;
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
