package com.msg91.sendotp.sample;

public class Cheque {

    private String image;
    private String status;
    private String user;
    private String prize;
    private String des;


    public Cheque(String image,String status,String user,String prize,String des) {

        this.image = image;
        this.status = status;
        this.user=user;
        this.prize=prize;
        this.des=des;
    }

    public Cheque() {
    }


    public String getImage() {
        return image;
    }
    public String getStatus() {
        return status;
    }
    public String getUser() {
        return user;
    }
    public String getPrize() { return  prize; }
    public String getDes()
    {
        return des;
    }}