import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

public class FullScreen extends JFrame {
    
    // this line is needed to avoid serialization warnings
    private static final long serialVersionUID = 1L;
    
    
    Image screenImage; // downloaded image
    int w, h; // Display height and width
    
    
    // Program entry
    public static void main(String[] args) {
        //if (args.length < 1) // by default program will load AnyExample logo
        //    new FullScreen("flat.png");
        //else
        new FullScreen(args[0]); // or first command-line argument
        
        
    }
    
    // Class constructor
    FullScreen(String source){
        
        // Exiting program on window close
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        // Exitig program on mouse click
        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) { System.exit(0); }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        }
                         );
        
        // remove window frame
        this.setUndecorated(true);
        
        // window should be visible
        this.setVisible(true);
        
        // switching to fullscreen mode
        GraphicsEnvironment.getLocalGraphicsEnvironment().
        getDefaultScreenDevice().setFullScreenWindow(this);
        
        // getting display resolution: width and height
        w = this.getWidth();
        h = this.getHeight();
        System.out.println("Display resolution: " + String.valueOf(w) + "x" + String.valueOf(h));
        
        // loading image
        //if (source.startsWith("http://")) // http:// URL was specified
        //    screenImage = Toolkit.getDefaultToolkit().getImage(new URL(source));
        //else
        screenImage = Toolkit.getDefaultToolkit().getImage(source); // otherwise - file
    }

    public void setImage(String source){
        screenImage = Toolkit.getDefaultToolkit().getImage(source); // otherwise - file
        this.repaint();
    }
    
    public void paint (Graphics g) {
        if (screenImage != null){ // if screenImage is not null (image loaded and ready)
            int img_newh = h;
            int img_neww = (int)( (1.0*h/screenImage.getHeight(this)  )* screenImage.getWidth(this) ) ;
            g.drawImage(screenImage, // draw it
                        w/2 - img_neww / 2, // at the center
                        h/2 - img_newh / 2, // of screen
                        img_neww,
                        img_newh,
                        this);
        // to draw image at the center of screen
        // we calculate X position as a half of screen width minus half of image width
        // Y position as a half of screen height minus half of image height
        }
    }
    
    
}

