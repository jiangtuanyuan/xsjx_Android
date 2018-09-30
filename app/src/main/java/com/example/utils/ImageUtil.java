package com.example.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.InputSource;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class ImageUtil {

	public static Bitmap getBitmapThumbnail(String pathName, int heigth, int width) {
		Options opts = new Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, opts);
		int r1 = opts.outWidth / width;
		int r2 = opts.outHeight / heigth;
		opts.inSampleSize = Math.max(r1, r2);
		if (opts.inSampleSize < 1) {
			opts.inSampleSize = 1;
		}
		opts.inJustDecodeBounds = false; // ��������λͼ��
		Bitmap bitmap = BitmapFactory.decodeFile(pathName, opts);// �ٴθ���ָ��ѡ�����������λͼ���õ��ľͲ���null
		return bitmap;
	}

	public Bitmap getBitmapToNetl(InputStream in, int heigth, int width) {
		Bitmap bitmap=null;
		try {
			ByteArrayOutputStream menoy = new ByteArrayOutputStream();
			int len = 0;
			byte[] b = new byte[512];
			while ((len = in.read(b)) > 0) {
				menoy.write(b, 0, len);
			}
			in.close();
			byte[]  data = menoy.toByteArray();
			Options opts = new Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(data, 0, data.length,opts);
			
			int r1 = opts.outWidth / width;
			int r2 = opts.outHeight / heigth;
			opts.inSampleSize = Math.max(r1, r2);
			if (opts.inSampleSize < 1) {
				opts.inSampleSize = 1;
			}
			opts.inPreferredConfig = Config.RGB_565;
			opts.inJustDecodeBounds = false; // ��������λͼ��
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,opts);// �ٴθ���ָ��ѡ�����������λͼ���õ��ľͲ���null		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	public Bitmap getBitmapThumbnail(Bitmap image,int heigth, int width) {//����ѹ��
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();        
	        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	        if( baos.toByteArray().length / 1024>1024) {//�ж����ͼƬ����1M,����ѹ������������ͼƬ��BitmapFactory.decodeStream��ʱ���    
	            baos.reset();//����baos�����baos
	            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//����ѹ��50%����ѹ��������ݴ�ŵ�baos��
	        }
	        Options newOpts = new Options();
	        //��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��
	        newOpts.inJustDecodeBounds = true;
	        BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length,newOpts);
	        newOpts.inJustDecodeBounds = false;
	        int r1 = newOpts.outWidth / width;
			int r2 = newOpts.outHeight / heigth;
			newOpts.inSampleSize = Math.max(r1, r2);
			if (newOpts.inSampleSize < 1) {
				newOpts.inSampleSize = 1;
			}
	        newOpts.inPreferredConfig = Config.RGB_565;//����ͼƬ��ARGB888��RGB565
	        //���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��    
	      Bitmap  bitmap =  BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length,newOpts);
	        
	        return bitmap;//ѹ���ñ�����С���ٽ�������ѹ��
	}
	
	public  Bitmap compressImage(Bitmap image) {//����ѹ��
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) {    //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��        
            baos.reset();//����baos�����baos
            options -= 10;//ÿ�ζ�����10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ���������baos��ŵ�ByteArrayInputStream��
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream��������ͼƬ
        return bitmap;
    }

}
