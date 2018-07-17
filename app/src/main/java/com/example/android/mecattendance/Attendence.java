package com.example.android.mecattendance;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import static android.R.attr.action;
import static android.R.attr.button;
import static android.R.attr.documentLaunchMode;
import static android.R.attr.start;
import static android.R.attr.switchMinWidth;
import static android.util.Log.v;
import static com.example.android.mecattendance.R.id.clas;
import static com.example.android.mecattendance.R.id.div;
import static com.example.android.mecattendance.R.id.roll;

public class Attendence extends AppCompatActivity {


    String[] subname=new String[10]; //used to store the name of the subjects
    String[] lastedit=new String[10];    //used to store the last edit details
    int[] sub_hrs=new int[10];   //used to store the subect hours
    float[] attn = new float[10];
    String[] sub = new String[10];
    int[] bunk_no=new int[10];
    String name;
    int nsub;
    Document document;

    void get_subjectcode(Document doc)    //get mydetails and the subject codes
    {
        nsub=0;
        int star,end;
        String text;
        org.jsoup.nodes.Element table = (org.jsoup.nodes.Element) doc.select("table").get(0);
        Elements rows=table.select("tr");
        org.jsoup.nodes.Element row = rows.get(0);
        Elements cols=row.select("td");
        int i=2;
        do {

            org.jsoup.nodes.Element subcode = cols.get(i);
            text=subcode.text();
            if (text.equals("Total %"))
                break;
            else
            {
                star=0;
                end=text.indexOf("(");
                if(text.substring(star,end).equals("Spl. "))
                    break;
                else {
                    sub[nsub] = text.substring(star, end);
                    star = text.indexOf("(") + 1;
                    end = text.indexOf(")");
                    sub_hrs[nsub] = Integer.parseInt(text.substring(star, end));
                    v("sub_code" + nsub, sub[nsub]);
                    v("sub_hrs" + nsub, Integer.toString(sub_hrs[nsub]));
                    nsub++;
                }

            }
            i++;
        }while (!text.equals("Total %"));
    }

    void get_subject_lastedit(Document doc)
    {
        org.jsoup.nodes.Element le=doc.select("table").get(1);
        Elements lrows=le.select("tr");
        for (int i=1;i<=nsub;i++)
        {
            org.jsoup.nodes.Element row=lrows.get(i);
            Elements cols=row.select("td");
            for(int j=0;j<2;j++)
            {
                org.jsoup.nodes.Element word=cols.get(j);
                if(j==0)
                {
                    String name=word.text();
                    subname[i-1]=name.replace(sub[i-1],"").trim();
                }
                if(j==1)
                    lastedit[i-1]=word.text().trim();
            }
            Log.v("Subject name "+(i-1),subname[i-1]);
            Log.v("Last Edit "+(i-1),lastedit[i-1]);
        }
    }

