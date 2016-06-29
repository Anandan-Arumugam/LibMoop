package com.libmoop.global;

import java.io.BufferedWriter;
import java.io.IOException;

import com.libmoop.solution.Individual;

public class Statistics {

	private static int _currGeneration = 0;
	private static int _bestGeneration = 0;

	private static int _bestRun = 0;
	private static int _currentRun = 0;

	private static double _bestFitnessEver = 0.0;
	private static double[] _bestSolution;

	private static Individual[] _population;
	private static double _maxFitness = 0.0;

	private static Individual _fittestIndividual;
	private static Individual _fittestIndividualEver;

	private static BufferedWriter _writer;

	public Statistics() {
	}

	public static void calculateStats(Individual[] population,
			int currGeneration, int currRun, BufferedWriter writer) {
		_population = population;
		_currGeneration = currGeneration;
		_currentRun = currRun;
		_writer = writer;

		// Calculate fittest inddividual in the current generation
		double fittestValue = 0.0;
		for (int i = 0; i < _population.length; i++) {
			if (fittestValue < _population[i].get_fitness()) {
				fittestValue = _population[i].get_fitness();
				_fittestIndividual = population[i];
			}
		}

		// This is the maximum fitness so far in the current generation
		_maxFitness = fittestValue;

		if (_bestFitnessEver < _maxFitness) {
			_bestFitnessEver = _maxFitness;
			_bestGeneration = _currGeneration;
			_bestRun = _currentRun;
			_fittestIndividualEver = _fittestIndividual;
		}
	}

	public static double getMaxFitness() {
		return _maxFitness;
	}

	public static double getBestEverFitness() {
		return _bestFitnessEver;
	}

	public static String getFittestVariables() {
		double[] fittestVariables = null;
		Individual fittestIndividual = null;
		for (int i = 0; i < _population.length; i++) {
			if (fittestIndividual == null
					|| fittestIndividual.get_fitness() < _population[i]
							.get_fitness()) {
				fittestIndividual = _population[i];
			}
		}
		fittestVariables = fittestIndividual.get_genes();

		String value = "";
		for (int i = 0; i < fittestVariables.length - 1; i++) {
			value += fittestVariables[i] + ", ";
		}
		value += fittestVariables[fittestVariables.length - 1];
		return value;
	}

	public static void write() {
		try {
			_writer.write("==========================================");
			_writer.newLine();
			_writer.write("            Run " + (_currentRun + 1));
			_writer.newLine();
			_writer.write("==========================================");
			_writer.newLine();
			_writer.write("            Generation " + _currGeneration);
			_writer.newLine();
			_writer.write("==========================================");
			_writer.newLine();
			_writer.write("(Variables followed by the fitness of individual.)");
			_writer.newLine();
			_writer.newLine();

			for (int i = 0; i < _population.length; i++) {
				double[] vars = _population[i].get_genes();
				int totalVars = vars.length;
				for (int j = 0; j < totalVars; j++) {
					_writer.write(String.valueOf(vars[j]) + "   ");
				}
				_writer.write(String.valueOf(_population[i].get_fitness()));
				_writer.newLine();
			}
			_writer.newLine();
			_writer.newLine();
			_writer.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getCurrGeneration() {
		return _currGeneration;
	}

	public static void setCurrGeneration(int currGeneration) {
		_currGeneration = currGeneration;
	}

	public static int getBestGeneration() {
		return _bestGeneration;
	}

	public static void setBestGeneration(int bestGeneration) {
		_bestGeneration = bestGeneration;
	}

	public static int getBestRun() {
		return _bestRun;
	}

	public static void setBestRun(int bestRun) {
		_bestRun = bestRun;
	}

	public static void setBestEverFitness(double _bestFitness) {
		Statistics._bestFitnessEver = _bestFitness;
	}

	public static double[] getBestSolution() {
		return _bestSolution;
	}

	public static void setBestSolution(double[] _bestSolution) {
		Statistics._bestSolution = _bestSolution;
	}
}