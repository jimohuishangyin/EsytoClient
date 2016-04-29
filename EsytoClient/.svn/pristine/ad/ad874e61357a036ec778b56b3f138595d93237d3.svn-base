package com.ec2.yspay.zxing.activity;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.zxing.decode.DecodeThread;

/**
 * 
 * 扫码结果页，成功失败业务逻辑处理
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月11日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ResultActivity extends Activity {

	private ImageView mResultImage;
	private TextView mResultText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		Bundle extras = getIntent().getExtras();

		mResultImage = (ImageView) findViewById(R.id.result_image);
		mResultText = (TextView) findViewById(R.id.result_text);

		if (null != extras) {
			int width = extras.getInt("width");
			int height = extras.getInt("height");

//			LayoutParams lps = new LayoutParams(width, height);
//			lps.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
//			lps.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
//			lps.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
//			
//			mResultImage.setLayoutParams(lps);

			String result = extras.getString("result");
			mResultText.setText(result);

			Bitmap barcode = null;
			byte[] compressedBitmap = extras.getByteArray(DecodeThread.BARCODE_BITMAP);
			if (compressedBitmap != null) {
				barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
				// Mutable copy:
				barcode = barcode.copy(Bitmap.Config.RGB_565, true);
			}

			mResultImage.setImageBitmap(barcode);
		}
	}
}
