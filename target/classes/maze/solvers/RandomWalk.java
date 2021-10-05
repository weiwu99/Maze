package maze.solvers;

import maze.model.Maze;
import maze.model.Spot;
import maze.util.Randomness;

import java.util.ArrayList;
import java.util.List;


/**
 * This class represents a random maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class RandomWalk extends SearchAlgorithm {
	public static final String TITLE = "Random Walk";
	public final double EXPLORE_BIAS = 0.999;

	public RandomWalk (Maze maze) {
		super(TITLE, maze);
	}

	/**
	 * @see SearchAlgorithm#step()
	 */
	@Override
	public boolean step () {
		// find possible next steps
		setMyNeighbors(getListNeighbors());

		// choose next spot to explore
		List<Spot> empties = new ArrayList<>();
		List<Spot> possibles = new ArrayList<>();

		checkWall(empties, possibles);

		Spot next = randomNextStep(empties, possibles);

		// update current spot
		getMyCurrent().markAsVisited();
		setMyCurrent(next);

		updateBacktrack();
//		System.out.println(getMaxSize());
//		System.out.println(getNumOfDeadEnds());

		return isSearchOver();
	}

	private Spot randomNextStep(List<Spot> empties, List<Spot> possibles) {
		Spot next;
		// prefer exploring empty paths over visited ones
		if (! empties.isEmpty() && Randomness.isRandomEnough(EXPLORE_BIAS)) {
			next = Randomness.getRandomElement(empties);
		}

		// guaranteed to be at least one possible, even if it is last spot visited
		else {
			next = Randomness.getRandomElement(possibles);
		}

		// mark next step
		next.markAsPath();
		return next;
	}

	private void checkWall(List<Spot> empties, List<Spot> possibles) {
		for (Spot spot : getMyNeighbors()) {
			if (spot.getState() == Spot.EMPTY) {
				empties.add(spot);
			}
			if (spot.getState() != Spot.WALL) {
				possibles.add(spot);
			}
		}
	}

	@Override
	public int getMaxSize() {
		return 1;
	}


}
