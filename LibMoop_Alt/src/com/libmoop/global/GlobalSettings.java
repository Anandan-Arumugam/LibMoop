package com.libmoop.global;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GlobalSettings {

	private static Map<String, String> params;
	private static BufferedReader bufferedReader = null;


	// public variables
	public static String getValueByKey(String key) {
		
		//This is important to avoid missing the keys due to different case
		key = key.toLowerCase();
		
		return params.get(key);
	}

	public static void readSettingsFromFile(String inputFilePath) {
		params = new HashMap<String, String>();
		try {
			bufferedReader = new BufferedReader(new FileReader(inputFilePath));
			String currLine;
			while ((currLine = bufferedReader.readLine()) != null) {
				if (!(currLine.startsWith("%") || currLine.isEmpty())) {
					String[] splittedValues = currLine.split("=");
					params.put(splittedValues[0].trim().toLowerCase(), splittedValues[1].trim());
				}
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find the input file.");
			e.printStackTrace();
		}
		catch (IOException e) {

			e.printStackTrace();
		}			
	}
}