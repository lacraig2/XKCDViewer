package com.lukecraig.XKCDViewer;

/**
 * @author Luke Craig
 * @Date: Aug 12, 2015
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;


public class Main extends JFrame implements ActionListener, KeyListener {
  private static final long serialVersionUID = -6769228874403804939L;
  private ComicPanel cp;
  private Random random;

  /**
   * Basic Constructor to create the program. Calls upon ComicPanel and Comic to create a JFrame and
   * display it.
   */
  public Main() {
    super("XKCD Viewer");
    Comic c = new Comic(); // create new Comic () -> newest comic
    cp = new ComicPanel(this, c); // create new comic panel to display comic
    setContentPane(new JScrollPane(cp)); // take panel with scroll pane
    this.setFocusable(true);// allow it to be focus so key listener can work
    addKeyListener(this); // add key listener (implemented in XKCDViewer
    pack(); // simple, but important to keep things working smoothly
    setDefaultCloseOperation(EXIT_ON_CLOSE); // exit on close
    setResizable(true);
    setVisible(true);
  }

  /**
   * Adjusts the JFrame window size. Simply ensures that the window is not larger than the screen
   * itself. If it is it makes it the size of the screen.
   * 
   * @param null
   * @return void
   */

  public void adjustScreenSize() {
    if (this.getSize().getHeight() > Toolkit.getDefaultToolkit().getScreenSize().getHeight()) {
      this.setSize(new Dimension(this.getWidth(),
          (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
    }
    if (this.getSize().getWidth() > Toolkit.getDefaultToolkit().getScreenSize().getWidth()) {
      this.setSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(),
          this.getHeight()));
    }
  }

  /**
   * Update function sets up the comic through comicpanel, repacks, and adjusts the JFrame window
   * size.
   * 
   * @param null
   * @return void
   */
  public void update() {
    cp.setup();
    pack();
    adjustScreenSize();
  }


  /**
   * Implementation of the ActionListener interface Checks which button was pushed and responds
   * appopriately Comic can never be 1525 because it is not an image.
   * 
   * @param ActionEvent e
   * @return void
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(cp.getFirst())) {
      cp.setComic(new Comic(1));
    } else if (e.getSource().equals(cp.getPrev())) {
      int x = cp.getComic().getComicNum() - 1;
      x = (x < 1) ? 1 : (x == 1525) ? 1524 : x;
      cp.setComic(new Comic(x));
    } else if (e.getSource().equals(cp.getRand())) {
      if (random == null)
        random = new Random();
      int randNum = random.nextInt((cp.getMaxComicNum() - 1) + 1) + 1;
      randNum = (randNum == 1525) ? 1526 : randNum;
      cp.setComic(new Comic(randNum));
    } else if (e.getSource().equals(cp.getNext())) {
      int x = cp.getComic().getComicNum() + 1;
      x = (x > cp.getMaxComicNum()) ? cp.getMaxComicNum() : (x == 1525) ? 1525 : x;
      cp.setComic(new Comic(x));
    } else if (e.getSource().equals(cp.getLast())) {
      cp.setComic(new Comic());
    }
    update();
  }

  /**
   * Main Function simply creates new instance of class
   * 
   * @param args
   */
  public static void main(String[] args) {
    Main m = new Main();
    getNumMissedComics(m);
  }

  /**
   * keyPressed is the only method of KeyListener used
   * 
   * @param KeyEvent e
   * @return void
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
      cp.getPrev().doClick();
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
      cp.getNext().doClick();
    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      if (JOptionPane.showConfirmDialog(null,
          "Are you sure you would like to exit?") == JOptionPane.YES_OPTION) {
        System.exit(0);
      }
    } else if (e.getKeyCode() == KeyEvent.VK_R) {
      cp.getRand().doClick();
    }
  }

  /**
   * This method uses a JOptionPane to report back the number of comics missed since the last time
   * the user opened the program. It uses a SQLite database for this purpose.
   * 
   * @param m
   */
  public static void getNumMissedComics(Main m) {
    // specific information for easy changing
    final String TABLE_NAME = "COMIC";
    final String FIELD_NAME = "COMICNUM";
    final String DB_NAME = "ComicRegisterV1";
    Connection c = null;
    Statement stmt = null;

    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME + ".db");
      stmt = c.createStatement();
      // create the table if you need to
      stmt.execute(
          "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + FIELD_NAME + " INT NOT NULL)");

      // get all entries from the table to find the maximum and count them
      String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + FIELD_NAME + " DESC";
      ResultSet rs = stmt.executeQuery(sql);
      int lastComic = 0, num = 0;
      while (rs.next()) {
        lastComic = Math.max(lastComic, rs.getInt(FIELD_NAME));
        num++; // count the number of entries
      }

      // if a last comic has never been recorded assume this is their first time opening the app
      lastComic = (lastComic == 0) ? m.cp.getMaxComicNum() : lastComic;

      if (num >= 10) // ensures table stays small
        stmt.executeUpdate("DELETE FROM " + TABLE_NAME);

      // adds current max comic to table
      stmt.execute("INSERT INTO " + TABLE_NAME + " (" + FIELD_NAME + ") VALUES ("
          + m.cp.getMaxComicNum() + ")");

      if (m.cp.getMaxComicNum() > lastComic) {
        // show the user the number of comics they have missed
        JOptionPane.showMessageDialog(null, "There are "
            + Math.abs(m.cp.getMaxComicNum() - lastComic) + " new comics since your last visit.");
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Not used
   */
  @Override
  public void keyTyped(KeyEvent e) {}

  /**
   * Not used
   */
  @Override
  public void keyReleased(KeyEvent e) {}
}
