package com.libmoop.crossover;

import com.libmoop.solution.Individual;

public interface ICrossover {
	public Individual[] cross(Individual parent1, Individual parent2);
	public void setNumberOfCrossovers(int numCrossovers);
	public int getNumberOfCrossovers();
}
