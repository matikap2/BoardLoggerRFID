package com.desginlab.agh.boardloggerrfid;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class BoardLog {
    private String studentId;
    private String studentName;
    private String boardId;
    private String databaseKey;
    private String boardImageId;
    private String timestamp;

    BoardLog(){
        studentName="DEFAULT";
    }
    public BoardLog(String mStudentId, String mBoardId, String mBoardImageId){
        studentId=mStudentId;
        boardId=mBoardId;
        studentName="DEFAULT";
        boardImageId=mBoardImageId;
    }

    public BoardLog(Double mStudentId, Double mBoardId,String mBoardImageId){
        studentId=Double.toString(mStudentId);
        boardId=Double.toString(mBoardId);
        studentName="DEFAULT";
        boardImageId=mBoardImageId;
    }

    public BoardLog(String mStudentId, String mBoardId, String mStudentName,String mBoardImageId){
        studentId=mStudentId;
        boardId=mBoardId;
        studentName=mStudentName;
        boardImageId=mBoardImageId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setDatabaseKey(String databaseKey) {
        this.databaseKey = databaseKey;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDatabaseKey() {
        return databaseKey;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("boardId", boardId);
        result.put("studentId", studentId);
        result.put("studentName", studentName);
        result.put("boardImageId", boardImageId);
        return result;
    }

    public void setBoardImageId(String boardImageId) {
        this.boardImageId = boardImageId;
    }

    public String getBoardImageId() {
        return boardImageId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
