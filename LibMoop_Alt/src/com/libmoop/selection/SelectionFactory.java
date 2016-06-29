package com.libmoop.selection;

public class SelectionFactory {
	public static ISelection getSelectionOperator(String selectionName) {
		ISelection selection = null;
		switch (selectionName) {
		case "BinaryTournament":
			selection = new BinaryTournamentSelection();
			break;

		default:
			break;
		}
		return selection;
	}
}