package com.kiwabolab.scotiabankloyalty.model;

public class Servidor {
    //----------------------------------------------------------------------------------------------
    //Variables
    public static String API = "http://kiwanolab.com/scotiabanck/index.php/";
    public static String API_IMG = "http://kiwanolab.com/scotiabanck/img/";
    //----------------------------------------------------------------------------------------------
    //
    public static String getImage_url(String image){
        return API_IMG+image;
    }
}
