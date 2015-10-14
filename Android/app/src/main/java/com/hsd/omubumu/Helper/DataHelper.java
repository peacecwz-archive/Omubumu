package com.hsd.omubumu.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by APALYazilim on 9.7.2015.
 */
public class DataHelper {
    public static String ReadInputStream(InputStream st){
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(st));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            return line;
        }
        catch (IOException ex){
            return  ex.getMessage();
        }
    }
}
