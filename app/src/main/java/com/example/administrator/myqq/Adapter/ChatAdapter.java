package com.example.administrator.myqq.Adapter;
import java.util.List;

//import org.yhn.yq.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myqq.Activity.ChatActivity;
import com.example.administrator.myqq.Entity.ChatEntity;
import com.example.administrator.myqq.R;

public class ChatAdapter extends BaseAdapter{
	private Context context;
	private List<ChatEntity> list;
	LayoutInflater inflater;

	public ChatAdapter(Context context,List<ChatEntity> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	public View getView(int position, View convertView, ViewGroup root) {
		ImageView avatar;
		TextView message;
		TextView time;
		ChatEntity ce=list.get(position);
		if(ce.isLeft()){
			convertView = inflater.inflate(R.layout.chat_listview_item_left, null);
			
			avatar=(ImageView) convertView.findViewById(R.id.avatar_chat_left);
			message=(TextView) convertView.findViewById(R.id.message_chat_left);
			time=(TextView) convertView.findViewById(R.id.sendtime_chat_left);
			int id=ce.getAvatar();
			avatar.setImageResource(ChatActivity.avatar[id]);
			message.setText(ce.getMessage());
			time.setText(ce.getTime());
		}else{
			convertView=inflater.inflate(R.layout.chat_listview_item_right, null);
			
			avatar=(ImageView) convertView.findViewById(R.id.avatar_chat_right);
			message=(TextView) convertView.findViewById(R.id.message_chat_right);
			time=(TextView) convertView.findViewById(R.id.sendtime_chat_right);
			int id=ce.getAvatar();
			avatar.setImageResource(ChatActivity.avatar[id]);
			message.setText(ce.getMessage());
			time.setText(ce.getTime());
		}

		return convertView;
	}
	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
}
