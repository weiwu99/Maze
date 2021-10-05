package maze.util;

import java.util.Random;
import java.util.List;
import maze.model.Spot;


/**
 * This class provides utility methods to make it easier to use Java's Random class.
 *
 * Utility methods are static, meaning they can be called directly without having to make
 * an instance of this class.
 *
 * @author Robert C. Duvall
 */
public class Randomness {
	// create only one and reuse it, in order to get a truly random SEQUENCE of values
	// make public so others can call typical random methods without having to create their own
	public static final Random ourRandom = new Random();


	/**
	 * Returns random element from given List.
	 */
	public static Spot getRandomElement (List<Spot> spots) {
		return spots.get(ourRandom.nextInt(spots.size()));
	}

	/**
	 * Returns true only if random value is below given threshold value.
	 */
	public static boolean isRandomEnough (double threshold) {
		return ourRandom.nextDouble() < threshold;
	}
}
