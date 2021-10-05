package maze.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class represents a maze with a starting point in the top-left
 * corner and the goal in the bottom-right corner.
 *
 * The maze is created randomly and is guaranteed not to have any cycles
 * in the paths through the maze (which could lead to infinite loops for an
 * algorithm searching for a solution).
 *
 * Maze spots are accessed by their point in (row, column) order
 *
 * @author Robert C. Duvall
 * @author Shannon Pollard
 * @author David J. Eck
 */
public class Maze {
	// size of maze's grid of spots, including its outer border of walls
	private final int myNumRows;
	private final int myNumColumns;
	// states making up the maze
	private Spot[][] myMaze;


	/**
	 * Create a maze of a given size.
	 */
	public Maze (int rows, int columns) {
		// simple error checking, we will see better ways in the future
		assert(rows > 0 && columns > 0);
		myNumRows = rows;
		myNumColumns = columns;
		createMaze();
	}

	/**
	 * Replace existing maze with a random maze of the same size.
	 */
	public void createMaze () {
		// simple error checking, we will see better ways in the future
		assert(myNumRows > 0 && myNumColumns > 0);
		setMaze(myNumRows, myNumColumns);
	}

	/**
	 * Returns width of the maze in spots.
	 */
	public int getNumRows () {
		return myNumRows;
	}

	/**
	 * Returns height of the maze in spots.
	 */
	public int getNumColumns () {
		return myNumColumns;
	}

	/**
	 * Returns maze's starting spot in the top-left corner.
	 */
	public Spot getStart () {
		// simple error checking, we will see better ways in the future
		assert(myMaze != null);
		return getSpot(1, 1);
	}

	/**
	 * Returns maze's goal spot in the bottom-right corner.
	 */
	public Spot getGoal () {
		// simple error checking, we will see better ways in the future
		assert(myMaze != null);
		return getSpot(myNumRows-2, myNumColumns-2);
	}

	/**
	 * Returns true only if given point is inside maze's outer walls
	 */
	public boolean isInBounds (int x, int y) {
		return 0 < x && x < myNumRows-1 && 0 < y && y < myNumColumns-1;
	}

	/**
	 * Returns true only if given point is in maze's bounds (to prevent out-of-bounds or null errors)
	 */
	public boolean isValid (int x, int y) {
		return 0 <= x && x < myNumRows && 0 <= y && y < myNumColumns;
	}
	
	/**
	 * Returns maze's spot at given point
	 */
	public Spot getSpot (int x, int y) {
		// simple error checking, we will see better ways in the future
		assert(isValid(x, y));
		return myMaze[x][y];
	}

	/**
	 * Returns given spot's neighbors, the four immediately to the north, south,
	 * east, and west, but only if they are within the maze's outer wall.
	 *
	 * A spot's state is NOT checked (say, to see if the point is a wall or visited).
	 */
	public List<Spot> getNeighbors (Spot center) {
		// simple error checking, we will see better ways in the future
		assert(myMaze != null);
		List<Spot> neighbors = new ArrayList<>();
		for (int x = center.getX()-1; x <= center.getX()+1; x+=2) {
			if (isInBounds(x, center.getY())) {
				neighbors.add(getSpot(x, center.getY()));
			}
		}
		for (int y = center.getY()-1; y <= center.getY()+1; y+=2) {
			if (isInBounds(center.getX(), y)) {
				neighbors.add(getSpot(center.getX(), y));
			}
		}
		return neighbors;
	}

	/**
	 * Resets maze by erasing "path" and "visited" spots, leaving only walls and empty halls
	 */
	public void resetMaze () {
		// simple error checking, we will see better ways in the future
		assert(myMaze != null);
		for (int r = 0; r < myNumRows; r+=1) {
			for (int c = 0; c < myNumColumns; c+=1) {
				getSpot(r, c).markAsEmpty();
			}
		}
	}


