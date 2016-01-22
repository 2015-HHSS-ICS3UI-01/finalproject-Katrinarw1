import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author watsk8668
 */


public class FinalProject extends JComponent implements KeyListener, MouseMotionListener, MouseListener{

    // Height and Width of our game
    static final int WIDTH = 1350;
    static final int HEIGHT = 700;
    
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000)/desiredFPS;
   
    //player 
    Rectangle player;
    
    // player position variables
    int x = 100;
    int y = 100;
   
    //platforms
    ArrayList<Rectangle> platforms = new ArrayList<>();
   
    //platform width
    int w = 75;
    //platform random height between 100 and 300
    int h = 1;
   
    //create gravity for jumping
    int gravity = 1;
    int frameCount = 0;
   
    //move
    int xmove = 0;
   
    //keyboard variables
    boolean right = false;
    boolean left = false;
    boolean jump = false;
   
    //move variables
    int moveX = 0;
    int moveY = 0;
    
    //import images
    BufferedImage background = loadImage("background1.png");
    BufferedImage standing = loadImage("playerStanding.jpg");
    BufferedImage running = loadImage("playerRunning.gif");


    
    public BufferedImage loadImage(String filename){
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File(filename));
        }catch(Exception e){
            System.out.println("Error loading " + filename);
        }
        return img;
    }
    
    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g)
    {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        // GAME DRAWING GOES HERE 
        // GAME DRAWING STARTS HERE
        Color greyish = new Color(149, 190, 204);
        g.setColor(greyish);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
       
        //player
        g.drawImage(standing, player.x, player.y, player.width, player.height, null);
        
        //platforms
        g.setColor(Color.BLACK);
        //array of blocks
        //MATH.random for heights?

        
        // GAME DRAWING ENDS HERE
    }
    
    
    // The main game loop
    // In here is where all the logic for my game will go
    public void run()
    {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;
        
        // the main game loop section
        // game will end if you set done = false;
        boolean done = false; 
        while(!done)
        {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
            
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            //move the player
             if(right){
                xmove = 1;
            } else if(left){
                xmove = -1;
            }else {
                xmove = 0;
            }
            

            // GAME LOGIC ENDS HERE 
            
            // update the drawing (calls paintComponent)
            repaint();
            
            
            
            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if(deltaTime > desiredTime)
            {
                //took too much time, don't wait
            }else
            {
                try
                {
                    Thread.sleep(desiredTime - deltaTime);
                }catch(Exception e){};
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");
       
        // creates an instance of my game
        FinalProject game = new FinalProject();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        // adds the game to the window
        frame.add(game);
         
        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        
        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        //when the arrow keys & space bar are pressed
        int key = ke.getKeyCode();
        if(key == KeyEvent.VK_SPACE){
            jump = true;
        }else if(key == KeyEvent.VK_LEFT){
            left = true;
        }else if(key == KeyEvent.VK_RIGHT){
            right = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
         //when the arrow keys & space bar are released
        int key = ke.getKeyCode();
        if(key == KeyEvent.VK_SPACE){
            jump = false;
        }else if(key == KeyEvent.VK_LEFT){
            left = false;
        }else if(key == KeyEvent.VK_RIGHT){
            right = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}