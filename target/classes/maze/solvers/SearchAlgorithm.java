package maze.solvers;


import maze.model.Maze;
import maze.model.Spot;

import java.util.*;

/**
 * This class represents the abstraction of a maze search algorithm.
 *
 * @author Wei Wu
 */
public abstract class SearchAlgorithm {

	// number of steps taken
	private int numOfSteps;

	// max size for myFrontier
	private int maxSize;

	// number of dead ends
	private int numOfDeadEnds;

	// name for this search algorithm
	private final String myDescription;
	private Maze myMaze;
	// current spot being explored
	private Spot myCurrent;

	// trail of all spots can be used to recreate chosen path
	private Map<Spot, Spot> myPaths;

	// data structure used to keep search frontier -- use a queue
	private Collection<Spot> myFrontier;

	private List<Spot> myNeighbors;

	/**
	 * Create an algorithm with its name.
	 */
	public SearchAlgorithm (String description, Maze maze) {
		myDescription = description;
		myMaze = maze;
		myCurrent = maze.getStart();
		myCurrent.markAsPath();

		myPaths = new HashMap<>();

		numOfSteps = 0;
		maxSize = 0;
		numOfDeadEnds = 0;

	}

	/**
	 * Take one step searching for solution path for the maze.
	 * @return true if goal has been found or no more paths possible
	 */
	public boolean step () {
		return false;
	}

	/**
	 * Choose next spot to explore
	 * @param myFrontier data structure used to keep search frontier
	 * @param next
	 */
	protected void chooseNextSpot(Queue<Spot> myFrontier, Spot next){

		// mark next step, if it exists
		if (next != null) {
			nextStepExists(myFrontier, next);
		}
		else {
			myCurrent.markAsVisited();
			myFrontier.remove();
		}
	}

	/**
	 * Update available spots and paths
	 * @param myFrontier
	 * @param next
	 */
	private void nextStepExists(Queue<Spot> myFrontier, Spot next) {
		next.markAsPath();
		myFrontier.add(next);
		myPaths.put(next, myCurrent);
	}

	/**
	 * Set next viable spot
	 * @return
	 */
	protected Spot nextSpot(){
		Spot next = null;
		for (Spot spot : myNeighbors) {
			if (spot.getState() == Spot.EMPTY) {
				next = spot;
				break;
			}
		}
		return next;
	}

	/**
	 * get a list of neighbors for the current spot
	 * @return A list of all neighbor spots
	 */
	protected List<Spot> getListNeighbors(){
		return myMaze.getNeighbors(myCurrent);
	}

	// Search is successful if current spot is the goal.
	// Search is unsuccessful if there are no more frontier spots to consider
	protected boolean isSearchOver () {
		return (getMyCurrent() != null && findTheGoal());
	}

	// When the search is over, color the chosen correct path using trail of successful spots
	protected void markPath () {
		Spot step = getMyMaze().getGoal();
		while (step != null) {
			step.markAsPath();
			step = myPaths.get(step);
		}
	}

	/**
	 * 	TODO: report whether or not the algorithm has successfully found the goal
	 * 	note, this is different behavior than the current private method isSearchOver()
 	 */
	public boolean findTheGoal() {
//		if (getMyCurrent().equals(getMyMaze().getGoal())) {
//			System.out.printf("Found the goal!");
//			return true;
//		}
		return getMyCurrent().equals(getMyMaze().getGoal());
	}

	/**
	 * TODO: report the current number of steps taken by the algorithm exploring the maze
	 * @return
	 */
	public void recordSteps() {
		numOfSteps ++;
	}

	/**
	 * TODO: report the current maximum size the data structure has been while exploring the maze
	 * @return
	 */
	public void updateMaxSize() {
		if (myFrontier.size() > maxSize) {
			maxSize = myFrontier.size();
		}
	}

	/**
	 * TODO: report the current number of times the algorithm has had to backtrack
	 * (i.e., reached a deadend and had to go back to a different path)
	 * @return
	 */
	public void updateBacktrack() {
		if (getNumOfWalls() <= 1) {
			numOfDeadEnds ++;
		}
	}

	private int getNumOfWalls() {
		int numOfPath = 0;
		for (Spot spot: myNeighbors) {
			if (spot.getState() != Spot.WALL) {
				numOfPath++;
			}
		}
		return numOfPath;
	}


	/*
	 * A bunch of getters and setters
	 */
	public Maze getMyMaze() {
		return myMaze;
	}

	public void setMyMaze(Maze myMaze) {
		this.myMaze = myMaze;
	}

	public Spot getMyCurrent() {
		return myCurrent;
	}

	public void setMyCurrent(Spot myCurrent) {
		this.myCurrent = myCurrent;
	}

	public Map<Spot, Spot> getMyPaths() {
		return myPaths;
	}

	public void setMyPaths(Map<Spot, Spot> myPaths) {
		this.myPaths = myPaths;
	}

	public int getNumOfSteps() {
		return numOfSteps;
	}

	public Collection<Spot> getMyFrontier() {
		return myFrontier;
	}

	public void setMyFrontier(Collection<Spot> myFrontier) {
		this.myFrontier = myFrontier;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public List<Spot> getMyNeighbors() {
		return myNeighbors;
	}

	public void setMyNeighbors(List<Spot> myNeighbors) {
		this.myNeighbors = myNeighbors;
	}


	public int getNumOfDeadEnds() {
		return numOfDeadEnds;
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString () {
		return myDescription;
	}

}
