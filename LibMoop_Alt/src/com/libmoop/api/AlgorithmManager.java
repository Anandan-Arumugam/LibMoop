package com.libmoop.api;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.libmoop.algorithm.Algorithm;
import com.libmoop.algorithm.AlgorithmFactory;
import com.libmoop.crossover.CrossoverFactory;
import com.libmoop.crossover.ICrossover;
import com.libmoop.global.GlobalSettings;
import com.libmoop.global.InputParser;
import com.libmoop.global.Statistics;
import com.libmoop.mutation.IMutation;
import com.libmoop.mutation.MutationFactory;
import com.libmoop.selection.ISelection;
import com.libmoop.selection.SelectionFactory;
import com.libmoop.solution.Individual;
import com.libmoop.solution.Problem;

/***
 * @class AlgorithmManager
 * @description This class is an entry point to the library. Other programs who
 *              want to make use of this library, should use this class and use
 *              the provided interfaces appropriately.
 * @author Team C
 * 
 */
public class AlgorithmManager {

	private Algorithm _algorithm;
	private ISelection _selection;
	private ICrossover _crossover;
	private IMutation _mutation;
	private Problem _problem;
	private BufferedWriter fileWriter;

	private int maxRuns;

	private Boolean saveToFile;
	private String outputFile;

	public AlgorithmManager(Problem problem, String inputFilePath,
			String outputFilePath) {

		// Read various property values from input file
		GlobalSettings.readSettingsFromFile(inputFilePath);

		// Then read the properties from the global settings container
		String algorithm = GlobalSettings.getValueByKey("algorithm");
		String selection = GlobalSettings.getValueByKey("selection_operator");
		String crossover = GlobalSettings.getValueByKey("crossover_operator");
		String mutation = GlobalSettings.getValueByKey("mutation_operator");

		_algorithm = AlgorithmFactory.getAlgorithm(algorithm);
		_selection = SelectionFactory.getSelectionOperator(selection);
		_crossover = CrossoverFactory.getCrossoverOperator(crossover);
		_mutation = MutationFactory.getMutationOperator(mutation);

		if (GlobalSettings.getValueByKey("save_to_file").compareToIgnoreCase(
				"true") == 0) {
			saveToFile = true;
		} else
			saveToFile = false;
		maxRuns = Integer.valueOf(GlobalSettings.getValueByKey("MAX_RUNS"));
		outputFile = outputFilePath;

		_problem = problem;
	}
	
	public AlgorithmManager(String problemFilePath, String inputFilePath,
			String outputFilePath) {

		//Read various property values from input file
		GlobalSettings.readSettingsFromFile(inputFilePath);
		_problem = InputParser.readProblemFromFile(problemFilePath);

		//Then read the properties from the global settings container
		String algorithm = GlobalSettings.getValueByKey("algorithm");
		String selection = GlobalSettings.getValueByKey("selection_operator");
		String crossover = GlobalSettings.getValueByKey("crossover_operator");
		String mutation = GlobalSettings.getValueByKey("mutation_operator");

		_algorithm = AlgorithmFactory.getAlgorithm(algorithm);
		_selection = SelectionFactory.getSelectionOperator(selection);
		_crossover = CrossoverFactory.getCrossoverOperator(crossover);
		_mutation = MutationFactory.getMutationOperator(mutation);

		if (GlobalSettings.getValueByKey("save_to_file").compareToIgnoreCase(
				"true") == 0) {
			saveToFile = true;
		} else
			saveToFile = false;
		maxRuns = Integer.valueOf(GlobalSettings.getValueByKey("MAX_RUNS"));
		outputFile = outputFilePath;
	}

	public AlgorithmManager(String problemFilePath, String algorithm,
			String selection, String crossover, String mutation,
			String inputFilePath, String outputFilePath) {
		// Read various property values from input file
		GlobalSettings.readSettingsFromFile(inputFilePath);

		_problem = InputParser.readProblemFromFile(problemFilePath);

		_algorithm = AlgorithmFactory.getAlgorithm(algorithm);
		_selection = SelectionFactory.getSelectionOperator(selection);
		_crossover = CrossoverFactory.getCrossoverOperator(crossover);
		_mutation = MutationFactory.getMutationOperator(mutation);

		saveToFile = Boolean.getBoolean(GlobalSettings
				.getValueByKey("save_to_file"));
		maxRuns = Integer.valueOf(GlobalSettings.getValueByKey("MAX_RUNS"));
		outputFile = outputFilePath;
	}

	public int execute() {
		_algorithm.setProblem(_problem);
		_algorithm.setSelectionOperator(_selection);
		_algorithm.setCrossoverOperator(_crossover);
		_algorithm.setMutationOperator(_mutation);

		if (saveToFile) {
			try {

				fileWriter = new BufferedWriter(new FileWriter(outputFile));
			} catch (IOException e) {
				return -1;
			}
		}
		
		for (int currRun = 0; currRun < maxRuns; currRun++) {

			int currGen = 0;
			// reset the current generation to 0 before starting with each run
			_algorithm.setCurrentGeneration(0);

			// Initialize population
			Individual[] population = _algorithm.initialize();

			System.out
					.println("\n+++++++++++++++ Generation 0 +++++++++++++++++++++\n");
			Statistics.calculateStats(population, currGen, currRun, fileWriter);
			if (saveToFile)
				Statistics.write();
			// System.out.println("Fittest individual: "
			// + Statistics.getFittestVariables());
			// System.out.println("Maximum Fitness: "
			// + Statistics.getMaxFitness());

			while (!_algorithm.isTerminationCriteriaMet()) {

				population = _algorithm.evolve(population);
				currGen++;
				System.out.println("\n+++++++++++++++ Generation " + currGen
						+ " +++++++++++++++++++++\n");
				Statistics.calculateStats(population, currGen, currRun,
						fileWriter);
				System.out.println("Fittest individual: "
						+ Statistics.getFittestVariables());
				System.out.println("Maximum Fitness: "
						+ Statistics.getMaxFitness());
				if (saveToFile)
					Statistics.write();
			}
		}
		
		try {
			fileWriter.close();
		} catch (IOException e) {
			return -1;
		} 
		// System.out.println("Fittest run: " + Statistics.getBestRun());
		return 0;
	}
}