package com.example.demo;

public class ImageRequest {
    private String imageBase;
    private int factor;

    public ImageRequest(String imageBase, int factor) {
        this.imageBase = imageBase;
        this.factor = factor;
    }

    public String getImageBase() {
        return imageBase;
    }

    public void setImageBase(String imageBase) {
        this.imageBase = imageBase;
    }

    public int getFactor() {
        return factor;
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }

}
