/**
* BouncingBalls Class File
* 
* This class has all classes needed to create the GUI for bouncing balls
* The Start, Panel, and Ball Classes
* Using these classes to create balls bouncing on screen concurrently
* 
* When moused is clicked the balls will start spawning in with different random colors, speed, and positioning
*
* @author Nathan Le
* @version 1.0
* @since 2019-11-2
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BouncingBalls {
    public static void main(String[] args) {
    	// To run the program
        Start program = new Start();
        program.run();
    }
}

/**
* Start Class File
* 
* This class creates the JFrame and panel for the application  and
* Adds a mouse listener to add the bouncing balls to the panel
* Use concurrency and thread to run the program and update the bouncing balls
* 
*/
class Start implements Runnable {
	
	// JFrame and JPanel
    private JFrame frame;
    private Panel panel;
    // Balls arraylist
    private java.util.List<Ball> balls;

    // How big the frame will start, can change by maximizing window
    private int windowWidth = 800;
    private int windowHeight = 500;
    private String windowLabel = "Bouncing Balls!";
    // count balls
    int count = 1;

    /**
	 * run(), use runnable to keep program going for bouncing balls
	 * 
	 * @param none
	 * 
	 * @return none
	 */
    public void run() {

        balls = new ArrayList<>(20);

        
       frame = new JFrame();
        
       // mouse listener to add more balls
        frame.addMouseListener(new MouseAdapter()
    	{
    		public void mouseClicked(MouseEvent e)
    		{
    			if(count <= 20)
    			{
    				if(count == 1)
    				{
    					// add balls to list and call constructor
    					balls.add(new Ball(/* Random positions from 0 to windowWidth or windowHeight */
    	                        (int) Math.floor(Math.random() * windowWidth - 25),
    	                        (int) Math.floor(Math.random() * windowHeight - 25),
    	                        /* Random RGB colors*/
    	                        new Color(0,0,255),
    	                        /* Random speed from 1 to 5 */
    	                        (int) Math.floor((Math.random() * 5) + 1),
    	                        (int) Math.floor((Math.random() * 5) + 1)));
    				}
    					
    				else {
    					balls.add(new Ball(/* Random positions from 0 to windowWidth or windowHeight */
                        (int) Math.floor(Math.random() * windowWidth - 25),
                        (int) Math.floor(Math.random() * windowHeight - 25),
                        /* Random RGB colors*/
                        new Color(
                                (int) Math.floor((Math.random() * 256)),
                                (int) Math.floor((Math.random() * 256)),
                                (int) Math.floor((Math.random() * 256))
                        ),
                        /* Random speed from 1 to 5 */
                        (int) Math.floor((Math.random() * 5) + 1),
                        (int) Math.floor((Math.random() * 5) + 1)));
    				}
    				
    			}
    			
    			count++;
    			
    		}
    	});
        
        // set the frame visible
        panel = new Panel();
        frame.getContentPane().add(panel);
        frame.setTitle(windowLabel);
        frame.setSize(windowWidth, windowHeight);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Concurrency
        while (true) {
            for (Ball b: balls) {
                b.update();
            }

            /* Give Swing 10 milliseconds to see the update! */
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

           frame.repaint();
        }
}


    /**
    * Panel Class File
    * 
    * This class will draw the panel and balls. Add balls by creating them using the paintComponents and graphics
    * 
    */
class Panel extends JPanel {
        /**
		 * 
		 */
		private static final long serialVersionUID = -7849959802892548780L;

		/**
		 * paintComponenet, to set panel background black and display balls when clicking mouse
		 * 
		 * @param Graphics g
		 * 
		 * @return none
		 */
		@Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.BLACK);
    		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
            
            for (Ball b: balls) {
                b.draw(g);
            }
            
            Graphics2D g2d = (Graphics2D) g;
    		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        }
}

/**
* Ball Class File
* 
* This class creates the bouncing balls when mouse is clicked
* Constructor to create different colored balls bouncing at different speeds and directions
* Use update function to change the bouncing of the balls when they hit the panel edges
* 
*/
    class Ball {
        private int posX; 
        private int posY;
        private int size = 50;
        private Color color;

        private int speedX = 5;
        private int speedY = 5;

        
        /**
    	 * Constructor, store the position, color, speed of ball
    	 * 
    	 * @param int, int, color, int, int
    	 * 
    	 * @return none
    	 */
        public Ball(int posX, int posY, Color color, int sx, int sy) {
            this.posX = posX;
            this.posY = posY;
            this.color = color;
            this.speedX = sx;
            this.speedY = sy;
        }

        /**
    	 * update(), To update each ball bounce when the ball hits the edage of the panel
    	 * 
    	 * @param none
    	 * 
    	 * @return none
    	 */
        void update() {

            if (posX + size > frame.getWidth() || posX < 0) {
                speedX *= -1;
            }

            if (posX >= frame.getWidth()) {
                posX = frame.getWidth();
            }

            if (posX <= 0) {
                posX = 0;
            }
            
            if (posY + size*2 > frame.getHeight() || posY < 0) {
                speedY *= -1;
            }

            if (posY > frame.getHeight()) {
                posY = frame.getHeight();
            }

            if (posY <= 0) {
                posY = 0;
            }

            this.posX += speedX;
            this.posY += speedY;

        }

        /**
    	 * draw(), to draw each new ball in different color to the panel when mouse is clicked
    	 * 
    	 * @param Graphics g
    	 * 
    	 * @return none
    	 */
        void draw(Graphics g) {
            g.setColor(color);
            g.fillOval(posX, posY, size, size);
        }
    }
}
