package com.lynchsoftwareengineering.drivespectrum;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SpectrumView extends Activity {
	Context context;
	int widthInt;
	int heightInt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(buildLayout());
	}
	
	private void getScreenSize(){
		WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		Point pointScreenSize = new Point();
		if(Build.VERSION.SDK_INT  >=  13){ 
			display.getSize(pointScreenSize);
		}else{
			DisplayMetrics displayMetrics = new DisplayMetrics();
			display.getMetrics(displayMetrics);
			pointScreenSize.set(displayMetrics.widthPixels, displayMetrics.heightPixels);
		}
		widthInt = pointScreenSize.x;
		heightInt = pointScreenSize.y;		
	}

	private RelativeLayout buildLayout() {
		
		RelativeLayout relativeLayout = new RelativeLayout(context);
		TextView textView = new TextView(context);
		textView.setText("This is boss!!!");
		
		ImageButton imageButton = new ImageButton(context);
		imageButton.setBackgroundColor(Color.YELLOW);// this is really ugly but it is for testing.

		//relativeLayout.setBackgroundDrawable(R.drawable.ic_launcher);
		
		RelativeLayout.LayoutParams relativeLayoutLayoutParams = new RelativeLayout.LayoutParams(widthInt/6, heightInt/9);
		relativeLayoutLayoutParams.setMargins((widthInt/6)+((widthInt/4)), (heightInt/9)/*+((heightInt/9)*i)*/, 0, 0);
		relativeLayout.addView(imageButton, relativeLayoutLayoutParams);
		
		relativeLayout.setBackgroundColor(Color.argb(255, 255, 150, 255));
		return relativeLayout;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.spectrum_view, menu);
		return true;
	}

}
