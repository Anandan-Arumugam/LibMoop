package com.libmoop.algorithm;

import com.libmoop.solution.Individual;

public class SimpleGA extends Algorithm{

	public Individual[] initialize() {
		Individual[] pop = new Individual[popSize];

		// Initialize the first generation
		for (int i = 0; i < popSize; i++) {
			pop[i] = new Individual(problem.get_numVariables(), 
					problem.get_numDiscreteVars(),
					30,//have to find way to make this auto calculated instead of asking from user 
					problem.get_lowerBounds(),
					problem.get_upperBounds(),
					problem.get_lowerRigidBounds(), 
					problem.get_upperRigidBounds());
		}

		// Create random individuals in 0th generation
		for (int i = 0; i < popSize; i++) {
			pop[i].createRandom();
		}

		// Print the initial population
		for (int i = 0; i < popSize; i++) {

			// decode the individual
			pop[i].decode();

			// evaluate the fitness of individual against the given objective
			// function
			problem.evaluate(pop[i]);

			// print the string
			System.out.println(pop[i]);
			System.out.println("Fitness: " + pop[i].get_fitness());
			System.out.println();
		}

		return pop;
	}

	public Individual[] evolve(Individual[] pop) {
	

		Individual[] newPop = new Individual[pop.length];
		for (int i = 0; i < pop.length; i += 2) {

			// selection
			Individual parent1 = selection.select(pop);
			Individual parent2 = selection.select(pop);

			// crossover
			Individual[] children = crossover.cross(parent1, parent2);

			// mutation
			mutation.mutate(children[0]);
			mutation.mutate(children[1]);

			// update the fitness value of new individuals
			problem.evaluate(children[0]);
			problem.evaluate(children[1]);

			// add children to the new generation
			newPop[i] = children[0];
			newPop[i + 1] = children[1];
		}

		currGeneration++;
		return newPop;
	}

	@Override
	public boolean isTerminationCriteriaMet() {
		return currGeneration >= maxGenerations;
	}
}