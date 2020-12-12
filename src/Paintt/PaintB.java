
package Paintt;

import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.swing.*;
import javax.swing.colorchooser.ColorChooserComponentFactory;
import javax.swing.plaf.ColorChooserUI;
import javax.swing.plaf.basic.BasicColorChooserUI;


public class PaintB extends JFrame {

    static GLCanvas glcanvas = null;

    public static void main(String[] args) {
        final PaintB app = new PaintB();

        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        app.setVisible(true);
                        glcanvas.requestFocusInWindow();
                    }
                }
        );
    }
     JButton jb1 = new JButton("Color");
     JButton jb2 = new JButton("Clear");
     
    public PaintB() {

        super("KeyListener Example");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel jp = new JPanel();
        jp.add(jb1);
        jp.add(jb2);
        getContentPane().add("South", jp);  
        MouseMotionDisplay mmd = new MouseMotionDisplay();

        GLCapabilities glcaps = new GLCapabilities();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(mmd);
        glcanvas.addMouseMotionListener(mmd);

        mmd.setGLCanvas(glcanvas);

        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setSize(500, 300);

        centerWindow(this);
        
          jb1.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent ev){
            
              ColorChooserUI c = new BasicColorChooserUI();
          }
         });
 

        jb2.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent ev){
              //         GL gl = drawable.getGL();
            //       
            //        gl.glClearColor(0, 0, 0, 0.0f);

                  // setBackground(Color.BLACK);
            //        Graphics g=getGraphics();
            //          g.setColor(Color.black);
        
          }
         });
    }

    public void centerWindow(Component frame) {
        Dimension screenSize
                = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        frame.setLocation(
                (screenSize.width - frameSize.width) >> 1,
                (screenSize.height - frameSize.height) >> 1
        );
    }
 
}

class MouseMotionDisplay
        implements GLEventListener, MouseMotionListener {

    int xPosition ;
    int yPosition ;
   
    double x=50 ;
    double y=50;
    
    GLCanvas glc;

    public void setGLCanvas(GLCanvas glc) {
        this.glc = glc;
    }

    /**
     * Take care of initialization here.
     */
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
       
        gl.glClearColor(0, 0, 0, 0.0f);
        gl.glViewport(0, 0, 100, 100);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0.0, 100, 0, 100, -1, 1);
    }

    /**
     * Take care of drawing here.
     */
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
  
        gl.glPointSize(5.0f);
        
        gl.glColor3f(1, 1, 1);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2d(xPosition, yPosition);
        gl.glEnd();
    }

 
    public void reshape(
            GLAutoDrawable drawable,
            int x,
            int y,
            int width,
            int height
    ) {
    }


    public void displayChanged(
            GLAutoDrawable drawable,
            boolean modeChanged,
            boolean deviceChanged
    ) {
    }
////////////////////////////////////////////
// MouseMotionListener implementation below

  
    @Override
    public void mouseDragged(MouseEvent e) {
         x = e.getX();
         y = e.getY();
        Component c = e.getComponent();
        double width = c.getWidth();
        double height = c.getHeight();

        xPosition = (int) ((x / width) * 100);
        yPosition = ((int) ((y / height) * 100));

        yPosition = 100 - yPosition;
        glc.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        //To change body of generated methods, choose Tools | Templates.
    }
}

