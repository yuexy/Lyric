package zq.mdlib.mdwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by YueXy on 2015/5/8.
 */
public class CustomView extends RelativeLayout
{

	final static String MATERIALWIDGETXML = "http://schemas.android.com/apk/res-auto";
	final static String ANDROIDXML = "http://schemas.android.com/apk/res/android";

	public boolean isLastTouch = false;

	public CustomView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
}
