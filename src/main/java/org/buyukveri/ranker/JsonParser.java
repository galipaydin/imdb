/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.buyukveri.ranker;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import org.buyukveri.common.WebPageDownloader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author galip
 */
public class JsonParser {

    static String folderPath = "/Users/galip/dev/data/faces/ranker";

    public void jsonParser(String listID) {
        try {
            String url = "http://cache.api.ranker.com/lists/" + listID + "/items?limit=50000";
            Document doc = WebPageDownloader.getPage(url);
            System.out.println(doc);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url, String genderType) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            System.out.println(json.length());

            JSONArray ja = new JSONArray(json.get("listItems").toString());
            System.out.println(ja.length());

            String path = folderPath + "/" + genderType + "/";

            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }

            for (int i = 0; i < ja.length(); i++) {
                path = folderPath + "/" + genderType + "/";
                JSONObject list = (JSONObject) ja.get(i);
                JSONObject node = (JSONObject) list.get("node");

                String name = (String) node.get("name");
                String link = (String) node.get("url");
                path = path + name.toLowerCase().replaceAll(" ", "_") + ".jpg";
                downloadPics(path, link);
            }
            return json;
        } finally {
            is.close();
        }
    }

    public static void downloadPics(String path, String url) {
        try {
            Document doc = WebPageDownloader.getPage(url);
            Element e = doc.getElementById("node__description");
            Element img = e.getElementsByTag("img").first();
            String link = img.attr("src");
//            System.out.println("Downloading: " + url);
            WebPageDownloader.saveImage(link, path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            JSONObject json = readJsonFromUrl("http://cache.api.ranker.com/lists/646277/items?limit=5000",
                    "actress");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
