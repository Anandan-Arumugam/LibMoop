package com.Parser.anandan;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ParseTree {
	private Stack<ParserNode> parseTree;
	private ParserNode root;
	final char[] tokens; 
	Stack<Character> Operators;
	private HashMap<Character, Double> pTreeOperandValue;
	public ParseTree(String Input,HashMap<Character, Double> OperandValues) {
		this.parseTree=new Stack<ParserNode>();
		tokens = Input.toCharArray();
		Operators=new Stack<Character>();
		root=null;
		pTreeOperandValue=OperandValues;
	}
	public void convertToParseTree(){
		for (int i=0;i<tokens.length;i++){
			if((this.isOperator(tokens[i]))){
				Operators.add(tokens[i]);
			}
			else if(tokens[i]=='('){
				Operators.add(tokens[i]);
			}
			else if(tokens[i]=='s'){
				if(tokens[i+1]=='u'){
					if(tokens[i+2]=='m'){
						
					}
				}
			}
			else if(tokens[i]==')'){
				if(Operators.isEmpty()){
					System.out.println("Syntax error");
				}
				else{
					Operators.add(tokens[i]);
					char thisOperator=Operators.pop();
					while(!isOperator(thisOperator)){
						thisOperator=Operators.pop();
					}
					if(Operators.pop()=='('){
						ParserNode thisNode=new ParserNode(thisOperator);
						thisNode.setRightTree(parseTree.pop());
						thisNode.setLeftTree(parseTree.pop());
						parseTree.add(thisNode);
						root=thisNode;
					}
					else{
						System.out.println("syntax error");
					}
				}
			}
			else if(tokens[i]==' '){
				continue;
			}
			else{
				ParserNode thisNode=new ParserNode(tokens[i]);
				parseTree.add(thisNode);
				if(pTreeOperandValue.get(tokens[i])!=null){
					thisNode.setValue(pTreeOperandValue.get(tokens[i]));
				}
				else{
					thisNode.setValue(Double.valueOf(Character.toString(thisNode.getToken())));
				}
			}
		}
		System.out.println(Operators.isEmpty());
	}
	private boolean isOperator(char token) {
		if(token=='*'|token=='+'|token=='-'|token=='/'|token=='^'){
			return true;
		}
		return false;
	}
	public void printPostFixNotatin(ParserNode node) {
		if(node!=null){
			printPostFixNotatin(node.getLeftTree());
			printPostFixNotatin(node.getRightTree());
			System.out.print(node.getToken());
		}
	}
	public void printExpression() {
		printPostFixNotatin(root);
	}
	public void evaluateExpression(){
		System.out.println();
		System.out.println(evaluateTree(root));
	}

	public void postFixLoop(){
		Stack<ParserNode> nodes=new Stack<ParserNode>();
		Queue<ParserNode> output=new LinkedList<ParserNode>();
		nodes.add(root);
		ParserNode lastAdded=null;
		System.out.println();
		while(!nodes.isEmpty()){
			ParserNode thisNode=nodes.peek();
			if(thisNode.getLeftTree()!=null){
				if(thisNode.getRightTree()==lastAdded){
					output.add(thisNode);
					lastAdded=nodes.pop();
				}
				else{
					nodes.add(thisNode.getRightTree());
					nodes.add(thisNode.getLeftTree());
				}
			}
			else if(thisNode.getRightTree()==null){
				output.add(thisNode);
				lastAdded=nodes.pop();
			}
			else{
				nodes.add(thisNode.getRightTree());
			}
		}
		for(ParserNode node:  output){
			System.out.print(node.getToken());
		}
		System.out.println();
	}

	public void inOrderLoop(){
		Stack<ParserNode> nodes=new Stack<ParserNode>();
		Queue<ParserNode> output=new LinkedList<ParserNode>();
		nodes.add(root.getRightTree());
		nodes.add(root);
		nodes.add(root.getLeftTree());
		ParserNode lastAdded=null;
		while(!nodes.empty()){
			ParserNode thisNode=nodes.pop();
			if(thisNode.getLeftTree()!=null){
				if(thisNode.getLeftTree()==lastAdded||thisNode==root){
					output.add(thisNode);
					lastAdded=thisNode;
				}
				else{
					nodes.add(thisNode.getRightTree());
					nodes.add(thisNode);
					nodes.add(thisNode.getLeftTree());
				}
			}
			else if(thisNode.getRightTree()==null){
				output.add(thisNode);
				lastAdded=thisNode;
			}
			else{
				nodes.add(thisNode.getRightTree());
				nodes.add(thisNode);
			}
		}
		for(ParserNode node:  output){
			System.out.print(node.getToken());
		}
		System.out.println();
	}
	
//	public void preOrderLoop(){
//		Stack<ParserNode> nodes=new Stack<ParserNode>();
//		Queue<ParserNode> output=new LinkedList<ParserNode>();
//		nodes.add(root);
//		ParserNode lastAdded=null;
//		while(!nodes.isEmpty()){
//			ParserNode thisNode=nodes.pop();
//			if(thisNode.getLeftTree()!=null){
//				output.add(thisNode);
//				nodes.add(thisNode.getRightTree());
//				nodes.add(thisNode.getLeftTree());
//			}
//			else if(thisNode)
//		}
//	}
	
	private Double evaluateTree(ParserNode node) {
		switch (node.getToken()){
		case '+':
			return (evaluateTree(node.getLeftTree()) + evaluateTree(node.getRightTree()));
		case '-':
			return (evaluateTree(node.getLeftTree()) - evaluateTree(node.getRightTree()));
		case '/':
			return (evaluateTree(node.getLeftTree()) / evaluateTree(node.getRightTree()));
		case '*':
			return (evaluateTree(node.getLeftTree()) * evaluateTree(node.getRightTree()));
		case '^':
			return Math.pow(evaluateTree(node.getLeftTree()),evaluateTree(node.getRightTree()));
		default:
//			return Double.valueOf(Character.toString(node.getToken()));
			return node.getValue();
		}
	}
}

