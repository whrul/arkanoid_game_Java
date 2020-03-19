package arkanoid;

import arkanoid.controllers.MainController;
import arkanoid.views.SwingView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingView swingView = new SwingView();
        MainController mainController = new MainController(swingView);
        swingView.setController(mainController);

        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(swingView.getWidth(), swingView.getHeight());
        jf.setTitle("Arkanoid");
        jf.setResizable(false);
        jf.setVisible(true);
        jf.add(swingView);
    }
}
