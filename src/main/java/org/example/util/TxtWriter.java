package org.example.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author shenyichen
 * @date 2022/3/18
 **/
public class TxtWriter {
    public static void writeTo(String path, String content) {
        try {
            File file = new File(path);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);//true表示追加
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        writeTo("src/main/resources/org/example/test.txt", "lala");
    }
}
