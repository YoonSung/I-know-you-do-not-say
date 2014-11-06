package gaongil.safereturnhome.support;

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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;

public class StaticUtils {
	
	//upload file to server
	//http://stackoverflow.com/questions/11044291/using-httppost-to-upload-file-android
	public static String postRequest(String url, List<NameValuePair> parameters) throws ClientProtocolException, IOException {
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
}
