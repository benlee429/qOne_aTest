package com.example.qone_atest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Task <E extends Comparable<E>>{
	private String name;
	private String owner;
	private int taskId;
	private String TaskType;
	private int duration;
	private boolean visible;
	private int startTime;
	private int endTime;
	
	public Task(String name, String owner){
		this.name = name;
		this.owner = owner;
		String newId = name + this.owner;
		this.taskId = newId.hashCode();
		this.TaskType = "generic";
		this.visible = true;
	}
	
	public int getId(){
		return this.taskId;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getType(){
		return this.TaskType;
	}
	
	public int getDur(){
		return this.duration; //HHmmss
	}
	
	public String getOwner(){
		return this.owner;
	}
	
	public void timeInit(){
		this.startTime = time(); 
	}
	
	public void timeEnd(){
		this.endTime = time();
		this.duration = this.startTime - this.endTime; //HHmmss
	}
	
	public void setDuration(int duration){
		this.duration = duration;
	}
	
	public void toggleVis(){
		this.visible = !this.visible;
	}
	
	public int compareTo(Object o){
		if(!(o instanceof Task)){
			System.err.println("bl-parameter isn't a Task");
			throw new IllegalArgumentException();
		}
		Task other = (Task) o;
		String otherName = other.getName();
		return this.name.compareTo(otherName);
	}
	
	public boolean equals(Object o){
		if(o instanceof Task){
			Task other = (Task) o;
			String otherName = other.getName();
			return this.name.equals(otherName);
		} else {
			return false;
		}
		
	}
	
	private int time(){
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		String time = sdf.format(cal.getTime());
		return Integer.parseInt(time);
	}
}
