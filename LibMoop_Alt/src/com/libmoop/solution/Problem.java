package com.libmoop.solution;

public class Problem {

	// private int _numVariables;
	private int MAXM = 1; // -1 means minimization problem
							// 1 means maximization
	private int _numVariables;
	private int _numDiscreteVars;
	private double[] _lowerBounds;
	private double[] _upperBounds;
	private boolean[] _lowerRigidBounds;
	private boolean[] _upperRigidBounds;
	
	public Problem(int numVariables, int numDiscreteVars, 
			double[] lowerBounds, double[] upperBounds, 
			boolean[] lowerRigidBounds, boolean[] upperRigidBounds, int maxm){
		MAXM = maxm;
		set_numVariables(numVariables);
		set_numDiscreteVars(numDiscreteVars);
		set_lowerBounds(lowerBounds);
		set_upperBounds(upperBounds);
		set_lowerRigidBounds(lowerRigidBounds);
		set_upperRigidBounds(upperRigidBounds);
	}

	// sample objective function x^2 + 1 / y^2 - z^2
	public void evaluate(Individual indiv) {

		double[] values = indiv.get_genes();

		double obj = Math.pow(values[0], 2) + (1 / Math.pow(values[1], 2))
				- Math.pow(values[2], 2);

		obj*=MAXM;
		
		indiv.set_fitness(obj);
	}

	public int get_numVariables() {
		return _numVariables;
	}

	public void set_numVariables(int _numVariables) {
		this._numVariables = _numVariables;
	}

	public int get_numDiscreteVars() {
		return _numDiscreteVars;
	}

	public void set_numDiscreteVars(int _numDiscreteVars) {
		this._numDiscreteVars = _numDiscreteVars;
	}

	public double[] get_lowerBounds() {
		return _lowerBounds;
	}

	public void set_lowerBounds(double[] _lowerBounds) {
		this._lowerBounds = _lowerBounds;
	}

	public double[] get_upperBounds() {
		return _upperBounds;
	}

	public void set_upperBounds(double[] _upperBounds) {
		this._upperBounds = _upperBounds;
	}

	public boolean[] get_lowerRigidBounds() {
		return _lowerRigidBounds;
	}

	public void set_lowerRigidBounds(boolean[] _lowerRigidBounds) {
		this._lowerRigidBounds = _lowerRigidBounds;
	}

	public boolean[] get_upperRigidBounds() {
		return _upperRigidBounds;
	}

	public void set_upperRigidBounds(boolean[] _upperRigidBounds) {
		this._upperRigidBounds = _upperRigidBounds;
	}
}
