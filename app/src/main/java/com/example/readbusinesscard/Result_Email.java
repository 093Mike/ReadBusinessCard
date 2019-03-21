package com.example.readbusinesscard;

public class Result_Email {

    //is Email (E: [email]).
    public Boolean isEmail_1(String s){
        return s.matches("[eE][:]");
    }
    public Boolean isEmail_2(String s){ return s.matches("[eE]mail[:]"); }
    public Boolean isEmail_3(String s){
        return s.matches("[eE][.]");
    }
    public Boolean isEmail_4(String s){ return s.matches("[eE]mail[.]"); }
    public Boolean isEmail_5(String s){
        return s.matches("[eE][ ]");
    }


    //is Mail (M: [email]).
    public Boolean isMail_1(String s){
        return s.matches("[mM][:]");
    }
    public Boolean isMail_2(String s){
        return s.matches("[mM]ail[:]");
    }
    public Boolean isMail_3(String s){
        return s.matches("[mM][.]");
    }
    public Boolean isMail_4(String s){return s.matches("[mM]ail[.]");}
    public Boolean isMail_5(String s){
        return s.matches("[mM][ ]");
    }

    //is Correo (M: [email]).
    public Boolean isCorreo_1(String s){
        return s.matches("[cC][:]");
    }
    public Boolean isCorreo_2(String s){
        return s.matches("[cC]orreo[:]");
    }
    public Boolean isCorreo_3(String s){
        return s.matches("[cC][.]");
    }
    public Boolean isCorreo_4(String s){return s.matches("[cC]orreo[.]");}
    public Boolean isCorreo_5(String s){
        return s.matches("[cC][ ]");
    }


}
