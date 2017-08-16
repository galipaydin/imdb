/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.buyukveri.imdb;

import java.io.FileWriter;
import java.util.Calendar;
import org.buyukveri.common.WebPageDownloader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author galip
 */
public class PhotoDownloader {

    public void linker() {
        try {
            String path = "/Users/galip/dev/data/imdb/links";
                        
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2017);
            for (int j = 0; j < 12; j++) {
                calendar.set(Calendar.MONTH, j);
                int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                for (int k = 1; k <= daysInMonth; k++) {
                    FileWriter fw = new FileWriter(path + "/" + (j+1) + "_" + k + ".txt", true);
                    String day = k + "", month = j + 1 + "";
                    boolean check = true;
                    if (check) {
                        for (int i = 1; i < 10000; i += 50) {
                            String link = "http://www.imdb.com/search/name?sort=alpha&birth_monthday=" + month + "-" + day
                                    + "&start=" + i;
                            System.out.println(link);

                            Document doc = WebPageDownloader.getPage(link);

                            Elements results = doc.getElementsByAttributeValue("class", "results");
                            Element result = results.first();
//                        
                            Elements names = result.getElementsByAttributeValue("class", "image");
                            System.out.println(names.size());
                            if (names.size() > 0) {
                                for (Element name : names) {
//                        System.out.println(name);
                                    Element e = name.getElementsByTag("a").first();
                                    String url = e.attr("href");
                                     
                                    String person = e.attr("title");
                                    
                                    Element imge = e.getElementsByTag("img").first();
                                    String imglink = imge.attr("src");
                                    
                                     String line = person + ";" + url  + ";" ;
                                    
                                    if(imglink.endsWith("name.gif"))
                                       line = line + "0";
                                    else 
                                        line = line + "1";
                                    System.out.println(line);
                                    fw.write(line+"\n");
                                    fw.flush();
                                    
                                }
                            } else {
                                check = false;
                                break;
                            }
                        }
                    }
                    fw.close();
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        PhotoDownloader p = new PhotoDownloader();
        p.linker();
    }
}
