package christmas;

import christmas.domain.ReservationDate;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {
    static InputView inputView = new InputView();
    static OutputView outputView = new OutputView();

    public static void main(String[] args) {
        reserveVisitDate();
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
}
