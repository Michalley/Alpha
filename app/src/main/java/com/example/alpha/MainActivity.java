package com.example.alpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import static com.example.alpha.FBreff.mAuth;
import static com.example.alpha.FBreff.refUsers;

public class MainActivity extends AppCompatActivity  {

    Intent t;
    EditText etE, etP, etPN,etN;
    Button btnLI,btnR;
    CheckBox cb;

    String name,email, password, phoneN, uid;
    Users userdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLI = (Button) findViewById(R.id.btnLI);
        btnR = (Button) findViewById(R.id.btnR);
        etN = (EditText) findViewById(R.id.etN);
        etP = findViewById(R.id.etP);
        etE = findViewById(R.id.etE);
        etPN = findViewById(R.id.etPN);
        cb = (CheckBox) findViewById(R.id.cb);

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences settings=getSharedPreferences("PREFS_NAME",0);
        Boolean isChecked=settings.getBoolean("isChecked",false);
        Intent si = new Intent(MainActivity.this,SecondActivity.class);
        if (mAuth.getCurrentUser()!=null && isChecked) {
            si = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(si);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void register(View view) {
        name=etN.getText().toString();
        phoneN=etPN.getText().toString();
        email=etE.getText().toString();
        password=etP.getText().toString();
        final ProgressDialog pd=ProgressDialog.show(this,"Register","Registering...",true);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.dismiss();
                        if (task.isSuccessful()) {
                            SharedPreferences settings=getSharedPreferences("PREFS_NAME",0);
                            SharedPreferences.Editor editor=settings.edit();
                            editor.putBoolean("stayConnect",cb.isChecked());
                            editor.commit();
                            Log.d("MainActivity", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            uid = user.getUid();
                            userdb=new Users(name,email,phoneN,uid);
                            refUsers.child(name).setValue(userdb);
                            Toast.makeText(MainActivity.this, "Successful registration", Toast.LENGTH_LONG).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                Toast.makeText(MainActivity.this, "User with e-mail already exist!", Toast.LENGTH_LONG).show();
                            else {
                                Log.w("MainActivity", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "User create failed.",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    public void login(View view) {
        email = etE.getText().toString();
        password = etP.getText().toString();
        //final ProgressDialog pd=ProgressDialog.show(this,"Login","Connecting...",true);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //pd.dismiss();
                        if (task.isSuccessful()) {
                            SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("stayConnect", cb.isChecked());
                            editor.commit();
                            Log.d("MainActivity", "signinUserWithEmail:success");
                            t = new Intent(MainActivity.this, SecondActivity.class);
                            startActivity(t);
                        } else {
                            Log.d("MainActivity", "signinUserWithEmail:fail");
                            Toast.makeText(MainActivity.this, "e-mail or password are wrong!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //activity menu defined
        menu.add("Database");
        menu.add("Storage");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //activity menu selection
        String st = item.getTitle().toString();
        if (st.equals("Database")) {
            t = new Intent(this, SecondActivity.class);
        }
        if (st.equals("Storage")) {
            t = new Intent(this, ThirdActivity.class);
        }
        startActivity(t);
        return true;
    }
}
