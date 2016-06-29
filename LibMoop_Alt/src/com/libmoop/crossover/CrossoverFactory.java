package com.libmoop.crossover;

public class CrossoverFactory {
	public static ICrossover getCrossoverOperator(String crossoverName) {
		ICrossover crossover = null;
		switch (crossoverName) {
		case "UniformBinaryCrossover":
			crossover = new UniformBinaryCrossover();
			break;

		default:
			break;
		}
		return crossover;
	}
}
