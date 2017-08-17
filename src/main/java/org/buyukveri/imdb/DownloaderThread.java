/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.buyukveri.imdb;

import java.io.File;
import java.io.FileWriter;
import org.buyukveri.common.WebPageDownloader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author galip
 */
public class DownloaderThread implements Runnable {

    private String url, outputPath, filename;
    FileWriter err;

    public DownloaderThread(String url, String outputPath, String filename) {
        this.url = url;
        this.outputPath = outputPath;
        this.filename = filename;
        try {
            err = new FileWriter(new File(outputPath + "/err.txt"), true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void downloadPics(String url, String outputPath, String filename) {
        try {
            System.out.println("Downloading " + url);
            Document doc = WebPageDownloader.getPage(url);
            Element div = doc.getElementById("name-overview-widget");
            
            Element img = div.getElementById("name-poster");

            String src = img.attr("src");
//            System.out.println("src: " + src);
            
            Element header = div.getElementsByAttributeValue("class", "header").first();
            
            String name = header.text();
            if(name!=null && name.trim().length()>2){
                name = name.replaceAll(" ", "_");
            }
//            System.out.println(name);    
            
            Element type = div.getElementsByAttributeValue("itemprop", "jobtitle").first();
            String fm = type.text();
            fm = fm.replaceAll(" ", "_");
            String path = outputPath + "/" + fm.toLowerCase();
            
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }


//            System.out.println(fm);
            WebPageDownloader.saveImage(src, path + "/" + name + ".jpg");
            
//            FileWriter fw = new FileWriter(outputPath + "/" + cat + ".txt", true);
//            fw.write(date + ";&" + haber + "\n");
//            fw.flush();
//            fw.close();
        } catch (Exception e) {
            try {
                System.out.println(url);
                err.write(url + "\n");
                err.flush();
                
            } catch (Exception ex) {
                System.out.println("downloadPics " + ex.getMessage() );
            }
//            e.printStackTrace();
        }
    }

    public void run() {
        try {
//            System.out.println(Thread.currentThread().getName());
            downloadPics(url, outputPath, filename);
            err.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
