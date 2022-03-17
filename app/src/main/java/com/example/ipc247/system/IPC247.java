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

    public static final String SCANDIT_LICENSE_KEY = "AZjRig3MLCoXNte7nQomxpczBwmkJdThAHvW60FJFXjBbNTk0mPTzoV9I/CnFULbrm7BZdELzyrORuFNx2I04OpV8drJfhGCTQ5WB7sT5EcnJHqhIQtB3VksqRbEPJWHOQZo5HdB0jqvQ2NPdDSWiEUe3aL2Obwe0s3/NCDWLf9kXmEfIuKLHEZZXPyPv+Yww2NOHd4txQMiiuLhFrq7YngrXzcHuZPzRj9nDoofZT6bAexIssxMxYAZCQDrPJnptKyabqtQ0pj1typlE0Ztb2onqdD2E70IWd1ixUtUZXGDq6j9TwYm/gyTvWjoseaHBpVm32qWPlhsjTmxxNkxBN9Yfmw35YvddUYmZ4AhhGh7/XWUKrQ/KvS0fq4jEwSpMuo9izhgVnyzl+MczK/utNVsNdsjXw5RiO0aN18EJebaTNiiH6hA06Sq4GW5wsj7Q6Z1EW3uMnF+tFutEucu/oqsUgJyfnXmV/i28HzhXF9KXdM4NfpsvUich4hnjNBIrQn1JYgYP7hb6Xm6tBTqUBKYMFvcTfY5Cmaz8h1fE3M9gRgIclrfr0cKC4L5qet45kOgM/oS1/DkiV4H6ygxwfM7fCP7v8xhVaWb6EnikOrFtaMRf1L3ESQZV2GCHi1ZaadkvqkAhJ7G0hyA+UYqrNxHZmdhA9mbtmO7tsiQDlUn5dJAI1NSCdNwE5IP44G2GB19zmxHhKNf4bZ0nzR7yPdU1RHcN2BilV8cT2Qyxa6rdvVc3Br0m6tZG65tmhHEeBJlkDvsG8lx4iU+5x15wau8aVj0xNdtsY3Z4XCDXjkERg==";

}
