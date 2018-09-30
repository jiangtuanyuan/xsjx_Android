package com.example.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 * @author 蒋团圆
 * 表格数据entity
 */
@SuppressWarnings("serial")
public class Fragmen2_Mycoach_Table_ShowEntity{
public String date;//时间
public String DayTimeStr;//上下午
//-- 
public int cdID;//场地ID
public String cdStr;//场地名称
//--
public int SubjectsId;//科目ID
public int CarID,id,MycoachID;//车id,预约上下晚场次ID  教练id 
public int Sum,Reduce,Surplus;//sum  预约总人数  已预约人家  剩余人数
public int yes_on;//是否可以预约
public List<Order_Spinner_TimeEntity> list;//时间场次分段
public Fragmen2_Mycoach_Table_ShowEntity(){}

public Fragmen2_Mycoach_Table_ShowEntity(String date,String DayTimeStr,int SubjectsId, int CarId,int id, int mycoachID,int sum, int reduce, int surplus,int yes_on,List<Order_Spinner_TimeEntity> list) {
	this.date = date;
	this.DayTimeStr=DayTimeStr;
	this.id = id;
	this.MycoachID = mycoachID;
	this.Sum = sum;
	this.Reduce = reduce;
	this.Surplus = surplus;
	this.yes_on=yes_on;
	this.CarID=CarId;
	this.SubjectsId=SubjectsId;
	this.list=list;
	
	
	
}



}
