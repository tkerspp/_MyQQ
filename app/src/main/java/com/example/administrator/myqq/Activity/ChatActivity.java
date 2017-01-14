package com.example.administrator.myqq.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myqq.Activity.FrontActivity;
import com.example.administrator.myqq.Adapter.ChatAdapter;
import com.example.administrator.myqq.Entity.BuddyEntity;
import com.example.administrator.myqq.Entity.ChatEntity;
import com.example.administrator.myqq.R;
import com.example.administrator.myqq.util.MyTime;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */
public class ChatActivity extends FragmentActivity implements View.OnClickListener,EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener{

    private static final String TAG = "AnonymousAuth";
    boolean bool = false;

    private BuddyEntity buddyEntity;
    private String chatContent;//消息内容
    private ListView chatListView;
    public static ChatActivity ca;

//    private EditText et_input;
//    private MsgListView msgListView;
    private ImageView ivIcon;
    private EmojiconEditText editEmojicon;
    private TextView btn_send;
    private FrameLayout emojicons;


    public List<ChatEntity> chatEntityList = new ArrayList<ChatEntity>();//所有聊天内容

    public static int[] avatar = new int[]{R.drawable.avatar_default, R.drawable.h001, R.drawable.h002, R.drawable.h003,
            R.drawable.h004, R.drawable.h005, R.drawable.h006};
    //    MyBroadcastReceiver br;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference mDatabase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
        setEmojiconFragment(false);


//        int chatAvatar = getIntent().getIntExtra("avatar", 0);
//        myAccount = getIntent().getStringExtra("account");
//        chatAccount = getIntent().getStringExtra("chatAccount");
//        chatNick = getIntent().getStringExtra("nick");
        buddyEntity = (BuddyEntity) getIntent().getSerializableExtra("buddyEntity");
        ivIcon = (ImageView)findViewById(R.id.iv_icon);
        editEmojicon = (EmojiconEditText)findViewById(R.id.editEmojicon);
        emojicons = (FrameLayout)findViewById(R.id.emojicons);
        btn_send = (TextView)findViewById(R.id.btn_send);
//        msgListView = (MsgListView)findViewById(R.id.msg_listView);
//
//        msgListView.setXListViewListener(this);
//        msgListView.setPullLoadEnable(false);
//        msgListView.setPullRefreshEnable(false);
        editEmojicon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() < 1) {
                    btn_send.setEnabled(false);
                    btn_send.setBackgroundResource(R.drawable.chat_text_send_nomal);

                } else {
                    btn_send.setEnabled(true);
                    btn_send.setBackgroundResource(R.drawable.chat_text_send_foc);
                }
            }
        });


        ImageView avatar_iv = (ImageView) findViewById(R.id.chat_top_avatar);
        TextView nick_tv = (TextView) findViewById(R.id.chat_top_nick);
        avatar_iv.setImageResource(avatar[buddyEntity.getAvatar()]);
        nick_tv.setText(buddyEntity.getNick());


        btn_send.setOnClickListener(this);
        ivIcon.setOnClickListener(this);
        editEmojicon.setOnClickListener(this);

        mDatabase = database.getReference("users").child(buddyEntity.getAccount()).child("message");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                String message = dataSnapshot.getValue(String.class);
                updateChatView(new ChatEntity(buddyEntity.getAvatar(), message, MyTime.geTime(), true));

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.addValueEventListener(postListener);
    }

    public void updateChatView(ChatEntity chatEntity) {
        chatEntityList.add(chatEntity);
        chatListView = (ListView) findViewById(R.id.lv_chat);
        chatListView.setAdapter(new ChatAdapter(this, chatEntityList));
    }

    private void setEmojiconFragment(boolean useSystemDefault) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(editEmojicon);
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(editEmojicon, emojicon);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_icon:
                if (!bool) {
                    hintKbTwo();
                    emojicons.setVisibility(View.VISIBLE);
                    bool = true;
                } else {
                    emojicons.setVisibility(View.GONE);
                    bool = false;
                }

                break;
            case R.id.btn_send:
                mDatabase = FirebaseDatabase.getInstance().getReference();
                chatContent = editEmojicon.getText().toString();
                mDatabase = database.getReference("users").child(FrontActivity.myUser.getAccount()).child("message");
                mDatabase.setValue(chatContent);
                updateChatView(new ChatEntity(2, chatContent, MyTime.geTime(), false));
                editEmojicon.setText("");
                break;
            case R.id.editEmojicon:
                if (bool) {
                    emojicons.setVisibility(View.GONE);
                    bool = false;
                }
                break;

        }
    }


    //此方法只是关闭软键盘
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


}
