package gaongil.safereturnhome.support;

import gaongil.safereturnhome.exception.saveImageFileException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

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
	
}
