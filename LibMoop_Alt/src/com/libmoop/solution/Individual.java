package com.libmoop.solution;
import java.util.BitSet;

public class Individual {

	/*
	 * private class variables
	 */

	// Binary representation of variables
	private BitSet _chromosome;

	// total number of bits in the chromosome
	private int _totalBits;

	// this array will hold the variable values (decoded values in case of BINGA
	// and real values in case of REALGA)
	private double[] _genes;

	// fitness value of the individual
	private double _fitness;

	// number of variables
	private int _numVariables;

	// number of discrete variables
	private int _numDiscrete;

	// total number of bits required for binary representation (bits_per_var =
	// _varBitlength/numVariables)
	private int _varBitLength;

	// Number of bits assigned to each variable for binary representation
	private int _numBitsPerVar;

	private double[] _varLowerBound;

	private double[] _varupperBound;

	// These arrays tell if the lower or upper bounds are rigid or not
	private boolean[] _upperBoundRigid;
	private boolean[] _lowerBoundRigid;

	/*
	 * Constructor
	 */
	public Individual() {
	}

	// This constructor can be used to copy the structure of one individual to
	// other
	public Individual(Individual indiv) {
		this._numVariables = indiv._numVariables;
		this._numDiscrete = indiv._numDiscrete;
		this._varBitLength = indiv._varBitLength;
		this._numBitsPerVar = indiv._numBitsPerVar;
		this._totalBits = indiv._totalBits;

		// intialize arrays
		this._chromosome = new BitSet(_varBitLength);
		this._genes = new double[_numVariables];

		this._varLowerBound = indiv._varLowerBound;
		this._varupperBound = indiv._varupperBound;

		this._lowerBoundRigid = indiv._lowerBoundRigid;
		this._upperBoundRigid = indiv._upperBoundRigid;
	}

	public Individual(int numVariables, int numDiscrete, int varBitLength,
			double[] lower, double[] upper, boolean[] lowerRigid,
			boolean[] upperRigid) // varBitLength
	// has
	// to
	// be
	// calculated
	// within
	// the
	// algorithm
	{
		// initialize class variables
		_numVariables = numVariables;
		_numDiscrete = numDiscrete;
		_varBitLength = varBitLength;
		_numBitsPerVar = _varBitLength / _numVariables;
		_totalBits = varBitLength;

		// intialize arrays
		_chromosome = new BitSet(varBitLength);
		_genes = new double[numVariables];

		_varLowerBound = lower;
		_varupperBound = upper;

		_lowerBoundRigid = lowerRigid;
		_upperBoundRigid = upperRigid;

	}

	/*
	 * Public access methods
	 */
	public BitSet get_chromosome() {
		return _chromosome;
	}

	public void set_chromosome(BitSet _chromosome) {
		this._chromosome = _chromosome;
	}

	public int get_totalBits() {
		return _totalBits;
	}

	public void set_totalBits(int _totalBits) {
		this._totalBits = _totalBits;
	}

	public double[] get_genes() {
		return _genes;
	}

	public void set_genes(double[] _genes) {
		this._genes = _genes;
	}

	public double get_fitness() {
		return _fitness;
	}

	public void set_fitness(double _fitness) {
		this._fitness = _fitness;
	}

	public int get_numVariables() {
		return _numVariables;
	}

	public void set_numVariables(int _numVariables) {
		this._numVariables = _numVariables;
	}

	public int get_varBitLength() {
		return _varBitLength;
	}

	public void set_varBitLength(int _varBitLength) {
		this._varBitLength = _varBitLength;
	}

	public void createRandom() {

		// initialize the variables with random values
		for (int j = 0; j < _totalBits; j++) {
			// Do a toss
			if (Math.random() <= 0.5) {
				// set bit to 1 at position k in the bits assigned to a
				// particular variable
				_chromosome.set(j);

			} else {
				_chromosome.clear(j);
			}
		}
	}

	/*
	 * For BINGA, this function will decode the binary representation in
	 * chromosome and save the decoded values to the _genes array
	 */
	public void decode() {

		// Decode discrete variables first
		for (int i = 0; i < _numDiscrete; i++) {
			double value = 0.0;
			int start = i * _numBitsPerVar;
			int pos = _numBitsPerVar;
			for (int k = start; k < start + _numBitsPerVar; k++) {
				value += getBit(k)
						* Math.pow(2.0, (double) _numBitsPerVar - pos);
				pos--;
			}

			value = Math.round(_varLowerBound[i]
					+ ((_varupperBound[i] - _varLowerBound[i]) / (Math.pow(2.0,
							(double) _numBitsPerVar) - 1)) * value);

			// check for rigid bounds violation
			if (value <= this.getLowerBound(i) && !this.lowerBoundRigid(i)) {
				value += 1;
			}

			if (value >= this.getUpperBound(i) && !this.upperBoundRigid(i)) {
				value -= 1;
			}

			_genes[i] = value;
		}

		// Decode continuous variables then
		for (int i = _numDiscrete; i < _numVariables; i++) {
			double value = 0.0;
			int start = i * _numBitsPerVar;
			int pos = _numBitsPerVar;
			for (int k = start; k < start + _numBitsPerVar; k++) {
				value += getBit(k)
						* Math.pow(2.0, (double) _numBitsPerVar - pos);
				pos--;
			}

			value = _varLowerBound[i]
					+ ((_varupperBound[i] - _varLowerBound[i]) / (Math.pow(2.0,
							(double) _numBitsPerVar) - 1)) * value;
			
			// check for rigid bounds violation
			if (value <= this.getLowerBound(i) && !this.lowerBoundRigid(i)) {
				value += 0.1;
			}

			if (value >= this.getUpperBound(i) && !this.upperBoundRigid(i)) {
				value -= 0.1;
			}

			_genes[i] = value;
		}
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < _totalBits; i++) {
			str += getBit(i);
		}
		return str;
	}

	public void setBit(int index, boolean bit) {
		_chromosome.set(index, bit);
	}

	// public void clearBit(int index) {
	// _chromosome.clear(index);
	// }

	public double getLowerBound(int index) {
		return _varLowerBound[index];
	}

	public double getUpperBound(int index) {
		return _varupperBound[index];
	}

	public boolean lowerBoundRigid(int index) {
		return _lowerBoundRigid[index];
	}

	public boolean upperBoundRigid(int index) {
		return _upperBoundRigid[index];
	}

	public boolean getBBit(int index) {
		return _chromosome.get(index);
	}

	public void flipBit(int index) {
		_chromosome.flip(index);
	}

	public int getBit(int index) {
		if (_chromosome.get(index)) {
			return 1;
		}
		return 0;
	}
}