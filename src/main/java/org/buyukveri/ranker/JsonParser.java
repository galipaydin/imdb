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
                
                String path2 = path + name.toLowerCase().replaceAll(" ", "_") + ".jpg";
                
                f = new File(path2);
                if(f.exists()){
                    path2 = path + name.toLowerCase().replaceAll(" ", "_") + "_1.jpg";
                }

                f = new File(path2);
                if(f.exists()){
                    path2 = path + name.toLowerCase().replaceAll(" ", "_") + "_2.jpg";
                }

                f = new File(path2);
                if(f.exists()){
                    path2 = path + name.toLowerCase().replaceAll(" ", "_") + "_3.jpg";
                }
                
                
                downloadPics(path2, link);
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
            System.out.println("Downloading: " + url);
            WebPageDownloader.saveImage(link, path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
/*
            readJsonFromUrl("http://cache.api.ranker.com/lists/646277/items?limit=5000", "actress");
            readJsonFromUrl("http://cache.api.ranker.com/lists/931728/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/2438718/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/688693/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/337697/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/165994/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/815392/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/646025/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/2438704/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/1448674/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/645707/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/645587/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/857426/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/2046269/items?limit=5000", "female");

            readJsonFromUrl("http://cache.api.ranker.com/lists/335820/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/938576/items?limit=5000", "female");
            
            readJsonFromUrl("http://cache.api.ranker.com/lists/700792/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/508668/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/614483/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/2046834/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/117574/items?limit=5000", "female");

            readJsonFromUrl("http://cache.api.ranker.com/lists/696961/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/829324/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/645699/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/166144/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/645853/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/165904/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/2438706/items?limit=5000", "female");
            

            readJsonFromUrl("http://cache.api.ranker.com/lists/166071/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/166185/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/645851/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/1638036/items?limit=5000", "female");
            readJsonFromUrl("http://cache.api.ranker.com/lists/2438706/items?limit=5000", "female");
*/
//            readJsonFromUrl("http://cache.api.ranker.com/lists/1871397/items?limit=5000", "female");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/1852082/items?limit=5000", "female");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/1450029/items?limit=5000", "female");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/2046269/items?limit=5000", "female");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/1224686/items?limit=5000", "female");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/1769465/items?limit=5000", "female");
//
//
//            readJsonFromUrl("http://cache.api.ranker.com/lists/593269/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/1871392/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/337587/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/166154/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/166181/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/165875/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/337545/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/166065/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/645871/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/166031/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/984678/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/984650/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/645990/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/542455/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/337532/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/166121/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/645654/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/166179/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/984739/items?limit=5000", "male");
//
//            readJsonFromUrl("http://cache.api.ranker.com/lists/645766/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/645828/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/165944/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/165925/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/165894/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/984817/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/166183/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/645902/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/645541/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/337638/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/901321/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/2186056/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/166002/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/166221/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/166073/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/336413/items?limit=5000", "male");
//            
//            readJsonFromUrl("http://cache.api.ranker.com/lists/165906/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/645776/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/165986/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/337721/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/984705/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/337370/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/166101/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/984741/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/166187/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/166241/items?limit=5000", "male");
//            
//            readJsonFromUrl("http://cache.api.ranker.com/lists/524845/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/495218/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/336820/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/2256716/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/645667/items?limit=5000", "male");
//            
//            readJsonFromUrl("http://cache.api.ranker.com/lists/166175/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/645615/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/166161/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/922050/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/165865/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/931308/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/554764/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/1852083/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/2096799/items?limit=5000", "male");
//            
//            readJsonFromUrl("http://cache.api.ranker.com/lists/2096799/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/2096799/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/2096799/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/2096799/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/2096799/items?limit=5000", "male");
//            readJsonFromUrl("http://cache.api.ranker.com/lists/2096799/items?limit=5000", "male");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
