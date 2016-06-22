package com.example.weimeng.lyric.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public static boolean isEmail(String input)
	{
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(input);

		return m.matches();
	}
}
