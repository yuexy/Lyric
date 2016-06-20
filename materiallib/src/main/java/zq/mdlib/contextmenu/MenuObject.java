package zq.mdlib.contextmenu;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

/**
 * Created by YueXy on 8/18/2015.
 */
public class MenuObject implements Parcelable
{
	private String mTitle;

	// background
	private Drawable mBgDrawable;
	private int mBgColor;
	private int mBgResource;

	// image
	private Drawable mDrawable;
	private int mColor;
	private Bitmap mBitmap;
	private int mResource;

	// image scale type
	private ImageView.ScaleType mScaleType = ImageView.ScaleType.CENTER_INSIDE;

	// text
	private int mTextColor;
	// divider
	private int mDividerColor = Integer.MAX_VALUE;

	private int mMenuTextAppearanceStyle;

	public MenuObject(String title)
	{
		this.mTitle = title;
	}

	public MenuObject()
	{
		this.mTitle = "";
	}

	public String getTitle()
	{
		return mTitle;
	}

	public void setTitle(String title)
	{
		this.mTitle = title;
	}

	public Drawable getBgDrawable()
	{
		return mBgDrawable;
	}

	public void setBgDrawable(Drawable drawable)
	{
		this.mBgDrawable = drawable;
		this.mBgColor = 0;
		this.mBgResource = 0;
	}

	public int getBgColor()
	{
		return mBgColor;
	}

	public void setBgColor(int bgColor)
	{
		this.mBgColor = bgColor;
		this.mBgDrawable = null;
		this.mBgResource = 0;
	}

	public int getBgResource()
	{
		return mBgResource;
	}

	public void setBgResource(int bgResource)
	{
		this.mBgResource = bgResource;
		this.mBgResource = 0;
		this.mBgDrawable = null;
	}

	public int getTextColor()
	{
		return mTextColor;
	}

	public void setTextColor(int color)
	{
		this.mTextColor = color;
	}

	public int getColor()
	{
		return mColor;
	}

	public void setColor(int color)
	{
		this.mColor = color;
		this.mResource = 0;
		this.mBitmap = null;
		this.mDrawable = null;
	}

	public Bitmap getBitmap()
	{
		return mBitmap;
	}

	public void setBitmap(Bitmap bitmap)
	{
		this.mBitmap = bitmap;
		this.mColor = 0;
		this.mResource = 0;
		this.mDrawable = null;
	}

	public int getResource()
	{
		return mResource;
	}

	public void setResource(int resource)
	{
		this.mResource = resource;
		this.mColor = 0;
		this.mBitmap = null;
		this.mBitmap = null;
	}

	public Drawable getDrawable()
	{
		return mDrawable;
	}

	public void setDrawable(Drawable drawable)
	{
		this.mDrawable = drawable;
		this.mColor = 0;
		this.mResource = 0;
		this.mBitmap = null;
	}

	public int getMenuTextAppearanceStyle()
	{
		return mMenuTextAppearanceStyle;
	}

	public void setMenuTextAppearanceStyle(int menuTextAppearenseStyle)
	{
		this.mMenuTextAppearanceStyle = menuTextAppearenseStyle;
	}

	public int getDividerColor()
	{
		return mDividerColor;
	}

	public void setDividerColor(int color)
	{
		this.mDividerColor = color;
	}

	public ImageView.ScaleType getScaleType()
	{
		return mScaleType;
	}

	public void setScaleType(ImageView.ScaleType scaleType)
	{
		this.mScaleType = scaleType;
	}

	public static Creator<MenuObject> getCreator()
	{
		return CREATOR;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(this.mTitle);
		dest.writeParcelable(mBgDrawable == null ? null : ((BitmapDrawable) this.mBgDrawable).getBitmap(), flags);
		dest.writeInt(this.mBgColor);
		dest.writeInt(this.mBgResource);
		dest.writeParcelable(mDrawable == null ? null : ((BitmapDrawable) this.mDrawable).getBitmap(), flags);
		dest.writeInt(this.mColor);
		dest.writeParcelable(this.mBitmap, 0);
		dest.writeInt(this.mResource);
		dest.writeInt(this.mScaleType == null ? -1 : this.mScaleType.ordinal());
		dest.writeInt(this.mTextColor);
		dest.writeInt(this.mDividerColor);
		dest.writeInt(this.mMenuTextAppearanceStyle);
	}

	private MenuObject(Parcel in)
	{
		this.mTitle = in.readString();
		Bitmap bitmapBgDrawable = in.readParcelable(Bitmap.class.getClassLoader());
		if (bitmapBgDrawable != null)
		{
			this.mBgDrawable = new BitmapDrawable(bitmapBgDrawable);
		}
		this.mBgColor = in.readInt();
		this.mBgResource = in.readInt();
		Bitmap bitmapDrawable = in.readParcelable(Bitmap.class.getClassLoader());
		if (bitmapDrawable != null)
		{
			this.mDrawable = new BitmapDrawable(bitmapDrawable);
		}
		this.mColor = in.readInt();
		this.mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
		this.mResource = in.readInt();
		int tmpMScaleType = in.readInt();
		this.mScaleType = tmpMScaleType == -1 ? null : ImageView.ScaleType.values()[tmpMScaleType];
		this.mTextColor = in.readInt();
		this.mDividerColor = in.readInt();
		this.mMenuTextAppearanceStyle = in.readInt();
	}

	public static final Creator<MenuObject> CREATOR = new Creator<MenuObject>()
	{
		public MenuObject createFromParcel(Parcel source)
		{
			return new MenuObject(source);
		}

		public MenuObject[] newArray(int size)
		{
			return new MenuObject[size];
		}
	};
}
