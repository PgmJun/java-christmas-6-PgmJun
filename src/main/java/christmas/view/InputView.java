package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.util.Validator;
import christmas.view.dto.OrderMenuDto;
import java.util.ArrayList;
import java.util.List;

public class InputView {
    private static final String ORDER_MENU_SEPARATOR = ",";
    private static final String MENU_NAME_AND_AMOUNT_SEPARATOR = "-";

    public int readReservationDate() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.\n12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
        String date = Console.readLine();
        Validator.validateReservationDateFormat(date);

        return Integer.parseInt(date);
    }

    public List<OrderMenuDto> readOrderMenus() {
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
        String orderMenus = Console.readLine();
        Validator.validateOrderMenusFormat(orderMenus);

        List<OrderMenuDto> orderMenuDtos = new ArrayList<>();
        for (String orderMenu : orderMenus.split(ORDER_MENU_SEPARATOR)) {
            String[] orderMenuValues = orderMenu.split(MENU_NAME_AND_AMOUNT_SEPARATOR);
            orderMenuDtos.add(new OrderMenuDto(orderMenuValues[0], Integer.parseInt(orderMenuValues[1])));
        }
        return orderMenuDtos;
    }
}
