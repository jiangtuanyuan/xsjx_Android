/**
  * Copyright 2018 bejson.com 
  */
package com.example.Coach.entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2018-02-26 17:17:53
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Ke2order {

    private int cconandoffid=0;
    private int dateid;
    private String date;
    private String week;
    private int daythreeid;
    private String daythreeName;
    private int state;
    private String liuyan;
    private List<Timeorder> timeorder=new ArrayList<Timeorder>();;
    
    public void setCconandoffid(int cconandoffid) {
         this.cconandoffid = cconandoffid;
     }
     public int getCconandoffid() {
         return cconandoffid;
     }

    public void setDateid(int dateid) {
         this.dateid = dateid;
     }
     public int getDateid() {
         return dateid;
     }

    public void setDate(String date) {
         this.date = date;
     }
     public String getDate() {
         return date;
     }

    public void setWeek(String week) {
         this.week = week;
     }
     public String getWeek() {
         return week;
     }

    public void setDaythreeid(int daythreeid) {
         this.daythreeid = daythreeid;
     }
     public int getDaythreeid() {
         return daythreeid;
     }

    public void setDaythreeName(String daythreeName) {
         this.daythreeName = daythreeName;
     }
     public String getDaythreeName() {
         return daythreeName;
     }

    public void setState(int state) {
         this.state = state;
     }
     public int getState() {
         return state;
     }

    public void setLiuyan(String liuyan) {
         this.liuyan = liuyan;
     }
     public String getLiuyan() {
         return liuyan;
     }

    public void setTimeorder(List<Timeorder> timeorder) {
         this.timeorder = timeorder;
     }
     public List<Timeorder> getTimeorder() {
         return timeorder;
     }

}