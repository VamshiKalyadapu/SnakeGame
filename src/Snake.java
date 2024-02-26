import java.util.ArrayList;

public class Snake {
    ArrayList<Game.Tile> body;
    Game game; // Reference to the Game instance

    Snake(Game game) {
        this.game = game;
        body = new ArrayList<>();
        body.add(new Game.Tile(5, 5)); // Initial position of the snake head
    }

    void move(int velocityX, int velocityY) {
        for (int i = body.size() - 1; i > 0; i--) {
            Game.Tile currentBodyPart = body.get(i);
            Game.Tile previousBodyPart = body.get(i - 1);
            currentBodyPart.x = previousBodyPart.x;
            currentBodyPart.y = previousBodyPart.y;
        }

        // Update the head separately
        Game.Tile head = body.get(0);
        head.x += velocityX;
        head.y += velocityY;
    }

    void grow() {
        // Add a new body part at the current head position
        Game.Tile head = body.get(0);
        body.add(0, new Game.Tile(head.x, head.y));
    }

    boolean checkCollision() {
        Game.Tile head = body.get(0);

        // Check for collision with the body
        for (int i = 1; i < body.size(); i++) {
            if (collision(head, body.get(i))) {
                return true;
            }
        }

        // Check for collision with the boundaries
        return head.x < 0 || head.x >= game.boardWidth / game.tileSize ||
                head.y < 0 || head.y >= game.boardHeight / game.tileSize;
    }

    private boolean collision(Game.Tile tile1, Game.Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }
}
