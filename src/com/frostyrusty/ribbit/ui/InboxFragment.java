package com.frostyrusty.ribbit.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.frostyrusty.ribbit.R;
import com.frostyrusty.ribbit.adapters.MessageAdapter;
import com.frostyrusty.ribbit.utils.ParseConstants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class InboxFragment extends ListFragment {
	
	protected List<ParseObject> mMessages;
	protected SwipeRefreshLayout mSwipeRefreshLayout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_inbox, container,
				false);
		
		mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
		mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
		mSwipeRefreshLayout.setColorScheme(R.color.swipeRefresh1,
				R.color.swipeRefresh2,
				R.color.swipeRefresh3,
				R.color.swipeRefresh4);
		
		return rootView;
	}
	@Override
	public void onResume() {
		super.onResume();
		
		getActivity().setProgressBarIndeterminateVisibility(true);
		
		retrieveMessages();
	}
	private void retrieveMessages() {
		// ask parse if we have any new messages!! that are only sent to our obj id
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_MESSAGES);
		query.whereEqualTo(ParseConstants.KEY_RECIPIENT_IDS, ParseUser.getCurrentUser().getObjectId());
		query.orderByDescending(ParseConstants.KEY_CREATED_AT);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> messages, ParseException e) {
				getActivity().setProgressBarIndeterminateVisibility(false);
				
				if (mSwipeRefreshLayout.isRefreshing()) {
					mSwipeRefreshLayout.setRefreshing(false);
				}
				
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
							
					if (getListView().getAdapter() == null) {
						MessageAdapter adapter = new MessageAdapter(getListView().getContext(), mMessages);
						setListAdapter(adapter);
					}
					else {
						// refill the adapter
						((MessageAdapter) getListView().getAdapter()).refill(mMessages);
					}
				}
			}
		});
	}
	
	// opening messages
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		ParseObject message = mMessages.get(position);
		String messageType = message.getString(ParseConstants.KEY_FILE_TYPE);
		ParseFile file = message.getParseFile(ParseConstants.KEY_FILE);
		Uri fileUri = Uri.parse(file.getUrl());
		
		if (messageType.equals(ParseConstants.TYPE_IMAGE)) {
			// view the image
			Intent intent = new Intent(getActivity(), ViewImageActivity.class);
			intent.setData(fileUri);
			startActivity(intent);
		}
		else {
			// view the video
			// we can let the user choose how to view the video
			Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
			intent.setDataAndType(fileUri, "video/*");
			startActivity(intent);
		}
		
		// delete it
		List<String> ids = message.getList(ParseConstants.KEY_RECIPIENT_IDS);
		if (ids.size() == 1) {
			// delete the actual file
			message.deleteInBackground(); // trust
		}
		else {
			// just remove the recipient
			ids.remove(ParseUser.getCurrentUser().getObjectId());
			ArrayList<String> idsToRemove = new ArrayList<String>();
			idsToRemove.add(ParseUser.getCurrentUser().getObjectId());
			
			message.removeAll(ParseConstants.KEY_RECIPIENT_IDS, idsToRemove);
			message.saveInBackground();
			
			// should also delete files on the database as well
		}
	}
	
	protected OnRefreshListener mOnRefreshListener = new OnRefreshListener() {
		
		@Override
		public void onRefresh() {
			//Toast.makeText(getActivity(), "We're refreshing!", Toast.LENGTH_SHORT).show();
			retrieveMessages();
			
		}
	};
}
