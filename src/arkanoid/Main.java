package arkanoid;

import arkanoid.controllers.MainController;
import arkanoid.views.SwingView;

public class Main {
    public static void main(String[] args) {
        SwingView swingView = new SwingView(1200, 600);
        MainController mainController = new MainController(swingView);
        swingView.setController(mainController);
    }
}
