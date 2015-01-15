package com.ivd.rotateImage;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class RotateOrientation {

	public Bitmap RotateOrientationCall(Bitmap src,float degree)
    {


    Matrix matrix=new Matrix();
    matrix.postRotate(degree);
   Bitmap bmOut = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
  return bmOut;

  }
}
