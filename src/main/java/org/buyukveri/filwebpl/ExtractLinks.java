/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.buyukveri.filwebpl;

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
public class ExtractLinks {

    String filePath = "/Users/galip/dev/data/faces/filwebpl/female/";
    FileWriter testlinksfw, trainlinksfw, errorfw;

    public void links() {
        String imgLink = "";

        try {
            trainlinksfw = new FileWriter(filePath + "/trainlinks.txt");
            testlinksfw = new FileWriter(filePath + "/testlinks.txt");
            errorfw = new FileWriter(filePath + "/errors.txt");
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File(filePath + "/test/");
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File(filePath + "/train/");
            if (!file.exists()) {
                file.mkdirs();
            }

            for (int i = 1; i < 60582; i++) {
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

                    imgLink = img.attr("src");
                    if (!imgLink.endsWith("plug.svg")) {
                        trainlinksfw.write(name + ".jpg" + ";" + imgLink + "\n");
                        trainlinksfw.flush();
                        WebPageDownloader.saveImage(imgLink, filePath + "/train/" + name + ".jpg");
                        personImages("http://www.filmweb.pl" + link, name);
                    }
                }
            }
            trainlinksfw.close();
            testlinksfw.close();
            errorfw.close();
        } catch (Exception e) {
            try {
                errorfw.write(imgLink + "\n");
                errorfw.flush();
            } catch (Exception e1) {
            }
            
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void personImages(String link, String name) {
        String imglink = "";   
        try {
            Document doc = WebPageDownloader.getPage(link);
            Elements pp1 = doc.getElementsByAttributeValue("class", "person-photos");
            if (pp1.size() > 0) {
                Element pp = pp1.first();
                Elements photos = pp.getElementsByAttributeValue("class", "photo");
                int cnt = 1;
                for (Element photo : photos) {
                    Element a = photo.getElementsByTag("a").first().getElementsByTag("img").first();
                    imglink = a.attr("src");
//                System.out.println("\t" + imglink);
                    String fileName = name + "_" + cnt + ".jpg";

                    testlinksfw.write(fileName + ";" + imglink + "\n");
                    testlinksfw.flush();
                    WebPageDownloader.saveImage(imglink, filePath + "/test/" + fileName);
                    cnt++;
                }
            }
        } catch (Exception e) {
            try {
                errorfw.write(imglink + "\n");
                errorfw.flush();
            } catch (Exception e1) {
            }
            
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExtractLinks e = new ExtractLinks();

        e.links();

    }
}
