
package CircleInRectangle;


import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.swing.*;

/**
 *
 * @author Mohamed
 */
public class CircleInRectangle extends JFrame {

    static GLCanvas glcanvas = null;

    public static void main(String[] args) {
        final CircleInRectangle app = new CircleInRectangle();
// show what we've done
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        app.setVisible(true);
                        glcanvas.requestFocusInWindow();
                    }
                }
        );
    }

    public CircleInRectangle() {
//set the JFrame title
        super("KeyListener Example");
//kill the process when the JFrame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//create our KeyDisplay which serves two purposes
// 1) it is our GLEventListener, and
// 2) it is our KeyListener
        MouseMotionDisplay mmd = new MouseMotionDisplay();
//only three JOGL lines of code ... and here they are
        GLCapabilities glcaps = new GLCapabilities();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(mmd);
        glcanvas.addMouseMotionListener(mmd);
//we'll want this for our repaint requests
        mmd.setGLCanvas(glcanvas);
//add the GLCanvas just like we would any Component
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setSize(500, 300);
//center the JFrame on the screen
        centerWindow(this);
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

    
    double x0Position = 0;
    double y0Position = 0;
    double xPosition = 0;
    double yPosition = 0;
    float red = 0.0f;
    float green = 0.5f;
    float blue = 0.5f;
    GLCanvas glc;
    
    double height1 =0;
    double width1 =0;
    public void setGLCanvas(GLCanvas glc) {
        this.glc = glc;
    }

    /**
     * Take care of initialization here.
     */
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        red = 0.5f;
        green = 0.0f;
        blue = 1.0f;
        gl.glClearColor(red, green, blue, 0.0f);
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
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        final double ONE_DEGREE = (Math.PI/180);
        final double THREE_SIXTY = 2 * Math.PI;
        double radius =0 ,x,y; 
            //Remember point size refers
            //to pixels, not the coordinate
            //system we've set up in the
            //GLCanvas
        gl.glPointSize(2.0f);
        red = 0.0f;
        green = 0.0f;
        blue = 0.0f;
        gl.glColor3f(red, green, blue);
       
        
        gl.glBegin(GL.GL_LINES);
        
        //diagonal line
        gl.glVertex2d(x0Position, y0Position);
        gl.glVertex2d(xPosition, yPosition);
        
        //left line
        gl.glVertex2d(x0Position, y0Position);
        gl.glVertex2d(x0Position, yPosition);
        
        // top line
        gl.glVertex2d(x0Position, y0Position);
        gl.glVertex2d(xPosition, y0Position);
        
        //right line
        gl.glVertex2d(xPosition, y0Position);
        gl.glVertex2d(xPosition, yPosition);
        
        //bottom line
        gl.glVertex2d(x0Position, yPosition);
        gl.glVertex2d(xPosition, yPosition);
        gl.glEnd();
        
        gl.glBegin(GL.GL_POINTS);
        //gl.glVertex2i(xPosition, yPosition);
        
        radius = (xPosition - x0Position)/2 ;
         
        width1 = xPosition - x0Position;
        height1 = yPosition - y0Position;    
        
        for (double a=0; a<THREE_SIXTY; a+=ONE_DEGREE) {
            x =width1/2   +  x0Position +((radius * (Math.cos(a)))) ;
            y = height1/2 +  y0Position +((height1/2 * (Math.sin(a))));
            
            gl.glVertex2d(x, y);
        }
        
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


    public void mouseDragged(MouseEvent e) {
        
       
        double x = e.getX();
        double y = e.getY();
        Component c = e.getComponent();
         
        double width = c.getWidth();
        double height = c.getHeight();

        x0Position = (int) ((x / width) * 100);
        y0Position = ((int) ((y / height) * 100));

        y0Position = 100 - y0Position;
        glc.repaint();
        
    }
     
    public void mouseMoved(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        Component c = e.getComponent();
         width1 = c.getWidth();
         height1 = c.getHeight();

        xPosition = (int) ((x / width1) * 100);
        yPosition = ((int) ((y / height1) * 100));

        yPosition = 100 - yPosition;
        
    }
}
