/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Quyết Chiến
 */
public class XDate {
    static SimpleDateFormat format = new SimpleDateFormat();
    public static Date toDate(String date,String pattern){
        try {
            format.applyPattern(pattern);
            if (date == null) {
                return XDate.now();
            }
            return format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static String toString(Date date,String pattern){
        try {
            format.applyPattern(pattern);
            if (date == null) {
                return "";
            }
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    
    public static Date addDay(Date date,long days){
        date.setTime(date.getTime()+days*24*60*60*1000);
        return date;
    }
    public static Date now(){
        return new Date();
    }
}
