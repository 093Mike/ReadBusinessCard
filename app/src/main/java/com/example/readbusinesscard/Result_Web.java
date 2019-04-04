package com.example.readbusinesscard;

public class Result_Web {
    public Boolean isWeb_1 (String s){return s.matches("[wW][.:]");}
    public Boolean isWeb_2(String s){return s.matches("[wW][ ]");}

    //Contains www. ?
    public Boolean isWWW(String s){ return s.matches("[wW][wW][wW][.]");}
    public Boolean isWW(String s){ return s.matches("[wW][wW][.]");}
    public Boolean isW(String s){ return s.matches("[wW][.]");}

    public Boolean isHsWWW(String s){ return s.matches("https://[wW][wW][wW][.]"); }
    public Boolean isHWWW(String s){ return s.matches("http://[wW][wW][wW][.]"); }

    //Contains .XX
    public Boolean is_Dot_XX(String s){
        return s.matches("[.][a-z][a-z]");
    }
    //Contains .XXX
    public Boolean is_Dot_XXX(String s){
        return s.matches("[.][a-z][a-z][a-z]");
    }

}
