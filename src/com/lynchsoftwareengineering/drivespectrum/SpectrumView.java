package com.lynchsoftwareengineering.drivespectrum;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
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
		getScreenSize();
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
		
		DrawView drawView  = new DrawView(context);
		// this is really ugly but it is for testing.

		//relativeLayout.setBackgroundDrawable(R.drawable.ic_launcher);
		
		RelativeLayout.LayoutParams relativeLayoutLayoutParams = new RelativeLayout.LayoutParams((int)(widthInt*.8), (int)(heightInt*.8));
		relativeLayoutLayoutParams.setMargins(widthInt/10, heightInt/10, 0, 0);
		relativeLayout.addView(drawView, relativeLayoutLayoutParams);
		
		relativeLayout.setBackgroundColor(Color.argb(255, 255, 0, 255));
		return relativeLayout;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.spectrum_view, menu);
		return true;
	}
	
	private class DrawView extends View{
		
		ArrayList<LinePaintDataOject> linePaintDataOjectArrayList = new ArrayList<LinePaintDataOject>();
		public DrawView(Context context) {
			super(context);
			setBackgroundColor(Color.argb(255, 255, 150, 255));
			fillFloatArray();
		}
		
		private void fillFloatArray() {
			float x1 = 0;
			float y1 = 0;
			float x2 = 3;
			float y2 = 3;
			int alphaInt = 255;
			int redInt = 100;
			int greenInt = 100;
			int blueInt = 100;
			
			for(int i = 0; i < 100; i++){
				LinePaintDataOject linePaintDataOject = new LinePaintDataOject();
				linePaintDataOject.setXYPointFloatArray(x1, y1, x2, y2);
				linePaintDataOject.setColorForPaintIntArray(alphaInt, redInt, greenInt, blueInt);
				x1 +=3;
				y1 +=3;
				x2 +=1;
				y2 *= 2;
				redInt +=10;
				linePaintDataOjectArrayList.add(linePaintDataOject); 
			}
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			/* test code
			float[] pointfloatArray = {10,10, 100,100};
			Paint paint  = new Paint();
			paint.setColor(Color.RED);
			testMethod(canvas);
			canvas.drawLine(10, 10, 275, 275, paint);
			canvas.drawRect((float)300.0,(float) 300.0, (float) 400.0, (float) 400.0, paint);
			canvas.drawLines(pointfloatArray, 0, 1, paint );
			*/ 
			for (LinePaintDataOject linePaintDataOject : linePaintDataOjectArrayList){
				Paint paint = new Paint();
				int[] paintColorInt = linePaintDataOject.getColorForPaintIntArray();
				paint.setColor(Color.argb( paintColorInt[0], paintColorInt[1], paintColorInt[2], paintColorInt[3]));
				canvas.drawLines(linePaintDataOject.getXyPointsFloatArray(), paint);
			}
			Log.d("DB_TEST", "onDraw was called");
		}

		private void testMethod(Canvas canvas) {
			Paint paint = new Paint();
			paint.setColor(Color.argb(255, 0, 255, 0));
			canvas.drawLine(400, 10, 100, 400, paint);
		}
		
	}

}
