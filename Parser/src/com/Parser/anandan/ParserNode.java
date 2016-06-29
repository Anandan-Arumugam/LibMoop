package com.Parser.anandan;

public class ParserNode {
	private char token;
	private double value;
	private ParserNode LeftTree;
	private ParserNode rightTree;
	public ParserNode(char token) {
		this.setToken(token);
		LeftTree =null;
		rightTree=null;
		setValue(0);
	}
	public ParserNode getLeftTree() {
		return LeftTree;
	}
	public void setLeftTree(ParserNode leftTree) {
		LeftTree = leftTree;
	}
	public ParserNode getRightTree() {
		return rightTree;
	}
	public void setRightTree(ParserNode rightTree) {
		this.rightTree = rightTree;
	}
	public char getToken() {
		return token;
	}
	public void setToken(char token) {
		this.token = token;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
}
