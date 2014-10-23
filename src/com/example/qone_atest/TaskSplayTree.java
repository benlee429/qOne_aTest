//10-13 splay tree (sorta) implemented with an array.

package com.example.qone_atest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Stack;

//bl- bst for tasks
public class TaskSplayTree {
	private TaskTreeNode[] data;
	private int size;

	public TaskSplayTree(){
		new TaskTree(null);
	}

	//bl-untested
	public TaskSplayTree(Task rootTask){
		if(rootTask == null){
			clear();
		} else {
			this.data = new TaskTreeNode[10];
			this.data[0] = new TaskTreeNode(rootTask);
			this.size = 1;
		}
	}

	public int getSize(){
		return this.size;
	}

	public void clear(){
		if(this.size == 0){
			this.size = 0;
			this.data = new TaskTreeNode[10];
		}
	}

	//bl-untested
	public String toString(){
		return "not done";
	}

	//bl - untested
	public void add(Object o){
		objectCheck(o);
		TaskTreeNode newNode = convertObject(o);
		if (this.size == 0){
			this.data[0] = newNode;
			this.size++;
		} else{
			if (!contains(newNode)){
				dataCheck();
				TaskTreeNode temp = this.data[2];
				this.data[2] = this.data[1];
				this.data[1] = this.data[0];
				this.data[0] = newNode;
				this.data[this.size] = temp;
				this.size++;
			}
		}
	}

	private void dataCheck(){
	  if(this.size == this.data.length){
	    TaskTreeNode[] biggerData = new TaskTreeNode[this.size * 2];
	    for(int i = 0; i <= this.size; i++){
	      biggerData[i] = this.data[i];
	    }
	    this.data = biggerData;
	  }
	}

	//bl-untested
	public void remove(Object o){
		objectCheck(o);
		TaskTreeNode deadNode = convertObject(o);
		for(int i = 0; i <= this.size; i++){
		  if (this.data[i].equals(deadNode)){
		    if(i < this.size){
		      this.data[i] = this.data[this.size];
		      this.data[this.size] = null;
		    } else {
		      this.data[i] = null;
		    }
		    this.size--;
		  }
		}
	}

	//bl-untested
	public void removeLast(){
		if(this.size == 0){
			throw new IllegalArgumentException();
		}
		this.data[0] = this.data[this.size];
		this.data[this.size] = null;
		this.size--;
	}

	//bl- untested
	public boolean contains(Object o){
		objectCheck(o);
		TaskTreeNode newNode = convertObject(o);
		if(this.size == 0){
			return false;
		} else {
			for(int i = 0; i <= this.size; i++){
			  if(this.data[i].equals(newNode)){
			    return true;
			  }
			}
			return false;
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

	//bl- TaskTreeNode is meant ONLY for TaskTree. Other classes should be using Task class.
	private class TaskTreeNode <E extends Comparable<E>>{
		public Task node;
		public Stack<TimeEntry> times;

		//bl- make parameters all objects. Do this for all constructors from now on.
		public TaskTreeNode(Task node){
			if(!(node instanceof Task)){
				System.err.println("bl-parameter isn't a task");
				throw new IllegalArgumentException();
			}
			this.node = node;
			this.times = new Stack<TimeEntry>();
		}

		public String getNodeName(){
			return this.node.getName();
		}

		public int count(){
		  return this.times.size();
		}

		public void startEntry(){
		  if(count() > 0 && !ready()){
		    TimeEntry recent = this.times.peek();
		    recent.end();
		  }
		  TimeEntry newEntry = new TimeEntry();
		  newEntry.start();
		  this.times.push(newEntry);
		}

		public void endEntry(){
		  if(count() == 0 || ready()){
		    System.err.println("bl-you're ending an entry that has already ended");
				throw new IllegalArgumentException();
		  }
		  TimeEntry recent = this.times.peek();
		  recent.end();
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

		private boolean ready(){
		  return this.times.peek().status() == 0;
		}
	}
}
	/*
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
	*/
