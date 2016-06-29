package com.Parser.anandan;

public class BinaryTreeNode {
	private int data;
	private BinaryTreeNode LeftTree;
	private BinaryTreeNode rightTree;
	public BinaryTreeNode(int data) {
		this.data=data;
		LeftTree =null;
		rightTree=null;
	}
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	public BinaryTreeNode getLeftTree() {
		return LeftTree;
	}
	public void setLeftTree(BinaryTreeNode leftTree) {
		LeftTree = leftTree;
	}
	public BinaryTreeNode getRightTree() {
		return rightTree;
	}
	public void setRightTree(BinaryTreeNode rightTree) {
		this.rightTree = rightTree;
	}
}
