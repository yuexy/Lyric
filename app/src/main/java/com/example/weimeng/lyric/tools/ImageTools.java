package com.example.weimeng.lyric.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;

import com.example.weimeng.lyric.MyApplication;

import java.io.ByteArrayInputStream;
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

	public static String img2StringWithCompress(Bitmap bitmap)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		int options = 80;
		while (baos.toByteArray().length / 1024 > 100)
		{
			baos.reset();
			options -= 10;
			bitmap.compress(Bitmap.CompressFormat.PNG, options, baos);
		}
		byte[] byteServer = baos.toByteArray();
		String result = Base64.encodeToString(byteServer, Base64.DEFAULT);
		return result;
	}

	public static Bitmap compressBmpFromBmp(Bitmap image)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;
		image.compress(Bitmap.CompressFormat.PNG, 100, baos);
		while (baos.toByteArray().length / 1024 > 100)
		{
			baos.reset();
			options -= 10;
			image.compress(Bitmap.CompressFormat.PNG, options, baos);
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
		return bitmap;
	}

	public static Bitmap string2Img(String str)
	{
		byte[] byteImg = Base64.decode(str.getBytes(), Base64.DEFAULT);
		Bitmap bitmap = BitmapFactory.decodeByteArray(byteImg, 0, byteImg.length);

		return bitmap;
	}

	////////////////////

	public static Bitmap compressImageFromFile(String srcPath)
	{
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;// 只读边,不读内容
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = 350f;//
		float ww = 250f;//
		int be = 1;
		if (w > h && w > ww)
		{
			be = (int) (newOpts.outWidth / ww);
		}
		else if (w < h && h > hh)
		{
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置采样率

		newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;// 该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		// return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
		// 其实是无效的,大家尽管尝试
		return bitmap;
	}

	@SuppressLint("NewApi")
	public static String getRealPathFromURI_API19(Context context, Uri uri)
	{
		String filePath = "";
		String wholeID = DocumentsContract.getDocumentId(uri);

		// Split at colon, use second item in the array
		String id = wholeID.split(":")[1];

		String[] column = {MediaStore.Images.Media.DATA};

		// where id is equal to
		String sel = MediaStore.Images.Media._ID + "=?";

		Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				column, sel, new String[]{id}, null);

		int columnIndex = cursor.getColumnIndex(column[0]);

		if (cursor.moveToFirst())
		{
			filePath = cursor.getString(columnIndex);
		}
		cursor.close();
		return filePath;
	}

	@SuppressLint("NewApi")
	public static String getRealPathFromURI_API11to18(Context context, Uri contentUri)
	{
		String[] proj = {MediaStore.Images.Media.DATA};
		String result = null;

		CursorLoader cursorLoader = new CursorLoader(
				context,
				contentUri, proj, null, null, null);
		Cursor cursor = cursorLoader.loadInBackground();

		if (cursor != null)
		{
			int column_index =
					cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			result = cursor.getString(column_index);
		}
		return result;
	}

	public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri)
	{
		String[] proj = {MediaStore.Images.Media.DATA};
		Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
		int column_index
				= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
}
