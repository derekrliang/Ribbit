package com.frostyrusty.ribbit;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class RibbitApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		Parse.initialize(this, "pXlV5CqjsqTBuHmsaUGFKoLa9r7OT3pmEJYNb5eZ",
				"8XT0rrvZc62wo8eXbLhTT8ZFy59zsCbWoOrIjeYN");
		
		ParseObject testObject = new ParseObject("TestObject");
		testObject.put("foo", "bar");
		testObject.saveInBackground();
	}
}
