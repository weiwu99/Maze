package maze.solvers;

import maze.model.Maze;
import maze.model.Spot;

import java.util.List;
import java.util.Queue;
import java.util.Stack;


/**
 * This class represents a Depth-First maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class DFS extends SearchAlgorithm {
	public static final String TITLE = "Depth-First";

	// data structure used to keep search frontier -- use a stack
	private Stack<Spot> myFrontier;

	public DFS (Maze maze) {
		super(TITLE, maze);
		myFrontier = new Stack<>();

		setMyFrontier(myFrontier);
		myFrontier.push(getMyCurrent());
	}

	/**
	 * @see SearchAlgorithm#step()
	 */
	@Override
	public boolean step () {

		recordSteps();

		// find possible next steps
		setMyNeighbors(getListNeighbors());

		// choose next spot to explore
		Spot next = nextSpot();
		chooseNextSpot(myFrontier, next);

		// update current spot
		setMyCurrent(myFrontier.peek());

		updateBacktrack();
//		System.out.println(getNumOfDeadEnds());
		updateMaxSize();
//		System.out.println(getMaxSize());

		return isSearchOver();
	}

	/**
	 * Choose next spot to explore
	 * @param myFrontier data structure used to keep search frontier
	 * @param next
	 */
	protected void chooseNextSpot(Stack<Spot> myFrontier, Spot next) {
		// mark next step, if it exists
		if (next != null) {
			next.markAsPath();
			myFrontier.push(next);
		}
		else {
			getMyCurrent().markAsVisited();
			myFrontier.pop();
		}
	}


	// Search is successful if current spot is the goal.
	// Search is unsuccessful if there are no more frontier spots to consider
	@Override
	protected boolean isSearchOver () {
		return getMyFrontier().isEmpty() || (super.isSearchOver());
	}
}
