/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.buyukveri.thefamouspeople;

import org.buyukveri.common.WebPageDownloader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author galip
 */
public class PhotoDownloader {
    public void download() {
        try {
            String link = "http://www.thefamouspeople.com/actors.php";
            
            Document doc = WebPageDownloader.getPage(link);
            Elements tiles = doc.getElementsByAttributeValue("class", "tile");
            for (Element tile : tiles) {
                System.out.println(tile.text());
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        PhotoDownloader p = new PhotoDownloader();
        p.download();
    }
}
