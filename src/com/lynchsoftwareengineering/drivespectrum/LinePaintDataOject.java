package com.lynchsoftwareengineering.drivespectrum;

public class LinePaintDataOject {
	public float[] getXyPointsFloatArray() {
		return xyPointsFloatArray;
	}

	public void setXYPointsFloatArray(float[] xyPointsFloatArray) {
		this.xyPointsFloatArray = xyPointsFloatArray;
	}

	public int[] getColorForPaintIntArray() {
		return colorForPaintIntArray;
	}

	public void setColorForPaintIntArray(int[] colorForPaintIntArray) {
		this.colorForPaintIntArray = colorForPaintIntArray;
	}

	float[] xyPointsFloatArray = new float[4];
	int[] colorForPaintIntArray = new int[4];

	/**
	 * @param args
	 */
	public LinePaintDataOject() {
		
	}
	
	public void setXYPointFloatArray(float x1 , float y1, float x2, float y2){
		xyPointsFloatArray[0] = x1;
		xyPointsFloatArray[1] = y1;
		xyPointsFloatArray[2] = x2;
		xyPointsFloatArray[3] = y2;
	}
	
	public void setColorForPaintIntArray(int alphaInt, int redInt, int greenInt, int blueInt) {
		colorForPaintIntArray[0] = alphaInt;
		colorForPaintIntArray[1] = redInt;
		colorForPaintIntArray[2] = greenInt;
		colorForPaintIntArray[3] = blueInt;
	}

}
