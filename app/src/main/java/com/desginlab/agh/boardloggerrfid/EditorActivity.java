package com.desginlab.agh.boardloggerrfid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class EditorActivity extends AppCompatActivity {

    BoardLog mBoardLog = new BoardLog();

    private TextView boardIdEditText;
    private TextView studentIdEditText;
    private EditText studentNameEditText;

    /**
     * Selected board image
     */
    private ImageView selected_frdm_k22f;
    private ImageView selected_frdm_kl46z;
    private ImageView selected_rdl_uno_atmega328;

    private RelativeLayout relativeLayout_frdm_k22f;
    private RelativeLayout relativeLayout_frdm_k146z;
    private RelativeLayout relativeLayout_rdl_uno_atmega328;

    /** FIREBASE **/
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mBoardLogsDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Intent intent = getIntent();

        /**FIREBASE DATABASE **/
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mBoardLogsDatabaseReference = mFirebaseDatabase.getReference().child("Data");

        boardIdEditText = (TextView) findViewById(R.id.editor_board_id_edit_text);
        studentIdEditText = (TextView) findViewById(R.id.editor_student_id_edit_text);
        studentNameEditText = (EditText) findViewById(R.id.editor_student_name_edit_text);
        selected_frdm_k22f = findViewById(R.id.frdm_k22f_selected);
        selected_frdm_kl46z = findViewById(R.id.frdm_k146z_selected);
        selected_rdl_uno_atmega328 = findViewById(R.id.rdl_uno_atmega328_selected);
        selected_frdm_k22f.setVisibility(View.GONE);
        selected_frdm_kl46z.setVisibility(View.GONE);
        selected_rdl_uno_atmega328.setVisibility(View.GONE);
        relativeLayout_frdm_k22f = findViewById(R.id.frdm_k22f_relative_layout);
        relativeLayout_frdm_k146z = findViewById(R.id.frdm_k146z_relative_layout);
        relativeLayout_rdl_uno_atmega328 = findViewById(R.id.rdl_uno_atmega328_relative_layout);


        mBoardLog.setBoardId(intent.getStringExtra("EXTRA_BOARD_ID"));
        mBoardLog.setStudentId(intent.getStringExtra("EXTRA_STUDENT_ID"));
        mBoardLog.setDatabaseKey(intent.getStringExtra("EXTRA_DATABASE_KEY"));
        mBoardLog.setBoardImageId(intent.getStringExtra("EXTRA_IMAGE_ID"));
        boardIdEditText.setText(mBoardLog.getBoardId());
        studentIdEditText.setText(mBoardLog.getStudentId());
        mBoardLog.setStudentName(intent.getStringExtra("EXTRA_STUDENT_NAME"));

        if (mBoardLog.getBoardImageId() != null) {
            if (mBoardLog.getBoardImageId().equals(Integer.toString(R.drawable.frdm_k22f))) {
                selected_frdm_k22f.setVisibility(View.VISIBLE);
            } else if (mBoardLog.getBoardImageId().equals(Integer.toString(R.drawable.frmd_kl46z))) {
                selected_frdm_kl46z.setVisibility(View.VISIBLE);
            } else if (mBoardLog.getBoardImageId().equals(Integer.toString(R.drawable.rdl_uno_atmega328))) {
                selected_rdl_uno_atmega328.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getApplicationContext(), "Select board image", Toast.LENGTH_SHORT);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Select board image", Toast.LENGTH_SHORT);
        }
        if (!mBoardLog.getStudentName().equals("DEFAULT")) {
            studentNameEditText.setText(mBoardLog.getStudentName());
        }

        relativeLayout_frdm_k22f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBoardLog.setBoardImageId(Integer.toString(R.drawable.frdm_k22f));
                selected_frdm_k22f.setVisibility(View.VISIBLE);
                selected_frdm_kl46z.setVisibility(View.GONE);
                selected_rdl_uno_atmega328.setVisibility(View.GONE);

            }
        });

        relativeLayout_frdm_k146z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBoardLog.setBoardImageId(Integer.toString(R.drawable.frmd_kl46z));
                selected_frdm_k22f.setVisibility(View.GONE);
                selected_frdm_kl46z.setVisibility(View.VISIBLE);
                selected_rdl_uno_atmega328.setVisibility(View.GONE);

            }
        });

        relativeLayout_rdl_uno_atmega328.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBoardLog.setBoardImageId(Integer.toString(R.drawable.rdl_uno_atmega328));
                selected_frdm_k22f.setVisibility(View.GONE);
                selected_frdm_kl46z.setVisibility(View.GONE);
                selected_rdl_uno_atmega328.setVisibility(View.VISIBLE);

            }
        });

    }
    /**
     * Get user input from editor and save event into database.
     */
    private boolean saveEvent() {
        String boardIdString = boardIdEditText.getText().toString().trim();
        String studentIdString = studentIdEditText.getText().toString().trim();
        String studentName = studentNameEditText.getText().toString().trim();

                mBoardLog.setBoardId(boardIdString);
                mBoardLog.setStudentId(studentIdString);
                mBoardLog.setStudentName(studentName);

                Map<String, Object> boardValues = mBoardLog.toMap();
                mBoardLogsDatabaseReference.child(mBoardLog.getDatabaseKey()).updateChildren(boardValues);
                return true;
            }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if(saveEvent()) {
                    finish();
                    return true;
                } else {
                    return false;
                }
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * Prompt the user to confirm that they want to delete this event.
     */
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                deleteEvent();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    /**
     * Perform the deletion of the event in the database.
     */
    private void deleteEvent() {
        if (mBoardLog.getDatabaseKey() != null) {
            mBoardLogsDatabaseReference.child(mBoardLog.getDatabaseKey()).removeValue();
            if (mBoardLogsDatabaseReference.child(mBoardLog.getDatabaseKey()).getKey().equals("")) {
                Toast.makeText(this, getString(R.string.editor_delete_log_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_delete_log_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
        // Close the activity
        finish();
    }
}
