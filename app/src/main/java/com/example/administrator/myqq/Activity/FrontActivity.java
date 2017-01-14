package com.example.administrator.myqq.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.administrator.myqq.Entity.BuddyEntity;
import com.example.administrator.myqq.Fragment.AddBuddyFragment;
import com.example.administrator.myqq.Fragment.BuddyFragment;
import com.example.administrator.myqq.Fragment.RecentFragment;
import com.example.administrator.myqq.Fragment.TrendsFragment;
import com.example.administrator.myqq.R;
import com.example.administrator.myqq.util.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/15.
 */
public class FrontActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "AnonymousAuth";

    public static String myAccount;
    public static User myUser;
    private  User myBuddy;
    private String[] Buddies = {""};
    public static List<BuddyEntity> myBuddies = new ArrayList<>();

    private RadioGroup radioGroup;
    private Button btn_welcome;

    private static final String RECENT = "会话";
    private static final String BUDDY = "好友";
    private static final String GROUP = "群组";
    private static final String TRENDS = "动态";
    private static final String MORE= "更多";

    private BuddyFragment buddyFragment;
    private RecentFragment recentFragment;
    private TrendsFragment trendsFragment;
    private AddBuddyFragment addBuddyFragment;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.tabcontent);
        myAccount = getIntent().getStringExtra("account");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference();

                mDatabase.child("users").addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Get user value
                                myUser = dataSnapshot.child(myAccount).getValue(User.class);
                                myUser.setAccount(myAccount);
                                Buddies = myUser.getBuddies().split(" ");
                                for(String s : Buddies) {
                                    myBuddy = (dataSnapshot.child(s).getValue(User.class));
                                    myBuddies.add(myBuddy.toBuddyEntity());
                                    Log.d(TAG, "+++++++++++++++++++++++++++" +(myBuddy.getEmail()));
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                if(buddyFragment!=null) {
                    repalceFragment(buddyFragment);
                }else {
                    buddyFragment = new BuddyFragment();
                    repalceFragment(buddyFragment);
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        radioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_recent:
                        if (recentFragment!=null) {
                            repalceFragment(recentFragment);
                        }else {
                            recentFragment = new RecentFragment();
                            repalceFragment(recentFragment);
                        }
                        break;
                    case R.id.tab_buddy:
                        if(buddyFragment!=null) {
                            repalceFragment(buddyFragment);
                        }else {
                            buddyFragment = new BuddyFragment();
                            repalceFragment(buddyFragment);
                        }
                        break;
                    case R.id.tab_trends:
                        if(trendsFragment!=null) {
                            repalceFragment(trendsFragment);
                        }else {
                            trendsFragment = new TrendsFragment();
                            repalceFragment(trendsFragment);
                        }
                        break;

                }
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:----------" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        };


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_add_buddies:
                addBuddyFragment = new AddBuddyFragment();
                repalceFragment(addBuddyFragment);
                break;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        // The activity is about to become visible.
    }

    @Override
    protected void onResume() {
        //取消通知栏图标
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(001);
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }

    @Override
    protected void onStop() {
        showNotification();//显示通知栏图标
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        // The activity is no longer visible (it is now "stopped")
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }
    private void showNotification() {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("MyQQ")
                        .setContentText("好难过!");
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    private void repalceFragment(Fragment fgt) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.tabcontent, fgt);
        transaction.addToBackStack(null);
// Commit the transaction
        transaction.commit();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
