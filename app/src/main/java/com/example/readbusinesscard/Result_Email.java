package com.example.readbusinesscard;

public class Result_Email {

    //[character][. or :]
    public Boolean isEmail_1(String s){
        return s.matches("[cemcEM][.:]");
    }
    public Boolean isEmail_5(String s){
        return s.matches("[cemCEM][ ]");
    }


    public Boolean isEmail_2(String s){ return s.matches("[eE]mail[.:]"); }
    public Boolean isMail_2(String s){
        return s.matches("[mM]ail[.:]");
    }
    public Boolean isCorreo_2(String s){
        return s.matches("[cC]orreo[.:]");
    }


}
