package org.buyukveri.kmdb;

import java.io.File;
import java.io.FileWriter;
import org.buyukveri.common.WebPageDownloader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkExtractor {

    public void links() {
        try {
            String filePath = "/Users/galip/dev/data/faces/kmdb/";
            FileWriter fw = new FileWriter(filePath + "/links.txt");
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            for (int i = 53; i < 202837; i++) {

                String digit = i + "";
                int length = digit.length();
                String no = "";
                for (int j = length; j < 8; j++) {
                    no += "0";
                }
                String url = "http://www.kmdb.or.kr/eng/vod/mm_basic.asp?person_id=" + no + digit + "&div=2";

                Document doc = WebPageDownloader.getPage(url);

                if (doc.getElementsByAttributeValue("class", "photo").size() > 0) {
                    Element e = doc.getElementsByAttributeValue("class", "photo").first();

                    Element imgel = e.getElementsByTag("img").first();
                    String imglink = imgel.attr("src");
                    String name = imgel.attr("alt");
                    name = name.toLowerCase().replaceAll(" ", "_");
                    System.out.println("name = " + name);
                    if (!imglink.endsWith("no_image2.gif")) {
                        System.out.println("imglink = " + imglink);
                        fw.write(i + ";" + name + ";" + imglink + "\n");
                        fw.flush();
                    }
                }
            }

            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LinkExtractor l = new LinkExtractor();
        l.links();

    }
}
