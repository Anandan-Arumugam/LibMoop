package com.Parser.anandan;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {
	private BinaryTreeNode root;
	
	public void getLevelOrder(){
		Queue<BinaryTreeNode> nodes = new LinkedList<BinaryTreeNode>();
		nodes.add(this.root);
		while (nodes.isEmpty()){
			if(nodes.peek().getLeftTree()!=null)
				nodes.add(nodes.peek().getLeftTree());
			if(nodes.peek().getRightTree()!=null)
				nodes.add(nodes.peek().getRightTree());
			System.out.println(nodes.poll().getData());
		}
	}
}
