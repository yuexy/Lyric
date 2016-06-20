package zq.mdlib.mdwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zswd.zq.materiallib.R;

import zq.mdlib.Utils.Utils;

/**
 * Created by YueXy on 2015/5/13.
 */
public class ButtonFlat extends Button
{
	TextView textButton;

	public ButtonFlat(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	protected void setDefaultProperties()
	{
		minHeight = 36;
		minWidth = 88;
		rippleSize = 3;

		setMinimumWidth((int) Utils.dpToPx(minWidth, getResources()));
		setMinimumHeight((int) Utils.dpToPx(minHeight, getResources()));
		setBackgroundResource(R.drawable.background_transparent);
	}

	@Override
	protected void setAttributes(AttributeSet attrs)
	{
		String text = " ";

		int textResource = attrs.getAttributeResourceValue(ANDROIDXML, "text", -1);

		if (textResource != -1)
		{
			text = getResources().getString(textResource);
		}
		else
		{
			text = attrs.getAttributeValue(ANDROIDXML, "text");
		}

		int textColorResource = attrs.getAttributeResourceValue(ANDROIDXML, "textColor", -1);

		textButton = new TextView(getContext());
		textButton.setText(text);
		//textButton.setBackgroundColor(backgroundColor);
		textButton.setTypeface(null, Typeface.BOLD);

		int textColor = attrs.getAttributeResourceValue(ANDROIDXML, "textColor", -1);

		if (textColor != -1)
		{
			textButton.setTextColor(getResources().getColor(textColor));
		}
		else
		{
			textButton.setTextColor(Color.parseColor("#009688"));
		}

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

		textButton.setLayoutParams(params);

		addView(textButton);

		if (isInEditMode())
			return;

		int backgroundColor = attrs.getAttributeResourceValue(ANDROIDXML, "background", -1);

		if (backgroundColor != -1)
		{
			setBackgroundColor(getResources().getColor(backgroundColor));
		}
		else
		{
			String background = attrs.getAttributeValue(ANDROIDXML, "background");
			if (background != null)
				setBackgroundColor(Color.parseColor(background));
		}


	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if (x != -1)
		{
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor(makePressColor());
			canvas.drawCircle(x, y, radius, paint);

			if (radius > getHeight() / rippleSize)
				radius += rippleSpeed;

			if (radius > getWidth())
			{
				x = -1;
				y = -1;
				radius = getHeight() / rippleSize;

				if (onClickListener != null)
					onClickListener.onClick(this);
			}
		}

		invalidate();
	}

	@Override
	protected int makePressColor()
	{
		return Color.parseColor("#88DDDDDD");
	}

	public void setText(String t)
	{
		textButton.setText(t.toUpperCase());
	}

	public void setTextColor(int Color)
	{
		textButton.setTextColor(Color);
	}


	public void setBackgroundColor(int Color)
	{
		textButton.setTextColor(Color);
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
