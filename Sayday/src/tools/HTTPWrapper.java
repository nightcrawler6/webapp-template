package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class HTTPWrapper {
	private String mProto;
	private String mHost;
	private String mPort;
	private HttpClient mClient;
	
	public HTTPWrapper(String proto, String host, String port){
		mProto = proto;
		mHost = host;
		mPort = port;
		mClient = HttpClientBuilder.create().build();
	}
	
	public <T> T sendGET(String uri, T t) throws UnsupportedOperationException, IOException{
		HttpGet get =  new HttpGet(buildUri(uri));
		HttpResponse execute = null;
		try {
			execute = mClient.execute(get);
		} catch (IOException e) {
			System.out.println("An error occured when sending GET request!");
			e.printStackTrace();
		}
		BufferedReader rd = new BufferedReader(new InputStreamReader(execute.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while((line = rd.readLine()) != null){
			result.append(line);
		}
		@SuppressWarnings("unchecked")
		T encoded = (T) new Gson().fromJson(result.toString(), t.getClass());
		return encoded;
	}
	
	
	public <T> T sendPOST(String uri, List<NameValuePair> headerParams, List<NameValuePair> postParams, T t) throws ClientProtocolException, IOException{
		HttpPost post = new HttpPost(buildUri(uri));
		for(NameValuePair headerPair: headerParams){
			post.setHeader(headerPair.getName(), headerPair.getValue());
		}
		post.setEntity(new UrlEncodedFormEntity(postParams));
		HttpResponse response = mClient.execute(post);
		BufferedReader rd = new BufferedReader(
		        new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		@SuppressWarnings("unchecked")
		T parsed = (T)new Gson().fromJson(result.toString(), t.getClass());
		return parsed;
	}
	
/*	public static void main(String[] args) throws ClientProtocolException, IOException{
		HTTPWrapper wrapper = new HTTPWrapper("https", "jsonplaceholder.typicode.com", "443");
		List<NameValuePair> postParams = new ArrayList<>();
		postParams.add(new BasicNameValuePair("id", "value2"));
		JsonObject sendPOST = wrapper.sendPOST("posts", new HashMap<>(), postParams, new JsonObject());
		System.out.println(sendPOST);
	}*/
	
	private String buildUri(String api){
		return String.format("%s://%s:%s/%s", 
				mProto,
				mHost,
				mPort,
				api);
	}

	public String getProto() {
		return mProto;
	}

	public void setProto(String mProto) {
		this.mProto = mProto;
	}

	public String getHost() {
		return mHost;
	}

	public void setIp(String mHost) {
		this.mHost = mHost;
	}

	public String getPort() {
		return mPort;
	}

	public void setPort(String mPort) {
		this.mPort = mPort;
	}
}
