/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.buyukveri.thefamouspeople;

import java.io.File;
import java.io.FileWriter;
import org.buyukveri.common.WebPageDownloader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author galip
 */
public class PhotoDownloader {

    public String picFolder = "/Users/galip/dev/data/faces/thefamouspeople";

    public void download() {
        try {
//            String type = "actor";
//            String link = "http://www.thefamouspeople.com/actors.php";

            String type = "actress";
            String link = "http://www.thefamouspeople.com/actress.php";

            for (int i = 1; i < 8; i++) {
                System.out.println("PAGE=" + i);
                Document doc = WebPageDownloader.getPage(link + "?page=" + i);
                Elements tiles = doc.getElementsByAttributeValue("class", "tile");
                for (Element tile : tiles) {
                    System.out.println(tile.text());
                    Element t = tile.getElementsByAttributeValue("class", "tileLink").first();
                    String personurl = t.attr("href");
                    System.out.println(personurl);
                    Document doc1 = WebPageDownloader.getPage("http:" + personurl);
                    Element el = doc1.getElementsByAttributeValue("class", "profile-pic-top").first();
                    if (el != null) {
                        String name = el.attr("title");
                        String picurl = el.attr("src");
//                    System.out.println(name + " " + picurl);
                        String path = picFolder + "/" + type;

                        File f = new File(path);
                        if (!f.exists()) {
                            f.mkdirs();
                        }

                        path = path + "/" + name.toLowerCase().replaceAll(" ", "_") + ".jpg";
                        WebPageDownloader.saveImage("http:" + picurl, path);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        PhotoDownloader p = new PhotoDownloader();
        p.download();
    }
}
