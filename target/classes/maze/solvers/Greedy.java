package maze.solvers;

import maze.model.Maze;
import maze.model.Spot;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


/**
 * This class represents a Greedy maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class Greedy extends SearchAlgorithm {
	public static final String TITLE = "Greedy";

	// data structure used to keep search frontier -- use a priority queue
	private PriorityQueue<Spot> myFrontier;

	public Greedy (Maze maze) {
		super(TITLE, maze);
		myFrontier = new PriorityQueue<>();

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

		// sort in order of closest to goal
		Collections.sort(getMyNeighbors());

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
