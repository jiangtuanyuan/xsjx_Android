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
public class Ke3order {

	
    public int cconandoffid=0;//ID
    public int dateid;//日期ID
    public String date;//日期
    public String week;//星期几
    public int daythreeid;//上下午的ID
    public String daythreeName;//上下午的名称
    public int state;//开启的状态
    public String liuyan;//教练的留言
    public int ccke3id;//教练的留言
    public int num;//教练的留言
    public List<Stuinfo> stuinfo=new ArrayList<Stuinfo>();

}