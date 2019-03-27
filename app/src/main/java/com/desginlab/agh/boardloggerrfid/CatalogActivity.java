package com.desginlab.agh.boardloggerrfid;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CatalogActivity extends AppCompatActivity {

    private BoardLogAdapter mAdapter;

    /** FIREBASE **/
    private FirebaseDatabase mFirebaseDatabase;
    ChildEventListener mChildBoardLogsListener;
    private DatabaseReference mBoardLogsDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        ListView boardLoggsListView = (ListView) findViewById(R.id.list);


        mAdapter = new BoardLogAdapter(this, new ArrayList<BoardLog>());
        boardLoggsListView.setAdapter(mAdapter);


        /**FIREBASE DATABASE **/
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mBoardLogsDatabaseReference = mFirebaseDatabase.getReference().child("Data");
        attachDatabaseReadListener();

        boardLoggsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                BoardLog currentBoardLogg= mAdapter.getItem(position);
                Intent editorIntent = new Intent(CatalogActivity.this,EditorActivity.class);
                editorIntent.putExtra("EXTRA_BOARD_ID", currentBoardLogg.getBoardId());
                editorIntent.putExtra("EXTRA_STUDENT_ID", currentBoardLogg.getStudentId());
                editorIntent.putExtra("EXTRA_DATABASE_KEY",currentBoardLogg.getDatabaseKey());
                if(currentBoardLogg.getStudentName()!=null) {
                    editorIntent.putExtra("EXTRA_STUDENT_NAME", currentBoardLogg.getStudentName());
                }
                if(currentBoardLogg.getBoardImageId()!=null) {
                    editorIntent.putExtra("EXTRA_IMAGE_ID", currentBoardLogg.getBoardImageId());
                }
                startActivity(editorIntent);
            }
        });
    }

    private void attachDatabaseReadListener() {
        if (mChildBoardLogsListener == null) {
            mChildBoardLogsListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    BoardLog mBoardLog = dataSnapshot.getValue(BoardLog.class);
                    mBoardLog.setDatabaseKey(dataSnapshot.getKey());
                    mAdapter.add(mBoardLog);

                    Map<String, Object> saveKeyMap = new HashMap<>();
                    saveKeyMap.put("databaseKey", dataSnapshot.getKey());
                    mBoardLogsDatabaseReference.child(dataSnapshot.getKey()).updateChildren(saveKeyMap);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }
                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            };
            mBoardLogsDatabaseReference.addChildEventListener(mChildBoardLogsListener);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        attachDatabaseReadListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mChildBoardLogsListener != null){
            mBoardLogsDatabaseReference.removeEventListener(mChildBoardLogsListener);
            mChildBoardLogsListener=null;
        }
        mAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Windsurfer clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_refresh:
                recreate();
        }
        return super.onOptionsItemSelected(item);
    }
}
