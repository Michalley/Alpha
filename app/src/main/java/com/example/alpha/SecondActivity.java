package com.example.alpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.alpha.FBreff.reff;

public class SecondActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Alpha alpha, alpTmp;

    Intent t;

    ListView lv;
    Button btnE;
    EditText etN, etI;

    ArrayAdapter<String> adp;

    ArrayList<String> alpList = new ArrayList<String>();
    ArrayList<Alpha> alpValues = new ArrayList<Alpha>();

    String info, name;
    String str1, str2, str;
    int pos;

    ValueEventListener alpListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        lv = (ListView) findViewById(R.id.lv);
        btnE = (Button) findViewById(R.id.btnE);
        etN = (EditText) findViewById(R.id.etN);
        etI = (EditText) findViewById(R.id.etI);

        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }


    public void EnterData(View view) {
        name=etN.getText().toString();
        info=etI.getText().toString();
        etN.setText("");
        etI.setText("");
        WriteDataBase(info,name);
        ReadDataBase();
    }

    public void WriteDataBase(String info, String name) {
        alpha = new Alpha(info, name);
        reff.child(name).setValue(alpha);
        Toast.makeText(SecondActivity.this, "Success", Toast.LENGTH_SHORT).show();
    }


    public void ReadDataBase() {
        alpListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                alpList.clear();
                alpValues.clear();
                for (DataSnapshot data : dS.getChildren()) {
                    str1 = (String) data.getKey();
                    Alpha alpha=data.getValue(Alpha.class);
                    alpValues.add(alpha);
                    str2 = alpha.getName();
                    str = str1+" "+str2;
                    alpList.add(str);
                }
                adp = new
                        ArrayAdapter<String>(SecondActivity.this,R.layout.support_simple_spinner_dropdown_item,alpList);
                lv.setAdapter(adp);
                Toast.makeText(SecondActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SecondActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        };
        reff.addValueEventListener(alpListener);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String ss = alpList.remove(position);
        Alpha cc = alpValues.get(position);
        String bb = cc.getInfo();
        //DatabaseReference drd = FirebaseDatabase.getInstance().getReference("Alpha").child(bb);
        reff.child(bb).removeValue();

        adp = new
                ArrayAdapter<String>(SecondActivity.this,R.layout.support_simple_spinner_dropdown_item,alpList);
        lv.setAdapter(adp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //activity menu defined
        menu.add("Authentication");
        menu.add("Storage");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //activity menu selection
        String st = item.getTitle().toString();
        if (st.equals("Database")) {
            t = new Intent(this, MainActivity.class);
        }
        if (st.equals("Storage")) {
            t = new Intent(this, ThirdActivity.class);
        }

        if (alpListener != null) {
            reff.removeEventListener(alpListener);
        }
        startActivity(t);
        return true;
    }
}

