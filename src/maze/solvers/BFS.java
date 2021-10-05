package maze.solvers;

import maze.model.Maze;
import maze.model.Spot;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * This class represents a Breadth-First maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class BFS extends SearchAlgorithm {
	public static final String TITLE = "Breadth-First";

	// data structure used to keep search frontier -- use a queue
	private Queue<Spot> myFrontier;

	public BFS (Maze maze) {
		super(TITLE, maze);
		myFrontier = new LinkedList<>();

		setMyFrontier(myFrontier);
		getMyFrontier().add(getMyCurrent());

	}

	/**
	 * @see SearchAlgorithm#step()
	 */
	@Override
	public boolean step () {
		// color successful path found
		if (isSearchOver()) {
//			System.out.println(getNumOfDeadEnds());
//			System.out.println(getMaxSize());
			markPath();
			return true;
		}

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

		return false;
	}

	// Search is successful if current spot is the goal.
	// Search is unsuccessful if there are no more frontier spots to consider
	@Override
	protected boolean isSearchOver () {
		return getMyFrontier().isEmpty() || (super.isSearchOver());
	}

}
