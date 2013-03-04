package com.terenceingram.flashlight;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FlashLightActivity extends Activity {
	
	private boolean isLightOn = false;
	private Camera camera;
	private Button button;
	private TextView statusText;
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_light);		
		
		button = (Button) findViewById(R.id.buttonFlashLight);
		
		if (!isCameraOnDevice()) return;
		
		camera = Camera.open();
		button.setOnClickListener(new MyOnClickListener(camera.getParameters()));
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash_light, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		//if (camera == null) init();
		super.onResume();
	}

	@Override
	protected void onPause() {
		//release camera
		//camera.release();
		super.onPause();
	}
	
	private boolean isCameraOnDevice() {
		
		statusText = (TextView) findViewById(R.id.textView1);
		 
		Context context = this;
		PackageManager pm = context.getPackageManager();
 
		if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Log.e("err", "Device has no camera!");
			statusText.setText("Device has no camera!");
			return false;
		} else {
			statusText.setText("Device has a camera. Yay!");
			return true;
		}
	}
	
	private class MyOnClickListener implements OnClickListener{
		
		final Parameters p;
		
		public MyOnClickListener(Parameters p) {
			this.p = p;
		}
		
		@Override
		public void onClick(View arg0) {

			if (isLightOn) {

				Log.i("info", "torch is turned off!");

				p.setFlashMode(Parameters.FLASH_MODE_OFF);
				camera.setParameters(p);
				camera.stopPreview();
				isLightOn = false;

			} else {

				Log.i("info", "torch is turned on!");

				p.setFlashMode(Parameters.FLASH_MODE_TORCH);

				camera.setParameters(p);
				camera.startPreview();
				isLightOn = true;

			}

		}
		
		
	}

}
