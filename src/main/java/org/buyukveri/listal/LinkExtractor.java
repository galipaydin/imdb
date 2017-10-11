/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.buyukveri.listal;

import java.io.File;
import org.buyukveri.common.WebPageDownloader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author galip
 */
public class LinkExtractor {

    public void links() {
        try {
            String filePath = "/Users/galip/dev/data/faces/listal/male/";
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            for (int i = 1; i < 275; i++) {
                String url = "http://www.listal.com/actors/actor/" + i;

                Document doc = WebPageDownloader.getPage(url);
                Elements es = doc.getElementsByAttributeValue("class", "gridviewimage");
                for (Element e : es) {
                    Element imgel = e.getElementsByTag("a").first();
                    String name = imgel.attr("title");
                    name = name.toLowerCase().replaceAll(" ", "_");
                    String link = imgel.getElementsByTag("img").first().attr("src");
                    System.out.println(name);
                    System.out.println(link);
                    WebPageDownloader.saveImage(link, filePath + "/" + name + ".jpg");
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        org.buyukveri.listal.LinkExtractor l = new org.buyukveri.listal.LinkExtractor();
        l.links();

    }
}
