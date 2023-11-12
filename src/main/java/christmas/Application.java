package christmas;

import christmas.domain.ChristmasEventProgram;
import christmas.view.InputView;
import christmas.view.OutputView;
import christmas.view.converter.ChristmasEventMessageConverter;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        new ChristmasEventProgram(inputView, outputView, new ChristmasEventMessageConverter()).run();
    }
}
