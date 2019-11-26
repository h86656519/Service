package com.example.service;

import android.util.Log;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void today() {
        Calendar current = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        begin.set(current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DATE), 00, 00, 00);
        end.set(current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DATE) + 1, 00, 00, 00);

//        end.add(Calendar.DATE, 1);
//        Log.i("ExampleUnitTest", "b.getTime().getDay(): " + b.getTime());
//        Log.i("ExampleUnitTest", "begin.getTime(): " + begin.getTime());
//        System.out.print("begin.getTime(): " + begin.getTime());
        System.out.println("current: " + current.getTime());
        System.out.println("begin: " + begin.getTime());
        System.out.println("end: " + end.getTime());
    }

    @Test
    public void date() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date current = new Date();
        Date begin = new Date();
        Date end = new Date();
        System.out.println("current: " + sdf.format(current));
        System.out.println("begin: " + sdf.format(begin));
        System.out.println("end: " + sdf.format(end));
    }
}