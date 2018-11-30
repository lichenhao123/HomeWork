package org.westos.demo;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

public class Utils {
    //获取网页内容，url网页地址，html字符串
    public static Optional<String> fetch(String url) {
        try {
            StringBuilder sb = new StringBuilder(1024 * 1024);
            URLConnection conn = new URL(url).openConnection();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                }
            }
            return Optional.ofNullable(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    //图片地址，下载图片，存储目录
    public static void download(String imageURL, String savePrefix) {
        try {
            URLConnection conn = new URL(imageURL).openConnection();
            String path = savePrefix + imageURL.substring(imageURL.lastIndexOf("/") + 1);
            try (
                    InputStream in = conn.getInputStream();
                    FileOutputStream out = new FileOutputStream(path)
            ) {
                byte[] buf = new byte[1024 * 1024];
                System.out.println("开始下载");
                while (true) {
                    int len = in.read(buf);
                    if (len == -1) {
                        break;
                    }
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("下载结束");
    }

}

