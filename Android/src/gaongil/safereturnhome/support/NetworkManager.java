package gaongil.safereturnhome.support;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class NetworkManager {
	
	//private static final String ROOT_PATH = "http://localhost:8080";
	private static final String ROOT_PATH = "http://10.73.43.247:8080";
	private static final String LINE_END = "\r\n";
	private static final String TWO_HYPHENS = "--";
	private static final String DATA_BOUNDARY = "*****";
	private static final String TAG = "NetworkManager";
	
	public static String uploadStringDataToServer(String url, List<Map<String, Object>> parameters) {
		String tempURL = ROOT_PATH + url;
		URL connectUrl = null;
		HttpURLConnection connection = null;
		
		try {
			connectUrl = new URL(tempURL);
			// open connection
			connection = (HttpURLConnection) connectUrl.openConnection();
			HttpRequest(connection, parameters);
			return getResponseData(connection);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
	}

	private static String getResponseData(HttpURLConnection connection) throws IOException {
		int ch;
		InputStream is = connection.getInputStream();
		StringBuffer sb = new StringBuffer();
		while ((ch = is.read()) != -1) {
			sb.append((char) ch);
		}
		return sb.toString();
	}

	public static String executePostRequest(String url, List<NameValuePair> parameters) throws ClientProtocolException, IOException {
		HttpPost httppost = new HttpPost(ROOT_PATH + url);
		
		if (parameters != null)
			httppost.setEntity(new UrlEncodedFormEntity(parameters));
		
		HttpResponse response = new DefaultHttpClient().execute(httppost);
		HttpEntity entity = response.getEntity();
		
		if (response != null && entity != null)
			return EntityUtils.toString(entity);
		else
			return null;
	}
	
	//TODO 소스수정해서 파일업로드로 만들기
	private static void HttpRequest(HttpURLConnection connection,
			List<Map<String, Object>> parameters) {
		
		
		DataOutputStream dos = null;

		try {
			connection.setConnectTimeout(10 * 1000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Cache-Control", "no-cache");
			connection.setRequestProperty("Connection", "Keep-Alive");
			
			connection.setRequestProperty("Transfer-Encoding", "chunked");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + DATA_BOUNDARY);
			dos = new DataOutputStream(connection.getOutputStream());
			
			for (Map<String, Object> parameter : parameters) {
				for (String key : parameter.keySet()) {
					dos.write( getPostData(key, parameter.get(key)).getBytes("UTF-8"));
				}
			}
			
			dos.writeBytes(LINE_END);
			dos.writeBytes(TWO_HYPHENS + DATA_BOUNDARY + TWO_HYPHENS + LINE_END);
			
			dos.flush(); // finish upload...
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dos != null)
				try {
					dos.close();
				} catch (IOException e) {
				}
		}
		
	}
	
	private static String getPostData(String key, Object value) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(TWO_HYPHENS + DATA_BOUNDARY + LINE_END);
		sb.append("Content-Disposition: form-data; name=\"" + key + "\"" + LINE_END);
		sb.append(LINE_END);
		sb.append(value.toString());
		sb.append(LINE_END);
		
		return sb.toString();
	}
}
