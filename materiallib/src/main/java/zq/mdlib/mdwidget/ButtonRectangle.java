package zq.mdlib.mdwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zswd.zq.materiallib.R;

import zq.mdlib.Utils.Utils;

/**
 * Created by YueXy on 2015/5/14.
 */
public class ButtonRectangle extends Button
{
	TextView textButton;

	public ButtonRectangle(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setDefaultProperties();
	}

	protected void setDefaultProperties()
	{
		super.minWidth = 80;
		super.minHeight = 36;
		super.background = R.drawable.background_button_rectangle;
		super.setDefaultProperties();
		rippleSpeed = Utils.dpToPx(3, getResources());
	}

	@Override
	protected void setAttributes(AttributeSet attrs)
	{
		int bacgroundColor = attrs.getAttributeResourceValue(ANDROIDXML, "background", -1);
		if (bacgroundColor != -1)
		{
			setBackgroundColor(getResources().getColor(bacgroundColor));
		}
		else
		{
			// Color by hexadecimal
			String background = attrs.getAttributeValue(ANDROIDXML, "background");
			if (background != null)
				setBackgroundColor(Color.parseColor(background));
		}

		String text = null;
		int textResource = attrs.getAttributeResourceValue(ANDROIDXML, "text", -1);
		if (textResource != -1)
		{
			text = getResources().getString(textResource);
		}
		else
		{
			text = attrs.getAttributeValue(ANDROIDXML, "text");
		}

		if (text != null)
		{
			textButton = new TextView(getContext());
			textButton.setText(text);
			textButton.setTextColor(attrs.getAttributeIntValue(ANDROIDXML, "textColor", Color.WHITE));
			textButton.setTypeface(null, Typeface.BOLD);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
			params.setMargins((int) Utils.dpToPx(5, getResources()), (int) Utils.dpToPx(5, getResources()), (int) Utils.dpToPx(5, getResources()), (int) Utils.dpToPx(5, getResources()));
			textButton.setLayoutParams(params);
			addView(textButton);
		}
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if (x != -1)
		{
			Rect src = new Rect(0, 0, (int) (getWidth() - Utils.dpToPx(6, getResources())), (int) (getHeight() - Utils.dpToPx(7, getResources())));
			Rect dst = new Rect((int) Utils.dpToPx(6, getResources()), (int) Utils.dpToPx(6, getResources()), (int) (getWidth() - Utils.dpToPx(6, getResources())), (int) (getHeight() - Utils.dpToPx(7, getResources())));
			canvas.drawBitmap(makeCircle(), src, dst, null);
		}
		invalidate();
	}

	public void setText(String text)
	{
		textButton.setText(text);
	}

	public void setTextColor(int color)
	{
		textButton.setTextColor(color);
	}

	public TextView getTextView()
	{
		return textButton;
	}

	public String getText()
	{
		return textButton.getText().toString();
	}
}
