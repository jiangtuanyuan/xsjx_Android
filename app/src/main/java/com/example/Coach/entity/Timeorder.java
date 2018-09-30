/**
  * Copyright 2018 bejson.com 
  */
package com.example.Coach.entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generated: 2018-02-26 17:17:53
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Timeorder {

    private int ccke2id;
    private String ccstarttime;
    private String ccendtime;
    private List<Stuinfo> stuinfo=new ArrayList<Stuinfo>();
    public void setCcke2id(int ccke2id) {
         this.ccke2id = ccke2id;
     }
     public int getCcke2id() {
         return ccke2id;
     }

    public void setCcstarttime(String ccstarttime) {
         this.ccstarttime = ccstarttime;
     }
     public String getCcstarttime() {
         return ccstarttime;
     }

    public void setCcendtime(String ccendtime) {
         this.ccendtime = ccendtime;
     }
     public String getCcendtime() {
         return ccendtime;
     }

    public void setStuinfo(List<Stuinfo> stuinfo) {
         this.stuinfo = stuinfo;
     }
     public List<Stuinfo> getStuinfo() {
         return stuinfo;
     }

}