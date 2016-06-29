package com.libmoop.global;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.libmoop.solution.Problem;

public class InputParser {
	private static BufferedReader bufferedReader = null;
	private static Map<String, String> map;

	public static Problem readProblemFromFile(String inputFilePath) {

		map = new HashMap<String, String>();

		Problem problem = null;

		try {
			bufferedReader = new BufferedReader(new FileReader(inputFilePath));
			String currLine;

			// First read all lines from file
			// Store the lines containing relevant information properties along
			// with values
			while ((currLine = bufferedReader.readLine()) != null) {
				if (!(currLine.startsWith("%") || currLine.isEmpty())) {
					String[] splittedValues = currLine.split("=");
					map.put(splittedValues[0].trim().toLowerCase(),
							splittedValues[1].trim());
				}
			}

			// Now fetch required properties from the hashmap and process them
			// to get desired information
//			int numberOfObjFuncs = Integer
//					.valueOf(getValueByKey("NUM_OBJ_FUNCTIONS"));
			int numberOfvars = Integer.valueOf(getValueByKey("NUM_VARIABLES"));
			int numberDiscreteVars = Integer.valueOf(getValueByKey("NUM_DISCRETE_VARS"));
			int maxm = Integer.valueOf(getValueByKey("MAXM"));

			// Fetch lower bounds of all variables
			String[] lowerBoundsString = getValueByKey("VAR_LOWER").split(",");
			double[] lowerBounds = new double[lowerBoundsString.length];
			for (int j = 0; j < lowerBoundsString.length; j++) {
				lowerBounds[j] = Double.valueOf(lowerBoundsString[j].trim());
			}

			// Fetch upper bounds of all variables
			String[] upperBoundsString = getValueByKey("VAR_UPPER").split(",");
			double[] upperBounds = new double[upperBoundsString.length];
			for (int j = 0; j < upperBoundsString.length; j++) {
				upperBounds[j] = Double.valueOf(upperBoundsString[j].trim());
			}

			// See whether lower bounds are rigid
			String[] lowerRigidBoundsString = getValueByKey("VAR_LOWER_RIGID").split(",");
			boolean[] lowerRigidBounds = new boolean[lowerRigidBoundsString.length];
			for (int j = 0; j < lowerRigidBoundsString.length; j++) {
				if(lowerRigidBoundsString[j].trim().compareToIgnoreCase("true") == 0){
					lowerRigidBounds[j] = true;
				}
				else if(lowerRigidBoundsString[j].trim().compareToIgnoreCase("false") == 0){
					lowerRigidBounds[j] = false;
				}
			}

			// See whether upper bounds are rigid
			String[] upperRigidBoundsString = getValueByKey("VAR_UPPER_RIGID").split(",");
			boolean[] upperRigidBounds = new boolean[upperRigidBoundsString.length];
			for (int j = 0; j < upperRigidBoundsString.length; j++) {
				if(upperRigidBoundsString[j].trim().compareToIgnoreCase("true") == 0){
					upperRigidBounds[j] = true;
				}
				else if(upperRigidBoundsString[j].trim().compareToIgnoreCase("false") == 0){
					upperRigidBounds[j] = false;
				}
			}

			// Finally, construct the problem object
			problem = new Problem(numberOfvars, numberDiscreteVars,
					lowerBounds, upperBounds, lowerRigidBounds,
					upperRigidBounds, maxm);

		} catch (FileNotFoundException e) {
			System.out.println("Cannot find the input file.");
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		
		return problem;

	}
	
	// private functions
	private static String getValueByKey(String key) {
		
		//This is important to avoid missing the keys due to different case
		key = key.toLowerCase();
		
		return map.get(key);
	}
}
