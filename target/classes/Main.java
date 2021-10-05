import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import maze.model.Maze;
import maze.view.MazeDisplay;

/**
 * This class launches the Maze solver simulation.
 *
 * @author Robert C. Duvall
 */
public class Main extends Application {
    public static final String TITLE = "A-MAZE-ING";
    public static final Paint BACKGROUND = Color.THISTLE;
    // size of maze in visitable spaces, including wall around edges
    public static final int NUM_ROWS = 31;
    public static final int NUM_COLUMNS = 41;

    /**
     * Organize display of game in a scene and start the game.
     */
    @Override
    public void start (Stage stage) {
        Maze maze = new Maze(NUM_ROWS, NUM_COLUMNS);
        MazeDisplay view = new MazeDisplay(maze);
        stage.setScene(view.setupDisplay(BACKGROUND));
        stage.setTitle(TITLE);
        stage.show();
    }
}
