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
	 * 获取未来6天的日期 星期几
	 */
	//private static List<String> daylist = new ArrayList<String>();
	public static List<String> getday(int dayi) {
		
		List<String> daylist = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // yyyy-MM-dd
																	// HH:mm:ss	
		String[] weeks = { "\n星期日", "\n星期一", "\n星期二", "\n星期三", "\n星期四", "\n星期五", "\n星期六" };
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
	 * @return 判断是否能转化为整数
	 */
	public static boolean isNoNumber(String str) {
		Pattern pattern = Pattern.compile("^[0-9]+$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

/**
 * 获取当前系统时间  
 */
	public static String getDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datestr = sdf.format(date);
		return datestr.replace(" ", "");
	}
	
	/**
	 * 通过一个Url地址 提取出Url的文件名
	 * 例如：http://192.168.137.55:8080/XSJX/SchoolTopImg/school1.jpg 返回
	 * school1.jpg
	 * 
	 * @author 蒋团圆
	 * @Date 2017年11月28日 下午8:45:37
	 */
	public static String isUrlFileName(String url) {

		String suffixes = "avi|mpeg|3gp|mp3|mp4|wav|jpeg|gif|jpg|png|apk|exe|txt|html|htm|java|doc|jsp";

		// String
		// url="http://192.168.137.55:8080/XSJX/SchoolTopImg/0g6a2ga2gschool1.jpg";

		String file = url.substring(url.lastIndexOf('/') + 1);// 截取url最后的数据
		Pattern pat = Pattern.compile("[\\w]+[\\.](" + suffixes + ")");// 正则判断
		Matcher mc = pat.matcher(file);
		while (mc.find()) {
			String substring = mc.group();// 截取文件名后缀名
			return substring;
		}
		return null;
	}
	
	/** 
     * 获取链接的后缀名 
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
	 * 下载图片到指定路劲
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

						InputStream in = url.openStream();// 打开图片资源
						File file = new File(LoadImgPath);
						if (!file.getParentFile().mkdirs()) {
							FileOutputStream out = new FileOutputStream(file);
							int len = 0;
							byte[] buf = new byte[1024];
							Log.v(TAG+"ImgDoload", "下载图片"+LoadImgPath);
							while ((len = in.read(buf)) > 0)
								out.write(buf, 0, len);
							Log.v(TAG+"ImgDoload", "下载成功!");
							out.close();
							in.close();

						} 
					else {
							int size = 0;
							FileInputStream fis = new FileInputStream(file);
							size = fis.available();
							fis.close();
							if (!(size == conn.getContentLength())) {
								Log.v(TAG+"ImgDoload", "图片大小不一致，重新下载!");
								FileOutputStream out = new FileOutputStream(file);

								int len = 0;
								byte[] buf = new byte[1024];
								while ((len = in.read(buf)) > 0)
									out.write(buf, 0, len);
								Log.v(TAG+"ImgDoload", "下载成功");

								out.close();
								in.close();

							} else {
								 Log.v(TAG+"ImgDoload", "图片已存在,无需下载!");
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
	 * 时间转化成年月日
	 * 例如参数：2017-11-29 15:18:09.0
	 * 转化   2017-11-29
	 */
	public static String isYMD(String dateStr){
		Date date=new Date(dateStr);
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
		return s.format(date);
	}
	
	
	/**
	 * 生成加密的MD5 随机数 String 类型返回和输入
	 */
	public static  String isMD5Rand(String s){
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
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
	 * 弹出Toast的方法 
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
	 * 弹出Toast的方法 
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
     *         车牌的正则 
     */  
    public static boolean isCarCodeName(String carCodeName) {  
//      Pattern p = Pattern.compile("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$");  
        Pattern p = Pattern.compile("^[(\u4e00-\u9fa5)|(a-zA-Z)]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{4,6}[a-zA-Z_0-9_\u4e00-\u9fa5]$");  
        Matcher m = p.matcher(carCodeName);  
        return m.matches();  
    }  
    
    /**
     * 身份证验证
     */
    
    public static String IDCardValidate(String IDStr) throws ParseException {  
        String errorInfo = "";// 记录错误信息  
        String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",  
                "3", "2" };  
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",  
                "9", "10", "5", "8", "4", "2" };  
        String Ai = "";  
        // ================ 号码的长度 15位或18位 ================  
        if (IDStr.length() != 15 && IDStr.length() != 18) {  
            errorInfo = "身份证号码长度应该为15位或18位。";  
            return errorInfo;  
        }  
        // =======================(end)========================  
  
        // ================ 数字 除最后以为都为数字 ================  
        if (IDStr.length() == 18) {  
            Ai = IDStr.substring(0, 17);  
        } else if (IDStr.length() == 15) {  
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);  
        }  
        if (isNumeric(Ai) == false) {  
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";  
            return errorInfo;  
        }  
        // =======================(end)========================  
  
        // ================ 出生年月是否有效 ================  
        String strYear = Ai.substring(6, 10);// 年份  
        String strMonth = Ai.substring(10, 12);// 月份  
        String strDay = Ai.substring(12, 14);// 月份  
        if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {  
            errorInfo = "身份证生日无效。";  
            return errorInfo;  
        }  
        GregorianCalendar gc = new GregorianCalendar();  
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");  
        if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150  
                || (gc.getTime().getTime() - s.parse(  
                        strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {  
            errorInfo = "身份证生日不在有效范围。";  
            return errorInfo;  
        }  
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {  
            errorInfo = "身份证月份无效";  
            return errorInfo;  
        }  
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {  
            errorInfo = "身份证日期无效";  
            return errorInfo;  
        }  
        // =====================(end)=====================  
  
        // ================ 地区码时候有效 ================  
        Hashtable h = GetAreaCode();  
        if (h.get(Ai.substring(0, 2)) == null) {  
            errorInfo = "身份证地区编码错误。";  
            return errorInfo;  
        }  
        // ==============================================  
  
        // ================ 判断最后一位的值 ================  
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
                errorInfo = "身份证无效，不是合法的身份证号码";  
                return errorInfo;  
            }  
        } else {  
            return "";  
        }  
        // =====================(end)=====================  
        return "right";  
    }
    /** 
     * 功能：设置地区编码 
     *  
     * @return Hashtable 对象 
     */ 
    private static Hashtable GetAreaCode() {  
        Hashtable hashtable = new Hashtable();  
        hashtable.put("11", "北京");  
        hashtable.put("12", "天津");  
        hashtable.put("13", "河北");  
        hashtable.put("14", "山西");  
        hashtable.put("15", "内蒙古");  
        hashtable.put("21", "辽宁");  
        hashtable.put("22", "吉林");  
        hashtable.put("23", "黑龙江");  
        hashtable.put("31", "上海");  
        hashtable.put("32", "江苏");  
        hashtable.put("33", "浙江");  
        hashtable.put("34", "安徽");  
        hashtable.put("35", "福建");  
        hashtable.put("36", "江西");  
        hashtable.put("37", "山东");  
        hashtable.put("41", "河南");  
        hashtable.put("42", "湖北");  
        hashtable.put("43", "湖南");  
        hashtable.put("44", "广东");  
        hashtable.put("45", "广西");  
        hashtable.put("46", "海南");  
        hashtable.put("50", "重庆");  
        hashtable.put("51", "四川");  
        hashtable.put("52", "贵州");  
        hashtable.put("53", "云南");  
        hashtable.put("54", "西藏");  
        hashtable.put("61", "陕西");  
        hashtable.put("62", "甘肃");  
        hashtable.put("63", "青海");  
        hashtable.put("64", "宁夏");  
        hashtable.put("65", "新疆");  
        hashtable.put("71", "台湾");  
        hashtable.put("81", "香港");  
        hashtable.put("82", "澳门");  
        hashtable.put("91", "国外");  
        return hashtable;  
    }  
  
    /** 
     * 验证日期字符串是否是YYYY-MM-DD格式 
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
     * 功能：判断字符串是否为数字 
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
    // 身份证号码验证：end  
      
    private String getAlpha(String str) {        
        if(isEmpty(str))  
            return "#";  
          
        String sortStr = str.trim().substring(0, 1).toUpperCase();  
        // 正则表达式，判断首字母是否是英文字母  
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
     * 跳转到收银台界面
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
     * X 图片加载框架的图片设置 视频List加载封面图片
     */
    public static ImageOptions Ximage(){
    	ImageOptions image = new ImageOptions.Builder()
			      //设置加载过程中的图片
			      .setLoadingDrawableId(R.drawable.video_jzz)
			      //设置加载失败后的图片
			      .setFailureDrawableId(R.drawable.video_jzsb)
			      .setSquare(true)
			      .setFadeIn(true)
			      .build();
		return image;
	
    }
    
    /**
     * X 图片加载框架的图片设置  头像的设置 默认是log
     */
    public static ImageOptions Xheadimg(){
    	ImageOptions image = new ImageOptions.Builder()
			      //设置加载过程中的图片
			      .setLoadingDrawableId(R.drawable.xsjxlog)
			      //设置加载失败后的图片
			      .setFailureDrawableId(R.drawable.xsjxlog)
			      .setSquare(true)
			      .setFadeIn(true)
			      .setCircular(true)
			      .build();
    	
    	
		return image;
    }
    
    /**
     * X 图片加载框架的图片设置  头像的设置 默认是log
     */
    public static ImageOptions XRunimag(){
    	ImageOptions image = new ImageOptions.Builder()
			      //设置加载过程中的图片
			      .setLoadingDrawableId(R.drawable.xsjx)
			      //设置加载失败后的图片
			      .setFailureDrawableId(R.drawable.xsjx)
			      .setSquare(true)
			      .setFadeIn(true)
			      .setCircular(false)
			      .build();
    	
    	
		return image;
    }
    
    
    /**
     * X 教练 头像加载框架的图片设置  头像的设置 默认是log
     */
    public static ImageOptions XCoachHeadimg(){
    	ImageOptions image = new ImageOptions.Builder()
			      //设置加载过程中的图片
			      .setLoadingDrawableId(R.drawable.coachheadlog)
			      //设置加载失败后的图片
			      .setFailureDrawableId(R.drawable.coachheadlog)
			      .setSquare(true)
			      .setFadeIn(true)
			      .setCircular(true)
			      .build();

		return image;
    }
    
    /**
     * X 教练 头像加载框架的图片设置  头像的设置 默认是log  圆的
     */
    public static ImageOptions XCoachCireHeadimg(){
    	ImageOptions image = new ImageOptions.Builder()
			      //设置加载过程中的图片
			      .setLoadingDrawableId(R.drawable.coachheadlog)
			      //设置加载失败后的图片
			      .setFailureDrawableId(R.drawable.coachheadlog)
			      .setSquare(true)
			      .setFadeIn(true)
			      .setCircular(true)
			      .build();

		return image;
    }
    
    
    /**
     * X 图片加载框架的图片设置  头像的设置 默认是log
     */
    public static ImageOptions XCiecHeadimg(){
    	ImageOptions image = new ImageOptions.Builder()
			      //设置加载过程中的图片
			      .setLoadingDrawableId(R.drawable.log)
			      //设置加载失败后的图片
			      .setFailureDrawableId(R.drawable.log)
			      .setSquare(true)
			      .setFadeIn(true)
			      .setCircular(true)
			      .build();
    	
    	
		return image;
    }
    /**
     * x 校内资讯 头部轮播图片加载失败 使用的框架
     */
    public static ImageOptions XShcoolTopimg(){
    	ImageOptions image = new ImageOptions.Builder()
			      //设置加载过程中的图片
			      .setLoadingDrawableId(R.drawable.scholltopimg)
			      //设置加载失败后的图片
			      .setFailureDrawableId(R.drawable.scholltopimg)
			      .setSquare(true)
			      .setFadeIn(true)
			      .setCircular(false)
			      .setSize(MainApplication.phoneWidth, MainApplication.phoneHeight)
			      .build();
    	
    	
		return image;
    }
    
    /**
     * X 图片加载框架的图片设置  头像的设置 默认是log
     */
    public static ImageOptions Xgvimgno(){
    	ImageOptions image = new ImageOptions.Builder()
			      //设置加载过程中的图片
			      .setLoadingDrawableId(R.drawable.imgurlno)
			      //设置加载失败后的图片
			      .setFailureDrawableId(R.drawable.imgurlno)
			      .setSquare(true)
			      .setFadeIn(true)
			      .setCircular(false)
			      .build();
    	
    	
		return image;
    }
    
    /**
     * 跳转到视频播放界面
     */
    public static void startVideoPlayAT(Context context,Video video){
    	Intent intent=new Intent(context,VideoPlayActivity.class);
    	intent.putExtra("id",video.id);//ID
    	
    	intent.putExtra("name",video.name);//视频名称
    	
    	intent.putExtra("img",video.imgurl);//图片地址
    	intent.putExtra("url",video.url);//视频地址
    	intent.putExtra("ksyq",video.ksyq);//考试要求
    	intent.putExtra("ppbz",video.ppbz);//评判标准
    	intent.putExtra("czff",video.czff);//操作方法
    	intent.putExtra("zysx",video.zysx);//注意事项
    	context.startActivity(intent);
    }
    /**
     * 校内资讯跳转到WEB
     */
	public static void intentWebActity(Context context,String Url, String webname) {
		Intent webintent = new Intent(context, WebActivity.class);
		webintent.putExtra("Url", Url);
		webintent.putExtra("Webname", webname);
		context.startActivity(webintent);

	}
    
    
    /**
     * 显示加载框  显示的内容    这里会报异常  暂时不用  208 1 27
     */
	public static Dialog showDialog(Context context, String s) {
		LayoutInflater inflatera = LayoutInflater.from(context);
		Dialog dialog = new AlertDialog.Builder(context).create();
		dialog.setCancelable(false);
		dialog.show();
		
		
		// 注意此处要放在show之后 否则会报异常
		View v = inflatera.inflate(R.layout.loading_process_dialog_anim, null);
		TextView stv = (TextView) v.findViewById(R.id.loading_dialog_tv);
		stv.setText(s);
		dialog.setContentView(v);
		return dialog;
	}
    
	/**
	 * 禁止输入框输入表情等4位字节的符号
	 * leng 长度
	 */
	public static InputFilter[] getInputFilter(final Context context,int leng){
		 InputFilter emojiFilter = new InputFilter() {
		        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
		                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		        @Override
		        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		            Matcher emojiMatcher = emoji.matcher(source);
		            if (emojiMatcher.find()) {
		               Utils.showToast(context, "Sorry,暂不支持输入表情!");
		                return "";
		            }
		            return null;
		        }
		    };
		InputFilter[] e= {emojiFilter,new InputFilter.LengthFilter(leng)};

		return e;
	}
	
	/**
	 * 重置ListView的高度    不重置的话会导致ListView 只显示一行数据
	 */
   public static void setListViewHeightBasedOnChildren(ListView listView) {   
	        // 获取ListView对应的Adapter   
	        ListAdapter listAdapter = listView.getAdapter();   
	        if (listAdapter == null) {   
	            return;   
	        }   
	   
	        int totalHeight = 0;   
	        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
	            // listAdapter.getCount()返回数据项的数目   
	            View listItem = listAdapter.getView(i, null, listView);   
	            // 计算子项View 的宽高   
	            listItem.measure(0, 0);    
	            // 统计所有子项的总高度   
	            totalHeight += listItem.getMeasuredHeight();    
	        }   
	        ViewGroup.LayoutParams params = listView.getLayoutParams();   
	        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
	        // listView.getDividerHeight()获取子项间分隔符占用的高度   
	        // params.height最后得到整个ListView完整显示需要的高度   
	        listView.setLayoutParams(params);   
	    } 
	
	/**
	 * 返回两个时间段相差多少分钟 例如：1:21 - 1:31   返回10分钟
	 */
   public static int TwoTimeDifferTime(int sh,int sm,int eh,int em){
	   int differ=0;
	   //先判断1的小时是否相等
	   int a=sh-eh;
	  
	   if(a==0){
		   //两个时候的小时相等
		   //处理分钟
		   differ=sm-em;
		      
	   }
	   else if(a>0){
		   //第一个时间的小时大于第二个时间的小时
		   differ=a*60+(sm-em);
		   
		   
	   }
	     else{
	    	//第二个时间的小时大于第一个时间的小时
		   
	   }

	   return differ;
   }
   
   /**
    * 根据两个时间段  返回时长  13:40 - 14:50  返回 60
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
	    	//如果都能转为整数
		   tsh=Integer.parseInt(sh);
		   tsm=Integer.parseInt(sm);
		   teh=Integer.parseInt(eh);
		   tem=Integer.parseInt(em);
		   
		   sum=(teh - tsh) * 60 + (tem - tsm); 
	     }
 
	   return sum;
   } 
   
   
  /**
   * 判断手机是否有底部虚拟按键
   */
   public static boolean checkDeviceHasNavigationBar(Context activity) {  
 
       //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar  
       boolean hasMenuKey = ViewConfiguration.get(activity)  
               .hasPermanentMenuKey();  
       boolean hasBackKey = KeyCharacterMap  
               .deviceHasKey(KeyEvent.KEYCODE_BACK);  
       if (!hasMenuKey && !hasBackKey) {  
           // 做任何你需要做的,这个设备有一个导航栏  
           return true;  
       }  
       return false;  
   }  
   
  
}
