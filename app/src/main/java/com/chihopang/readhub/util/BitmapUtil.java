package com.chihopang.readhub.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
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
    if (!(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
        && (ContextCompat.checkSelfPermission(context, Manifest.permission_group.STORAGE)
        != PackageManager.PERMISSION_GRANTED))) {
      return false;
    }
    File dirFile =
        new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "readhub");
    dirFile.mkdirs();
    File file = new File(dirFile, fileName + ".jpg");
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(file);
      bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
      fos.flush();
      fos.close();
      context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }
}
