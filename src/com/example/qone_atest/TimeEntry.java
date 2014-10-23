package com.example.qone_atest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//bl- TimeEntry for TaskTrees
public class TimeEntry {
  private String[] data;
  private long start;

  public TimeEntry(){
    this.data = new String[4];
    Calendar cal = Calendar.getInstance();
    String todayDate = cal.get(cal.MONTH) + "/" + cal.get(cal.DAY_OF_MONTH) + "/" + cal.get(cal.YEAR);
    this.data[0] = todayDate;
    this.data[1] = null;
    this.data[2] = null;
  }

  public void start(){
    if(status() != 1){
      throw new IllegalArgumentException();
    }
    Calendar cal = Calendar.getInstance();
    this.data[1] = cal.get(cal.HOUR_OF_DAY) + ":" + cal.get(cal.MINUTE) + ":" + cal.get(cal.SECOND);
    this.start = cal.getTimeInMillis();
  }

  public int status(){
    if(this.data[1] == null){
      return 1; //hasn't started
    } else if (this.data[2] == null || this.data[3] == null){
      return 2; //hasn't ended
    } else {
      return 0; //complete entry
    }
  }

  //if this diff way fails,  try: https://stackoverflow.com/questions/5351483/calculate-date-time-difference-in-java
  public void end(){
    if(status() != 2){
      throw new IllegalArgumentException();
    }
    Calendar cal = Calendar.getInstance();
    this.data[2] = cal.get(cal.HOUR_OF_DAY) + ":" + cal.get(cal.MINUTE) + ":" + cal.get(cal.SECOND);
    long diff = cal.getTimeInMillis() - this.start;
    long diffSeconds = diff / 1000 % 60;
    long diffMinutes = diff / (60 * 1000) % 60;
    this.data[3] = diffSeconds + ":" + diffMinutes;
  }
}