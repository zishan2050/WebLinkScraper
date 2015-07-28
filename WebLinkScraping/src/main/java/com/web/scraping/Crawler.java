/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.scraping;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author ZISHAN MOHSIN
 * @created on 29 Jul 2015
 * @last modified 29 Jul 2015
 */
public class Crawler {

    /**
     * @summary It prints all the links present in the web page url
     *          provided at runtime in args.
     * @param args
     */
    public static void main(String[] args) {
        try {
            //Getting web page url
            String webUrl = args[0];
            try {
                System.out.println("Connecting...");
                System.out.println("-----------------");
                
                Document document = Jsoup.connect(webUrl).get();
                Elements elements = document.select("a");
                short count = 0;
                String baseUri = null;
                String concatenator = null;
                
                //Getting each element
                for (Element element : elements) {
                    
                    //It will run once
                    if (count == 0) {
                        baseUri = element.baseUri();
                        if (baseUri.endsWith("/")) {
                            concatenator = null;
                        } else {
                            concatenator = "/";
                        }
                        count++;
                    }
                    
                    if (element.hasAttr("href")) {
                        String href = element.attr("href");
                        
                        if (concatenator != null) {
                            if (href.startsWith("/")) {
                                concatenator = "";
                            }
                        }
                        else
                        {
                            if (href.startsWith("/")) {
                                concatenator = "";
                                href=href.substring(1, href.length());
                            }
                        }
                        
                        if (href.startsWith(baseUri) || href.startsWith("http")
                                || href.startsWith("https") 
                                || href.startsWith("www")
                                || href.startsWith("/www")) {
                            baseUri = "";
                            concatenator = "";
                        }
                        
                        //Printing url
                        System.out.println(baseUri + concatenator + href);
                    }
                }
            } catch (IOException ex) {
                System.out.println("Unable to connect: webUrl");
            }
            
        } catch (IndexOutOfBoundsException ioex) {
            System.out.println("Please pass any valid web page url to scrap all link");
        }
    }
}
