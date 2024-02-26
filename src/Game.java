import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Game extends JPanel implements ActionListener, KeyListener {

    static class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize;
    Random random;
    int velocityX;
    int velocityY;
    int score;

    Snake snake;
    Food food;
    Timer gameLoop;

    boolean gameOver = false;

    Game(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        tileSize = 25;
        score = 0;

        velocityX = 0;
        velocityY = 0;

        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);

        addKeyListener(this);
        setFocusable(true);

        snake = new Snake(this);
        food = new Food();
        random = new Random();

        gameLoop = new Timer(200, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Draw Food
        g.setColor(Color.red);
        g.fill3DRect(food.position.x * tileSize, food.position.y * tileSize, tileSize, tileSize, true);

        // Draw Snake
        g.setColor(Color.green);
        for (Tile bodyPart : snake.body) {
            g.fill3DRect(bodyPart.x * tileSize, bodyPart.y * tileSize, tileSize, tileSize, true);
        }

        // Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over! Score: " + String.valueOf(score), tileSize - 16, tileSize);
            g.drawString("Press 'R' to restart", tileSize - 16, tileSize + 20);
        } else {
            g.drawString("Score: " + String.valueOf(score), tileSize - 16, tileSize);
            
        }

    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        if (collision(snake.body.get(0), food.position)) {
            snake.grow();
            food.placeFood(this);
            score++;
        }

        snake.move(velocityX, velocityY);

        if (snake.checkCollision()) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            restartGame();
        }
    }

    private void restartGame() {
        snake.reset();
        food.placeFood(this);
        score = 0;
        gameOver = false;
        gameLoop.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
