package com.example.testpic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.utils.Filepath;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class FileUtils
{

	public static String SDPATH =Filepath.PostingimgPath+"/";
	public static void saveBitmap(Bitmap bm, String picName)
	{
		Log.e("", "保存图片");
		try
		{
			if (!isFileExist(""))
			{
				File tempf = createSDDir("");
			}
			File f = new File(SDPATH, picName + ".jpeg");
			if (f.exists())
			{
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			Log.e("", "保存成功");
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static File createSDDir(String dirName) throws IOException
	{
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
		{

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName)
	{
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}

	public static void delFile(String fileName)
	{
		File file = new File(SDPATH + fileName);
		if (file.isFile())
		{
			file.delete();
		}
		file.exists();
	}

	public static void deleteDir()
	{
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles())
		{
			if (file.isFile())
				file.delete(); // 删除所有文件
			
			else if (file.isDirectory())
				   deleteDir(); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	public static boolean fileIsExists(String path)
	{
		try
		{
			File f = new File(path);
			if (!f.exists())
			{
				return false;
			}
		} catch (Exception e)
		{

			return false;
		}
		return true;
	}
	
	/** 
     *获取缓存文件夹下的所有缓存图片
     * @return 
     */  
    public static List<String> getImagePathFromSD() {  
        List<String> imagePathList = new ArrayList<String>();  
        File fileAll = new File(SDPATH);  
        File[] files = fileAll.listFiles();  
     
        for (int i = 0; i < files.length; i++) {  
            File file = files[i];  
            if (checkIsImageFile(file.getPath())) {  
                imagePathList.add(file.getPath());  
            }  
        }  
        return imagePathList;  
    }  
    /** 
     * 检查扩展名，得到图片格式的文件 
     * @param fName  文件名 
     * @return 
     */  
    @SuppressLint("DefaultLocale")  
    public static boolean checkIsImageFile(String fName) {  
        boolean isImageFile = false;  
        // 获取扩展名  
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,  
                fName.length()).toLowerCase();  
        if (FileEnd.equals("jpeg")) {  
            isImageFile = true;  
        } else {  
            isImageFile = false;  
        }  
        return isImageFile;  
    }  
  
    
}
