package gaongil.safereturnhome.support;

import gaongil.safereturnhome.exception.saveImageFileException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

public class ImageUtil {

	private Context context;
	
	public ImageUtil(Context context) {
		this.context = context;
	}

	public void saveProfileImage(Bitmap image) throws saveImageFileException {
		String filePath = context.getApplicationContext().getFilesDir().getPath();
		File file = new File(filePath + File.separator + Constant.PROFILE_IMAGE_NAME);
		
		OutputStream out = null;
			
		try {
			// Create File And Stream Interface
			file.createNewFile();
		    out = new FileOutputStream(file);
		    
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    try {
		        out.close();
		    } catch (IOException e) {}
		}
		
		// Bitmap Image Compress With Sending Stream
	    if (!image.compress(Bitmap.CompressFormat.PNG, 85, out)) {
	    	// if save failed.
	    	throw new saveImageFileException();
	    }
	    	
	}
	
	public Drawable getProfileImage() {
        File file = context.getFileStreamPath(Constant.PROFILE_IMAGE_NAME);
		return Drawable.createFromPath(file.getAbsolutePath()); 
	}

	public Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
	        bitmap.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);
	 
	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	 
	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
	 
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);
	 
	    return output;
	  }

	public Bitmap getRoundedCornerBitmap(Drawable profile) {
		return getRoundedCornerBitmap(((BitmapDrawable)profile).getBitmap());
	}
	
}
