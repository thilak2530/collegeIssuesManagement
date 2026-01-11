package com.practise.security.responcedto;

import org.springframework.stereotype.Component;


public class staffRecordResponce {
    private int id;
    private String refId;
    private String title;
    private String status;
    private String roomno;

    public staffRecordResponce(int id, String refId, String title, String status, String roomno) {
        this.id=id;
        this.refId = refId;
        this.title = title;
        this.status = status;
        this.roomno = roomno;
    }

    public String getRefId() {
        return refId;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getRoomno() {
        return roomno;
    }
}
