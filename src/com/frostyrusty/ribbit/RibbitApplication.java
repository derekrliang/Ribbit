package com.frostyrusty.ribbit;

import android.app.Application;

import com.frostyrusty.ribbit.ui.MainActivity;
import com.frostyrusty.ribbit.utils.ParseConstants;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

public class RibbitApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		Parse.initialize(this, "pXlV5CqjsqTBuHmsaUGFKoLa9r7OT3pmEJYNb5eZ",
				"8XT0rrvZc62wo8eXbLhTT8ZFy59zsCbWoOrIjeYN");
		
		//PushService.setDefaultPushCallback(this, MainActivity.class);
		PushService.setDefaultPushCallback(this, MainActivity.class, R.drawable.ic_stat_ic_launcher);
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}
	
	public static void updateParseInstallation(ParseUser user) {
		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		installation.put(ParseConstants.KEY_USER_ID, user.getObjectId());
		installation.saveInBackground();
	}
}
