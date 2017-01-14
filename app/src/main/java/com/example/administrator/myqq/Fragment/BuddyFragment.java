package com.example.administrator.myqq.Fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.myqq.Adapter.BuddyAdapter;
import com.example.administrator.myqq.Entity.BuddyEntity;
import com.example.administrator.myqq.Activity.ChatActivity;
import com.example.administrator.myqq.Activity.FrontActivity;
import com.example.administrator.myqq.R;
import com.example.administrator.myqq.util.User;

import java.util.List;

/**
 * Created by Administrator on 2016/10/16.
 */
public class BuddyFragment extends ListFragment {

    private String myAccount;
    private User myUser;
    private List<BuddyEntity> myBuddies;

    private ListView listView;
    public static int[] avatar=new int[]{R.drawable.avatar_default,R.drawable.h001,R.drawable.h002,R.drawable.h003,
            R.drawable.h004,R.drawable.h005,R.drawable.h006};
    public static String[] nick = new String[]{"1","2","3","4","5","6","7"};
    public static String[] trends = new String[]{"A","B","C","D","E","F","G"};
    private View view;

//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    private DatabaseReference mDatabase;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.buddy_fragment, container,false);
        listView = (ListView) view.findViewById(android.R.id.list);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//onCreat()方法在onCreateView()之前调用
//        myAccount = getActivity().getIntent().getStringExtra("account");
        myBuddies = FrontActivity.myBuddies;

        BuddyAdapter adapter = new BuddyAdapter(getActivity(), myBuddies);
        setListAdapter(adapter);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        BuddyEntity temp= (BuddyEntity) listView.getItemAtPosition(position);
        //打开聊天页面
        Intent intent=new Intent(getActivity(),ChatActivity.class);
        Bundle os = new Bundle();
        os.putSerializable("buddyEntity",temp);
        intent.putExtras(os);
//        intent.putExtra("avatar", temp.getAvatar());
//        intent.putExtra("chatAccount", temp.getAccount());
//        intent.putExtra("account",myAccount);
//        intent.putExtra("nick", temp.getNick());
//        intent.putExtra("trends", temp.getTrends());
        startActivity(intent);

    }

    public static String[] jiexi(String buddies) {

        String[] myBuddies= {""};
        if(buddies==null) {
            return myBuddies;
        }else {
            myBuddies = buddies.split(" ");
            return myBuddies;
        }


    }


}
