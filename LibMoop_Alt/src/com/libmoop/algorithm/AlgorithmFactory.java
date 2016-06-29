package com.libmoop.algorithm;

public class AlgorithmFactory {
	public static Algorithm getAlgorithm(String algorithmName) {
		Algorithm algo = null;
		switch (algorithmName) {
		case "SimpleGA":
			algo = new SimpleGA();
			break;
		default:
			algo = new SimpleGA();
			break;
		}
		return algo;
	}
}