package com.libmoop.mutation;

import com.libmoop.solution.Individual;

public interface IMutation {
	
	public void mutate(Individual child);
	
	public void setNumberOfMutations(int numMutations);
	
	public int getNumberOfMutations();
}
