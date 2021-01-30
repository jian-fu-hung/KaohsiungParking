package com.jerryhong.kaohsiungparking.util;

public final class CommonUnit {

    private CommonUnit(){}

    public static boolean isString2Double(String str){
        try{
            Double.valueOf(str);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
