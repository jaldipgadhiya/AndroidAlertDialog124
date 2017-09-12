package com.android.acadgild.androidalertdialog124;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.acadgild.androidalertdialog124.adapter.CustomAdapter;
import com.android.acadgild.androidalertdialog124.database.DBHelper;
import com.android.acadgild.androidalertdialog124.model.InfoData;
import com.android.acadgild.androidalertdialog124.utils.CommonUtilities;
import com.android.acadgild.androidalertdialog124.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView list;
    ArrayList<InfoData> model= new ArrayList<>();
    DBHelper dbHelper;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        Toast.makeText(MainActivity.this, "Clicked on Item: " + position, Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list= (ListView)findViewById(R.id.toList);


        dbHelper= CommonUtilities.getDBObject(this);
        //To get all info to dispaly in LIST
        model = dbHelper.getAllInfos();
        //Cusotom Adapter to display
        final CustomAdapter adapter= new CustomAdapter(this, model);
        //Set Adapter to list
        list.setAdapter(adapter);
        //Set On Item Click Listner on List
        list.setOnItemClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater M = getMenuInflater();
        M.inflate(R.menu.main, menu);



        //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //Check When click on Add menu
        if (id == R.id.menuAdd) {
            //Create dialog Object
            AlertDialog.Builder mBuilder =  new AlertDialog.Builder(MainActivity.this);
            //Inflate dialog Object view
            final View mView = getLayoutInflater().inflate(R.layout.dialog_addtask,null);
            final EditText mName = (EditText) mView.findViewById(R.id.etName);
            final EditText mPhone = (EditText) mView.findViewById(R.id.etPhoneNo);
            final EditText mDob = (EditText) mView.findViewById(R.id.etDob);
            Button mSave = (Button) mView.findViewById(R.id.btnSave);
            Button mCancel = (Button) mView.findViewById(R.id.btnCancel);
            //Set view to Dialog
            mBuilder.setView(mView);
            //Create Dialog
            final AlertDialog dialog = mBuilder.create();
            //Show Dialog
            dialog.show();

            //On click of save button on Dialog
            mSave.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //Insert Record into DB
                    insertToDoRecord(mView,mName,mPhone,mDob );
                    //Close Dialog
                    dialog.cancel();
                }
            });

            mCancel.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });



            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Method to insert record in DB
    public void insertToDoRecord(View v,EditText vTitle,EditText vDesc,EditText dob){

        dbHelper= CommonUtilities.getDBObject(this);


        if(!vTitle.getText().toString().isEmpty() && !vDesc.getText().toString().isEmpty() && !dob.getText().toString().isEmpty())
        {
            ContentValues vals = new ContentValues();
            vals.put(Constants.KEY_NAME, vTitle.getText().toString());
            vals.put(Constants.KEY_PHONE, vDesc.getText().toString());
            vals.put(Constants.KEY_DOB, dob.getText().toString());
            long insId = dbHelper.insertContentVals(Constants.INFO_RECORD, vals);
            Log.d("Insert","insId: " + insId);
            if(insId != -1){
                model = dbHelper.getAllInfos();
                //Generate Custom Adapter
                CustomAdapter adapter= new CustomAdapter(this, model);
                //Set Adapter to refresh List
                list.setAdapter(adapter);
                Toast.makeText(MainActivity.this, "New row added, row id: " + insId, Toast.LENGTH_SHORT).show();}

            else
                Toast.makeText(MainActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
        }
    }

}
