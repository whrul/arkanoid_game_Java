package arkanoid.views;

import arkanoid.controllers.MainController;
import arkanoid.models.Ball;
import arkanoid.models.Brick;
import arkanoid.models.GameBonus;
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

    private JFrame jFrame;
    private JLabel scores;
    private JLabel lives;

    public SwingView(int width, int height, MainController mainController) {
        super();

        this.width = width;
        this.height = height;
        this.mainController = mainController;

        this.jFrame = new JFrame("Arkanoid");
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.setSize(this.width, this.height);
        this.jFrame.setResizable(false);
        this.jFrame.setVisible(true);

        this.scores = new JLabel();
        this.scores.setFont(new Font("Arial", Font.BOLD, 20));

        this.lives= new JLabel();
        this.lives.setFont(new Font("Arial", Font.BOLD, 20));

        this.add(this.scores);
        this.add(this.lives);

        this.jFrame.add(this);

        this.setFocusable(true);
        this.addKeyListener(this);
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


        Toolkit.getDefaultToolkit().sync();
    }

    private void drawScene(Graphics graphics) {
        fillBackground(graphics);
        drawBall(graphics);
        drawPlayer(graphics);
        drawGameBonuses(graphics);
        drawBricks(graphics);
        updateScores();
        updateLives();
    }

    private void drawGameBonuses(Graphics graphics) {
        Vector<GameBonus> gameBonuses = this.mainController.getGameBonuses();
        graphics.setColor(Color.orange);
        for (GameBonus gameBonus : gameBonuses) {
            graphics.fillRect(gameBonus.getPosX(), gameBonus.getPosY(), gameBonus.getWidth(), gameBonus.getHeight());
        }
    }

    private void updateScores() {
        this.scores.setText("Scores: " + this.mainController.getScores());
        this.scores.setBounds(5, 5, 250, 30);
    }
    private void updateLives() {
        this.lives.setText("Lives: " + this.mainController.getLives());
        this.lives.setBounds(5, 45, 250, 30);
    }


    private void fillBackground(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, this.width, this.height);
    }

    private void drawBall(Graphics graphics) {
        graphics.setColor(Color.yellow);
        Vector<Ball> balls = this.mainController.getBalls();
        for (Ball ball : balls) {
            graphics.fillOval(ball.getPosX(), ball.getPosY(), ball.getDiameter(), ball.getDiameter());
        }
    }

    private void drawBricks(Graphics graphics) {
        Vector<Brick> bricks = this.mainController.getBricks();
        for (Brick brick : bricks) {
            if (brick.getHitsForDestroying() > 1) {
                graphics.setColor(Color.green);
            } else {
                graphics.setColor(Color.red);
            }
            graphics.fillRect(brick.getPosX(), brick.getPosY(), brick.getWidth(), brick.getHeight());
        }

    }

    private void drawPlayer(Graphics graphics) {
        graphics.setColor(Color.red);
        Player player = this.mainController.getPlayer();
        graphics.fillRoundRect(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), player.getHeight(), player.getHeight());
    }

}
