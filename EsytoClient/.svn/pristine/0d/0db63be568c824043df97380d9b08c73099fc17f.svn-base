package com.ec2.yspay.common;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.widget.Toast;

import com.ec2.yspay.AboutUsActivity;
import com.ec2.yspay.activity.BaseActivity;
import com.ec2.yspay.http.OnTaskFinished;
import com.ec2.yspay.http.response.GetNewVersionResponse;
import com.ec2.yspay.http.task.GetNewVersionTask;
import com.ec2.yspay.http.task.NewVersionUpdateTask;
import com.ec2.yspay.widget.PopupDialog;

public class VersionInfo {
	public static String code = "1";
	public static String clientVersion = "1.1.0";
	public static String downloadUrl = "";
	public static String feature = "";
	public static String fileSize = "0";
	public final static String HAVE_NEW_VERSION = "0";

	public static void GetVersion(final Context context) {
		try {

			PackageManager pm = context.getPackageManager();

			PackageInfo pinfo = pm.getPackageInfo(context.getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
			int versionCode = pinfo.versionCode;

			clientVersion = pinfo.versionName;
			GetNewVersionTask getNewVersionTask = new GetNewVersionTask(
					context, context.getPackageName(), clientVersion, ""
							+ versionCode);
			getNewVersionTask.setOnTaskFinished(new OnTaskFinished() {

				@Override
				public void onSucc(Object obj) {
					// TODO Auto-generated method stub
					GetNewVersionResponse response = (GetNewVersionResponse) obj;
					String isupdate = response.getIsForceUpdate();
					final String url = response.getDownloadUrl();
					final String mSize = response.getFileSize();
					float fileSize = Long.valueOf(mSize) / 1024 / 1024;
					String message = "新版本:" + response.getClientVersion()
							+ "\n新版本大小:" + fileSize + "M" + "\n更新内容:\n"
							+ response.getFeature();
					showPopupDialog(context, message, mSize, url, isupdate);
				}

				@Override
				public void onFail(Object obj) {
					GetNewVersionResponse response = (GetNewVersionResponse) obj;
					ToastUtils.show(context, response.getResultDesc());
				}
			});
			getNewVersionTask.execute();
		} catch (NameNotFoundException e) {
		}
	}

	private static void showPopupDialog(final Context context, String message,
			final String size, final String url, final String isupdate) {
		final PopupDialog.Builder builder = new PopupDialog.Builder(context);
		builder.setTitle("发现新版本");
		builder.setEditEnabled(false);
		builder.setCancelable(false);
		builder.setMessage(message);
		builder.setNegativeButton("下次再说",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if ("1".equals(isupdate)) {
							((Activity) context).finish();
						} else {
							dialog.dismiss();
						}

					}
				});
		builder.setPositiveButton("立即更新",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						new NewVersionUpdateTask(context, size, true)
								.execute(url);
						dialog.dismiss();

					}
				});

		builder.create().show();

	}
}
