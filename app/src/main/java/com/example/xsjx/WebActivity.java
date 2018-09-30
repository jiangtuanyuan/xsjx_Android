package com.example.xsjx;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import com.example.utils.SharedUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class WebActivity extends Activity {
public WebView webview;
public TextView WebName,Web_dian;
public ImageView WebClose;
public ProgressBar pg;
public LinearLayout ll;
public String url=MainApplication.XSJX;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_web);
		initState();
		
		if(SharedUtils.getInstance().getInt("WebShowWinddos")==0){
			showWindows();
		}
		
		
		WebClose=(ImageView) findViewById(R.id.Web_close);
		WebClose.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View arg0) {
				webview.removeAllViews();
				webview.destroy();
				WebActivity.this.finish();
				
			}
		});
		
		Web_dian=(TextView) findViewById(R.id.Web_dian);
		Web_dian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showDian();
				
			}
		});
		
		
		
		WebName=(TextView) findViewById(R.id.Web_name);
		
		WebName.setText("?????У-"+getIntent().getStringExtra("Webname"));
		
		url=getIntent().getStringExtra("Url");
		
		
		ll=(LinearLayout) findViewById(R.id.Web_lllayout);
		webview=new WebView(getApplicationContext()); 
		ll.addView(webview);
		
		
	//	webview=(WebView) findViewById(R.id.Web_webview);
		
		pg=(ProgressBar) findViewById(R.id.progressBar1);
		webview.getSettings().setDomStorageEnabled(true);
		webview.getSettings().setJavaScriptEnabled(true);  

		webview.getSettings().setSupportZoom(true); 

		webview.getSettings().setBuiltInZoomControls(true);

		webview.getSettings().setUseWideViewPort(true);

		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webview.getSettings().setLoadWithOverviewMode(true);

		webview.getSettings().setAppCacheEnabled(true);  

		webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  

		webview.setWebViewClient(new WebViewClient()  
	        {
	            @Override  
	            public boolean shouldOverrideUrlLoading(WebView view, String url)  
	            {  
	                view.loadUrl(url);  
	                return true;  
	            }  
	        });  
		
		webview.setWebChromeClient(new WebChromeClient(){
			@Override
			 public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){  
                    pg.setVisibility(view.GONE);
                }  
                else{  
                    pg.setVisibility(view.VISIBLE);
                    pg.setProgress(newProgress);
                }       
            }  
			
			
		});
		 webview.loadUrl(url);
	}
	@Override  
	public void finish() {  
	    ViewGroup view = (ViewGroup) getWindow().getDecorView();  
	    view.removeAllViews();  
	    super.finish();  
	}  


	Builder  builder = null;
	Dialog dialog=null;
	public void showWindows(){
		if(builder==null){
	 	builder = new Builder(this);
		builder.setTitle("提示:");
		builder.setMessage("如果加载失败，请点击右上角刷新或者用浏览器打开试试~");
		builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				SharedUtils.getInstance().putInt("WebShowWinddos", 1);
				dialog.dismiss();
			}
		  });
		builder.create();
		}
   
		dialog = builder.show();
		
	}

	public Dialog DRunHead = null;
	public View VRunHead = null;
	public LayoutInflater inflater;
	public void showDian(){
		if (inflater == null)
			inflater = LayoutInflater.from(this);
		if (DRunHead == null)
			DRunHead = new Dialog(this, R.style.ActionSheetDialogStyle);
		if (VRunHead == null)
			VRunHead = inflater.inflate(R.layout.fragment_5_roundhead_dialog, null);
		   DRunHead.setContentView(VRunHead);

			Window dialogWindow = DRunHead.getWindow();
			dialogWindow.setGravity(Gravity.BOTTOM);
			DRunHead.show();
			
			WindowManager.LayoutParams lp = DRunHead.getWindow().getAttributes();
			lp.width = MainApplication.phoneWidth;
			DRunHead.getWindow().setAttributes(lp);
			
			TextView tv = (TextView) VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_head);
			tv.setText("操  作");
			
			TextView tv1 = (TextView) VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_paizhao);
			tv1.setText("刷  新");
			tv1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					webview.reload();
					DRunHead.dismiss();
				}
			});
			
			
			
			TextView tv2 = (TextView) VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_xiangce);
			tv2.setText("用浏览器打开");
		    tv2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					 Intent intent = new Intent();
					 intent.setAction("android.intent.action.VIEW");
					 Uri content_url = Uri.parse(url);
					 intent.setData(content_url);
					 startActivity(intent);
					 DRunHead.dismiss();
				}
			});
			
			
			
			VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_quxiao).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					DRunHead.dismiss();
				}
			});;
			
			
	}
	
	

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	webview.removeAllViews();
	webview.destroy();
	}
	
	 @Override  
	    public boolean onKeyDown(int keyCode, KeyEvent event)  
	    {  
	        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack())  
	        {  
	        	webview.goBack();
	            return true;  
	        }  
	        return super.onKeyDown(keyCode, event);  
	    }  

     public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
         handler.proceed();
         onReceivedSslError(view, handler, error);
     }
	 

     
     

	private void initState() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}
}
