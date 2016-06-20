package zq.mdlib.mdwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.zswd.zq.materiallib.R;

import zq.mdlib.Utils.Utils;

/**
 * Created by YueXy on 2015/5/13.
 */
public class ButtonFloat extends Button
{
	int sizeIcon = 24;
	int sizeRadius = 28;

	ImageView icon;
	Drawable drawableIcon;

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public ButtonFloat(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		setBackgroundResource(R.drawable.background_button_float);
		icon = new ImageView(getContext());

		if (drawableIcon != null)
		{
			try
			{
				icon.setBackground(drawableIcon);
			}
			catch (NoSuchMethodError e)
			{
				icon.setBackgroundDrawable(drawableIcon);
			}
		}

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) Utils.dpToPx(sizeIcon, getResources()), (int) Utils.dpToPx(sizeIcon, getResources()));
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		icon.setLayoutParams(params);
		addView(icon);
	}

	protected void setDefaultProperties()
	{
		rippleSpeed = (int) Utils.dpToPx(2, getResources());
		rippleSize = (int) Utils.dpToPx(5, getResources());
		minHeight = sizeRadius * 2;
		minWidth = sizeRadius * 2;
		background = R.drawable.background_button_float;

		setMinimumHeight((int) Utils.dpToPx(minHeight, getResources()));
		setMinimumWidth((int) Utils.dpToPx(minWidth, getResources()));
		setBackgroundResource(background);
		setBackgroundColor(backgroundColor);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void setAttributes(AttributeSet attrs)
	{
		int backgroundColor = attrs.getAttributeResourceValue(ANDROIDXML, "background", -1);
		if (backgroundColor != -1)
		{
			setBackgroundColor(getResources().getColor(backgroundColor));
		}
		else
		{
			String backgroundColor_s = attrs.getAttributeValue(ANDROIDXML, "background");
			if (backgroundColor_s != null)
				setBackgroundColor(Color.parseColor(backgroundColor_s));
		}

		int iconResource = attrs.getAttributeResourceValue(MATERIALWIDGETXML, "widget_iconFloat", -1);
		if (iconResource != -1)
			drawableIcon = getResources().getDrawable(iconResource);

		boolean isanim = attrs.getAttributeBooleanValue(MATERIALWIDGETXML, "widget_isanim", false);

		if (isanim)
		{
			post(new Runnable()
			{
				@Override
				public void run()
				{
					float originalY = ViewHelper.getY(ButtonFloat.this) - Utils.dpToPx(24, getResources());
					ViewHelper.setY(ButtonFloat.this, ViewHelper.getY(ButtonFloat.this) + getHeight() * 3);
					ObjectAnimator animator = ObjectAnimator.ofFloat(ButtonFloat.this, "y", originalY);
					animator.setInterpolator(new BounceInterpolator());
					animator.setDuration(1500);
					animator.start();
				}
			});
		}
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		if (x != -1)
		{
			Rect src = new Rect(0, 0, getWidth(), getHeight());
			Rect dst = new Rect((int) Utils.dpToPx(1, getResources()), (int) Utils.dpToPx(2, getResources()), (int)( getWidth()-Utils.dpToPx(1, getResources())), (int) (getHeight()-Utils.dpToPx(2, getResources())));
			canvas.drawBitmap(cropCircle(makeCircle()), src, dst, null);
		}

		invalidate();
	}

	public Bitmap cropCircle(Bitmap bitmap)
	{
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
				bitmap.getWidth() / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	public ImageView getIcon()
	{
		return icon;
	}

	public void setIcon(ImageView icon)
	{
		this.icon = icon;
	}

	public Drawable getDrawableIcon()
	{
		return drawableIcon;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void setDrawableIcon(Drawable drawableIcon)
	{
		this.drawableIcon = drawableIcon;
		try
		{
			icon.setBackground(drawableIcon);
		}
		catch (NoSuchMethodError e)
		{
			icon.setBackgroundDrawable(drawableIcon);
		}
	}

}
