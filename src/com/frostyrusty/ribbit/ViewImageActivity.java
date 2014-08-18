package com.frostyrusty.ribbit;

import com.squareup.picasso.Picasso;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class ViewImageActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_image);
		
		ImageView imageView = (ImageView) findViewById(R.id.imageView);
		
		Uri imageUri = getIntent().getData();
		
		// imageView.setImageURI(uri) // doesn't work for web; only local!
		// we can use picasso lib to load directly
		Picasso.with(this).load(imageUri.toString()).into(imageView);
	}
}
