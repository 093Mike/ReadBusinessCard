package com.example.readbusinesscard;

public class Result_phone {
    //Phone
    //- - - - - - - - - - - - - - - - - - -
    //IsNumber ?
    public Boolean isNumber(String s){
        return s.matches("[0-9]");
    }
    //isPhoneplus (" +34 549 234 234").
    public Boolean isPlusAnd2Numbers(String s){
        return s.matches("[+][0-9][0-9]");
    }
    public Boolean isPlusAnd3Numbers(String s){
        return s.matches("[+][0-9][0-9][0-9]");
    }

    //is (+34) 6923851742
    public Boolean isPlusAnd2Numbers_enc(String s){
        return s.matches("[(][+][0-9][0-9][)]");
    }
    //is (+314) 6923851742
    public Boolean isPlusAnd3Numbers_enc(String s){
        return s.matches("[(][+][0-9][0-9][0-9][)]");
    }

    //  Text
    // - - - - - - - - - - - - - - - - - - - - - -

    //is Phone (P: [phone]).
    public Boolean isP_Phone(String s){ return s.matches("[pP][:]"); }
    public Boolean isP_Phone_2A(String s){ return s.matches("[pP]hone[:]"); }
    public Boolean isP_Phone_2B(String s){ return s.matches("[pP]hone[.]"); }
    public Boolean isP_Phone_3(String s){ return s.matches("[pP][.]"); }
    public Boolean isP_Phone_4(String s){ return s.matches("[pP]"); }


    //is Telefono (T: [phone]).
    public Boolean isT_Phone(String s){
        return s.matches("[tT][:]");
    }
    public Boolean isT_Phone_2A(String s){
        return s.matches("[tT]elefono[:]");
    }
    public Boolean isT_Phone_2B(String s){
        return s.matches("[tT]elefono[.]");
    }
    public Boolean isT_Phone_3(String s){
        return s.matches("[tT][.]");
    }
    public Boolean isT_Phone_4(String s){
        return s.matches("[tT]");
    }


    //is Mobile
    public Boolean isM_Mobile(String s){return s.matches("[mM][:]");}
    //ENG
    public Boolean isM_Mobile_2A(String s){return s.matches("[mM]obile[:]");}
    public Boolean isM_Mobile_2B(String s){return s.matches("[mM]obile[.]");}
    //ESP
    public Boolean isM_Movil_2A(String s){return s.matches("[mM]ovil[:]");}
    public Boolean isM_Movil_2B(String s){return s.matches("[mM]ovil[.]");}
    public Boolean isM_Mobile_3(String s){return s.matches("[mM][.]");}
    public Boolean isM_Mobile_4(String s){return s.matches("[mM]");}


}