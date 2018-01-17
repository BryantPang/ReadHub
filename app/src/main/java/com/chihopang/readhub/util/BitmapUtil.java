package com.chihopang.readhub.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {
  public static boolean saveViewAsImage(View view, String fileName) {
    Bitmap b = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas c = new Canvas(b);
    view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    view.draw(c);
    return saveImage(view.getContext(), b, fileName);
  }

  public static boolean saveImage(Context context, Bitmap bmp, String fileName) {
    if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) return false;
    String filePath = Environment.getExternalStorageDirectory() + "/readhub/";
    File file = new File(filePath);
    if (!file.exists()) file.mkdir();
    file = new File(filePath, fileName + ".jpg");
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(file);
      if (null != fos) {
        bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
        fos.flush();
        fos.close();
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        return true;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }
}
