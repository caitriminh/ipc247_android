package com.example.ipc247.system;

import android.util.Log;

import com.example.ipc247.model.login.T_User;
import com.example.ipc247.model.menu.T_Menu;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class IPC247 {

    public static String BASE_URL_IPC = "http://194.233.92.141:5001/";
    //public static String BASE_URL_IPC = "http://167.86.99.178:5001/";
    public static T_User objUser;
    public static T_Menu objMenu;
    public static String tendangnhap = "ADMIN";
    public static String strMaNV = "";
    public static String strTenNV = "";
    public static String IdNhomQuyen = "0";
    public static String IDQRCode = "";
    public static String IDCompany = "0";
    public static String IDDonHang = "";
    public static String strCompany = "-";

    public static String getPublicIPAddress() {
        String value = null;
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<String> result = es.submit(new Callable<String>() {
            public String call() throws Exception {
                try {
                    URL url = new URL("http://whatismyip.akamai.com/");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader r = new BufferedReader(new InputStreamReader(in));
                        StringBuilder total = new StringBuilder();
                        String line;
                        while ((line = r.readLine()) != null) {
                            total.append(line).append("");
                        }
                        urlConnection.disconnect();
                        return total.toString();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (IOException e) {
                    Log.e("Public IP: ", e.getMessage());
                }
                return null;
            }
        });
        try {
            value = result.get();
        } catch (Exception e) {
            // failed
        }
        es.shutdown();
        return value;
    }

    public static final String SCANDIT_LICENSE_KEY = "AcBQKRZnNxIhNVFAGAS6C6oR2srWEvYSxkPZJ191PbL7bwZYlA3eH1Rh4rEGWS16vgzq/rdY3Ol3edgQr1rLFHNR/0UcVtQTdCFfKawwA403IVoGLT5N1r4BzgfdOk+tU0OlbVsOOZcaZBY5OF3OAFVyPMgLXaJJslgVGGAsnG+yZ478sESmV0VkwDmVWfEQfFy/c4ZRRxqqeXBrdFuA+HN0Y6nqebVZinK4c09JS2ySPXMaM1nqAQdF/lhxR16xx1kN3SEMSsNfVu0CGFWIG/hOJdPXULTjQH9Cdo17OnxgQWgxlWCWFF5fDVgoLuJZ3FM+NogvhZPkRqULkX1z8zhRiof2RUSuIUHL7p4G/KEET9fjQjVjO41ujyNjRz8xaGpGlnNtAxR/EDZ24GQAQNEgYQc6N6wZYUvGWqtolcjjNA+wFUVjS45phHMeWm4SS3Tx14lAalVECvX84lOVnGcOdYZYBZ8cWxOCTE9wAJsxELoTNjVxQR5kIxM9TIMAz3SHEG1RRyZrKo8fFUEIwb9ptrFtLSKqGk2uNmnd4aLgw/Bvg4MUsny41raIBM93AxyMZHrzdvSfEQ5WA1+YbUethL2JN9MoisX9pZrqSbhDJuBwMPy9rM91LCxJTgJbaEGNpX8IFU6yuqJUX5To7QY/ihDTxx6TBojdakOmGcee8Myyk6aSEe8snUriRLGk9LBLKn/mMCv0JDImCaogEYFrDVH7Rgdxe+8P2zZjTiiyhBRNTB8MU+9EiX/WQl62V/BmtBoi/sl2B3eHuLbyqzobtksVdvesayNyMaVOsMVxR7fHElh0Z0KSR3H3zR2jEFTqYjnktmEeEu/5/n+ZGSWRPy+cfTsl2d/OHe39viSKRgenxrV77Cdbo0g6uDL94VL/fC9K7AUZ82YIayEHDh3yx5NTu9xXNVls219zNm1aRpPESSUX84GxVBt9t4g+/s510jSwUe9L8OkHOmRczVYRjXbdUds6LQKFt9pwCNtU5DdBoJDBayOcWKQDjNcWi7QXEXUqN4pcO7UFFsbTM63S2Qm4HTiofxSylzVq2Lox9Ce7v+xVoXLEKyyAVrc6bKOVCzNhSy5zrsJFxSKvNftaMRGCfsXixi7U7METDdBJ6bvfoDCFoRNC2L9yBeSPwuzxweiCA8OtdterI5QUJHayb+Gb3Xa96v2PpgarX8osnzVoJY81BsqiCnmLphfTd2nP7BGE";

}
