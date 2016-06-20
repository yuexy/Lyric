package com.example.weimeng.lyric.tools;

/**
 * Created by weimeng on 2016/6/21.
 */
public class StringTools
{
	public static boolean isEmpty(String input)
	{
		if (input == null || input.length() <= 0 || "".equals(input))
			return true;

		return false;
	}
}
