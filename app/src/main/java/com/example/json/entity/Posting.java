package com.example.json.entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Posting {

    public int PostingID=0;//����ID
    public String PostingTitle;//���ӱ���
    public String PostingConten;//��������
    public String date;//����ʱ��
    
    public int identityID;// �������ID
    public String identity; //���
    public int typesID;  //ID
    public String name; //�û����� ѧԱ ���������û���
    public String headImg; //ͷ���ַ
    
    
    public ArrayList<String> PostingImg=new ArrayList<String>(); //���ӵ�ͼƬurl����
    

}