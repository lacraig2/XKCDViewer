package com.lukecraig.XKCDViewer;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author Luke Craig
 * @Date: Aug 12, 2015
 */

@SuppressWarnings("restriction")
public class Comic {
  private ImageIcon image;
  private String text;
  private String title;
  private String comicRef;
  private int comicNum;

  /**
   * Three basic constructors for Comic all accomplish the same task: to get the title, text, and
   * image from the xkcd site.
   * 
   * @param s
   */
  public Comic(String s) {
    this.comicRef = s;
    processComic();
  }

  public Comic(int num) {
    this.comicRef = String.valueOf(num);
    processComic();
  }

  public Comic() {
    this.comicRef = "";
    processComic();
  }

  /**
   * Uses JSoup to find the required information
   */
  public void processComic() {
    try {
      Document doc = Jsoup.connect("http://xkcd.com/" + comicRef).get();
      Element e = doc.getElementById("comic");
      BufferedImage bi = ImageIO.read(new URL("http:" + e.select("img").attr("src")));
      image = new ImageIcon(bi);
      title = doc.getElementById("ctitle").text();
      text = doc.getElementById("comic").select("img").attr("title");
      String[] z = doc.text().split(" ");
      for (int i = 0; i < z.length; i++) {
        if (z[i].startsWith("http://xkcd.com/")) {
          comicNum = Integer.parseInt(z[i].split("/")[3]);
        }
      }
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null,
          "Your connection timed out. Please check your internet connection.");
    }
  }

  public ImageIcon getImage() {
    return image;
  }

  public String getText() {
    return text;
  }

  public String getTitle() {
    return title;
  }

  public String getComicRef() {
    return comicRef;
  }

  public int getComicNum() {
    return comicNum;
  }
}
