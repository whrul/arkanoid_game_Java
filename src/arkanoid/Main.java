// Author: Walerij Hrul
//
package arkanoid;

import arkanoid.controllers.MainController;
import arkanoid.views.SwingView;

public class Main {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {
            MainController mainController = new MainController();
            SwingView swingView = new SwingView(1200, 600, mainController);
            mainController.setView(swingView);
        });

    }
}
