package com.example.administrator.myqq.util;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Administrator on 2016/10/19.
 */
public class DButil {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference mDatabase;

//    public static String getBuddies(String myAccount) {
//        String myBuddies;
//        mDatabase.child("users").child(myAccount).addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // Get user value
//                        User user = dataSnapshot.getValue(User.class);
//                        myBuddies= myBuddies + " " + buddyAccount;
//                        myUser.setBuddies(myBuddies);
//                        mDatabase.child("users").updateChildren(myUser.toMap());
//                        Toast.makeText(getActivity(), "添加成功O(∩_∩)O~", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                });
//    }

}
