package com.Parser.anandan;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ParseTree1 {
	private Stack<ParserNode> parseTree;
	private ParserNode root;
	final char[] tokens; 
	Stack<Character> Operators;
	private HashMap<Character, Double> pTreeOperandValue;
	private int charIndex;
	public ParseTree1(String Input,HashMap<Character, Double> OperandValues) {
		this.parseTree=new Stack<ParserNode>();
		tokens = Input.toCharArray();
		Operators=new Stack<Character>();
		root=null;
		pTreeOperandValue=OperandValues;
		charIndex=0;
	}
	public void convertToParseTree(){
		while(charIndex<tokens.length){
			if((this.isOperator(tokens[charIndex]))){
				Operators.add(tokens[charIndex]);
				charIndex++;
			}
			else if(tokens[charIndex]=='('){
				Operators.add(tokens[charIndex]);
				charIndex++;
			}
			else if(tokens[charIndex]=='s'){
				if(tokens[charIndex+1]=='u'){
					if(tokens[charIndex+2]=='m'){
						ParserNode unaryNode=constructUnaryOperatorNode(3);
						parseTree.add(unaryNode);
						charIndex++;
					}
				}
			}
			else if(tokens[charIndex]==')'){
				if(Operators.isEmpty()){
					System.out.println("Syntax error");
					break;
				}
				else{
					Operators.add(tokens[charIndex]);
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
						break;
					}
					charIndex++;
				}
			}
			else if(tokens[charIndex]==' '){
				charIndex++;
				continue;
			}
			else{
				ParserNode thisNode=new ParserNode(tokens[charIndex]);
				parseTree.add(thisNode);
				if(pTreeOperandValue.get(tokens[charIndex])!=null){
					thisNode.setValue(pTreeOperandValue.get(tokens[charIndex]));
				}
				else{
					thisNode.setValue(Double.valueOf(Character.toString(thisNode.getToken())));
				}
				charIndex++;
			}
		}
	}
	private ParserNode constructUnaryOperatorNode(int tokensCounter) {
		charIndex=charIndex+3;
		boolean start=false;
		Integer startValue = 0;
		Integer endValue=0;
		boolean node=false;
		ParserNode OperandNode=null;
		ParserNode unaryRoot=null;
		Stack<ParserNode> OperandStack=new Stack<ParserNode>();
		Stack<Character> OperatorStack=new Stack<Character>();
		System.out.println("Inside UnaryOperator");
		while(tokens[charIndex]!='('){
			if(tokens[charIndex]==' '){
				charIndex++;
				continue;
			}
			else if(tokens[charIndex]=='{'){
				charIndex++;
				continue;
			}
			else if(tokens[charIndex]==','){
				charIndex++;
				continue;
			}
			else if(!node){
				OperandNode=new ParserNode(tokens[charIndex]);
				node=true;
				charIndex++;
			}
			else if(!start){
				startValue=Integer.valueOf(Character.toString(tokens[charIndex]));
				start=true;
				charIndex++;
			}
			else{
				endValue=Integer.valueOf(Character.toString(tokens[charIndex]));
				charIndex++;
			}
		}
		while (tokens[charIndex]!='}'){
			if((this.isOperator(tokens[charIndex]))){
				OperatorStack.add(tokens[charIndex]);
				charIndex++;
			}
			else if(tokens[charIndex]=='('){
				OperatorStack.add(tokens[charIndex]);
				charIndex++;
			}
			else if(tokens[charIndex]==')'){
				if(OperatorStack.isEmpty()){
					System.out.println("Syntax error");
					break;
				}
				else{
					OperatorStack.add(tokens[charIndex]);
					char thisOperator=OperatorStack.pop();
					while(!isOperator(thisOperator)){
						thisOperator=OperatorStack.pop();
					}
					if(OperatorStack.pop()=='('){
						ParserNode thisNode=new ParserNode(thisOperator);
						thisNode.setRightTree(OperandStack.pop());
						thisNode.setLeftTree(OperandStack.pop());
						OperandStack.add(thisNode);
						unaryRoot=thisNode;
					}
					else{
						System.out.println("syntax error");
						break;
					}
					charIndex++;
				}
			}
			else if(tokens[charIndex]==' '){
				charIndex++;
				continue;
			}
			else{
				ParserNode thisNode=new ParserNode(tokens[charIndex]);
				OperandStack.add(thisNode);
				charIndex++;
			}
		}	 
		System.out.println("Unary Operator Parse Tree:");
		this.printPostFixNotatin(unaryRoot);
		System.out.println();
		Double sumValue=0.0;
		for(int k=startValue;k<=endValue;k++){
			this.initializeOperatorTree(unaryRoot,k,OperandNode);
			sumValue=sumValue+evaluateTree(unaryRoot);
		}
		ParserNode unaryNode=new ParserNode('$');
		unaryNode.setValue(sumValue);
		return unaryNode;
	}
	private void initializeOperatorTree(ParserNode operandRoot, int k, ParserNode operandNode) {
		Queue<ParserNode> nodes = new LinkedList<ParserNode>();
		nodes.add(operandRoot);
		while (!nodes.isEmpty()){
			if(nodes.peek().getLeftTree()!=null)
				nodes.add(nodes.peek().getLeftTree());
			if(nodes.peek().getRightTree()!=null)
				nodes.add(nodes.peek().getRightTree());
			if(nodes.peek().getToken()==operandNode.getToken()){
				nodes.poll().setValue(k);
			}
			else if(pTreeOperandValue.get(nodes.peek().getToken())!=null){
				nodes.peek().setValue(pTreeOperandValue.get(nodes.peek().getToken()));
				nodes.poll();
			}
			else if(!isOperator(nodes.peek().getToken())){
				nodes.peek().setValue(Double.valueOf(Character.toString(nodes.poll().getToken())));
			}
			else{
				nodes.poll();
			}
		}
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
		System.out.println("Final Parse Tree :");
		printPostFixNotatin(root);
	}
	public void evaluateExpression(){
		System.out.println();
		System.out.println("Value :" + evaluateTree(root));
	}
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
			return node.getValue();
		}
	}
}

