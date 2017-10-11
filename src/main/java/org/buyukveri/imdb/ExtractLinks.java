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
public class ExtractLinks {

    public void linker() {
        try {
            String path = "/Users/galip/dev/data/imdb/links";

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2017);
            for (int j = 0; j < 12; j++) {
                calendar.set(Calendar.MONTH, j);
                int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                for (int k = 1; k <= daysInMonth; k++) {
                    System.out.println("Month: " + j +" Day:" + k);
                    FileWriter fw0 = new FileWriter(path + "/" + (j + 1) + "_" + k + "_0.txt", true);
                    FileWriter fw1 = new FileWriter(path + "/" + (j + 1) + "_" + k + "_1.txt", true);
                    String day = k + "", month = j + 1 + "";
                    boolean check = true;
                    if (check) {
                        int count =0;
                        for (int i = 1; i < 10000; i += 50) {
                           
                            String link = "http://www.imdb.com/search/name?sort=alpha&birth_monthday=" + month + "-" + day
                                    + "&start=" + i;
//                            System.out.println(link);

                            Document doc = WebPageDownloader.getPage(link);
                            if (doc != null) {
                                Elements results = doc.getElementsByAttributeValue("class", "results");
                                Element result = results.first();
//                        
                                Elements names = result.getElementsByAttributeValue("class", "image");

                                if (names.size() > 0) {
                                    for (Element name : names) {
                                        count++;
//                        System.out.println(name);
                                        Element e = name.getElementsByTag("a").first();
                                        String url = e.attr("href");

                                        String person = e.attr("title");

                                        Element imge = e.getElementsByTag("img").first();
                                        String imglink = imge.attr("src");

                                        String line = person + ";" + url + ";";

                                        if (imglink.endsWith("name.gif")) {
                                            line = line + "0";
                                            fw0.write(line + "\n");
                                            fw0.flush();
                                        } else {
                                            line = line + "1";
                                            fw1.write(line + "\n");
                                            fw1.flush();
                                        }
//                                    System.out.println(line);

                                    }
                                } else {
                                    check = false;
                                    break;
                                }
                            }
                        }
                        System.out.println("\t" + count);
                        fw0.close();
                        fw1.close();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        ExtractLinks p = new ExtractLinks();
        p.linker();
    }
}
