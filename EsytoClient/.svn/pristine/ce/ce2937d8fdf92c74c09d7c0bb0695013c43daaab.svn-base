package com.ec2.yspay.http.task;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.ec2.yspay.common.ToastUtils;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

public class NewVersionUpdateTask extends AsyncTask<String, Integer, String> {

	private Context context;
	private long totalSize;
	private ProgressDialog progressDlg;
	private String newRbd;
	private String newVer;
	private boolean mbFinishActivity = false;

	public NewVersionUpdateTask(Context context,String mSize, boolean bFinishActivity) {
		this.context = context;
		this.totalSize = Long.valueOf(mSize);
		this.mbFinishActivity = bFinishActivity;
		
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
	 	  progressDlg = new ProgressDialog(context); 
	 	  progressDlg.setCancelable(true);
	 	  progressDlg.setCanceledOnTouchOutside(false);
	 	  progressDlg.setOnCancelListener(new OnCancelListener()
	 	  {
				@Override
				public void onCancel(DialogInterface dialog)
				{

					NewVersionUpdateTask.this.cancel(true);
				}
	 	  });
	 	 progressDlg.setIndeterminate(false);  
		 progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		 progressDlg.show();
	}

	@Override
	protected String doInBackground(String... params) {
		String url = params[0];
		String path = downApk(url);
		if (path != null)
		{
		}
		return path;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		progressDlg.setProgress(values[0]);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (progressDlg != null)
			progressDlg.cancel();	
		if (result == null)
		{
			final String state = Environment.getExternalStorageState();
			String toast = "网络异常，请检查网络设置！";
			if (!Environment.MEDIA_MOUNTED.equals(state)) 
			{
				toast = "没有找到检测到sdcard，需要插入sdcard才能完成更新！";
			}
            ToastUtils.show(context,toast);
		    return;
		}

		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		intent.setDataAndType(Uri.fromFile(new File(result)),
				"application/vnd.android.package-archive");

		context.startActivity(intent);
	}

	private String downApk(String url) {		
		try {
			
			DefaultHttpClient client = new DefaultHttpClient();
			String str=Uri.decode(url);
			
			HttpGet request = new HttpGet(Uri.decode(url));
			HttpResponse response = client.execute(request);

			BufferedInputStream bis = new BufferedInputStream(response
					.getEntity().getContent());

			File SdCardPath = Environment.getExternalStorageDirectory();
			File dir = new File(SdCardPath + "/EYSTO/");
				if (!dir.exists())
					dir.mkdirs();
				String path = SdCardPath + "/EYSTO/EsytoClient.apk";
			
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(path));
			byte[] bb = new byte[4096];
			int readed = 0;
			int currentSize = 0;
			while ((readed = bis.read(bb)) != -1) {
				bos.write(bb, 0, readed);
				currentSize += readed;
				int proc = (int) ((currentSize*100)/totalSize);
				this.publishProgress(proc);
			}
			bis.close();
			bos.flush();
			bos.close();
			return path;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
