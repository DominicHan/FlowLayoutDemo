package com.echo.myflowlayoutdemo.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

public class DrawableUtil {
	/**
	 * 生成Drawable
	 * @param argb
	 * @param radius
	 * @return
	 */
	public static GradientDrawable generateDrawable(int argb,float radius){
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE);//设置矩形，其实默认就是矩�?
		drawable.setCornerRadius(radius);//设置圆角的半�?
		drawable.setColor(argb);//设置颜色
		return drawable;
	}
	/**
	 * 动�?的生成Selector
	 * @return
	 */
	public static StateListDrawable generateSelector(Drawable pressed,Drawable normal){
		StateListDrawable stateListDrawable = new StateListDrawable();
		stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressed);//设置按下的状态对应的图片
		stateListDrawable.addState(new int[]{}, normal);//设置默认的图�?
		return stateListDrawable;
	}
}
