package com.example.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 * @author ����Բ
 * �������entity
 */
@SuppressWarnings("serial")
public class Fragmen2_Mycoach_Table_ShowEntity{
public String date;//ʱ��
public String DayTimeStr;//������
//-- 
public int cdID;//����ID
public String cdStr;//��������
//--
public int SubjectsId;//��ĿID
public int CarID,id,MycoachID;//��id,ԤԼ��������ID  ����id 
public int Sum,Reduce,Surplus;//sum  ԤԼ������  ��ԤԼ�˼�  ʣ������
public int yes_on;//�Ƿ����ԤԼ
public List<Order_Spinner_TimeEntity> list;//ʱ�䳡�ηֶ�
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
