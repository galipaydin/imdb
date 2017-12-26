package org.buyukveri.listal;

import java.io.File;
import java.io.FileWriter;
import org.buyukveri.common.WebPageDownloader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkExtractor {

    public void links() {
        try {
            String filePath = "/Users/galip/dev/data/faces/listal/female/";
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileWriter tags = new FileWriter(filePath + "/tags.txt", true);

            String[][] links;

            for (int i = 1; i < 361; i++) {
                String url = "http://www.listal.com/actors/actress/" + i;

                Document doc = WebPageDownloader.getPage(url);
                Elements es = doc.getElementsByAttributeValue("class", "gridviewimage");
                for (Element e : es) {
                    //TAGS

                    Element imgel = e.getElementsByTag("a").first();
                    String name = imgel.attr("title");
                    String url1 = imgel.attr("href");

                    name = name.toLowerCase().replaceAll(" ", "_");
                    System.out.println(name);

                    Document actorpage = WebPageDownloader.getPage(url1);
                    Element pg = actorpage.getElementsByAttributeValue("class", "pure-g").first();
                    Element e1 = pg.getElementsByTag("a").first();
                    String link = e1.getElementsByTag("img").first().attr("src");
//                    System.out.println(link);
                    WebPageDownloader.saveImage(link, filePath + "/" + name + ".jpg");
                    String newurl = url1 + "/pictures//";

                    //TAGS
                    Element t = actorpage.getElementById("alltags");
                    String tag = t.text().replaceAll("\\d", "");
                    tag = tag.replace("(", "");
                    tag = tag.replace(")", "");
                    tag = tag.replace(" ", "");
                    
                    tags.write(name + ";" + tag.trim() + "\n");
                    tags.flush();
                    System.out.println(tag.trim());
                    pictures(newurl, filePath, name);
                }
            }
            tags.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void pictures(String url, String filepath, String name) {
        try {
            filepath += "/test/"+name +"/";
            File file = new File(filepath);
            if (!file.exists()) {
                file.mkdirs();
            }

            
            for (int i = 1; i < 6; i++) {
                url = url + i + "/top-voted";
                Document doc = WebPageDownloader.getPage(url);
                Element e = doc.getElementById("browseimagescontainer");
                Elements es = e.getElementsByAttributeValue("class", "imagewrap-inner");
                System.out.println("PAGE " + i);
                int count = 0;
                for (Element e1 : es) {
//                    System.out.println("e1.html() = " + e1.html());

                    Element imgel = e1.getElementsByTag("a").first();
                    String url1 = imgel.attr("href");
//                    System.out.println("url1 = " + url1);
                    Document doc1 = WebPageDownloader.getPage(url1);
                    Element aa = doc1.getElementsByTag("center").first();
//                    Element e2 = doc1.getElementsByAttributeValueContaining("class", "pure-u-lg-16-24").first();
//                    Element aa = e2.getElementsByTag("center").first();
//                    System.out.println(aa.html());
//                    Element aa = e2.getElementsByTag("a").first();
                    String link = aa.getElementsByTag("img").first().attr("src");
                    String filename = filepath + "/" + name + "_" + count++ + ".jpg";
//                    System.out.println("filename = " + filename);
                    WebPageDownloader.saveImage(link, filename);

//                  System.out.println("\t" + link);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        org.buyukveri.listal.LinkExtractor l = new org.buyukveri.listal.LinkExtractor();
        l.links();

    }
}
