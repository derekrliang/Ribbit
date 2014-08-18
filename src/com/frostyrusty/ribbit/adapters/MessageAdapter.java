package com.frostyrusty.ribbit.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.frostyrusty.ribbit.R;
import com.frostyrusty.ribbit.R.drawable;
import com.frostyrusty.ribbit.R.id;
import com.frostyrusty.ribbit.R.layout;
import com.frostyrusty.ribbit.utils.ParseConstants;
import com.parse.ParseObject;

public class MessageAdapter extends ArrayAdapter<ParseObject> {
	
	protected Context mContext;
	protected List<ParseObject> mMessages;
	
	public MessageAdapter(Context context, List<ParseObject> messages) {
		super(context, R.layout.message_item, messages);
		mContext = context;
		mMessages = messages;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// we completely handle it; a good method makes scrolling/refreshing efficient
		ViewHolder holder;
		
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
			holder = new ViewHolder();
			holder.iconImageView = (ImageView) convertView.findViewById(R.id.messageicon);
			holder.nameLabel = (TextView) convertView.findViewById(R.id.senderLabel);
			convertView.setTag(holder);
		}
		else { // already created, we can recycle
			holder = (ViewHolder) convertView.getTag();
		}
		
		ParseObject message = mMessages.get(position);
		
		if (message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)) {
			holder.iconImageView.setImageResource(R.drawable.ic_action_picture);
		}
		else {
			holder.iconImageView.setImageResource(R.drawable.ic_video);
		}
		holder.nameLabel.setText(message.getString(ParseConstants.KEY_SENDER_NAME));
		
		return convertView;
	}
	
	private static class ViewHolder {
		ImageView iconImageView;
		TextView nameLabel;
	}
	
	public void refill(List<ParseObject> messages) {
		mMessages.clear();
		mMessages.addAll(messages);
		notifyDataSetChanged();
	}
}
