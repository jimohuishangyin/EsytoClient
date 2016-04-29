package com.ec2.yspay.activity;

import com.ec2.yspay.R;
import com.ec2.yspay.R.id;
import com.ec2.yspay.R.layout;
import com.ec2.yspay.zxing.common.BitmapUtils;
import com.google.zxing.WriterException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class PicShowAllScreenActivity extends Activity
{
    private ImageView imageView;
    private String codeMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_show_all_screen); 
        imageView = (ImageView)findViewById(R.id.iv_code);
        codeMsg = getIntent().getStringExtra("codeMsg");
        showQRCode();
        imageView.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                finish();
            }
        });
        
    }
    private void showQRCode(){
        if (codeMsg != null) {
            
            
            try {
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                int mScreenWidth = dm.widthPixels;
                Bitmap bitmap = BitmapUtils.createQRCode(codeMsg, mScreenWidth,null);

                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                    imageView.setPadding(0, 0, 0, 0);
                }

            } catch (WriterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}
