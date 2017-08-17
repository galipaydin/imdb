/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.buyukveri.imdb;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author galip
 */
public class PhotoDownloader {

    public void processLinkFolder(String linkFilesFolder, String outputFolder) {
        try {
            File f = new File(linkFilesFolder);
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                for (File file : files) {
                    readLinkFile(file, outputFolder);
                }
            }
        } catch (Exception e) {
        }
    }

    public void readLinkFile(File inputFile, String outputPath) {
        try {
            String filename = inputFile.getName();

            File f = new File(outputPath);
            if (!f.exists()) {
                f.mkdirs();
            }

            ExecutorService executor = Executors.newFixedThreadPool(5);

//            String path = outputPath + "/" + filename;
            Scanner s = new Scanner(inputFile);
            int count = 0;

            while (s.hasNext()) {
                String line = s.nextLine();
                String[] tokens = line.split(";");
                if (tokens.length > 2) {
                    String name = tokens[0];
                    String url = "http://www.imdb.com" + tokens[1];
                    String hasPic = tokens[2];

                    if (hasPic.equals("1")) {
                        Runnable worker = new DownloaderThread(url, outputPath, filename);
                        executor.execute(worker);
                        count++;
                        if (count == 10) {
                            System.out.println("Sleep");
                            Thread.sleep(10000);
                            count = 0;
                        }
                    }
                }
            }

            executor.shutdown();

            while (!executor.isTerminated()) {
            }
            System.out.println("Finished all threads");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        PhotoDownloader p = new PhotoDownloader();
        p.processLinkFolder("/Users/galip/dev/data/imdb/links", "/Users/galip/dev/data/imdb/pics");
    }
}
