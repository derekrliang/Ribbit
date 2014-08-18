package com.frostyrusty.ribbit.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.frostyrusty.ribbit.R;
import com.frostyrusty.ribbit.utils.MD5Util;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

public class UserAdapter extends ArrayAdapter<ParseUser> {
	protected Context mContext;
	protected List<ParseUser> mUsers;
	
	public UserAdapter(Context context, List<ParseUser> users) {
		super(context, R.layout.message_item, users);
		mContext = context;
		mUsers = users;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// we completely handle it; a good method makes scrolling/refreshing efficient
		ViewHolder holder;
		
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.user_item, null);
			holder = new ViewHolder();
			holder.userImageView = (ImageView) convertView.findViewById(R.id.userImageView);
			holder.nameLabel = (TextView) convertView.findViewById(R.id.nameLabel);
			convertView.setTag(holder);
		}
		else { // already created, we can recycle
			holder = (ViewHolder) convertView.getTag();
		}
		
		ParseUser user = mUsers.get(position);
		String email = user.getEmail().toLowerCase();
		
		if (email.equals("")) {
			// use default profile image
			holder.userImageView.setImageResource(R.drawable.avatar_empty);
		}
		else {
			String hash = MD5Util.md5Hex(email);
			String gravatarUrl = "http://www.gravatar.com/avatar/" + hash + "?s=204&d=404";
			Picasso.with(mContext)
				.load(gravatarUrl)
				.placeholder(R.drawable.avatar_empty) // if case we get a 404, no gravatar availabe for this email
				.into(holder.userImageView);
		}
		
		holder.nameLabel.setText(user.getUsername());
		
		return convertView;
	}
	
	private static class ViewHolder {
		ImageView userImageView;
		TextView nameLabel;
	}
	
	public void refill(List<ParseUser> users) {
		mUsers.clear();
		mUsers.addAll(users);
		notifyDataSetChanged();
	}
}
