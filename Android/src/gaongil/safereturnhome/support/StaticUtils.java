package gaongil.safereturnhome.support;

import gaongil.safereturnhome.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.MediaStore.Images;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.Toast;

public class StaticUtils {

	// upload file to server
	// http://stackoverflow.com/questions/11044291/using-httppost-to-upload-file-android
	public static String postRequest(String url, List<NameValuePair> parameters)
			throws ClientProtocolException, IOException {
		HttpPost httppost = new HttpPost(Constant.ROOT_PATH + url);

		if (parameters != null)
			httppost.setEntity(new UrlEncodedFormEntity(parameters));

		HttpResponse response = new DefaultHttpClient().execute(httppost);
		HttpEntity entity = response.getEntity();

		if (response != null && entity != null)
			return EntityUtils.toString(entity);
		else
			return null;
	}

	public static void centerToast(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
				0, 0);
		toast.show();
	}

	public static boolean checkNetwork(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isWifiConn = ni.isConnected();
		ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobileConn = ni.isConnected();
		if (isWifiConn == true || isMobileConn == true)
			return true;
		centerToast(context, "네트워크 연결에 실패하였습니다.\n인터넷 연결상태를 확인해 주세요.");
		return false;
	}

	/**
	 * Scales the provided bitmap to have the height and width provided.
	 * (Alternative method for scaling bitmaps since
	 * Bitmap.createScaledBitmap(...) produces bad (blocky) quality bitmaps.)
	 * 
	 * @param bitmap
	 *            is the bitmap to scale.
	 * @param newWidth
	 *            is the desired width of the scaled bitmap.
	 * @param newHeight
	 *            is the desired height of the scaled bitmap.
	 * @return the scaled bitmap.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static Bitmap scaleBitmap(Context context, Uri ImageUri,
			int newWidth, int newHeight) throws FileNotFoundException,
			IOException {

		Bitmap bitmap = Images.Media.getBitmap(context.getContentResolver(),
				ImageUri);

		Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight,
				Config.ARGB_8888);

		float scaleX = newWidth / (float) bitmap.getWidth();
		float scaleY = newHeight / (float) bitmap.getHeight();
		float pivotX = 0;
		float pivotY = 0;

		Matrix scaleMatrix = new Matrix();
		scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

		Canvas canvas = new Canvas(scaledBitmap);
		canvas.setMatrix(scaleMatrix);
		canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

		return scaledBitmap;
	}

	public static boolean isScreenOn(Context context) {
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		return pm.isInteractive();
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	public static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	@SuppressWarnings("deprecation")
	public static ActionBarDrawerToggle getActionBarDrawerToggle(
																	final Activity activity, 
																	final DrawerLayout drawerLayout,
																	final View leftDrawerView, 
																	final View rightDrawerView,
																	final LinearLayout mainContentLayout,
																	final int drawerToggleDrawableId) {

		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		return new ActionBarDrawerToggle(activity, drawerLayout,
				drawerToggleDrawableId, 0, 0) {

			// It's for lastTranslate Saved Variation
			private float lastTranslate = 0.0f;

			@Override
			public void onDrawerClosed(View view) {
			}

			@Override
			public void onDrawerOpened(View drawerView) {
			}

			@SuppressLint("NewApi")
			public void onDrawerSlide(View drawerView, float slideOffset) {

				// Its Drawer action Conflict Prevent Code
				if (drawerView == leftDrawerView) {
					drawerLayout.closeDrawer(rightDrawerView);
				} else {
					drawerLayout.closeDrawer(leftDrawerView);
				}

				if (drawerView.getId() == R.id.drawer_main_right) {
					// if rightDrawer Action, Opposite Direction Set
					slideOffset *= -1;
				}

				float moveFactor = (drawerLayout.getWidth()
						* Constant.DRAWER_SLIDE_WIDTH_RATE * slideOffset);

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					mainContentLayout.setTranslationX(moveFactor);
				} else {
					TranslateAnimation anim = new TranslateAnimation(
							lastTranslate, moveFactor, 0.0f, 0.0f);
					anim.setFillAfter(true);
					mainContentLayout.startAnimation(anim);

					lastTranslate = moveFactor;
				}
			} //onDrawerSlide
		}; //new ActionBarDrawerToggle
	}
}
