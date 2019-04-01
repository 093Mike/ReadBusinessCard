package com.example.readbusinesscard;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class Result_ORC extends AppCompatActivity {
    ImageView photo;
    Result_Email m;
    Result_phone p;
    Result_Web w;
    ResultControl controlOrc;
    TextView t_results;
    EditText correo, web, phone;
    ImageView photo_alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_text);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        m = new Result_Email();
        p = new Result_phone();
        w = new Result_Web();
        controlOrc = new ResultControl();
        photo = findViewById(R.id.ima_result);
        t_results = findViewById(R.id.result);
        correo = findViewById(R.id.e_email);
        web = findViewById(R.id.e_web);
        phone = findViewById(R.id.e_phone);

        photo.setImageBitmap(MainActivity.getInstance().bitmap);

        final TextRecognizer textR = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textR.isOperational()) {
            Log.w("TextRecognizer", "DETECTOR NO DISPONIBLE");
        } else {
            Frame imageFrame = new Frame.Builder()
                    .setBitmap(MainActivity.getInstance().bitmap)
                    .build();
            final SparseArray<TextBlock> textBlocks = textR.detect(imageFrame);

            if (textBlocks.size() != 0) {
                t_results.post(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder stringB = new StringBuilder();
                        for (int i = 0; i < textBlocks.size(); i++) {
                            TextBlock item = textBlocks.valueAt(i);
                            stringB.append(item.getValue().toLowerCase());
                            stringB.append("\n");
                        }
                        t_results.setText(stringB.toString());
                        String[] lines = stringB.toString().split("\\n");
                        for (String s : lines) {
                            if(controlOrc.isError_1(s.substring(0,1))){ s=s.substring(1); }
                            if(s.contains("@")){
                                detectEmail(s);
                            }
                            else {
                                detectPhone(s);
                                detectWeb(s);
                            }
                        }
                    }
                });
            }
        }
    }


    public void detectEmail(String s) {
            //[character][. or :]
            if (m.isEmail_1(s.substring(0, 2))) { s = s.substring(2); }
            //[character]
            else if(m.isEmail_5(s.substring(0,2)) ) {s = s.substring(11);}

            //Email: || Mail:
            //Email. || Mail.
            s = detectEmail_intials(s);
            s = s.replaceAll(" ","");

            if (correo.getText().toString().length() == 0) {
                correo.setText(s);
            } else {
                correo.setText(correo.getText().toString() + "\n" + s);
            }
    }

    public String detectEmail_intials(String s){
        int tipo = -1;
        String[] what_is = {"email","mail","correo"};
        for (int i = 0 ; i < what_is.length ; i++){
            if(what_is[i].equals(s.substring(0,what_is[i].length()))){
                tipo=i;
            }
        }
        switch (tipo){
            //Email
            case 0:
                if(m.isEmail_2(s.substring(0, 6))){ return s.substring(6); }
                else{s.substring(5);}
            case 1:
                if(m.isMail_2(s.substring(0, 5))){ return s.substring(5);}
                else {return s.substring(4);}
            case 2:
                if(m.isCorreo_2(s.substring(0,7))){return s.substring(7);}
                else {return s.substring(6);}
            default:
                return s;
        }
    }
    public void detectPhone(String s) {
        if (s.contains("+")) {
            //[character][. or :]
            if(p.isControl_Phone(s.substring(0,2))) { s = s.substring(2); }

            s = detectPhone_Initial(s);

            //[character]
            if(p.isControl_Phone_2(s.substring(0,1))) { s = s.substring(1); }

            s = s.replaceAll(" ","");
            //First
            //if Contains value "(+[int value])" ?
            if (p.isPlusAnd2Numbers_enc(s.substring(0, 5)) || p.isPlusAnd3Numbers(s.substring(0, 6))) {
                s = s.replaceAll("[(]", "");
                s = s.replaceAll("[)]", "");
            }
            //Second
            //if Contains +??  ||  +???    ?
            if (p.isPlusAnd2Numbers(s.substring(0, 3))) {
                s = detectPhone_Format(s, 3);
            } else if (p.isPlusAnd3Numbers(s.substring(0, 4))) {
                s = detectPhone_Format(s, 4);
            }

//            for (int i = 0 ; i < s.length(); i++){
//                if(!p.isNumber(s.substring(i,i+1)) && !s.substring(i,i+1).equals("+")
//                        && !s.substring(i,i+1).equals(" ") ){
//                    detectPhone_Text(s.substring(0,i));
//                    s = s.substring(i);
//                    if(s.contains("+")){
//                        while(p.isControl_Phone(s.substring(0,1))|| s.substring(0,1).equals("+")){
//                            s = s.substring(1);
//                        }
//                    }
//                    detectPhone(s);
//                }
//            }
            if(!phone.getText().toString().contains(s)){
                detectPhone_Text(s);
            }

            //Contains value int ?
        } else {
            try {
                if (p.isControl_Phone(s.substring(0, 2))) {
                    s = s.substring(2);
                }
                if (p.isNumber(s.substring(0, 1)) && p.isNumber(s.substring(s.length()-1))) {
                    detectPhone_Text(s);
                }
            }catch (Exception e){

            }

        }

    }

    public String detectPhone_Format(String s, int pos) {
        s = s.substring(0, pos) + " " + s.substring(pos);
        return s;
    }

    public void detectPhone_Text(String s) {
        if (phone.getText().toString().length() == 0) {
            phone.setText(s);
        } else {
            phone.setText(phone.getText().toString() + "\n" + s);
        }
    }
    public String detectPhone_Initial(String s){
        int tipo = -1;
        String[] what_is = {"telefono","mobile","phone","movil"};
        for (int i = 0 ; i < what_is.length ; i++){
            if(what_is[i].equals(s.substring(0,what_is[i].length()))){
                if(i != 3){tipo=i;}
                else{tipo = 2;}
            }
        }
        switch (tipo){
            //Telefono
            case 0:
                if(p.isT_Phone_3(s.substring(0,9))){ return s.substring(9); }
                else{ return s.substring(8); }
            //Mobile
            case 1:
                if(p.isP_Phone_3(s.substring(0,6))){ return s.substring(6);}
                else{ return s.substring(5);}
            // Phone || Movil
            case 2 :
                if(p.isM_Mobile_3(s.substring(0,7)) || p.isM_Movil_3(s.substring(0,7))) {return s.substring(7);}
                else {return s.substring(6);}
             default:
                 return s;
        }
    }

    public void detectWeb(String s) {
        s = s.replaceAll(" ","");
        if(s.length() > 4) {
            if (w.is_Dot_XX(s.substring(s.length() - 3)) || w.is_Dot_XXX(s.substring(s.length() - 4))) {
                //formato.
                if (w.isWeb_1(s.substring(0, 2))) {
                    s = s.substring(2);
                } else if (w.isWeb_2(s.substring(0, 1))) {
                    s = s.substring(0, 1);
                }
                if(s.length()>12) {
                    if (w.isHsWWW(s.substring(0, 12))) {
                        s = s.substring(8);
                    } else if (w.isHWWW(s.substring(0, 11))) {
                        s = s.substring(7);
                    }
                }
                //Control ww. or w. for not read www.
                if(w.isW(s.substring(0,2))){ s = s.substring(2); }
                else if(w.isWW(s.substring(0,3))){ s = s.substring(3);}

                if (!w.isWWW(s.substring(0, 4))) {
                    s = "www." + s;
                }
                web.setText(s);
            }
        }
    }


}
