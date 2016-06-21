package com.example.weimeng.lyric.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;

import com.example.weimeng.lyric.MyApplication;

import java.io.ByteArrayOutputStream;

/**
 * Created by weimeng on 2016/6/21.
 */
public class ImageTools
{
	public static String img2String(int id)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bitmap bitmap = ((BitmapDrawable) MyApplication.getInstance().getResources().getDrawable(id)).getBitmap();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] byteServer = baos.toByteArray();
		String result = Base64.encodeToString(byteServer, Base64.DEFAULT);
		return result;
	}

	public static String img2String(Bitmap bitmap)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] byteServer = baos.toByteArray();
		String result = Base64.encodeToString(byteServer, Base64.DEFAULT);
		return result;
	}

	public static Bitmap string2Img(String str)
	{
		byte[] byteImg = Base64.decode(str.getBytes(), Base64.DEFAULT);
		Bitmap bitmap = BitmapFactory.decodeByteArray(byteImg, 0, byteImg.length);

		return bitmap;
	}
}
