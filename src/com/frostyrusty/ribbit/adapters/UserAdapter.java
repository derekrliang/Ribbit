package com.frostyrusty.ribbit.adapters;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.frostyrusty.ribbit.R;
import com.frostyrusty.ribbit.utils.ParseConstants;
import com.parse.ParseObject;
import com.parse.ParseUser;

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
			//holder.iconImageView = (ImageView) convertView.findViewById(R.id.messageicon);
			holder.nameLabel = (TextView) convertView.findViewById(R.id.nameLabel);
			convertView.setTag(holder);
		}
		else { // already created, we can recycle
			holder = (ViewHolder) convertView.getTag();
		}
		
		ParseUser user = mUsers.get(position);
		
//		if (user.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)) {
//			holder.iconImageView.setImageResource(R.drawable.ic_picture);
//		}
//		else {
//			holder.iconImageView.setImageResource(R.drawable.ic_video);
//		}
		holder.nameLabel.setText(user.getUsername());
		
		return convertView;
	}
	
	private static class ViewHolder {
		//ImageView iconImageView;
		TextView nameLabel;
	}
	
	public void refill(List<ParseUser> users) {
		mUsers.clear();
		mUsers.addAll(users);
		notifyDataSetChanged();
	}
}
