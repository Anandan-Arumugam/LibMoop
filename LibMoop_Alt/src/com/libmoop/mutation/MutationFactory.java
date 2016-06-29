package com.libmoop.mutation;

public class MutationFactory {
	public static IMutation getMutationOperator(String mutationName) {
		IMutation mutation = null;
		switch (mutationName) {
		case "SingleBitBinaryMutation":
			mutation = new SingleBitBinaryMutation();
			break;

		default:
			break;
		}
		return mutation;
	}
}
