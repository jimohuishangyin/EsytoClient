package com.ec2.yspay.pay.best;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.bestpay.plugin.Plugin;

public class Util {

	// 模拟下单url
	public static final String ORDER_URL = "https://webpaywg.bestpay.com.cn/order.action";

	// 模拟下单，63准生产环境；
	// public static String ORDER_URL =
	// "https://webpaywgback.bestpay.com.cn/order.action";

	// public static String ORDER_URL =
	// "http://wapchargewg.bestpay.com.cn/order.action";
	// Timeout
	public static final int CONNECT_TIMEOUT = 30 * 1000;
	public static final int READ_TIMEOUT = 30 * 1000;
	SchemeRegistry registry = new SchemeRegistry();
	ClientConnectionManager ccm = null;
	public String order(Hashtable<String, String> hash) {
		HttpPost httpRequest = new HttpPost(ORDER_URL);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("MERCHANTID", hash
				.get(Plugin.MERCHANTID)));
		params.add(new BasicNameValuePair("ORDERSEQ", hash.get(Plugin.ORDERSEQ)));
		params.add(new BasicNameValuePair("ORDERREQTRANSEQ", hash
				.get(Plugin.ORDERREQTRANSEQ)));
		params.add(new BasicNameValuePair("ORDERREQTIME", hash
				.get(Plugin.ORDERTIME)));
		params.add(new BasicNameValuePair("KEY", hash.get(Plugin.KEY)));
		String encodedString = "";
		try {
			encodedString = CryptTool.md5Digest(getMacString(params));
		} catch (Exception e) {
			e.printStackTrace();
		}
		params.add(new BasicNameValuePair("ORDERAMT", hash
				.get(Plugin.ORDERAMOUNT)));
		params.add(new BasicNameValuePair("SUBMERCHANTID", hash
				.get(Plugin.SUBMERCHANTID)));
		params.add(new BasicNameValuePair("MAC", encodedString));
		params.add(new BasicNameValuePair("TRANSCODE", "01"));

		params.add(new BasicNameValuePair("DIVDETAILS", hash
				.get(Plugin.DIVDETAILS)));

		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = getNewHttpClient()
					.execute(httpRequest);
			String str = getReturnCode(response.getEntity().getContent(), response
					.getEntity().getContentLength());
			release();
			return str;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		release();
		return null;

	}

	private String getMacString(List<NameValuePair> list) {
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			NameValuePair nvp = list.get(i);
			if (str.equals("")) {
				str += nvp.getName() + "=" + nvp.getValue();
			}

			else {
				str += "&" + nvp.getName() + "=" + nvp.getValue();
			}
		}
		return str;

	}

	private String getReturnCode(InputStream stream, long length) {
		byte bb[] = new byte[(int) length];

		try {
			stream.read(bb);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return new String(bb);
	}
	private void release(){
	    registry.unregister("http");
	    registry.unregister("https");
	    if(ccm!=null){
	        ccm.shutdown();
	        ccm = null;
	    }
	}
	private HttpClient getNewHttpClient() {
		HttpClient httpCilent;
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, CONNECT_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, READ_TIMEOUT);

		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ccm = new ThreadSafeClientConnManager(
					params, registry);
			
			httpCilent = new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			httpCilent = new DefaultHttpClient(params);
		}
		return httpCilent;
	}

	private class SSLSocketFactoryEx extends SSLSocketFactory {

		SSLContext sslContext = SSLContext.getInstance("TLS");

		public SSLSocketFactoryEx(KeyStore truststore)
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {

				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {

				}
			};

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host,
					port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}

	/**
	 * 获取准确的金额
	 * 
	 * @param orderAmt
	 * @return
	 */
	private int getOrderAmt(String orderAmt) {
		BigDecimal b1 = new BigDecimal(orderAmt);
		BigDecimal b2 = new BigDecimal("100");
		return b1.multiply(b2).intValue();
	}

	private String jsonToResult(String jsonStr) {
		try {
			JSONObject json = new JSONObject(jsonStr);
			if (!json.isNull("result")) {
				return json.getString("result");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
