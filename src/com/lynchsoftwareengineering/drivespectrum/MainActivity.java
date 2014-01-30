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

public class MainActivity extends Activity {
	Context context;
	int widthInt; 
	int heightInt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = this;
		getScreenSize();
		buildLayout();
		super.onCreate(savedInstanceState);
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
		
		ArrayList<View> viewArrayList = new ArrayList<View>();
		viewArrayList.add(textView);
		
		ImageButton imageButton = new ImageButton(context);
		imageButton.setBackgroundColor(Color.YELLOW);// this is really ugly but it is for testing.
		viewArrayList.add(imageButton);

		
		ImageButton imageButtonTwo = new ImageButton(context);
		imageButtonTwo.setBackgroundColor(Color.GRAY);
		viewArrayList.add(imageButtonTwo);
		
		
		
		for(int i = 0; i < viewArrayList.size(); i++){ 
			RelativeLayout.LayoutParams relativeLayoutLayoutParams = new RelativeLayout.LayoutParams(widthInt/6, heightInt/9);
			relativeLayoutLayoutParams.setMargins((widthInt/6)+((widthInt/9)*i), (heightInt/9)+((heightInt/9)*i), 0, 0);
			relativeLayout.addView(viewArrayList.get(i), relativeLayoutLayoutParams);
		}
		
		relativeLayout.setBackgroundColor(Color.argb(255, 0, 150, 255));
		return relativeLayout;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
