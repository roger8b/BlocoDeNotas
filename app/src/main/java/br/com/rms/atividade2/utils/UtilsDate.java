package br.com.rms.atividade2.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UtilsDate {

    public static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YY HH:mm:ss", new Locale("pt", "BR"));
        Date date = new Date();
        return dateFormat.format(date);
    }

}
