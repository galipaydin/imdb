/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.buyukveri.ranker;

import org.buyukveri.common.WebPageDownloader;
import org.jsoup.nodes.Document;

/**
 *
 * @author galip
 */
public class JsonParser {
    
    public void jsonParser(String listID) {
        try {
            String url = "http://cache.api.ranker.com/lists/"+listID+"/items?limit=50000";
                Document doc = WebPageDownloader.getPage(url);
                System.out.println(doc);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        JsonParser j = new JsonParser();
        j.jsonParser("646277");
    }
}
