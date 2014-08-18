package com.frostyrusty.ribbit;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class InboxFragment extends ListFragment {
	
	protected List<ParseObject> mMessages;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_inbox, container,
				false);
		return rootView;
	}
	@Override
	public void onResume() {
		super.onResume();
		
		getActivity().setProgressBarIndeterminateVisibility(true);
		
		// ask parse if we have any new messages!! that are only sent to our obj id
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_MESSAGES);
		query.whereEqualTo(ParseConstants.KEY_RECIPIENT_IDS, ParseUser.getCurrentUser().getObjectId());
		query.orderByDescending(ParseConstants.KEY_CREATED_AT);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> messages, ParseException e) {
				getActivity().setProgressBarIndeterminateVisibility(false);
				
				if (e == null) {
					// we found messages for this user!
					mMessages = messages;

					String[] usernames = new String[mMessages.size()];
					int i = 0;
					for (ParseObject message : mMessages) {
						usernames[i] = message.getString(ParseConstants.KEY_SENDER_NAME);
						++i;
					}
					
					// this only displays the sender's names in a basic list :~ just like friends list
					//ArrayAdapter<String> adapter = new ArrayAdapter<>(getListView().getContext(), android.R.layout.simple_list_item_1, usernames);
					//setListAdapter(adapter);
							
					MessageAdapter adapter = new MessageAdapter(getListView().getContext(), mMessages);
					setListAdapter(adapter);
				}
			}
		});
	}
}
