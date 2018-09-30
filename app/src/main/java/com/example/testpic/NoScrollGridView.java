package com.example.testpic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;




import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.widget.GridView;

public class NoScrollGridView extends GridView
{
	public NoScrollGridView(Context context)
	{
		super(context);
	}

	public NoScrollGridView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
	
 
}
