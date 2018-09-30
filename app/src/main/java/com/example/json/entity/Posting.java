package com.example.json.entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Posting {

    public int PostingID=0;//帖子ID
    public String PostingTitle;//帖子标题
    public String PostingConten;//帖子内容
    public String date;//发贴时间
    
    public int identityID;// 身份类型ID
    public String identity; //身份
    public int typesID;  //ID
    public String name; //用户名名 学员 教练或者用户名
    public String headImg; //头像地址
    
    
    public ArrayList<String> PostingImg=new ArrayList<String>(); //帖子的图片url集合
    

}