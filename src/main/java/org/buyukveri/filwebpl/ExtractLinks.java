/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.buyukveri.filwebpl;

import java.io.File;
import org.buyukveri.common.WebPageDownloader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author galip
 */
public class ExtractLinks {

    String filePath = "/Users/galip/dev/data/faces/filwebpl/female/";

    public void links() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File("/Users/galip/dev/data/faces/filwebpl/female/test/");
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File("/Users/galip/dev/data/faces/filwebpl/female/train/");
            if (!file.exists()) {
                file.mkdirs();
            }

            for (int i = 1; i < 60277; i++) {
                String url = "http://www.filmweb.pl/search/person?&q=&sex=1&sort=COUNT&sortAscending=false&page=" + i;

                Document doc = WebPageDownloader.getPage(url);
                Elements ps = doc.getElementsByAttributeValueStarting("id", "person");
                for (Element p : ps) {
                    System.out.println(p.attr("id"));
                    Element hd = p.getElementsByAttributeValue("class", "hitDesc").first();
                    Element hi = p.getElementsByAttributeValue("class", "hitImage").first();
                    Element a = hd.getElementsByTag("a").first();
//                    System.out.println(a.attr("href"));
                    String link = a.attr("href");
                    String name = a.text().toLowerCase().replaceAll(" ", "_");
//                    System.out.println(name);
                    Element img = hi.getElementsByTag("img").first();

                    String imgLink = img.attr("src");
                    WebPageDownloader.saveImage(imgLink, filePath + "/train/" + name + ".jpg");

                    personImages("http://www.filmweb.pl" + link, name);
                }

//                for (Element e : es) {
//                    Element e1 = e.getElementsByAttributeValue("class", "hitDescWrapper").first();
//
//                    Element imgel = e1. getElementsByTag("a").first();
//                    String link = imgel.attr("href");
//                    System.out.println(link);
//                    Element hitImg = doc.getElementsByAttributeValue("class", "hitImage").first();
//                    Element img = hitImg.getElementsByTag("img").first();
//                    String imgLink = img.attr("src");
//                    
//                    System.out.println(imgLink);
//            
//                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void personImages(String link, String name) {
        try {
            Document doc = WebPageDownloader.getPage(link);
            Element pp = doc.getElementsByAttributeValue("class", "person-photos").first();
            Elements photos = pp.getElementsByAttributeValue("class", "photo");
            int cnt = 1;
            for (Element photo : photos) {
                Element a = photo.getElementsByTag("a").first().getElementsByTag("img").first();
                String imglink = a.attr("src");
//                System.out.println("\t" + imglink);
                String fileName = name + "_" + cnt + ".jpg";

                WebPageDownloader.saveImage(imglink, filePath + "/test/" +  fileName);
                cnt++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExtractLinks e = new ExtractLinks();

        e.links();

    }
}
