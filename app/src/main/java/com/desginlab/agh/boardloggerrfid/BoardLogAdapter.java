package com.desginlab.agh.boardloggerrfid;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class BoardLogAdapter extends ArrayAdapter<BoardLog> {


    public BoardLogAdapter(Context context, List<BoardLog> boardLogs) {
        super(context, 0, boardLogs);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.board_log_list_item, parent, false);
        }

        BoardLog currentBoardLog = getItem(position);

        TextView boardIdTextView = (TextView) listItemView.findViewById(R.id.board_id_text_view);
        TextView studentIdTextView = (TextView) listItemView.findViewById(R.id.student_id_text_view);
        TextView studentNameTextView = (TextView) listItemView.findViewById(R.id.student_name_text_view);
        ImageView boardImageView = listItemView.findViewById(R.id.board_image_view);
        TextView dateTextView = listItemView.findViewById(R.id.date_text_view);

        String dateString = currentBoardLog.getTimestamp();
        dateTextView.setText(dateString);
        boardIdTextView.setText(currentBoardLog.getBoardId());
        studentIdTextView.setText(currentBoardLog.getStudentId());
        studentNameTextView.setText(currentBoardLog.getStudentName());
        if(currentBoardLog.getStudentName().length()>14){
            studentNameTextView.setTextSize(12);
        }

        if(currentBoardLog.getBoardImageId()!=null){
            if(currentBoardLog.getBoardImageId().equals(Integer.toString(R.drawable.frdm_k22f))){
                boardImageView.setImageResource(R.drawable.frdm_k22f);
            } else if (currentBoardLog.getBoardImageId().equals(Integer.toString(R.drawable.frmd_kl46z))){
                boardImageView.setImageResource(R.drawable.frmd_kl46z);
            } else if (currentBoardLog.getBoardImageId().equals(Integer.toString(R.drawable.rdl_uno_atmega328))){
                boardImageView.setImageResource(R.drawable.rdl_uno_atmega328);
            } else {
                boardImageView.setImageResource(R.drawable.frmd_kl46z);
            }
        } else {
            boardImageView.setImageResource(R.drawable.frmd_kl46z);
        }

        return listItemView;
    }
}