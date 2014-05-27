package me.planetguy.robots.misc;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Utils {

    public static String readStringFile(FileInputStream fis) throws java.io.IOException {
        StringBuffer fileContent = new StringBuffer("");

        byte[] buffer = new byte[1024];

        while (fis.read(buffer) != -1) {
            fileContent.append(new String(buffer));
        }

        return fileContent.toString();
    }

    public static void writeStringFile(FileOutputStream fos, String text) throws java.io.IOException {
        fos.write(text.getBytes());
    }

}
