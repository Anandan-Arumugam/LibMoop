package com.libmoop.mutation;
import com.libmoop.global.GlobalSettings;
import com.libmoop.solution.Individual;

public class SingleBitBinaryMutation implements IMutation {

	//This field is used by getter and setter methods implemented by interface
	private int numMutations = 0;

	private double mutationProb = Double.valueOf(GlobalSettings.getValueByKey("MUTATION_PROBABILITY")); 
	// 1/n

	public void mutate(Individual indiv) {

		if (mutationProb < Math.random())
			return;

		int totalBits = indiv.get_totalBits();
		int randomIndex = (int) (Math.random() * totalBits);

		indiv.flipBit(randomIndex);
		
		//re-decode the individual after mutation
		indiv.decode();		
		
		numMutations++;
	}

	@Override
	public void setNumberOfMutations(int numMutations) {
		this.numMutations = numMutations;		
	}

	@Override
	public int getNumberOfMutations() {
		return numMutations;
	}
}
