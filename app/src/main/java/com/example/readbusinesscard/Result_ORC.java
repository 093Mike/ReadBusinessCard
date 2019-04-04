package com.example.readbusinesscard;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    Boolean acabo_detect_phone;

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
        acabo_detect_phone = false;

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
                            if (controlOrc.isError_1(s.substring(0, 1))) {
                                s = s.substring(1);
                            }
                            acabo_detect_phone = false;
                            if (s.contains("@")) {
                                detectEmail(s);
                            } else {
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
        if (m.isEmail_1(s.substring(0, 2))) {
            s = s.substring(2);
        }
        //[character]
        else if (m.isEmail_5(s.substring(0, 2))) {
            s = s.substring(2);
        }

        //Email: || Mail:
        //Email. || Mail.
        if(s.length()>5) {
            s = detectEmail_intials(s);
            s = s.replaceAll(" ", "");

            if (correo.getText().toString().length() == 0) {
                correo.setText(s);
            } else {
                correo.setText(correo.getText().toString() + "\n" + s);
            }
        }
    }

    public String detectEmail_intials(String s) {
        int tipo = -1;
        String[] what_is = {"email", "mail", "correo"};
        for (int i = 0; i < what_is.length; i++) {
            if (what_is[i].equals(s.substring(0, what_is[i].length()))) {
                tipo = i;
            }
        }
        switch (tipo) {
            //Email
            case 0:
                if (m.isEmail_2(s.substring(0, 6))) {
                    return s.substring(6);
                } else {
                    s.substring(5);
                }
            case 1:
                if (m.isMail_2(s.substring(0, 5))) {
                    return s.substring(5);
                } else {
                    return s.substring(4);
                }
            case 2:
                if (m.isCorreo_2(s.substring(0, 7))) {
                    return s.substring(7);
                } else {
                    return s.substring(6);
                }
            default:
                return s;
        }
    }

    public void detectPhone(String s) {
        if (s.contains("+")) {
            //[character][. or :]
            if (p.isControl_Phone(s.substring(0, 2))) {
                s = s.substring(2);
            }
            s = detectPhone_Initial(s);

            //[character]
            if (p.isControl_Phone_2(s.substring(0, 1))) {
                s = s.substring(1);
            }

            s = s.trim();
            s = detectControl_Phone(s,1);
            boolean despuesSuma = false;
            for (int i = 0; i < s.length(); i++) {
                if (!acabo_detect_phone) {
                    if (!p.isNumber(s.substring(i, i + 1)) && !s.substring(i, i + 1).equals("+")
                            && !s.substring(i, i + 1).equals(" ") && !s.substring(i, i + 1).equals("(")
                            && !s.substring(i, i + 1).equals(")")) {
                        if (i == 0) {
                            if (!controlOrc.isError_1(s.substring(0, i + 1)) || !p.isNumber(s.substring(0, i + 1))) {
                                detectPhone_Text(s.substring(0, i + 1));
                            }
                            s = s.substring(i + 1);
                        } else {
                            if (!controlOrc.isError_1(s.substring(0, i)) || !p.isNumber(s.substring(0, i))) {
                                detectPhone_Text(s.substring(0, i));
                            }
                            s = s.substring(i);
                        }
                        if (s.contains("+")) {
                            detectPhone(s);
                        }

                    } else if (!p.isNumber(s.substring(i, i + 1)) && s.substring(i, i + 1).equals("+")) {
                        if (despuesSuma) {
                            despuesSuma = false;
                            detectPhone_Text(s.substring(0, i));
                            s = s.substring(i);
                            if (s.contains("+")) {
                                detectPhone(s);
                            }
                        } else {
                            despuesSuma = true;
                        }
                    }
                }
                if (i == s.length() - 1) {
                    try {
                        if (!acabo_detect_phone) {
                            s = s.trim();
                            detectPhone_Text(s);
                            String[] numeros = phone.getText().toString().split("\n");
                            phone.setText("");
                            for (int j = 0; j < numeros.length; j++) {
                                numeros[j] = detectControl_Phone(numeros[j], 2);
                                if (numeros[j].substring(0, 1).equals("+")) {
                                    if (j == 0) {
                                        phone.setText(phone.getText().toString() + numeros[j]);
                                    } else {
                                        phone.setText(phone.getText().toString() + "\n" + numeros[j]);
                                    }
                                }
                            }

                        }
                        acabo_detect_phone = true;
                    } catch (Exception e) {
                        Toast toast1 = Toast.makeText(getApplicationContext(),"No se ha podido procesar uno o mas telefonos", Toast.LENGTH_SHORT);
                        toast1.show();
                    }

                }
            }
            //Contains value int ?
        } else {
            try {
                if (p.isControl_Phone(s.substring(0, 2))) {
                    s = s.substring(2);
                }
                s = detectPhone_Initial(s);
                if (p.isControl_Phone_2(s.substring(0, 1))) {
                    s = s.substring(1);
                }
                s = s.trim();

                if (p.isNumber(s.substring(0, 1)) && p.isNumber(s.substring(s.length() - 1))) {
                    detectPhone_Text(s);
                }
            } catch (Exception e) {

            }
        }
    }

    public String detectControl_Phone(String s,int pass){
    switch (pass){
        //First
        //if Contains value "(+[int value])" ?
        case 1:
            if(s.length()>6) {
                if (p.isPlusAnd2Numbers_enc(s.substring(0, 5)) || p.isPlusAnd3Numbers(s.substring(0, 6))) {
                    s = s.replaceAll("[(]", "");
                    s = s.replaceAll("[)]", "");
                }
            }
            return s;
        case 2:
            if(s.contains("+")) {
                if (p.isPlusAnd3Numbers(s.substring(0, 5))) {
                    s = detectPhone_Format(s, 5);
                } else if (p.isPlusAnd2Numbers(s.substring(0, 4))) {
                    s = detectPhone_Format(s, 4);
                }
            }
            return s;
    }

        return "\n";
    }

    public String detectPhone_Format(String s, int pos) {
        String phone_country = s.substring(0, pos).replace(" " ,"");
        phone_country+=" ";
        String phone = s.substring(pos).replaceAll(" ", "");
        return phone_country+phone;
    }

    public void detectPhone_Text(String s) {
        if (phone.getText().toString().length() == 0) {
            phone.setText(s);
        } else {
            phone.setText(phone.getText().toString() + "\n" + s);
        }
    }

    public String detectPhone_Initial(String s) {
        int tipo = -1;
        String[] what_is = {"telefono", "mobile", "phone", "movil","tel","telf","ph"};
        for (int i = 0; i < what_is.length; i++) {
            if (what_is[i].equals(s.substring(0, what_is[i].length()))) {
                if (i != 3) {
                    tipo = i;
                } else {
                    tipo = 2;
                }
            }
        }
        switch (tipo) {
            //Telefono
            case 0:
                if (p.isT_Phone_3(s.substring(0, 9))) {
                    return s.substring(9);
                } else {
                    return s.substring(8);
                }
                //Mobile
            case 1:
                if (p.isM_Mobile_3(s.substring(0,7))) {
                    return s.substring(7);
                } else {
                    return s.substring(6);
                }
                // Phone || Movil
            case 2:
                if (p.isP_Phone_3(s.substring(0, 7)) || p.isM_Movil_3(s.substring(0, 7))) {
                    return s.substring(7);
                } else {
                    return s.substring(6);
                }
                // Tel
            case 4:
                if (p.isT_Tel_3(s.substring(0,4))) {
                    return s.substring(4);
                } else {
                    return s.substring(3);
                }
                // Telf
            case 5:
                if (p.isT_Telf_3(s.substring(0,5))) {
                    return s.substring(5);
                } else {
                    return s.substring(4);
                }
            case 6:
                if(p.isP_ph_3(s.substring(0,3))){
                    return s.substring(3);
                } else {
                    return s.substring(2);
                }
            default:
                return s;
        }
    }

    public void detectWeb(String s) {
        if (s.length() > 4) {
            if (w.is_Dot_XX(s.substring(s.length() - 3)) || w.is_Dot_XXX(s.substring(s.length() - 4))) {
                //formato.
                if (w.isWeb_1(s.substring(0, 2))) {
                    s = s.substring(2);
                } else if (w.isWeb_2(s.substring(0, 1))) {
                    s = s.substring(0, 1);
                }
                s=s.trim();
                if(s.contains(" ")) {
                    if (s.length() > 12) {
                        if (w.isHsWWW(s.substring(0, 12))) {
                            s = s.substring(8);
                        } else if (w.isHWWW(s.substring(0, 11))) {
                            s = s.substring(7);
                        }
                    }
                }
                //Control ww. or w. for not read www.
                if (w.isW(s.substring(0, 2))) {
                    s = s.substring(2);
                } else if (w.isWW(s.substring(0, 3))) {
                    s = s.substring(3);
                }

                if (!w.isWWW(s.substring(0, 4))) {
                    s = "www." + s;
                }
                web.setText(s);
            }
        }
    }



}
