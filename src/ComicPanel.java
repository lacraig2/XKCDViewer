
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author Luke Craig
 * @Date: Aug 12, 2015
 */
@SuppressWarnings("restriction")
public class ComicPanel extends JPanel {
  private static final long serialVersionUID = 3479749878694126090L;
  private JLabel headerLabel, image; // text above, image
  private JButton first, prev, rand, next, last; // navigation buttons
  private int maxComicNum; // current issue #
  private Comic c;
  private Main x; // reference back to viewer for button changes

  /**
   * Basic constructor for Comic Panel. Takes viewer to get reference, takes Comic to show comic.
   * 
   * @param viewer
   * @param comic
   */
  public ComicPanel(Main viewer, Comic comic) {
    this.x = viewer;
    this.c = comic;
    setup();
    findMaxComicNum();
  }

  /**
   * Basic setup for ComicPanel. Calls all required methods and sets up layout.
   */
  public void setup() {
    // this method requires all objects be re-written each refresh
    removeAll();
    // use borderlayout
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    // set title
    add(getTitle());
    // add image
    add(getImage());
    // Button panel setup
    add(getButtons());
  }


  /**
   * Returns panel with title Uses BorderLayout to center text
   * 
   * @return Panel p
   */
  public Panel getTitle() {
    Panel p = new Panel();
    p.setLayout(new BorderLayout());
    headerLabel = new JLabel(c.getTitle(), JLabel.CENTER);
    headerLabel.setToolTipText(c.getText());
    headerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
    p.add(headerLabel, BorderLayout.CENTER);
    return p;
  }

  /**
   * Returns panel with appopriate image Uses BorderLayout to center image
   * 
   * @return Panel p
   */
  public Panel getImage() {
    Panel p = new Panel();
    image = new JLabel("", JLabel.CENTER);
    image.setIcon(c.getImage());
    image.setToolTipText(c.getText());
    p.add(image, BorderLayout.CENTER);
    return p;
  }

  /**
   * Returns panel with buttons for navigation Uses XKCDViewer to respond to their actions
   * 
   * @return Panel q
   */
  public Panel getButtons() {
    Panel q = new Panel();
    first = new JButton("|<");
    prev = new JButton("<PREV");
    rand = new JButton("RANDOM");
    next = new JButton("NEXT>");
    last = new JButton(">|");
    first.addActionListener(x);
    prev.addActionListener(x);
    first.addActionListener(x);
    rand.addActionListener(x);
    next.addActionListener(x);
    last.addActionListener(x);
    q.setLayout(new FlowLayout());
    q.add(first);
    q.add(prev);
    q.add(rand);
    q.add(next);
    q.add(last);
    return q;
  }

  /**
   * Sets global variable maxComicNum so that random knows it's largest number to select from
   * 
   * @return void
   */
  public void findMaxComicNum() {
    try {
      Document doc = Jsoup.connect("http://xkcd.com/").get();
      String[] z = doc.text().split(" ");
      for (int i = 0; i < z.length; i++) {
        if (z[i].startsWith("http://xkcd.com/")) {
          maxComicNum = Integer.parseInt(z[i].split("/")[3]);
        }
      }
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null,
          "Your connection timed out. Please check your internet connection.");
    }
  }

  public void setComic(Comic c) {
    this.c = c;
  }

  public Comic getComic() {
    return c;
  }

  public JButton getFirst() {
    return first;
  }

  public void setFirst(JButton first) {
    this.first = first;
  }

  public JButton getPrev() {
    return prev;
  }

  public void setPrev(JButton prev) {
    this.prev = prev;
  }

  public JButton getRand() {
    return rand;
  }

  public void setRand(JButton rand) {
    this.rand = rand;
  }

  public JButton getNext() {
    return next;
  }

  public void setNext(JButton next) {
    this.next = next;
  }

  public JButton getLast() {
    return last;
  }

  public void setLast(JButton last) {
    this.last = last;
  }

  public int getMaxComicNum() {
    return maxComicNum;
  }
}


