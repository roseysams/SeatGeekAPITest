package com.example.rsams4190.seatgeekapitest;


import java.util.ArrayList;

public class CurrentEvents {

    private String title;
    //private ArrayList<String> performers = new ArrayList<>();
    private String address;
    private String extended_address;
    private String type;
    private String image;

    public CurrentEvents(String title, ArrayList<String> performers, String address, String extended_address, String type, String image){

        this.title = title;
        //this.performers = performers;
        this.address = address;
        this.extended_address = extended_address;
        this.type = type;
        this.image = image;

    }

    public String getTitle() {
        return title;
    }

    //public ArrayList<String> getPerformers() {return performers;}

    public String getImage() {return image;}

    public String getAddress() {
        return address;
    }

    public String getExtended_address() {
        return extended_address;
    }

    public String getType() {
        return type;
    }
}
