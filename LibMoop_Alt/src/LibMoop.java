import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import com.libmoop.api.AlgorithmManager;
import com.libmoop.solution.Problem;

public class LibMoop {

	private static String _inputFilePath = "";
	private static String _outputFilePath = "";
	private static String _problemFilePath = "";

	/**
	 * Command Line tool for LibMoop
	 * 
	 * @param command
	 *            line arguments
	 */
	public static void main(String[] args) {

		/*
		 * Get the current source directory
		 */
		String currDir = "";
		try {
			currDir = new java.io.File(".").getCanonicalPath();
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}

		// TODO - Add proper exception handling logic for this commnad line tool

		/*
		 * If no arguments are supplied Show HELP menu to the user to help them
		 * choose the options and provide proper input
		 */
		if (args.length == 0) {
			// Display menu
			// displayMenu();
			// return;
			System.out.println("Running the libmoop tool with default inputs.");
			_inputFilePath = currDir + "\\" + "input.txt";
			_problemFilePath = currDir + "\\" + "problem.txt";
			_outputFilePath = currDir + "\\" + "output.txt";
		}
		
		else if(args.length == 1 && args[0].trim().toLowerCase()=="-help"){
			//Show help menu
			System.out.println(args[0].trim().toLowerCase());
			displayMenu();
		}

		/*
		 * If odd number of arguments are supplied, then something is missing,
		 * show help
		 */
		else if (args.length % 2 != 0) {						
			System.out
					.println("\nPlease look at the below menu for options.");
			displayMenu();
			return;
		}

		else {
			for (int i = 0; i < args.length; i += 2) {
				String swtch = args[i].trim();
				readSwitchArguments(args, i, swtch);
			}
		}

		/*
		 * Check whether file paths are provided as arguments If not, then look
		 * for the default files in the current working directory
		 */
		if (_inputFilePath == "") {
			_inputFilePath = Paths.get(currDir, "input.txt").toString();
			if (!fileExists(_inputFilePath)) {
				System.out
						.println("Input file does not exist at the given location: "
								+ _inputFilePath);
				return;
			}
		}

		if (_outputFilePath == "") {
			_outputFilePath = Paths.get(currDir, "output.txt").toString();
			if (!fileExists(_outputFilePath)) {
				System.out
						.println("Output file does not exist at the given location: "
								+ _outputFilePath);
				return;
			}
		}

		if (_problemFilePath == "") {
			_problemFilePath = Paths.get(currDir, "problem.txt").toString();
			if (!fileExists(_problemFilePath)) {
				System.out
						.println("Problem file does not exist at the given location: "
								+ _problemFilePath);
				return;
			}
		}

		// Initialize Algorithm Manager with the provided or default inputs
		System.out.println("Initializing the algorithm manager..");
		
//		Problem problem = new Problem(3, 2, new double[]{1,1,1}, new double[]{10,15,20}, new boolean[]{true, false, true}, new boolean[]{true, false, true}, 1);
//        

		AlgorithmManager algoMgr = new AlgorithmManager(_problemFilePath,
				_inputFilePath, _outputFilePath);

		// Execute the algorithm
		System.out.println("Starting the execution..");
		algoMgr.execute();

		// Execution completed
		System.out.println("Execution complete..");
	}

	private static boolean fileExists(String path) {
		return new File(path).exists();
	}

	private static void readSwitchArguments(String[] arguments,
			int currentIndex, String swtch) {

		// Check for array bounds
		if (currentIndex + 1 >= arguments.length) {
			displayMenu();
			return;
		}

		switch (swtch) {
		case "-i":
		case "-I":
			if (arguments[currentIndex + 1] != null
					&& arguments[currentIndex + 1] != "") {
				_inputFilePath = arguments[currentIndex + 1].trim();
			}
			break;
		case "-o":
		case "-O":
			if (arguments[currentIndex + 1] != null
					&& arguments[currentIndex + 1] != "") {
				_outputFilePath = arguments[currentIndex + 1].trim();
			}
			break;
		case "-p":
		case "-P":
			if (arguments[currentIndex + 1] != null
					&& arguments[currentIndex + 1] != "") {
				_problemFilePath = arguments[currentIndex + 1].trim();
			}
			break;

		default:
			System.out
					.println("Invalid switches not allowed. Please try again with right switches.");
			displayMenu();
			return;
		}
	}

	private static void displayMenu() {
		System.out.println();
		System.out
				.println("\t**************************************************************");
		System.out
				.println("\t************************ LibMoop Tool ************************");
		System.out
				.println("\t**************************************************************\n");

		System.out.println("  Usage: ");
		System.out.println(" --------");
		System.out
				.println(" libmoop [-p ProblemInputFile] [-i AlgorithmSettingsInput] [-o OutputFilePath]\n");

		System.out.println("  Options: ");
		System.out.println(" ----------");
		System.out.println(String.format(" %-28s %-80s", "-p ProblemInputFile",
				"Path of file containing problem related values"));
		System.out.println(String.format(" %-28s %-80s",
				"-i AlgorithmSettingsInput",
				"Path of file containing algorithm related settings"));
		System.out.println(String.format(" %-28s %-80s", "-o OutputFilePath",
				"Path of output file"));

		System.out.println("\n");
	}
}