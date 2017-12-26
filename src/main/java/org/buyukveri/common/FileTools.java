/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.buyukveri.common;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
 * @author galip
 */
public class FileTools {

    public void moveLines(String path, String outPath) {
        try {
            Scanner s = new Scanner(new File(path));
            int pcount = 0, ncount = 0;
            while (s.hasNextLine()) {
                try {
                    String line = s.nextLine();
                    FileWriter fw;
                    if (line.startsWith("positive")) {
                        fw = new FileWriter(outPath + "/p_" + pcount++ + ".txt");
                        fw.write(line.replace("positive", "").trim());
                        fw.flush();
                        fw.close();
                    } else if (line.startsWith("negative")) {
                        fw = new FileWriter(outPath + "/n_" + ncount++ + ".txt");
                        fw.write(line.replace("positive", "").trim());
                        fw.flush();
                        fw.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
        }
    }

    public void modelFile(String path, String outPath) {
        try {
            Scanner s = new Scanner(new File(path));

            FileWriter fwp = new FileWriter(outPath + "/pos_embeds.txt");
            FileWriter fwn = new FileWriter(outPath + "/neg_embeds.txt");
            while (s.hasNextLine()) {
                try {
                    String line = s.nextLine();
                    if (line.startsWith("p_")) {
                        fwp.write(line + "\n");
                        fwp.flush();
                    } else if (line.startsWith("n_")) {
                        fwn.write(line + "\n");
                        fwn.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            fwp.close();
            fwn.close();
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        FileTools f = new FileTools();
        f.moveLines(args[0], args[1]);
    }
}
