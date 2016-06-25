package com.example.heman.noteapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.location.SettingInjectorService;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.Telephony;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import static android.location.LocationManager.*;
import static android.location.LocationManager.GPS_PROVIDER;
import static android.widget.Toast.*;
import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    DatabaseClass myDb;
    EditText noteDataText;
    Button addNoteButton;
    EditText user_input;
    int REQUEST_CODE = 1;
    Long cRow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNoteButton=(Button)findViewById(R.id.addNote);
        openDB();
        addNoteButton = (Button)findViewById(R.id.addNote);
        populateList();




       addNoteButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final Dialog dialog = new Dialog(MainActivity.this);
               dialog.setContentView(R.layout.note_input_dailog);
               dialog.show();

               Button AddNote = (Button) dialog.findViewById(R.id.Add);
               final EditText Notes = (EditText) dialog.findViewById(R.id.userinput);
               AddNote.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       //longitude = (int) currentLocation.getLongitude();
                       //latitude = (int) currentLocation.getLatitude();
                       myDb.insertRow(Notes.getText().toString(),null);
                       populateList();
                       dialog.cancel();
                   }
               });

           }
       });
}





    private void openDB(){
        myDb = new DatabaseClass(this); myDb.open();}


    public void populateList(){
        Cursor cursor =myDb.getRows();
       // byte[] image = cursor.getBlob(2);
        //Bitmap Hello  = BitmapFactory.decodeByteArray(image, 0, image.length);



        String[] fromfieldnames =new  String[]{DatabaseClass.Note_id,DatabaseClass.Note_data, };
        int[] toviewids = new int[]{R.id.textViewNumber,R.id.textViewData};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(),R.layout.item_layout, cursor,fromfieldnames,toviewids, 0);
        final ListView myList =(ListView)findViewById(R.id.listViewData);
        myList.setAdapter(myCursorAdapter);



        myList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                final Dialog Udialog = new Dialog(MainActivity.this);
                Udialog.setContentView(R.layout.del_upd);
                Udialog.show();

                Button del = (Button) Udialog.findViewById(R.id.delete);
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor cursor = (Cursor) myList.getItemAtPosition(position);
                        //String Cdata = cursor.getString(0);
                        long Cdata = cursor.getLong(0);
                        myDb.delRow(Cdata);
                        //Toast.makeText(getApplicationContext(), Cdata, Toast.LENGTH_LONG).show();
                        populateList();
                        Udialog.cancel();
                    }
                });


                Button ADDimg = (Button) Udialog.findViewById(R.id.add_image);
                ImageView Image = (ImageView) Udialog.findViewById(R.id.imageView);
                ADDimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, REQUEST_CODE);
                        }
                    }
                });

                Button edit = (Button)Udialog.findViewById(R.id.edit);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Udialog.cancel();
                        Cursor cursor = (Cursor) myList.getItemAtPosition(position);
                        final String cData = cursor.getString(1);
                        cRow = cursor.getLong(0);

                        final Dialog edialog = new Dialog(MainActivity.this);

                       edialog.setContentView(R.layout.edit_layout);
                        edialog.show();
                        final EditText note_edit =(EditText)edialog.findViewById(R.id.note_edit);
                        note_edit.setText(cData);

                        Button edit_2 = (Button)edialog.findViewById(R.id.edit_btn);
                        edit_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               String cData = note_edit.getText().toString();
                                //Toast.makeText(getApplicationContext(), cData, Toast.LENGTH_LONG).show();
                                myDb.EditRow(cRow, cData);
                                populateList();
                                edialog.cancel();

                            }
                        });
                    }
                });
            }
        });

   }







    public void onActivityResult ( int requestcode, int resultcode, Intent data){
        if (requestcode == REQUEST_CODE) ;
        {
            if (resultcode == RESULT_OK) {
                //Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();

                Bundle bundle = new Bundle();
                bundle = data.getExtras();
                Bitmap CurBitMap;
                CurBitMap = (Bitmap) bundle.get("data");

               /* ByteArrayOutputStream stream = new ByteArrayOutputStream();
                CurBitMap.compress(Bitmap.CompressFormat.PNG, 0, stream);
                byte[] byteArray = stream.toByteArray();
                myDb.InsertImage(cRow, byteArray);
                populateList();*/




                //

            }

        }


    }




}