package com.example.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xutils.image.ImageOptions;

import com.example.entity.Video;
import com.example.testpic.PublishedActivity;
import com.example.xsjx.CheckStandActivity;
import com.example.xsjx.MainApplication;
import com.example.xsjx.R;
import com.example.xsjx.VideoPlayActivity;
import com.example.xsjx.WebActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.os.Message;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Utils {

	public static String TAG="Utils";
	
	/**
	 * ��ȡδ��6������� ���ڼ�
	 */
	//private static List<String> daylist = new ArrayList<String>();
	public static List<String> getday(int dayi) {
		
		List<String> daylist = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // yyyy-MM-dd
																	// HH:mm:ss	
		String[] weeks = { "\n������", "\n����һ", "\n���ڶ�", "\n������", "\n������", "\n������", "\n������" };
		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		for (int i = 1; i < dayi + 1; i++) {
			calendar.add(Calendar.DAY_OF_MONTH, +1);
			date = calendar.getTime();
			int week_index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			if (week_index < 0) {
				week_index = 0;
			}
			String xingqi = weeks[week_index];

			String dateString = sdf.format(date) + xingqi;
			daylist.add(dateString);

		}
		return daylist;
	}

	/**
	 * @param str
	 * @return �ж��Ƿ���ת��Ϊ����
	 */
	public static boolean isNoNumber(String str) {
		Pattern pattern = Pattern.compile("^[0-9]+$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

/**
 * ��ȡ��ǰϵͳʱ��  
 */
	public static String getDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datestr = sdf.format(date);
		return datestr.replace(" ", "");
	}
	
	/**
	 * ͨ��һ��Url��ַ ��ȡ��Url���ļ���
	 * ���磺http://192.168.137.55:8080/XSJX/SchoolTopImg/school1.jpg ����
	 * school1.jpg
	 * 
	 * @author ����Բ
	 * @Date 2017��11��28�� ����8:45:37
	 */
	public static String isUrlFileName(String url) {

		String suffixes = "avi|mpeg|3gp|mp3|mp4|wav|jpeg|gif|jpg|png|apk|exe|txt|html|htm|java|doc|jsp";

		// String
		// url="http://192.168.137.55:8080/XSJX/SchoolTopImg/0g6a2ga2gschool1.jpg";

		String file = url.substring(url.lastIndexOf('/') + 1);// ��ȡurl��������
		Pattern pat = Pattern.compile("[\\w]+[\\.](" + suffixes + ")");// �����ж�
		Matcher mc = pat.matcher(file);
		while (mc.find()) {
			String substring = mc.group();// ��ȡ�ļ�����׺��
			return substring;
		}
		return null;
	}
	
	/** 
     * ��ȡ���ӵĺ�׺�� 
     * @return 
     */  
    public static String isUrlSuffix(String url) {  
    	Pattern pattern = Pattern.compile("\\S*[?]\\S*");  
        Matcher matcher = pattern.matcher(url);  
  
        String[] spUrl = url.toString().split("/");  
        int len = spUrl.length;  
        String endUrl = spUrl[len - 1];  
  
        if(matcher.find()) {  
            String[] spEndUrl = endUrl.split("\\?");  
            return spEndUrl[0].split("\\.")[1];  
        }  
        return endUrl.split("\\.")[1];  
    }  
	
	/**
	 * ����ͼƬ��ָ��·��
	 */
	public static void ImgDoload(final String Url, final String LoadImgPath) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL(Url);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setReadTimeout(8000);
					conn.setConnectTimeout(8000);
					if (conn.getResponseCode() == 200) {

						InputStream in = url.openStream();// ��ͼƬ��Դ
						File file = new File(LoadImgPath);
						if (!file.getParentFile().mkdirs()) {
							FileOutputStream out = new FileOutputStream(file);
							int len = 0;
							byte[] buf = new byte[1024];
							Log.v(TAG+"ImgDoload", "����ͼƬ"+LoadImgPath);
							while ((len = in.read(buf)) > 0)
								out.write(buf, 0, len);
							Log.v(TAG+"ImgDoload", "���سɹ�!");
							out.close();
							in.close();

						} 
					else {
							int size = 0;
							FileInputStream fis = new FileInputStream(file);
							size = fis.available();
							fis.close();
							if (!(size == conn.getContentLength())) {
								Log.v(TAG+"ImgDoload", "ͼƬ��С��һ�£���������!");
								FileOutputStream out = new FileOutputStream(file);

								int len = 0;
								byte[] buf = new byte[1024];
								while ((len = in.read(buf)) > 0)
									out.write(buf, 0, len);
								Log.v(TAG+"ImgDoload", "���سɹ�");

								out.close();
								in.close();

							} else {
								 Log.v(TAG+"ImgDoload", "ͼƬ�Ѵ���,��������!");
							}
						}
					}

				} catch (MalformedURLException e) {
					 Log.v(TAG+"ImgDoload", e.toString());
					e.printStackTrace();
				} catch (IOException e) {
					 Log.v(TAG+"ImgDoload", e.toString());
					e.printStackTrace();
				}

				
			}
		}).start();;
	
	}
	/**
	 * ʱ��ת����������
	 * ���������2017-11-29 15:18:09.0
	 * ת��   2017-11-29
	 */
	public static String isYMD(String dateStr){
		Date date=new Date(dateStr);
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
		return s.format(date);
	}
	
	
	/**
	 * ���ɼ��ܵ�MD5 ����� String ���ͷ��غ�����
	 */
	public static  String isMD5Rand(String s){
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] btInput = s.getBytes();
            // ���MD5ժҪ�㷨�� MessageDigest ����
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // ʹ��ָ�����ֽڸ���ժҪ
            mdInst.update(btInput);
            // �������
            byte[] md = mdInst.digest();
            // ������ת����ʮ�����Ƶ��ַ�����ʽ
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
		
	}
	
	/**
	 * ����Toast�ķ��� 
	 */
	public static void showToast(Context ctx, int id, String str) {
        if (str == null) {
            return;
        }
        Toast toast = Toast.makeText(ctx, ctx.getString(id) + str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
	/**
	 * ����Toast�ķ��� 
	 */
    public static void showToast(Context ctx, String errInfo) {
        if (errInfo == null) {
            return;
        }
        Toast toast = Toast.makeText(ctx, errInfo, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
	
    /**
     *  
     *         ���Ƶ����� 
     */  
    public static boolean isCarCodeName(String carCodeName) {  
//      Pattern p = Pattern.compile("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$");  
        Pattern p = Pattern.compile("^[(\u4e00-\u9fa5)|(a-zA-Z)]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{4,6}[a-zA-Z_0-9_\u4e00-\u9fa5]$");  
        Matcher m = p.matcher(carCodeName);  
        return m.matches();  
    }  
    
    /**
     * ���֤��֤
     */
    
    public static String IDCardValidate(String IDStr) throws ParseException {  
        String errorInfo = "";// ��¼������Ϣ  
        String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",  
                "3", "2" };  
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",  
                "9", "10", "5", "8", "4", "2" };  
        String Ai = "";  
        // ================ ����ĳ��� 15λ��18λ ================  
        if (IDStr.length() != 15 && IDStr.length() != 18) {  
            errorInfo = "���֤���볤��Ӧ��Ϊ15λ��18λ��";  
            return errorInfo;  
        }  
        // =======================(end)========================  
  
        // ================ ���� �������Ϊ��Ϊ���� ================  
        if (IDStr.length() == 18) {  
            Ai = IDStr.substring(0, 17);  
        } else if (IDStr.length() == 15) {  
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);  
        }  
        if (isNumeric(Ai) == false) {  
            errorInfo = "���֤15λ���붼ӦΪ���� ; 18λ��������һλ�⣬��ӦΪ���֡�";  
            return errorInfo;  
        }  
        // =======================(end)========================  
  
        // ================ ���������Ƿ���Ч ================  
        String strYear = Ai.substring(6, 10);// ���  
        String strMonth = Ai.substring(10, 12);// �·�  
        String strDay = Ai.substring(12, 14);// �·�  
        if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {  
            errorInfo = "���֤������Ч��";  
            return errorInfo;  
        }  
        GregorianCalendar gc = new GregorianCalendar();  
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");  
        if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150  
                || (gc.getTime().getTime() - s.parse(  
                        strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {  
            errorInfo = "���֤���ղ�����Ч��Χ��";  
            return errorInfo;  
        }  
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {  
            errorInfo = "���֤�·���Ч";  
            return errorInfo;  
        }  
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {  
            errorInfo = "���֤������Ч";  
            return errorInfo;  
        }  
        // =====================(end)=====================  
  
        // ================ ������ʱ����Ч ================  
        Hashtable h = GetAreaCode();  
        if (h.get(Ai.substring(0, 2)) == null) {  
            errorInfo = "���֤�����������";  
            return errorInfo;  
        }  
        // ==============================================  
  
        // ================ �ж����һλ��ֵ ================  
        int TotalmulAiWi = 0;  
        for (int i = 0; i < 17; i++) {  
            TotalmulAiWi = TotalmulAiWi  
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))  
                    * Integer.parseInt(Wi[i]);  
        }  
        int modValue = TotalmulAiWi % 11;  
        String strVerifyCode = ValCodeArr[modValue];  
        Ai = Ai + strVerifyCode;  
  
        if (IDStr.length() == 18) {  
            if (Ai.equals(IDStr) == false) {  
                errorInfo = "���֤��Ч�����ǺϷ������֤����";  
                return errorInfo;  
            }  
        } else {  
            return "";  
        }  
        // =====================(end)=====================  
        return "right";  
    }
    /** 
     * ���ܣ����õ������� 
     *  
     * @return Hashtable ���� 
     */ 
    private static Hashtable GetAreaCode() {  
        Hashtable hashtable = new Hashtable();  
        hashtable.put("11", "����");  
        hashtable.put("12", "���");  
        hashtable.put("13", "�ӱ�");  
        hashtable.put("14", "ɽ��");  
        hashtable.put("15", "���ɹ�");  
        hashtable.put("21", "����");  
        hashtable.put("22", "����");  
        hashtable.put("23", "������");  
        hashtable.put("31", "�Ϻ�");  
        hashtable.put("32", "����");  
        hashtable.put("33", "�㽭");  
        hashtable.put("34", "����");  
        hashtable.put("35", "����");  
        hashtable.put("36", "����");  
        hashtable.put("37", "ɽ��");  
        hashtable.put("41", "����");  
        hashtable.put("42", "����");  
        hashtable.put("43", "����");  
        hashtable.put("44", "�㶫");  
        hashtable.put("45", "����");  
        hashtable.put("46", "����");  
        hashtable.put("50", "����");  
        hashtable.put("51", "�Ĵ�");  
        hashtable.put("52", "����");  
        hashtable.put("53", "����");  
        hashtable.put("54", "����");  
        hashtable.put("61", "����");  
        hashtable.put("62", "����");  
        hashtable.put("63", "�ຣ");  
        hashtable.put("64", "����");  
        hashtable.put("65", "�½�");  
        hashtable.put("71", "̨��");  
        hashtable.put("81", "���");  
        hashtable.put("82", "����");  
        hashtable.put("91", "����");  
        return hashtable;  
    }  
  
    /** 
     * ��֤�����ַ����Ƿ���YYYY-MM-DD��ʽ 
     *  
     * @param str 
     * @return 
     */  
    public static boolean isDataFormat(String str) {  
        boolean flag = false;  
        // String  
        // regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";  
        String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";  
        Pattern pattern1 = Pattern.compile(regxStr);  
        Matcher isNo = pattern1.matcher(str);  
        if (isNo.matches()) {  
            flag = true;  
        }  
        return flag;  
    }  
  
    /** 
     * ���ܣ��ж��ַ����Ƿ�Ϊ���� 
     *  
     * @param str 
     * @return 
     */  
    private static boolean isNumeric(String str) {  
        Pattern pattern = Pattern.compile("[0-9]*");  
        Matcher isNum = pattern.matcher(str);  
        if (isNum.matches()) {  
            return true;  
        } else {  
            return false;  
        }  
    }  
    // ���֤������֤��end  
      
    private String getAlpha(String str) {        
        if(isEmpty(str))  
            return "#";  
          
        String sortStr = str.trim().substring(0, 1).toUpperCase();  
        // ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ  
        if (sortStr.matches("[A-Z]")) {  
            return sortStr;  
        } else {  
            return "#";  
        }  
    }  
    public static boolean isEmpty(CharSequence str) {  
        if (str == null || "".equals(str) || "null".equals(str)  
                || "NULL".equals(str)) {  
            return true;  
        }  
        return false;  
    }  
    
    /**
     * ��ת������̨����
     */
    public static void CheckStand(Context context,String orderNam,String orderID,int orderNum,String userName,String userID,Double money){
    	Intent intent=new Intent(context,CheckStandActivity.class);
    	intent.putExtra("orderNam", orderNam);
    	intent.putExtra("orderID", orderID);
    	intent.putExtra("orderNum", orderNum);
    	intent.putExtra("userName", userName);
    	intent.putExtra("userID", userID);
    	intent.putExtra("money", money);
    	context.startActivity(intent);
    }
    /**
     * X ͼƬ���ؿ�ܵ�ͼƬ���� ��ƵList���ط���ͼƬ
     */
    public static ImageOptions Ximage(){
    	ImageOptions image = new ImageOptions.Builder()
			      //���ü��ع����е�ͼƬ
			      .setLoadingDrawableId(R.drawable.video_jzz)
			      //���ü���ʧ�ܺ��ͼƬ
			      .setFailureDrawableId(R.drawable.video_jzsb)
			      .setSquare(true)
			      .setFadeIn(true)
			      .build();
		return image;
	
    }
    
    /**
     * X ͼƬ���ؿ�ܵ�ͼƬ����  ͷ������� Ĭ����log
     */
    public static ImageOptions Xheadimg(){
    	ImageOptions image = new ImageOptions.Builder()
			      //���ü��ع����е�ͼƬ
			      .setLoadingDrawableId(R.drawable.xsjxlog)
			      //���ü���ʧ�ܺ��ͼƬ
			      .setFailureDrawableId(R.drawable.xsjxlog)
			      .setSquare(true)
			      .setFadeIn(true)
			      .setCircular(true)
			      .build();
    	
    	
		return image;
    }
    
    /**
     * X ͼƬ���ؿ�ܵ�ͼƬ����  ͷ������� Ĭ����log
     */
    public static ImageOptions XRunimag(){
    	ImageOptions image = new ImageOptions.Builder()
			      //���ü��ع����е�ͼƬ
			      .setLoadingDrawableId(R.drawable.xsjx)
			      //���ü���ʧ�ܺ��ͼƬ
			      .setFailureDrawableId(R.drawable.xsjx)
			      .setSquare(true)
			      .setFadeIn(true)
			      .setCircular(false)
			      .build();
    	
    	
		return image;
    }
    
    
    /**
     * X ���� ͷ����ؿ�ܵ�ͼƬ����  ͷ������� Ĭ����log
     */
    public static ImageOptions XCoachHeadimg(){
    	ImageOptions image = new ImageOptions.Builder()
			      //���ü��ع����е�ͼƬ
			      .setLoadingDrawableId(R.drawable.coachheadlog)
			      //���ü���ʧ�ܺ��ͼƬ
			      .setFailureDrawableId(R.drawable.coachheadlog)
			      .setSquare(true)
			      .setFadeIn(true)
			      .setCircular(true)
			      .build();

		return image;
    }
    
    /**
     * X ���� ͷ����ؿ�ܵ�ͼƬ����  ͷ������� Ĭ����log  Բ��
     */
    public static ImageOptions XCoachCireHeadimg(){
    	ImageOptions image = new ImageOptions.Builder()
			      //���ü��ع����е�ͼƬ
			      .setLoadingDrawableId(R.drawable.coachheadlog)
			      //���ü���ʧ�ܺ��ͼƬ
			      .setFailureDrawableId(R.drawable.coachheadlog)
			      .setSquare(true)
			      .setFadeIn(true)
			      .setCircular(true)
			      .build();

		return image;
    }
    
    
    /**
     * X ͼƬ���ؿ�ܵ�ͼƬ����  ͷ������� Ĭ����log
     */
    public static ImageOptions XCiecHeadimg(){
    	ImageOptions image = new ImageOptions.Builder()
			      //���ü��ع����е�ͼƬ
			      .setLoadingDrawableId(R.drawable.log)
			      //���ü���ʧ�ܺ��ͼƬ
			      .setFailureDrawableId(R.drawable.log)
			      .setSquare(true)
			      .setFadeIn(true)
			      .setCircular(true)
			      .build();
    	
    	
		return image;
    }
    /**
     * x У����Ѷ ͷ���ֲ�ͼƬ����ʧ�� ʹ�õĿ��
     */
    public static ImageOptions XShcoolTopimg(){
    	ImageOptions image = new ImageOptions.Builder()
			      //���ü��ع����е�ͼƬ
			      .setLoadingDrawableId(R.drawable.scholltopimg)
			      //���ü���ʧ�ܺ��ͼƬ
			      .setFailureDrawableId(R.drawable.scholltopimg)
			      .setSquare(true)
			      .setFadeIn(true)
			      .setCircular(false)
			      .setSize(MainApplication.phoneWidth, MainApplication.phoneHeight)
			      .build();
    	
    	
		return image;
    }
    
    /**
     * X ͼƬ���ؿ�ܵ�ͼƬ����  ͷ������� Ĭ����log
     */
    public static ImageOptions Xgvimgno(){
    	ImageOptions image = new ImageOptions.Builder()
			      //���ü��ع����е�ͼƬ
			      .setLoadingDrawableId(R.drawable.imgurlno)
			      //���ü���ʧ�ܺ��ͼƬ
			      .setFailureDrawableId(R.drawable.imgurlno)
			      .setSquare(true)
			      .setFadeIn(true)
			      .setCircular(false)
			      .build();
    	
    	
		return image;
    }
    
    /**
     * ��ת����Ƶ���Ž���
     */
    public static void startVideoPlayAT(Context context,Video video){
    	Intent intent=new Intent(context,VideoPlayActivity.class);
    	intent.putExtra("id",video.id);//ID
    	
    	intent.putExtra("name",video.name);//��Ƶ����
    	
    	intent.putExtra("img",video.imgurl);//ͼƬ��ַ
    	intent.putExtra("url",video.url);//��Ƶ��ַ
    	intent.putExtra("ksyq",video.ksyq);//����Ҫ��
    	intent.putExtra("ppbz",video.ppbz);//���б�׼
    	intent.putExtra("czff",video.czff);//��������
    	intent.putExtra("zysx",video.zysx);//ע������
    	context.startActivity(intent);
    }
    /**
     * У����Ѷ��ת��WEB
     */
	public static void intentWebActity(Context context,String Url, String webname) {
		Intent webintent = new Intent(context, WebActivity.class);
		webintent.putExtra("Url", Url);
		webintent.putExtra("Webname", webname);
		context.startActivity(webintent);

	}
    
    
    /**
     * ��ʾ���ؿ�  ��ʾ������    ����ᱨ�쳣  ��ʱ����  208 1 27
     */
	public static Dialog showDialog(Context context, String s) {
		LayoutInflater inflatera = LayoutInflater.from(context);
		Dialog dialog = new AlertDialog.Builder(context).create();
		dialog.setCancelable(false);
		dialog.show();
		
		
		// ע��˴�Ҫ����show֮�� ����ᱨ�쳣
		View v = inflatera.inflate(R.layout.loading_process_dialog_anim, null);
		TextView stv = (TextView) v.findViewById(R.id.loading_dialog_tv);
		stv.setText(s);
		dialog.setContentView(v);
		return dialog;
	}
    
	/**
	 * ��ֹ�������������4λ�ֽڵķ���
	 * leng ����
	 */
	public static InputFilter[] getInputFilter(final Context context,int leng){
		 InputFilter emojiFilter = new InputFilter() {
		        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
		                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		        @Override
		        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		            Matcher emojiMatcher = emoji.matcher(source);
		            if (emojiMatcher.find()) {
		               Utils.showToast(context, "Sorry,�ݲ�֧���������!");
		                return "";
		            }
		            return null;
		        }
		    };
		InputFilter[] e= {emojiFilter,new InputFilter.LengthFilter(leng)};

		return e;
	}
	
	/**
	 * ����ListView�ĸ߶�    �����õĻ��ᵼ��ListView ֻ��ʾһ������
	 */
   public static void setListViewHeightBasedOnChildren(ListView listView) {   
	        // ��ȡListView��Ӧ��Adapter   
	        ListAdapter listAdapter = listView.getAdapter();   
	        if (listAdapter == null) {   
	            return;   
	        }   
	   
	        int totalHeight = 0;   
	        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
	            // listAdapter.getCount()�������������Ŀ   
	            View listItem = listAdapter.getView(i, null, listView);   
	            // ��������View �Ŀ��   
	            listItem.measure(0, 0);    
	            // ͳ������������ܸ߶�   
	            totalHeight += listItem.getMeasuredHeight();    
	        }   
	        ViewGroup.LayoutParams params = listView.getLayoutParams();   
	        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
	        // listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�   
	        // params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�   
	        listView.setLayoutParams(params);   
	    } 
	
	/**
	 * ��������ʱ��������ٷ��� ���磺1:21 - 1:31   ����10����
	 */
   public static int TwoTimeDifferTime(int sh,int sm,int eh,int em){
	   int differ=0;
	   //���ж�1��Сʱ�Ƿ����
	   int a=sh-eh;
	  
	   if(a==0){
		   //����ʱ���Сʱ���
		   //�������
		   differ=sm-em;
		      
	   }
	   else if(a>0){
		   //��һ��ʱ���Сʱ���ڵڶ���ʱ���Сʱ
		   differ=a*60+(sm-em);
		   
		   
	   }
	     else{
	    	//�ڶ���ʱ���Сʱ���ڵ�һ��ʱ���Сʱ
		   
	   }

	   return differ;
   }
   
   /**
    * ��������ʱ���  ����ʱ��  13:40 - 14:50  ���� 60
    */
   
   public static int getTimeLeng(String startime,String endtime){
	     int tsh=0,tsm=0,teh=0,tem=0;
	     int sum=0;
	     String sh,sm,eh,em;
	     sh=startime.substring(0, 2);
	     sm=startime.substring(startime.length()-2);
	     eh=endtime.substring(0, 2);
	     em=endtime.substring(startime.length()-2);
	   if(Utils.isNoNumber(sh) && Utils.isNoNumber(sm) && Utils.isNoNumber(eh) && Utils.isNoNumber(em)){
	    	//�������תΪ����
		   tsh=Integer.parseInt(sh);
		   tsm=Integer.parseInt(sm);
		   teh=Integer.parseInt(eh);
		   tem=Integer.parseInt(em);
		   
		   sum=(teh - tsh) * 60 + (tem - tsm); 
	     }
 
	   return sum;
   } 
   
   
  /**
   * �ж��ֻ��Ƿ��еײ����ⰴ��
   */
   public static boolean checkDeviceHasNavigationBar(Context activity) {  
 
       //ͨ���ж��豸�Ƿ��з��ؼ����˵���(���������,���ֻ���Ļ��İ���)��ȷ���Ƿ���navigation bar  
       boolean hasMenuKey = ViewConfiguration.get(activity)  
               .hasPermanentMenuKey();  
       boolean hasBackKey = KeyCharacterMap  
               .deviceHasKey(KeyEvent.KEYCODE_BACK);  
       if (!hasMenuKey && !hasBackKey) {  
           // ���κ�����Ҫ����,����豸��һ��������  
           return true;  
       }  
       return false;  
   }  
   
  
}
