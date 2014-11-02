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

public class NetworkManager {
	
	public static String upload(String url, List<NameValuePair> parameters) throws ClientProtocolException, IOException {
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
	
	//upload file to server
	//http://stackoverflow.com/questions/11044291/using-httppost-to-upload-file-android

}
