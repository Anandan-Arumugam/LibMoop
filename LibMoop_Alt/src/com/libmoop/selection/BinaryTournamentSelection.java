package com.libmoop.selection;
import com.libmoop.solution.Individual;

public class BinaryTournamentSelection implements ISelection {

	// for binary ga, population size is 2
	int tourSize = 2;

	public Individual select(Individual[] pop) {
		Individual best = null;	
		for (int i = 0; i < tourSize; i++) {

			int randIndex = (int) (Math.random() * pop.length);
			if (best == null
					|| best.get_fitness() <= pop[randIndex].get_fitness()) {
				best = pop[randIndex];
			}
		}

		return best;
	}
}
