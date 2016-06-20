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

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by YueXy on 2015/5/6.
 */
public class MaterialViewPagerImageHeader extends KenBurnsView
{
	public MaterialViewPagerImageHeader(Context context)
	{
		super(context);
	}

	public MaterialViewPagerImageHeader(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public MaterialViewPagerImageHeader(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public void setImageURL(final String URL, final int fadeDuration)
	{
		final float alpha = getAlpha();
		final ImageView viewToAnimate = this;

		final ObjectAnimator fadeOut = ObjectAnimator.ofFloat(viewToAnimate, "alpha", 0).setDuration(fadeDuration);
		fadeOut.setInterpolator(new DecelerateInterpolator());
		fadeOut.addListener(new AnimatorListenerAdapter()
		{
			@Override
			public void onAnimationEnd(Animator animation)
			{
				super.onAnimationEnd(animation);
				Picasso.with(getContext()).load(URL).centerCrop().fit().into(viewToAnimate, new Callback()
				{
					@Override
					public void onSuccess()
					{
						final ObjectAnimator fadeIn = ObjectAnimator.ofFloat(viewToAnimate, "alpha", alpha).setDuration(fadeDuration);
						fadeIn.setInterpolator(new AccelerateInterpolator());
						fadeIn.start();
					}

					@Override
					public void onError()
					{

					}
				});
			}
		});

		fadeOut.start();
	}

	public void setImageDrawable(final Drawable drawable, final int fadeDuration)
	{
		final float alpha = getAlpha();
		final ImageView viewToAnimate = this;

		final ObjectAnimator fadeOut = ObjectAnimator.ofFloat(viewToAnimate, "alpha", 0).setDuration(fadeDuration);
		fadeOut.setInterpolator(new DecelerateInterpolator());
		fadeOut.addListener(new AnimatorListenerAdapter()
		{
			@Override
			public void onAnimationEnd(Animator animation)
			{
				super.onAnimationEnd(animation);
				setImageDrawable(drawable);

				final ObjectAnimator fadeIn = ObjectAnimator.ofFloat(viewToAnimate, "alpha", alpha).setDuration(fadeDuration);
				fadeIn.setInterpolator(new AccelerateInterpolator());
				fadeIn.start();
			}
		});
		fadeOut.start();
	}
}
