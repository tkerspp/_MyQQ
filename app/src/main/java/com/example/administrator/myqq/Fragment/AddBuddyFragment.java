package com.example.administrator.myqq.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.myqq.R;
import com.example.administrator.myqq.util.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Administrator on 2016/10/19.
 */
public class AddBuddyFragment extends Fragment {

    EditText et_buddyAccount;
    Button btn_add;

    String myAccount;
    String buddyAccount;
    String myBuddies;
    User myUser;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private  static DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_buddies, container, false);
        et_buddyAccount = (EditText) view.findViewById(R.id.buddy_account);


        btn_add = (Button) view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buddyAccount = et_buddyAccount.getText().toString();
                myAccount = getActivity().getIntent().getStringExtra("account");
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("users").child(myAccount).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Get user value
                                myUser = dataSnapshot.getValue(User.class);
                                if(myUser.getBuddies()==null) {
                                    myBuddies = buddyAccount;
                                }else {
                                    myBuddies= myUser.getBuddies() + " " + buddyAccount;
                                }

                                myUser.setBuddies(myBuddies);
                                mDatabase.child("users").child(myAccount).updateChildren(myUser.toMap());
                                Toast.makeText(getActivity(),"添加成功O(∩_∩)O~",Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
            }
        });




        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
