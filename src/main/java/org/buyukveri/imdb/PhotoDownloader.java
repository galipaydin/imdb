/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.buyukveri.imdb;

import java.util.Calendar;

/**
 *
 * @author galip
 */
public class PhotoDownloader {
    
    public void linker(){
        try {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, 2017);
                for (int j = 1; j < 13; j++) {
                    calendar.set(Calendar.MONTH, j);
                    int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    for (int k = 1; k <= daysInMonth; k++) {
                        String day = k + "", month = j + "";

                        if (j <= 9) {
                            month = "0" + j;
                        }
                        if (k <= 9) {
                            day = "0" + k;
                        }

                        System.out.println(month + "-" + day);
                    }
                }
            
        } catch (Exception e) {
        }
    }
    
    public static void main(String[] args) {
        PhotoDownloader p = new PhotoDownloader();
        p.linker();
    }
}
