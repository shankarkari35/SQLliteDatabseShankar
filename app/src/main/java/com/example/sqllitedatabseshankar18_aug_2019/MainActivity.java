package com.example.sqllitedatabseshankar18_aug_2019;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnClickListener {
EditText eroll,ename,eMarks;
Button add,del,modify,vAll,showInfo,bview;
SQLiteDatabase db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eroll=findViewById(R.id.rollnum);
        eMarks=findViewById(R.id.mark);
        ename=findViewById(R.id.editname);
        add=findViewById(R.id.add);
        del=findViewById(R.id.delet);
        modify=findViewById(R.id.modify);
        vAll=findViewById(R.id.viewall);
        showInfo=findViewById(R.id.show);
        bview=findViewById(R.id.view);
        add.setOnClickListener(this);
        del.setOnClickListener(this);
        modify.setOnClickListener(this);
        vAll.setOnClickListener(this);
        showInfo.setOnClickListener(this);
        bview.setOnClickListener(this);
        db=openOrCreateDatabase("studentDB",MODE_PRIVATE,null);
        db.execSQL("create table if not exists student(rollno varchar,name varchar,marks varchar);");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.add:
                //Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
               if( eroll.getText().toString().trim().length()==0||
                        ename.getText().toString().trim().length()==0||
                        eMarks.getText().toString().trim().length()==0){
                  // Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
                   shwmsg("error"," Invalid Input");
                   db.execSQL("insert into student values('"+eroll.getText()+"','"+ename.getText()+"','"+eMarks.getText()+"')");
                   shwmsg("Success","Record add");
                   return;
               }
                break;

            case R.id.delet:

               // Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
                if(eroll.getText().toString().trim().length()==0)
                {
                    shwmsg("Error", "Please enter Rollno");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+eroll.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("DELETE FROM student WHERE rollno='"+eroll.getText()+"'");
                    shwmsg("Success", "Record Deleted");
                }
                else
                {
                    shwmsg("Error", "Invalid Rollno");
                }
                clearText();
                break;

            case R.id.modify:
                Toast.makeText(this, "Modify", Toast.LENGTH_SHORT).show();

                if(eroll.getText().toString().trim().length()==0)
                {
                    shwmsg("Error", "Please enter Rollno");
                    return;
                }
                Cursor cursor=db.rawQuery("SELECT * FROM student WHERE rollno='"+eroll.getText()+"'", null);
                if(cursor.moveToFirst())
                {
                    db.execSQL("UPDATE student SET name='"+ename.getText()+"',marks='"+eMarks.getText()+
                            "' WHERE rollno='"+eroll.getText()+"'");
                    shwmsg("Success", "Record Modified");
                }
                else
                {
                    shwmsg("Error", "Invalid Rollno");
                }
                clearText();
                break;

            case R.id.view:
                Cursor cursor1=db.rawQuery("SELECT * FROM student", null);
                if(cursor1.getCount()==0)
                {
                    shwmsg("Error", "No records found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(cursor1.moveToNext())
                {
                    buffer.append("Rollno: "+cursor1.getString(0)+"\n");
                    buffer.append("Name: "+cursor1.getString(1)+"\n");
                    buffer.append("Marks: "+cursor1.getString(2)+"\n\n");
                }
                shwmsg("Student Details", buffer.toString());
                Toast.makeText(this, "View", Toast.LENGTH_SHORT).show();
                break;

            case R.id.viewall:

                Cursor cursor2=db.rawQuery("SELECT * FROM student", null);
                if(cursor2.getCount()==0)
                {
                    shwmsg("Error", "No records found");
                    return;
                }
                StringBuffer buffer1=new StringBuffer();
                while(cursor2.moveToNext())
                {
                    buffer1.append("Rollno: "+cursor2.getString(0)+"\n");
                    buffer1.append("Name: "+cursor2.getString(1)+"\n");
                    buffer1.append("Marks: "+cursor2.getString(2)+"\n\n");
                }
                shwmsg("Student Details", buffer1.toString());
                Toast.makeText(this, "View All", Toast.LENGTH_SHORT).show();
                break;

            case R.id.show:

                shwmsg("Develop By-","mr shankar kari");
                Toast.makeText(this, "Show", Toast.LENGTH_SHORT).show();
                break;


        }
    }/*public void showMessege(String title,String messege){


    }*/
   public void clearText()
    {
        eroll.setText(" ");
        ename.setText(" ");
        eMarks.setText(" ");
    }

    private void shwmsg(String s, String msg) {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);

        alertDialog.setCancelable(true);
        alertDialog.setTitle(s);
        alertDialog.setMessage(msg);
        alertDialog.show();
        alertDialog.setIcon(R.mipmap.ic_launcher_round);


    }

}
