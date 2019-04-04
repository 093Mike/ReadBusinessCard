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
        return s.matches("[+][0-9][0-9][ ]");
    }
    public Boolean isPlusAnd3Numbers(String s){
        return s.matches("[+][0-9][0-9][0-9][ ]");
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

    //[character][. or :]
    public Boolean isControl_Phone(String s){ return s.matches("[fptmFPTM][:.]"); }
    public Boolean isControl_Phone_2(String s){ return s.matches("[fptmFPTM]"); }

    //[Character] ... [. or :]
    public Boolean isP_Phone_3(String s){ return s.matches("[pP]hone[:.]"); }
    public Boolean isT_Phone_3(String s){
        return s.matches("[tT]elefono[:.]");
    }
    public Boolean isM_Mobile_3(String s){return s.matches("[mM]obile[:.]");}
    public Boolean isM_Movil_3(String s){return s.matches("[mM]ovil[:.]");}
    public Boolean isT_Tel_3(String s){return s.matches("[tT]el[:.]");}
    public Boolean isT_Telf_3(String s){return s.matches("[tT]elf[:.]");}
    public Boolean isP_ph_3(String s){return s.matches("[phPH][:.]");}



}
