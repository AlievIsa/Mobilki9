package com.example.mobilki9.adapterClass;

public class Affirmation {
    private String text;
    private int imageNum;

    public Affirmation(String text, int imageNum) {
        this.text = text;
        this.imageNum = imageNum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }
}
