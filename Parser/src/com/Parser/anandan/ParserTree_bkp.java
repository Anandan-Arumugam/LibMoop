package com.Parser.anandan;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ParserTree_bkp {
	private Stack<ParserNode> parseTree;
	private ParserNode root;
	final char[] tokens; 
	Stack<Character> Operators;
	private HashMap<Character, Double> pTreeOperandValue;
	private int charIndex;
	public ParserTree_bkp(String Input,HashMap<Character, Double> OperandValues) {
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
				else if(tokens[charIndex+1]=='i'){
						if(tokens[charIndex+2]=='n'){
							System.out.println("here 'Sin'");
							ParserNode trigNode=constructTrignometaryNode(3);
							parseTree.add(trigNode);
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
	private ParserNode constructTrignometaryNode(int i) {
		System.out.println("Here in Trig");
		charIndex=charIndex+i;
		Stack<ParserNode> trigOperandStack=new Stack<ParserNode>();
		Stack<Character> trigOperatorStack=new Stack<Character>();
		ParserNode trigRoot=null;
		while (tokens[charIndex]!='}'){
			if((this.isOperator(tokens[charIndex]))){
				trigOperatorStack.add(tokens[charIndex]);
				charIndex++;
			}
			else if(tokens[charIndex]=='('){
				trigOperatorStack.add(tokens[charIndex]);
				charIndex++;
			}
			else if(tokens[charIndex]==')'){
				if(trigOperatorStack.isEmpty()){
					System.out.println("Syntax error");
					break;
				}
				else{
					trigOperatorStack.add(tokens[charIndex]);
					char thisOperator=trigOperatorStack.pop();
					while(!isOperator(thisOperator)){
						thisOperator=trigOperatorStack.pop();
					}
					if(trigOperatorStack.pop()=='('){
						ParserNode thisNode=new ParserNode(thisOperator);
						thisNode.setRightTree(trigOperandStack.pop());
						thisNode.setLeftTree(trigOperandStack.pop());
						trigOperandStack.add(thisNode);
						trigRoot=thisNode;
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
			else if(tokens[charIndex]=='{'){
				charIndex++;
				continue;
			}
			else{
				ParserNode thisNode=new ParserNode(tokens[charIndex]);
				trigOperandStack.add(thisNode);
				if(pTreeOperandValue.get(tokens[charIndex])!=null){
					thisNode.setValue(pTreeOperandValue.get(tokens[charIndex]));
				}
				else{
					thisNode.setValue(Double.valueOf(Character.toString(thisNode.getToken())));
				}
				charIndex++;
			}
		}
		System.out.println("Trignometary Tree");
		this.printPostFixNotatin(trigRoot);
		ParserNode trigNode=new ParserNode('$');
		Double trigNodeValue= evaluateTree(trigRoot);
		trigNode.setValue(Math.sin(trigNodeValue));
		System.out.println();
		return trigNode ;
	}
	
	private ParserNode constructUnaryOperatorNode(int tokensCounter) {
		charIndex=charIndex+3;
		boolean start=false;
		Integer startValue = 0;
		Integer endValue=0;
		boolean node=false;
		ParserNode OperandNode=null;
		ParserNode sumRoot=null;
		Stack<ParserNode> sumOperandStack=new Stack<ParserNode>();
		Stack<Character> sumOperatorStack=new Stack<Character>();
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
		System.out.println(tokens[charIndex]);
		System.out.println("Done with Initialize sum");
		while (tokens[charIndex]!='}'){
			if((this.isOperator(tokens[charIndex]))){
				sumOperatorStack.add(tokens[charIndex]);
				charIndex++;
			}
			else if(tokens[charIndex]=='('){
				sumOperatorStack.add(tokens[charIndex]);
				charIndex++;
			}
			else if(tokens[charIndex]==')'){
				if(sumOperatorStack.isEmpty()){
					System.out.println("Syntax error");
					break;
				}
				else{
					sumOperatorStack.add(tokens[charIndex]);
					char thisOperator=sumOperatorStack.pop();
					while(!isOperator(thisOperator)){
						thisOperator=sumOperatorStack.pop();
					}
					if(sumOperatorStack.pop()=='('){
						ParserNode thisNode=new ParserNode(thisOperator);
						thisNode.setRightTree(sumOperandStack.pop());
						thisNode.setLeftTree(sumOperandStack.pop());
						sumOperandStack.add(thisNode);
						sumRoot=thisNode;
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
				System.out.println("here");
				ParserNode thisNode=new ParserNode(tokens[charIndex]);
				sumOperandStack.add(thisNode);
				charIndex++;
				System.out.println("done");
			}
		}	 
		this.printPostFixNotatin(sumRoot);
		Double sumValue=0.0;
		System.out.println();
		System.out.println(OperandNode.getToken());
		System.out.println(startValue);
		System.out.println(endValue);
		for(int k=startValue;k<=endValue;k++){
			this.initializeSumTree(sumRoot,k,OperandNode);
			sumValue=sumValue+evaluateTree(sumRoot);
		}
		System.out.println();
		System.out.println("Done with creating sum node");
		System.out.println("Sum Value :"+sumValue);
		ParserNode unaryNode=new ParserNode('$');
		unaryNode.setValue(sumValue);
		return unaryNode;
	}
	private void initializeSumTree(ParserNode sumRoot, int k, ParserNode operandNode) {
		Queue<ParserNode> nodes = new LinkedList<ParserNode>();
		nodes.add(sumRoot);
		while (!nodes.isEmpty()){
			if(nodes.peek().getLeftTree()!=null)
				nodes.add(nodes.peek().getLeftTree());
			if(nodes.peek().getRightTree()!=null)
				nodes.add(nodes.peek().getRightTree());
			if(nodes.peek().getToken()==operandNode.getToken()){
//				System.out.println(k);
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
		printPostFixNotatin(root);
	}
	
	public void evaluateExpression(){
		System.out.println();
		root=parseTree.peek();
		System.out.println(evaluateTree(root));
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

