package com.example.entity;

public class Fragment2_all_GV_CarEntity {
public int CarId; //��ID
public String CarImg;//��ͼƬ
public String CarNum;//����
public String CarCoach;//����
public String CarPlaces;//��ԤԼ������
public String CarAdvPlaces;//��ԤԼ����
public String CarRemainPlaces; //ʣ������

//���췽��
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
