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

                    /*
                    Document doc1 = WebPageDownloader.getPage("https://www.themoviedb.org" + link);
                    Element el1 = doc1.getElementsByAttributeValueContaining("class", "image_content").first();

                    Element el2 = el1.getElementsByTag("img").first();
                    if (el2 != null) {

                        String imglink = el2.attr("data-src");
                        if (imglink != null) {

                            Element e3 = doc1.getElementById("left_column");

                            String gender = "";
                            Element f = e3.getElementsMatchingText("Female").first();
                            if (f != null) {
                                System.out.println("FEMALE -" + name);
                                gender = "female";
                                filePath = filePath + "/" + gender + "/";
                            }

                            Element m = e3.getElementsMatchingText("Male").first();
                            if (m != null) {
                                System.out.println("MALE  -" + name);
                                gender = "male";
                                filePath = filePath + "/" + gender + "/";
                            }


                            
}
                    }*/
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
