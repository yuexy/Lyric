package zq.mdlib.mdviewpager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

/**
 * Created by YueXy on 2015/5/6.
 */
public class MaterialViewPagerImageLogo extends ImageView
{

	public MaterialViewPagerImageLogo(Context context)
	{
		super(context);
	}

	public MaterialViewPagerImageLogo(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public MaterialViewPagerImageLogo(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	public void setImageDrawable(final Drawable drawable, final int duration)
	{
		final ImageView viewToAnimate = this;

		final ObjectAnimator scaleXs = ObjectAnimator.ofFloat(viewToAnimate, "scaleX", 0).setDuration(duration);
		scaleXs.setInterpolator(new DecelerateInterpolator());
		scaleXs.addListener(new AnimatorListenerAdapter()
		{
			@Override
			public void onAnimationEnd(Animator animation)
			{
				super.onAnimationEnd(animation);

				setImageDrawable(drawable);

				final ObjectAnimator scaleXb = ObjectAnimator.ofFloat(viewToAnimate, "scaleX", 1).setDuration(duration);
				scaleXb.setInterpolator(new AccelerateInterpolator());
				scaleXb.start();
			}
		});

		final ObjectAnimator scaleYs = ObjectAnimator.ofFloat(viewToAnimate, "scaleY", 0).setDuration(duration);
		scaleYs.setInterpolator(new DecelerateInterpolator());
		scaleYs.addListener(new AnimatorListenerAdapter()
		{
			@Override
			public void onAnimationEnd(Animator animation)
			{
				super.onAnimationEnd(animation);

				final ObjectAnimator scaleYb = ObjectAnimator.ofFloat(viewToAnimate, "scaleY", 1).setDuration(duration);
				scaleYb.setInterpolator(new AccelerateInterpolator());
				scaleYb.start();
			}
		});

		scaleXs.start();
		scaleYs.start();
	}
}
