package com.example.heman.noteapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseClass myDb;
    EditText noteDataText;
    Button addNoteButton;
    EditText user_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //noteDataText = (EditText)findViewById(R.id.noteData);

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


                    Button AddNote = (Button)dialog.findViewById(R.id.Add);
                   final EditText Notes = (EditText)dialog.findViewById(R.id.userinput);
                    AddNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDb.insertRow(Notes.getText().toString());
                        populateList();
                        dialog.cancel();


                    }
                });

                }

       });
};



    private void openDB(){
        myDb = new DatabaseClass(this); myDb.open();}




    public void populateList(){
                        Cursor cursor =myDb.getRows();
                        String[] fromfieldnames =new  String[]{DatabaseClass.Note_id,DatabaseClass.Note_data};
                        int[] toviewids = new int[]{R.id.textViewNumber,R.id.textViewData};
                        SimpleCursorAdapter myCursorAdapter;
                        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(),R.layout.item_layout, cursor,fromfieldnames,toviewids, 0);
                        ListView myList =(ListView)findViewById(R.id.listViewData);
                        myList.setAdapter(myCursorAdapter);




        /*ListView myList =(ListView)findViewById(R.id.listViewData);
                        myList.setAdapter(myCursorAdapter);*/


   }

}