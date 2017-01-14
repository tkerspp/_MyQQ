package com.example.administrator.myqq.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.myqq.R;
import com.example.administrator.myqq.util.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends BaseActivity {

    private static final String TAG = "AnonymousAuth";


    Button btn_login;
    Button btn_register;
    EditText accountEt;
    EditText passwordEt;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        };

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);


        accountEt = (EditText) findViewById(R.id.et_account);
        passwordEt = (EditText) findViewById(R.id.et_password);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register(accountEt.getText().toString(), passwordEt.getText().toString());

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountEt.getText().toString().equals("") || passwordEt.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "账号或密码不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    login(accountEt.getText().toString(), passwordEt.getText().toString());
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //注册账号
    private void register(final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "注册失败",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            writeNewUser(email.split("@")[0], email, password);
                            Toast.makeText(MainActivity.this, "注册成功",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    //登陆账号
    private void login(final String email,final String password) {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();

        final String account= email.split("@")[0];
//        Intent intent = new Intent(MainActivity.this, FrontActivity.class);
//        intent.putExtra("account",email.split("@")[0]);
//        startActivity(intent);
        showProgressDialog();
        // [START link_credential]
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "linkWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "登陆失败",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "登陆成功",
                                    Toast.LENGTH_SHORT).show();
//

//                            mDatabase.child("users").child(account).addListenerForSingleValueEvent(
//                                    new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//                                            // Get user value
//                                            User user = dataSnapshot.getValue(User.class);
//
//                                            mDatabase.child("users").child(account).updateChildren(user.toMap());
//
////                                            myUser = dataSnapshot.child(myAccount).getValue(User.class);
////                                            Buddies = myUser.getBuddies().split(" ");
////                                            for (String s : Buddies) {
////                                                myBuddies.add((dataSnapshot.child(s).getValue(User.class)));
////                                                Log.d(TAG, "+++++++++++++++++++++++++++" + (dataSnapshot.child(s).getValue(User.class)).getEmail());
//
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//                                        }
//                                    });


                            Intent intent = new Intent(MainActivity.this, FrontActivity.class);
                            intent.putExtra("account",email.split("@")[0]);
                            startActivity(intent);
                        }
                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
// [END link_credential]
    }

    private void writeNewUser(String account, String email, String password) {
        User user = new User(email, password);
//        mDatabase.child("users").child(account).
//        user.toMap();
//        mDatabase.child("users").child(account).updateChildren(user.toMap());
        mDatabase.child("users").child(account).setValue(user);
    }
}
