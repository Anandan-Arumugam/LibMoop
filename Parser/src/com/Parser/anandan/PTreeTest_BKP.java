package com.Parser.anandan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class PTreeTest_BKP {

	public static void main(String[] args) throws IOException {
		
		BufferedReader Input = new BufferedReader (new InputStreamReader(System.in));
		System.out.println("Enter Expression : ");
		HashMap<Character, Double> values=new HashMap<Character,Double>();
		values.put('x', 10.0);
		values.put('y',20.0);
		values.put('z', 30.0);
		String InputToken=Input.readLine();
		ParserTree_bkp ptree=new ParserTree_bkp(InputToken,values);
		ptree.convertToParseTree();
		ptree.printExpression();
		ptree.evaluateExpression();
	}

}
