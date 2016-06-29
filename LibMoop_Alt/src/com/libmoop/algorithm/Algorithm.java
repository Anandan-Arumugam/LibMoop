/**
 * 
 */
package com.libmoop.algorithm;

import com.libmoop.crossover.ICrossover;
import com.libmoop.global.GlobalSettings;
import com.libmoop.mutation.IMutation;
import com.libmoop.selection.ISelection;
import com.libmoop.solution.Individual;
import com.libmoop.solution.Problem;

/**
 * @author Team C
 * 
 */
public abstract class Algorithm {

	// private variables
	protected boolean elitism;

	// Class dependencies
	protected ISelection selection;
	protected ICrossover crossover;
	protected IMutation mutation;

	// problem reference
	protected Problem problem;

	// Population reference
	//protected Individual bestIndividual;
	
	protected int popSize, maxGenerations, currGeneration;

	// default constructor
	public Algorithm() {
		// set default class variables here
		elitism = false;
		popSize = Integer.valueOf(GlobalSettings
				.getValueByKey("MAX_POP_SIZE"));
		maxGenerations = Integer.valueOf(GlobalSettings
				.getValueByKey("MAX_GENERATIONS"));		
	}

	public Algorithm(Problem problem, ISelection selection,
			ICrossover crossover, IMutation mutation) {

		this.problem = problem;

		// set local class variables here using the GlobalSettings dictionary
		this.elitism = Boolean.getBoolean(GlobalSettings
				.getValueByKey("elitism_on"));

		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;

	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public void setSelectionOperator(ISelection selection) {
		this.selection = selection;
	}

	public void setCrossoverOperator(ICrossover crossover) {
		this.crossover = crossover;
	}

	public void setMutationOperator(IMutation mutation) {
		this.mutation = mutation;
	}

	public abstract Individual[] initialize();

	/***
	 * This is an abstract method Subclasses must implement this method
	 * 
	 * @method initializePopulation
	 * @param nth
	 *            population
	 * @return (n+1)th population
	 */
	public abstract Individual[] evolve(Individual[] pop);
	
	public void setCurrentGeneration(int currGen){
		this.currGeneration = currGen;
	}
	
	public int getCurrentGeneration(){
		return currGeneration;
	}

	/***
	 * This method can be used by extending classes to add the termination
	 * criteria specific to the algorithm and/or user needs
	 * 
	 * @return True OR False
	 */
	public abstract boolean isTerminationCriteriaMet();

}
