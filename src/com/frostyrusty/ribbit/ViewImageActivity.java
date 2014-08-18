package com.frostyrusty.ribbit;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewImageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_image);
		
		ImageView imageView = (ImageView) findViewById(R.id.imageView);
		
		Uri imageUri = getIntent().getData();
		
		// imageView.setImageURI(uri) // doesn't work for web; only local!
		// we can use picasso lib to load directly
		Picasso.with(this).load(imageUri.toString()).into(imageView);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				finish(); // end the activity
			}
			
		}, 10 * 1000); // based in ms; this is 10 seconds flat
	}
}
