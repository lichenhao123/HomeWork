package org.westos.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Crawler {
    //获取网页和地址
    public static final String TIEBA_URL = "https://tieba.baidu.com/p/2256306796?red_tag=1781367364";
    //图片
    public static final Pattern pattern = Pattern.compile("<img class=\"BDE_Image\" src=\"(.*?)\"");
    //存储路径
    public static final String SAVE_PREFIX = "E:\\爬虫图片\\";

    public static void main(String[] args) {
           //创建线程，下载图片
           ExecutorService es = Executors.newFixedThreadPool(10);
           //抓取网页
           Utils.fetch(TIEBA_URL).ifPresent(html -> {
               Matcher matcher = pattern.matcher(html);
               //匹配图片
               while (matcher.find()) {
                   String imageURL = matcher.group(1);
                   //提交线程
                   es.submit(() -> Utils.download(imageURL, SAVE_PREFIX));
         }
     });
     //关闭
     es.shutdown();
 }
}
