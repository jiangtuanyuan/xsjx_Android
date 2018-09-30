package com.example.entity;

public class Fragment2_all_GV_CarEntity {
public int CarId; //车ID
public String CarImg;//车图片
public String CarNum;//车牌
public String CarCoach;//教练
public String CarPlaces;//可预约总名额
public String CarAdvPlaces;//已预约名额
public String CarRemainPlaces; //剩余名额

//构造方法
public Fragment2_all_GV_CarEntity(){}

public Fragment2_all_GV_CarEntity
(int carId, String carImg, String carNum, String carCoach,
		String carPlaces,String carAdvPlaces, String carRemainPlaces)
{
	super();
	CarId = carId;
	CarImg = carImg;
	CarNum = carNum;
	CarCoach = carCoach;
	CarPlaces = carPlaces;
	CarAdvPlaces = carAdvPlaces;
	CarRemainPlaces = carRemainPlaces;
}




}
