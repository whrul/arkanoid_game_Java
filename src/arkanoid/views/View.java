package arkanoid.views;

import arkanoid.controllers.MainController;

public interface View {
    void setController(MainController mainController);
    void updateView();
    int getWidth();
    int getHeight();
}
