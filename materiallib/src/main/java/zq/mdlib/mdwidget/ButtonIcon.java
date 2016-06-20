package zq.mdlib.mdwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zswd.zq.materiallib.R;

import zq.mdlib.Utils.Utils;

/**
 * Created by YueXy on 8/8/2015.
 */
public class ButtonIcon extends Button
{
	private static final String TAG = "ButtonIcon";

	private final int min_height = 30;
	private final int min_width = 30;

	private ImageView iconButton;
	private Drawable drawableIcon;

	private int iconSize;

	public ButtonIcon(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void setAttributes(AttributeSet attrs)
	{
		iconButton = new ImageView(getContext());

		int iconResource = attrs.getAttributeResourceValue(MATERIALWIDGETXML, "widget_iconFloat", -1);
		if (iconResource != -1)
			drawableIcon = getResources().getDrawable(iconResource);
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

		TypedArray styledAttrs = getContext().obtainStyledAttributes(attrs, R.styleable.MaterialWidget);

		iconSize = styledAttrs.getDimensionPixelSize(R.styleable.MaterialWidget_widget_iconSize, 47);

		int iconSizePx = (int) Utils.dpToPx(iconSize, getResources());

		if (drawableIcon != null)
		{
			iconButton.setImageDrawable(drawableIcon);
		}
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(iconSizePx, iconSizePx);
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		iconButton.setLayoutParams(params);
		iconButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		addView(iconButton);
	}

	@Override
	protected void setDefaultProperties()
	{
		minHeight = min_height;
		minWidth = min_width;
		rippleSize = 3;

		setMinimumWidth((int) Utils.dpToPx(minWidth, getResources()));
		setMinimumHeight((int) Utils.dpToPx(minHeight, getResources()));
		setBackgroundResource(R.drawable.background_transparent);
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
}
