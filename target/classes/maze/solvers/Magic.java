package maze.solvers;

import maze.model.Maze;
import maze.model.Spot;
import maze.util.Randomness;

import java.util.List;
import java.util.PriorityQueue;


/**
 * This class represents a Magic maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class Magic extends SearchAlgorithm {
	public static final String TITLE = "Magic";

	// data structure used to keep search frontier -- use a priority queue
	private PriorityQueue<Spot> myFrontier;

	public Magic (Maze maze) {
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
//		if (findTheGoal()) {
//			System.out.println(getNumOfDeadEnds());
//			System.out.println(getMaxSize());
			getMyPaths().get(getMyMaze().getGoal());
			return true;
		}
		// find possible next steps
		setMyNeighbors(getListNeighbors());

		// choose next spot to explore -- magic means next spot could be a wall!
		Spot next = Randomness.getRandomElement(getMyNeighbors());
		chooseNextSpot(myFrontier, next);

		// update current spot
		setMyCurrent(myFrontier.peek());

//		updateBacktrack();
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

	@Override
	public int getNumOfDeadEnds() {
		return 0;
	}
}
