package com.libmoop.selection;

import com.libmoop.solution.Individual;

public interface ISelection {	
	public Individual select(Individual[] population);
}
