package christmas;

import christmas.domain.ChristmasEventProgram;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {
    public static void main(String[] args) {
        ChristmasEventProgram christmasEventProgram = new ChristmasEventProgram(new InputView(), new OutputView());
        christmasEventProgram.run();
    }
}
