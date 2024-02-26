public class Food {
    Game.Tile position;

    Food() {
        position = new Game.Tile(10, 10); // Initial position of the food
    }

    void placeFood(Game game) {
        position.x = game.random.nextInt(game.boardWidth / game.tileSize);
        position.y = game.random.nextInt(game.boardHeight / game.tileSize);
    }
}
