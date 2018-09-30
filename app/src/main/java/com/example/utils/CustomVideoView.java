package com.example.utils;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.VideoView;

public class CustomVideoView extends VideoView {
    //���յ���Ƶ��Դ���
    private int mVideoWidth=480;
    //������Ƶ��Դ�߶�
    private int mVideoHeight=480;
    //��Ƶ��Դԭʼ���
    private int videoRealW=1;
    //��Ƶ��Դԭʼ�߶�
    private int videoRealH=1;

    public CustomVideoView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void setVideoPath(String path) {
        super.setVideoPath(path);

        MediaMetadataRetriever retr = new MediaMetadataRetriever();
        retr.setDataSource(path);
        String height = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT); // ��Ƶ�߶�
        String width = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH); // ��Ƶ���
        try {
            videoRealH=Integer.parseInt(height);
            videoRealW=Integer.parseInt(width);
        } catch (NumberFormatException e) {
            Log.e("----->" + "VideoView", "setVideoPath:" + e.toString());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        if(height>width){
            //����
            if(videoRealH>videoRealW){
                //�����Ƶ��Դ������
                //ռ����Ļ
                mVideoHeight=height;
                mVideoWidth=width;
            }else {
                //�����Ƶ��Դ�Ǻ���
                //���ռ�����߶ȱ������
                mVideoWidth=width;
                float r=videoRealH/(float)videoRealW;
                mVideoHeight= (int) (mVideoWidth*r);
            }
        }else {
            //����
            if(videoRealH>videoRealW){
                //�����Ƶ��Դ������
                //���ռ�����߶ȱ������
                mVideoHeight=height;
                float r=videoRealW/(float)videoRealH;
                mVideoWidth= (int) (mVideoHeight*r);
            }else {
                //�����Ƶ��Դ�Ǻ���
                //ռ����Ļ
                mVideoHeight=height;
                mVideoWidth=width;
            }
        }
        if(videoRealH==videoRealW&&videoRealH==1){
            //û�ܻ�ȡ����Ƶ��ʵ�Ŀ�ߣ�����Ӧ�Ϳ����ˣ�ʲôҲ������
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        }else {
            setMeasuredDimension(mVideoWidth, mVideoHeight);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //���δ�������¼�
        return true;
    }
}