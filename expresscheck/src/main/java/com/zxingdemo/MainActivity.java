package com.zxingdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.zxing.WriterException;
import com.zxingdemo.android.CaptureActivity;
import com.zxingdemo.encode.CodeCreator;

public class MainActivity extends AppCompatActivity {

	private static final int REQUEST_CODE_SCAN = 1;

	private static final String DECODED_CONTENT_KEY = "codedContent";
	private static final String DECODED_BITMAP_KEY = "codedBitmap";

	TextView qrCoded;
	ImageView qrCodeImage;
	Button creator, scanner;
	EditText qrCodeUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		creator.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String url = qrCodeUrl.getText().toString();
				try {
					Bitmap bitmap = CodeCreator.createQRCode(url);
					qrCodeImage.setImageBitmap(bitmap);
				} catch (WriterException e) {
					e.printStackTrace();
				}

			}
		});

		scanner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						CaptureActivity.class);
				startActivityForResult(intent, REQUEST_CODE_SCAN);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 扫描二维码/条码回传
		if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
			if (data != null) {
				String content = data.getStringExtra(DECODED_CONTENT_KEY);
				Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
				qrCoded.setText("解码结果： \n" + content);
				qrCodeImage.setImageBitmap(bitmap);
			}
		}
	}
}
