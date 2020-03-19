package arkanoid.views;

import arkanoid.controllers.MainController;
import arkanoid.models.Ball;
import arkanoid.models.Brick;
import arkanoid.models.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class SwingView extends JPanel implements View, KeyListener {

    private MainController mainController;
    private int width;
    private int height;

    public SwingView() {
        this.setFocusable(true);
        this.addKeyListener(this);
        this.width = 1200;
        this.height = 600;
    }

    @Override
    public void setController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void updateView() {
        this.repaint();
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        this.mainController.keyTyped(keyEvent);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        this.mainController.keyPressed(keyEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        this.mainController.keyReleased(keyEvent);
    }

    @Override
    public void paintComponent(Graphics g) {
        fillBackground(g);
        drawBall(g);
        drawPlayer(g);
        drawBricks(g);
    }

    public void fillBackground(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, this.width, this.height);
    }

    public void drawBall(Graphics g) {
        g.setColor(Color.yellow);
        Vector<Ball> balls = this.mainController.getBalls();
        for (Ball ball : balls) {
            g.fillOval(ball.getPosX(), ball.getPosY(), ball.getRadius(), ball.getRadius());
        }
    }

    public void drawBricks(Graphics g) {
        g.setColor(Color.black);
        Vector<Brick> bricks = this.mainController.getBricks();
        for (Brick brick : bricks) {
            g.fillRect(brick.getPosX(), brick.getPosY(), brick.getWidth(), brick.getHeight());
        }

    }

    public void drawPlayer(Graphics g) {
        g.setColor(Color.red);
        Player player = this.mainController.getPlayer();
        g.fillRect(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());
    }

}
