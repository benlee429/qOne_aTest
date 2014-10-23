package com.example.qone_atest;

import java.text.SimpleDateFormat; 
import java.util.Calendar;

//bl- bst for tasks
public class TaskTree {
	private TaskTreeNode root;
	private int size;
	private String lastNodeName;
	
	public TaskTree(){
		new TaskTree(null);
	}
	
	//bl-untested
	public TaskTree(Task rootTask){
		if(rootTask == null){
			clear();
		} else {	
			TaskTreeNode root = new TaskTreeNode(rootTask);
			this.root = root;
			this.size = 1;
			this.lastNodeName = getRootName();
		}
	}
	
	public String getRootName(){
		return this.root.getNodeName();
	}
	
	public int getSize(){
		return this.size;
	}
	
	public String getLastTask(){
		return this.lastNodeName;
	}
	
	public void clear(){
		if(this.size == 0){
			this.size = 0;
			this.root = null;
			this.lastNodeName = null;
		}
	}
	
	//bl-untested
	public String toString(){
		String result = "{";
		String content = "";
		if(this.size > 0){
			content = toStringRec(this.root);
		}
		return content + "}";
	}
	
	//bl - untested
	public void add (Object o){
		objectCheck(o);
		TaskTreeNode newNode = convertObject(o);
		this.lastNodeName = newNode.getNodeName();
		
		if (this.size == 0){
			this.root = newNode;
		} else{
			if (!contains(newNode)){
				TaskTreeNode current = this.root;
				addRec(current, newNode);
			}
		}
		this.size++;
	}
	
	//bl-untested
	public void remove(Object o){
		objectCheck(o);
		TaskTreeNode deadNode = convertObject(o);
		if(this.size > 0 && contains(deadNode)){
			if(this.size == 1 || this.root.equals(deadNode)){
				this.root = null;
			} else {
				removeRec(this.root, deadNode);
			}
			this.size--;
			String deadName = deadNode.getNodeName();
			if(deadName.equals(this.lastNodeName)){
				this.lastNodeName = null;
			}
		}
	}
	
	//bl-untested
	public void removeLast(){
		if(this.size == 0){
			throw new IllegalArgumentException();
		}
		removeLastRec(this.root);
		this.lastNodeName = null;
	}
	
	//bl- untested
	public boolean contains(Object o){
		objectCheck(o);
		TaskTreeNode newNode = convertObject(o);
		if(this.size == 0){
			return false;
		} else {
			return containRec(this.root, newNode);
		}
	}
	
	//bl-untested
	private void objectCheck(Object o){
		if(!(o instanceof Task) || !(o instanceof TaskTreeNode)){
			System.err.println("bl-parameter isn't task or TaskTreeNode.");
			throw new IllegalArgumentException(); 
		}
	}
	
	//bl-untested
	private TaskTreeNode convertObject(Object o){
		TaskTreeNode newNode;
		if (o instanceof Task){
			Task newTask = (Task) o;
			newNode = new TaskTreeNode(newTask) ;
		} else {
			newNode = (TaskTreeNode) o;
		}
		return newNode;
	}
	
	private String toStringRec(TaskTreeNode current){
		if(current == null){
			return "";
		} else if(current.noChild()){
			return current.getNodeName();
		} else {
			String content = "";
			content += toStringRec(current.left) + ", ";
			content += toStringRec(current.right) + ", ";
			content += current.getNodeName() + ", ";
			return content;
		}
	}
	
	private void addRec(TaskTreeNode current, TaskTreeNode newNode){
		if(current.noChild()){
			if(current.compareTo(newNode) < 0){
				current.left = newNode;
			} else {
				current.right = newNode;
			}
		} else {
			if(current.compareTo(newNode) < 0){
				if(current.hasLeft()){
					addRec(current.left, newNode);
				} else {
					current.left = newNode;
				}
			} else {
				if(current.hasRight()){
					addRec(current.right, newNode);
				} else {
					current.right = newNode;
				}
			}
		}
	}
	
	private void removeRec(TaskTreeNode current, TaskTreeNode deadNode){
		if(!current.noChild()){
			if(current.hasLeft()){
				if(current.left.equals(deadNode)){
					current.left = current.left.left;
				} else {
					removeRec(current.left, deadNode);
				}
			}
			if(current.hasRight()){
				if(current.right.equals(deadNode)){
					current.right = current.right.right;
				} else {
					removeRec(current.right, deadNode);
				}
			}
		}
	}
	
	private void removeLastRec(TaskTreeNode current){
		if(!current.noChild()){
			if(current.hasLeft()){
				String leftName = current.left.getNodeName();
				if(current.left.noChild()){
					if(leftName.equals(getLastTask())){
						current.left = null;
					}
				} else {
					removeLastRec(current.left);
				}
			} 
			if(current.hasRight()){
				String rightName = current.right.getNodeName();
				if(current.right.noChild()){
					if(rightName.equals(getLastTask())){
						current.right = null;
					}
				} else {
					removeLastRec(current.right);
				}
			}
		}
	}
	
	private boolean containRec(TaskTreeNode current, TaskTreeNode node){
		if(current == null){
			return false;
		} else if (current.equals(node)){
			return true;
		} else {
			return containRec(current.left, node) || containRec(current.right, node);
		}
	}
	
	//bl- TaskTreeNode is meant ONLY for TaskTree. Other classes should be using Task class.
	private class TaskTreeNode <E extends Comparable<E>>{
		public Task node;
		public TaskTreeNode<E> left;
		public TaskTreeNode<E> right;
		
		public TaskTreeNode(Task node){
			new TaskTreeNode(node, null, null);
		}
		
		//bl- make parameters all objects. Do this for all constructors from now on.
		public TaskTreeNode(Task node, TaskTreeNode left, TaskTreeNode right){
			if(!(node instanceof Task) 
					|| (left != null) && !(left instanceof TaskTreeNode) 
					|| (right!= null) && !(right instanceof TaskTreeNode)){
				System.err.println("bl-parameters aren't tasks.");
				throw new IllegalArgumentException();
			}
			this.node = node;
			this.left = left;
			this.right = right;
		}
		
		public String getNodeName(){
			return this.node.getName();
		}
		
		public boolean hasLeft(){
			return this.left != null;
		}
		
		public boolean hasRight(){
			return this.right != null;
		}
		
		public boolean noChild(){
			return !hasRight() && !hasLeft();
		}
		
		public int compareTo(Object o){
			if(!(o instanceof TaskTreeNode)){
				System.err.println("bl-parameter isn't a TaskTreeNode");
				throw new IllegalArgumentException();
			} 
			TaskTreeNode other = (TaskTreeNode) o;
			return this.node.compareTo(other);
		}
		
		public boolean equals(Object o){
			if(o instanceof TaskTreeNode){
				TaskTreeNode other = (TaskTreeNode) o;
				return this.node.equals(other.node);
			} else {
				return false;
			}
		}
	}
}
