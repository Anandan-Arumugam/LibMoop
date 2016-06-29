package com.libmoop.global;

import libmoop.solutions.Individual;
import libmoop.solutions.Problem;

public class GlobalContainer {
	public static int numCrossovers = 0;
	public static int numMutations = 0;
	public static int crossoverType = 0;
	public static int chromLength = 0;
	public static int chromSize = 0;
	public static boolean BINGA = true;
	public static boolean REALGA = false;
	public static boolean RIGID = false;
	public static boolean SHARING = true;
	public static int popSize = 0;
	public static double nDistributionCrossover = 0.0;
	public static double nDistributionMutation = 0.0;
	public static int xOverType = 1;//BLX by default
	public static int numberOfBytes = 0;
	//Minimization = 1
	//Maximization = -1
	public static int MINM = 1;
	
	public static int LONGSIZE = 8*(Long.BYTES); 
	public static Problem problem;
	
	
	//Individual related
	public static Individual bestIndividual; // this should be set in initialize pop
	public static int bestEverGen;
	public static double sumObj = 0.0;
	public static double avgObj = 0.0;
	public static double minObj = 0.0;
	public static double maxObj = 0.0;
	public static double[] maxVar;
	public static double[] minVar;
}
