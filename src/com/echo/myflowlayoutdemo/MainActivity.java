package com.echo.myflowlayoutdemo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.myflowlayoutdemo.constant.Constant;
import com.echo.myflowlayoutdemo.utils.ColorUtil;
import com.echo.myflowlayoutdemo.utils.DrawableUtil;
import com.echo.myflowlayoutdemo.view.FlowLayout;


public class MainActivity extends Activity {
	
	private FlowLayout flowLayout;
	private ScrollView scrollView;
	private int horizontalPadding,verticalPadding;
	private float radius;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		horizontalPadding = (int) getResources().getDimension(R.dimen.horizontal_text_padding);
		verticalPadding = (int) getResources().getDimension(R.dimen.vertical_text_padding);
		radius = getResources().getDimension(R.dimen.hot_text_radius);
		
		scrollView = new ScrollView(this);
		flowLayout = new FlowLayout(this);//宽高params默认是match
		//设置flowLayout的padding值
		int flowLayoutPadding = (int) getResources().getDimension(R.dimen.flow_layout_padding);
		flowLayout.setPadding(flowLayoutPadding,flowLayoutPadding, flowLayoutPadding, flowLayoutPadding);
		for (int i = 0; i < Constant.ITEM.length; i++) {
			final TextView textView = new TextView(this);
			textView.setGravity(Gravity.CENTER);
			textView.setText(Constant.ITEM[i]);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			textView.setTextColor(Color.WHITE);
			Drawable normal = DrawableUtil.generateDrawable(ColorUtil.randomColor(), radius);
			Drawable pressed = DrawableUtil.generateDrawable(ColorUtil.randomColor(), radius);
			textView.setBackgroundDrawable(DrawableUtil.generateSelector(pressed, normal));
			textView.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
			
			flowLayout.addView(textView);
			
			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(), textView.getText().toString(), 0);
				}
			});
		}
		
		scrollView.addView(flowLayout);
		setContentView(scrollView);
	}

}
