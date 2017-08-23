/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.buyukveri.themoviedb;

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

            for (int i = 1; i < 960; i++) {
                String url = "https://www.themoviedb.org/person?page=" + i;

                Document doc = WebPageDownloader.getPage(url);
                Elements es = doc.getElementsByAttributeValue("class", "image_content");
                for (Element e : es) {
                    String filePath = "/Users/galip/dev/data/faces/themoviedb/";

                    Element imgel = e.getElementsByTag("a").first();

                    String name = imgel.attr("title");
                    name = name.toLowerCase().replaceAll(" ", "_");

                    String link = imgel.attr("href");
                    if (link.contains("w235_and_h235")) {
                        link = link.replace("w235_and_h235", "w470_and_h470");
                    }

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

                            File file = new File(filePath);
                            if (!file.exists()) {
                                file.mkdirs();
                            }

                            WebPageDownloader.saveImage(imglink, filePath + name + ".jpg");
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LinkExtractor l = new LinkExtractor();
        l.links();

    }
}
