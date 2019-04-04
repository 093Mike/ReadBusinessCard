package com.example.readbusinesscard;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private Result_Email m;
    private Result_phone p;
    private Result_Web w;
    @Before
    public void setUp(){
        m = new Result_Email();
        p = new Result_phone();
        w =  new Result_Web();
    }
    @Test
    public void isEmail_Tests() {
        //Email
        assertEquals(true, m.isEmail_1("E:"));
        assertEquals(true, m.isEmail_2("Email:"));
        assertEquals(false, m.isEmail_1("M:"));
        assertEquals(false, m.isEmail_2("Mail:"));
    }

    @Test
    public void isNumber(){
        assertEquals(false, p.isNumber("E:"));
        assertEquals(true, p.isNumber("1"));
    }
    @Test
    public void isPhoneplusAndNumbers(){
        //2 numbers
        assertEquals(false, p.isPlusAnd2Numbers("E:"));
        assertEquals(true, p.isPlusAnd2Numbers("+34 "));
        assertEquals(true, p.isPlusAnd3Numbers("+342 "));
    }
    @Test
    public void isPhonPlusAndNumbers_enc(){
        //2 numbers
        assertEquals(false, p.isPlusAnd2Numbers_enc("E:"));
        assertEquals(false, p.isPlusAnd2Numbers_enc("+"));
        assertEquals(true, p.isPlusAnd2Numbers_enc("(+34)"));
        //3 numbers
        assertEquals(false, p.isPlusAnd3Numbers_enc("E:"));
        assertEquals(false, p.isPlusAnd3Numbers_enc("+"));
        assertEquals(false, p.isPlusAnd3Numbers_enc("(+34)"));
        assertEquals(true, p.isPlusAnd3Numbers_enc("(+350)"));
    }

    @Test
    public void isWebTest(){
        assertEquals(true, w.is_Dot_XX(".es"));
        assertEquals(true, w.is_Dot_XXX(".esx"));

    }
}