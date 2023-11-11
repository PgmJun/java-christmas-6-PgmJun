package christmas;

import christmas.domain.OrderMenu;
import christmas.domain.OrderMenus;
import christmas.domain.ReservationDate;
import christmas.view.InputView;
import christmas.view.OutputView;
import christmas.view.dto.OrderMenuDto;
import java.util.List;

public class Application {
    static InputView inputView = new InputView();
    static OutputView outputView = new OutputView();

    public static void main(String[] args) {
        reserveVisitDate();
        orderMenus();
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
