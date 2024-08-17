import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
//import java.awt.geom.AffineTransform;
//import java.awt.image.*;
//import javax.imageio.*;
import javax.swing.*;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
//import java.io.File;
//import javax.imageio.ImageIO;

class ImageDrawingComponent extends Component {
    private BufferedImage bi;
    int w, h, px;
    
    public BufferedImage getImg() {
		return bi;
	}		
	 
	public int getH() {
		return h;
	}
	
	public int getW() {
		return w;
	}
	 
    public ImageDrawingComponent() {
		px = 127;
		
        // Load the image (you can replace "image.jpg" with your image file)
        try {
            //BufferedImage img = ImageIO.read(imageSrc);
            int w1 = 255; // img.getWidth(null);
            int h1 = 255; // img.getHeight(null);
            // bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            bi = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_RGB);

			for(int r=0; r<h1; r++)
			for(int c=0; c<w1; c++)
			{
			  int red= r & 0xFF;
			  int green= c & 0xFF;
			  int blue= (r + c) & 0xFF;
			  int rgb = (red << 16) | (green << 8) | blue;
			  bi.setRGB(c, r, rgb);
			}
            // image = ImageIO.read(new File("image.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }

		try {
            w = bi.getWidth(null);
            h = bi.getHeight(null);
        } catch (Exception e) {
            System.out.println("Image could not be read");
            System.exit(1);
        }
    }
 
    public Dimension getPreferredSize() { return new Dimension(w, h); }
 
    public void paint(Graphics g) {
 
        Graphics2D g2 = (Graphics2D) g;
 
		bi.setRGB((px++) % 255, 127 + (px >> 8), 0xff80);
        g2.drawImage(bi, 0, 0, null);

		g2.setColor(Color.blue);
		g2.fillRect(20, 20, 8, 8);

		g2.setColor(new Color(0xFF, 0xCF, 0x00));
		
		// if(mysz != null) g2.fillRect((mysz.x / 11) * 11, (mysz.y / 11) * 11, 10, 10);
		if ( mysz != null ) g2.fillOval( (mysz.x / 11) * 11, (mysz.y / 11) * 11, 10, 10 );

		// System.out.println(bi.getRGB(30,30));
    }
	
	private Point mysz;
	
	public void setMysz(Point p) {
		mysz = p;
	}
}

public class ImageApplet extends JApplet {
    // ten static bledogenny
	private static BufferedImage img;
	private static int h1, w1;

	private static ImageDrawingComponent idc;

	public ImageApplet () {}
    public void init() {}
    public void buildUI() {
		final ImageDrawingComponent id = new ImageDrawingComponent();
		idc = id;
		img = id.getImg();
		h1 = id.getH();
		w1 = id.getW();
		add("Center", id);
	}
	
	public static void malujTlo(int x, int y) {
			for(int r=0; r<h1; r++)
			for(int c=0; c<w1; c++)
			{
			  int red= (r + x) & 0xFF;
			  int green= (c + y) & 0xFF;
			  int blue= (r + c + x + y) & 0xFF;
			  int rgb = (red << 16) | (green << 8) | blue;
			  img.setRGB(c, r, rgb);
			}
	}
	
	public static void main(String s[]) {
        JFrame f = new JFrame("ImageDrawing");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        ImageApplet id = new ImageApplet();
        id.buildUI();

        id.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                System.out.print("Ruch mysza: ");
				
				Point p = e.getPoint();
				idc.setMysz(p);
				
				System.out.print(p.x);
				System.out.print(" ");
				System.out.print(p.y);
				System.out.print(" ");
				System.out.print(p);
				System.out.println(" . ");
				
				malujTlo(p.x, p.y);
				img.setRGB(p.x, p.y, 0xFFFFFF);
                id.repaint();  
            }
        });

		id.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				System.out.println("id; Wcisniety klawisz: ");
			}
		});

		JTextField typingArea = new JTextField(20);
        JButton button = new JButton("Clear");


		typingArea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				System.out.println("Wcisniety klawisz: ");
			}
		});


        f.getContentPane().add(typingArea, BorderLayout.PAGE_START);
        f.getContentPane().add(id, BorderLayout.CENTER);
        f.getContentPane().add(button, BorderLayout.PAGE_END);

        //f.add("Center", id);
        //f.add("Center", typingArea);
		f.pack();

		id.requestFocusInWindow();

        f.setVisible(true);
    }
}
