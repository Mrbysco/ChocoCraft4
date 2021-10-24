package net.chococraft.utils;

import java.util.InputMismatchException;
import java.util.Random;

public class RandomHelper {
	public static Random random = new Random();

	//returns true if random returns a value that is under or equal to the percentage required for success
	public static boolean getChanceResult(int percentageForSuccess) {
		if(percentageForSuccess == 0)
			return false;
		if(percentageForSuccess >= 0 && percentageForSuccess <= 100)
			return random.nextInt(100) <= percentageForSuccess;
		else
			throw new InputMismatchException("getChanceResult passed " + percentageForSuccess + " but expected range of 0-100");
	}

	public static int getRandomInt(int maxValue) {
		return random.nextInt(maxValue);
	}
}
