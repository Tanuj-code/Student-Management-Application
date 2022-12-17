package com.example.studentmanagement;

public class model {
private int imageview;
private String textView;

    public model(int imageview, String textView) {
        this.imageview = imageview;
        this.textView = textView;
    }

    public int getImageview() {
        return imageview;
    }

    public String getTextView() {
        return textView;
    }
}
