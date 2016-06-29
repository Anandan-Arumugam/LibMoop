package com.libmoop.crossover;
import com.libmoop.global.GlobalSettings;
import com.libmoop.solution.Individual;

public class UniformBinaryCrossover implements ICrossover {

	//This field is used by the methods that are the part of the Interface
	private int numCrossovers = 0;

	// get this value from settings
	private double crossoverProb = Double.valueOf(GlobalSettings.getValueByKey("XOVER_PROBABILITY"));

	public Individual[] cross(Individual parent1, Individual parent2) {

		// If crossover probability is less than random value
		// Return the parents without performing any crossover
		if (crossoverProb < Math.random())
			return new Individual[] { parent1, parent2 };

		Individual[] children = new Individual[2];

		for (int i = 0; i < children.length; i++) {

			children[i] = new Individual(parent1);

			int totalBits = children[i].get_totalBits();

			for (int k = 0; k < totalBits; k++) {
				if (Math.random() < 0.5) {
					children[i].setBit(k, parent1.getBBit(k));
				} else {
					children[i].setBit(k, parent2.getBBit(k));
				}
			}
		}

		// decode the values of the newly created children
		children[0].decode();
		children[1].decode();

		numCrossovers++;
		return children;
	}

	@Override
	public void setNumberOfCrossovers(int numCrossovers) {
		this.numCrossovers = numCrossovers;
		
	}

	@Override
	public int getNumberOfCrossovers() {
		return numCrossovers;
	}
}
