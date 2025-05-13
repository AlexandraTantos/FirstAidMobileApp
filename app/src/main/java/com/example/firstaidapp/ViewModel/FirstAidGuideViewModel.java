package com.example.firstaidapp.ViewModel;

public class FirstAidGuideViewModel {
    private String title;
    private String text1;
    private String text2;
    private String text3;
    private String imageUrl;
    private String category;
    private boolean isBabyFirstAid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isBabyFirstAid() {
        return isBabyFirstAid;
    }

    public void setBabyFirstAid(boolean babyFirstAid) {
        isBabyFirstAid = babyFirstAid;
    }
}
