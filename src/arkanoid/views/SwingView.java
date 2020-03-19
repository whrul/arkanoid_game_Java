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
        super();
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
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.drawScene(graphics);
    }

    public void drawScene(Graphics graphics) {
        fillBackground(graphics);
        drawBall(graphics);
        drawPlayer(graphics);
        drawBricks(graphics);
    }

    public void fillBackground(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, this.width, this.height);
    }

    public void drawBall(Graphics graphics) {
        graphics.setColor(Color.yellow);
        Vector<Ball> balls = this.mainController.getBalls();
        for (Ball ball : balls) {
            graphics.fillOval(ball.getPosX(), ball.getPosY(), ball.getRadius(), ball.getRadius());
        }
    }

    public void drawBricks(Graphics graphics) {
        graphics.setColor(Color.black);
        Vector<Brick> bricks = this.mainController.getBricks();
        for (Brick brick : bricks) {
            graphics.fillRect(brick.getPosX(), brick.getPosY(), brick.getWidth(), brick.getHeight());
        }

    }

    public void drawPlayer(Graphics graphics) {
        graphics.setColor(Color.red);
        Player player = this.mainController.getPlayer();
        graphics.fillRect(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());
    }

}