	// Turn maze of states into Spots for easier use by search algorithms
	private void setMaze (int rows, int cols) {
		int[][] states = createMaze(rows, cols);
		myMaze = new Spot[rows][cols];
		for (int r = 0; r < rows; r+=1) {
			for (int c = 0; c < cols; c+=1) {
				myMaze[r][c] = new Spot(r, c, states[r][c], distanceToGoal(r, c));
			}
		}
	}

	// Compute "Manhattan" distance from given point to maze's goal point
	private int distanceToGoal (int x, int y) {
		return (myNumRows-2)-x + (myNumColumns-2)-y;
	}

	/*
	 * Remaining code is from "Introduction to Programming Using Java" by David J. Eck.
	 */
	// Create new random maze that has no cycles with given dimensions
	private int[][] createMaze (int rows, int cols) {
		// Create a random maze.  The strategy is to start with
		// a grid of disconnected "rooms" separated by walls,
		// then look at each of the separating walls, in a random
		// order.  If tearing down a wall would not create a loop
		// in the maze, then tear it down.  Otherwise, leave it in place.
		int[][] states = new int[rows][cols];
		// start with everything being a wall
		for (int r = 0; r < rows; r+=1) {
			Arrays.fill(states[r], Spot.WALL);
		}
		int[] wallRow = new int[(rows*cols)/2];  // position of walls between rooms
		int[] wallCol = new int[(rows*cols)/2];
		// make a grid of empty rooms
		int emptyCt = 0; // number of rooms
		int wallCt = 0;  // number of walls
		for (int r = 1; r < rows-1; r+=2)  {
			for (int c = 1; c < cols-1; c+=2) {
				emptyCt++;
				states[r][c] = -emptyCt;  // each room is represented by a different negative number
				if (r < rows-2) {  // record info about wall below this room
					wallRow[wallCt] = r+1;
					wallCol[wallCt] = c;
					wallCt += 1;
				}
				if (c < cols-2) {  // record info about wall to right of this room
					wallRow[wallCt] = r;
					wallCol[wallCt] = c+1;
					wallCt += 1;
				}
			}
		}
		// choose a wall randomly and maybe tear it down
		for (int w = wallCt-1; w > 0; w-=1) {
			int r = (int)(Math.random() * w);
			tearDown(states, wallRow[r], wallCol[r]);
			wallRow[r] = wallRow[w];
			wallCol[r] = wallCol[w];
		}
		// replace negative values in maze[][] with emptyCode
		for (int r = 1; r < rows-1; r+=1) {
			for (int c = 1; c < cols-1; c+=1) {
				if (states[r][c] < 0) {
					states[r][c] = Spot.EMPTY;
				}
			}
		}
		return states;
	}

	// Removes a wall, unless doing so makes a cycle in the maze
	private void tearDown (int[][] states, int row, int col) {
		// Tear down a wall, unless doing so will form a loop.  Tearing down a wall
		// joins two "rooms" into one "room".  (Rooms begin to look like corridors
		// as they grow.)  When a wall is torn down, the room codes on one side are
		// converted to match those on the other side, so all the cells in a room
		// have the same code.  Note that if the room codes on both sides of a
		// wall already have the same code, then tearing down that wall would 
		// create a loop, so the wall is left in place.
		if (row % 2 == 1 && states[row][col-1] != states[row][col+1]) {
			// row is odd; wall separates rooms horizontally
			fill(states, row, col-1, states[row][col-1], states[row][col+1]);
			states[row][col] = states[row][col+1];
		}
		else if (row % 2 == 0 && states[row-1][col] != states[row+1][col]) {
			// row is even; wall separates rooms vertically
			fill(states, row-1, col, states[row-1][col], states[row+1][col]);
			states[row][col] = states[row+1][col];
		}
	}

	// Joins "rooms" that were previously separate, once a wall is torn down
	private void fill (int[][] states, int row, int col, int replace, int replaceWith) {
		// called by tearDown() to change "room codes"
		if (states[row][col] == replace) {
			states[row][col] = replaceWith;
			fill(states, row+1, col, replace, replaceWith);
			fill(states, row-1, col, replace, replaceWith);
			fill(states, row, col+1, replace, replaceWith);
			fill(states, row, col-1, replace, replaceWith);
		}
	}
}