    void get_attn(Document doc)
    {
        try {
            org.jsoup.nodes.Element attrec = doc.select("table").get(0);
            SharedPreferences p = getApplicationContext().getSharedPreferences("Student", MODE_PRIVATE);
            int r = Integer.parseInt(p.getString("roll", null));
            Elements attrow = attrec.select("tr");
            org.jsoup.nodes.Element row = attrow.get(r + 1);
            Elements cols = row.select("td");
            name = cols.get(1).text().trim();
            Log.v("Name", name);
            for (int i = 2; i < (nsub + 2); i++) {
                org.jsoup.nodes.Element subper = cols.get(i);
                if (subper.text().equals("Nil"))
                    attn[i - 2] = 0;
                else
                    attn[i - 2] = Float.valueOf(subper.text().trim()).floatValue();
                Log.v("Attendence" + (i - 2), String.valueOf(attn[i - 2]));
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  Toast.makeText(Attendence.this,"Roll number not found",Toast.LENGTH_LONG).show();
                                  Intent i=new Intent(Attendence.this,MainActivity.class);
                                  startActivity(i);
                              }
                          });

        }
    }

    void get_bunkable()
    {
//        final Intent intent = getIntent();
//        final int nsub=getsubects(intent.getStringExtra("sem"));//used to get the number of subjects
        boolean flag=true;
        int sub_no=-1,i=0,j=1,no=0;
        float per;
        while (flag)
        {
            sub_no=sub_no+1;
            j=sub_hrs[sub_no];
            no=0;
            if(attn[sub_no]>=75)
            {
                while (i == 0)
                {
                    j = j + 1;
                    per = ((attn[sub_no] * sub_hrs[sub_no] / 100) / j) * 100;
                    if (per < 75.0) {
                        bunk_no[sub_no] = no;
                        i = 1;
                    }
                    no=no+1;
                }
            }
            else
            {
                while (i == 0) {
                    j = j + 1;
                    no = no + 1;
                    per = (((attn[sub_no] * sub_hrs[sub_no] / 100)+no) / j) * 100;
                    if (per >= 75.0) {
                        bunk_no[sub_no] = (-no);
                        i = 1;
                    }
                }
            }
            if (sub_no==(nsub-1))
                flag=false;
            else
            {
                i=0;

            }
            Log.v("Bunkable "+sub_no,String.valueOf(bunk_no[sub_no]));
        }
    }

    void feedAdapter()
    {
//        final Intent intent = getIntent();
//        final int nsub=getsubects(intent.getStringExtra("sem"));//used to get the number of subjects

        final String[] sname = subname;
        final int[] bno=bunk_no;
        final float[] at=attn;
        final String[] le=lastedit;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getSupportActionBar().setTitle(name);
                ArrayList<Subject> Subjects= new ArrayList<Subject>();
                for (int k=0;k<nsub;k++)
                {
                    Subjects.add(new Subject(sname[k],bno[k],at[k],le[k]));
                }
                subjetcAdapter subjetcAdapter = new subjetcAdapter(Attendence.this,Subjects);
                ListView listView = (ListView)findViewById(R.id.listview);
                listView.setAdapter(subjetcAdapter);
            }
        });
    }

    void gettimetable(Document doc)
    {
        org.jsoup.nodes.Element table = doc.select("table").get(2); //select the first table.
        Elements rows = table.select("tr");
        String[][] TimeTable =new String[5][6];
        String SubjectTeacher,nam,s;
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("timetable", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 2; i <=6; i++) { //first row is the col names so skip it.
            org.jsoup.nodes.Element row = rows.get(i);
            Elements cols = row.select("td");
            for (int j = 1; j <= 6; j++) {

                org.jsoup.nodes.Element words = cols.get(j);

                nam = words.select("b").text();
                SubjectTeacher = cols.get(j).text();
                if(SubjectTeacher.equals(""))
                    TimeTable[i - 2][j - 1]=" ";
                else {
                    s = SubjectTeacher.replace(nam, "");

                    TimeTable[i - 2][j - 1] = s.substring(sub[0].length());


                }
                editor.putString(Integer.toString((i * 10) + j), TimeTable[i - 2][j - 1]);
                Log.v("TimeTable" + Integer.toString((i * 10) + j), TimeTable[i - 2][j - 1]);

            }

        }
        editor.apply();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_timetable:
                Intent i = new Intent(Attendence.this, Timetable.class);
                startActivity(i);
                break;
            case R.id.credits:i=new Intent(Attendence.this, credits.class);
                startActivity(i);
                break;
            case R.id.update: i=i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://drive.google.com/open?id=1j3wvDMbnk-KLKpn8CphCXhfHjCJfyJPK"));
                startActivity(i);

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Attendence.this,MainActivity.class));
        overridePendingTransition(
                0,
                R.anim.play_panel_close_background
        );
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.attendence_action_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread th, Throwable ex) {
            System.out.println("Uncaught exception: " + ex);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        String clas = null;
        SharedPreferences p = getApplicationContext().getSharedPreferences("Student", MODE_PRIVATE);
        if (p.getString("roll", "-1").equals("-1")) {
            Intent i = new Intent(Attendence.this, MainActivity.class);
           // i.putExtra("Document", (Parcelable) document);
            startActivity(i);
        } else {
            clas = p.getString("class", null);
            final String finalClas = clas;
            final Thread download = new Thread() {
                public void run() {
                    Document doc = null;
                    try {
                            doc = Jsoup.connect("http://attendance.mec.ac.in/view4stud.php").data("class", finalClas).data("submit", "view").get();
                        document=doc;
                    } catch (IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.v("Hello","Hello");
                                //Toast.makeText(Attendence.this,"Connect to Internet",Toast.LENGTH_LONG).show();
                                setContentView(R.layout.offline);
                                Button refresh = (Button) findViewById(R.id.refresh);
                                refresh.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {recreate();
                                    }
                                });

                                //Intent i=new Intent(Attendence.this,MainActivity.class);
                               // startActivity(i);
                            }
                        });

                    }

                    get_subjectcode(doc); //get mydetails and find the subjects with the subject codes

                    get_subject_lastedit(doc);  //get the subject name and the last edit details

                    get_attn(doc); //get the attendence percentage

                    get_bunkable(); //get the bunkable nuber of hours

                    gettimetable(doc);

                    feedAdapter();  //feed the adapter class the data

                }

            };
            download.setUncaughtExceptionHandler(h);
            download.start();

        }
    }
}
