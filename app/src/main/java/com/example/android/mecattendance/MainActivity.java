package com.example.android.mecattendance;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.mecattendance.R.id.clas;
import static com.example.android.mecattendance.R.id.div;


public class MainActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);

            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;

            }
        }, 2000);
    }
    public static boolean hasPermissions(Context context, String... permissions)
    {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null)
        {
            for (String permission : permissions)
            {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = (Button) findViewById(R.id.button1);

        getSupportActionBar().hide();
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.sem, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.checked_text_view);

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.INTERNET,};

    if (!hasPermissions(this, PERMISSIONS)) {
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
    }

        final EditText e = (EditText) findViewById(R.id.roll);
        final RadioGroup clas = (RadioGroup) findViewById(R.id.clas);
        final RadioGroup div = (RadioGroup) findViewById(R.id.div);
        final Spinner sem = (Spinner) findViewById(R.id.sem);
        sem.setAdapter(adapter);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Attendence.class);
                String cla = "", di = "";
                if (clas.getCheckedRadioButtonId() != -1)
                    cla = ((RadioButton) findViewById(clas.getCheckedRadioButtonId())).getText().toString();
                if (div.getCheckedRadioButtonId() != -1)
                    di = ((RadioButton) findViewById(div.getCheckedRadioButtonId())).getText().toString();
                String roll = "";
                roll = e.getText().toString();
                String year = "";
                String se = sem.getSelectedItem().toString();
                switch (cla) {
                    case "Computer Science":
                        cla = "C";
                        break;
                    case "Electronics and Communication":
                        cla = "E";
                        break;
                    case "Electrical":
                        cla = "EE";
                        break;
                    case "Biomedical":
                        cla = "B";
                        break;
                    default:
                        cla = "";
                }
                if (cla.equals(""))
                    Toast.makeText(MainActivity.this, "Select Class", Toast.LENGTH_SHORT).show();
                else if (di.equals("")&&(cla.equals("C")||cla.equals("E")))
                    Toast.makeText(MainActivity.this, "Select Division", Toast.LENGTH_SHORT).show();
                else if (roll.equals(""))
                    Toast.makeText(MainActivity.this, "Enter Rollnumber", Toast.LENGTH_SHORT).show();
                else if (se.equals("Semester"))
                    Toast.makeText(MainActivity.this, "Enter Semester", Toast.LENGTH_SHORT).show();
                else if (cla.equals("EE") || cla.equals("B")) {
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("Student",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    i.putExtra("roll", roll);
                    editor.putString("roll",roll);
                    i.putExtra("class", cla + se);
                    editor.putString("class",cla+se);
                    editor.apply();
                    startActivity(i);
                } else {
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("Student",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    i.putExtra("roll", roll);
                    editor.putString("roll",roll);
                    i.putExtra("class", cla + se + di);
                    editor.putString("class",cla+se+di);
                    editor.apply();
                    startActivity(i);
                }


            }
        });
    }
}
