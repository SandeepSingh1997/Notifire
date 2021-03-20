package com.example.notifire;

public class Board {
    private String boardID, title, description, contact, ownerID;

    public Board() {
    }

    public Board(String title, String description, String contact, String ownerID) {
        this.title = title;
        this.description = description;
        this.contact = contact;
        this.ownerID = ownerID;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public String getContact() {
        return contact;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getBoardID() {
        return boardID;
    }
}
