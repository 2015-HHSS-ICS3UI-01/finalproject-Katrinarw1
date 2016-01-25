import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author watsk8668
 */
public class FinalGame2 extends JComponent implements KeyListener, MouseMotionListener, MouseListener {

    // Height and Width of our game
    static final int windowWidth = 1000;
    static final int windowHeight = 700;

    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;

    // player position variables
    int x = 100;
    int y = 500;
    int moveX = 0;
    int moveY = 0;
    boolean inAir = false;
    int width = 40;
    int height = 40;
    
    //if the player is hit with a brick
    boolean crushed = false;
    
    ArrayList<Rectangle> bricks = new ArrayList<>();
    
    // why cant I use <Circle>????
    ArrayList<Rectangle> coins = new ArrayList<>(); 
    
    //coffee
    ArrayList<Rectangle> coffees = new ArrayList<>();
    
    // keyboard variables
    boolean right = false;
    boolean left = false;
    boolean jump = false;
    boolean prevJump = false;
    double energy = 210;
    boolean playerMoving = false;
    
    // mouse clicks for changing screens
    int buttonPressed = 0;
    
    //coins collected
    int score = 0;
    
    int delay = 50;
    
    Timer timer = null;
    
    
    int coinY = 0;
    
    // player
     Rectangle player = new Rectangle(500, 200, 40, 40);

    int frameCount = 0;
    
    // import images
    BufferedImage background = loadImage("sky.png");
    BufferedImage brickPic = loadImage("brick3.png");
    BufferedImage coin = loadImage("coin.png");
    BufferedImage coffeePic = loadImage("coffecup.png");
    BufferedImage title = loadImage("Instructions.png");
    BufferedImage gameOver = loadImage("GAMEOVER.png");
    BufferedImage scoreScreen = loadImage("SCORE.png");
    

    public BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (Exception e) {
            System.out.println("Error loading " + filename);
        }
        return img;
    }

    public FinalGame2() {
        bricks = new ArrayList<>();
        bricks.add(new Rectangle(100, 0, 40, 20));
        bricks.add(new Rectangle(0, 150, 40, 20));
        bricks.add(new Rectangle(300, 200, 40, 20));
        bricks.add(new Rectangle(400, 0, 40, 20));
        bricks.add(new Rectangle(150, 400, 40, 20));
        bricks.add(new Rectangle(150, 650, 40, 20));
        bricks.add(new Rectangle(550, 250, 40, 20));
        bricks.add(new Rectangle(700, 75, 40, 20));
        bricks.add(new Rectangle(950, 0, 40, 20));
        bricks.add(new Rectangle(800, 300, 40, 20));
        bricks.add(new Rectangle(750, 500, 40, 20));
        bricks.add(new Rectangle(980, 300, 40, 20));
        bricks.add(new Rectangle(400, 600, 40, 20));
        bricks.add(new Rectangle(900, 550, 40, 20));
        bricks.add(new Rectangle(600, 680, 40, 20));
        
        coins = new ArrayList<>();
        coins.add(new Rectangle(220, 370, 15, 15));
        coins.add(new Rectangle(470, 180, 15, 15));
        coins.add(new Rectangle(346, 123, 15, 15));
        coins.add(new Rectangle(212, 321, 15, 15));
        coins.add(new Rectangle(987, 456, 15, 15));
        coins.add(new Rectangle(784, 180, 15, 15));
        coins.add(new Rectangle(543, 50, 15, 15));
        coins.add(new Rectangle(908, 328, 15, 15));
        coins.add(new Rectangle(800, 500, 15, 15));
        coins.add(new Rectangle(750, 180, 15, 15));
        coins.add(new Rectangle(100, 600, 15, 15));
        coins.add(new Rectangle(50, 200, 15, 15));
        
        coffees = new ArrayList<>();
        coffees.add(new Rectangle(200, 320, 25, 25));
        coffees.add(new Rectangle(500, 100, 25, 25));
        coffees.add(new Rectangle(900, 550, 25, 25));
        
        
    }
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, windowWidth, windowHeight);
        // GAME DRAWING GOES HERE 
        if (buttonPressed == 0) {
            //to see of its working b/c pic not working
            g.setColor(Color.YELLOW);
            g.fillRect(0, 0, windowWidth, windowHeight);
            //Instruction screen
            g.drawImage(title, 0, 0, windowWidth, windowHeight, null);
        }

        if (buttonPressed == 1) {
            
            //background
            Color lightBlue = new Color(148, 255, 250);
            g.setColor(lightBlue);
            g.fillRect(0, 0, windowWidth, windowHeight);
            //if pic will work
            g.drawImage(background, 0, 0, windowWidth, windowHeight, null);

            //draw ground
            Color grassGreen = new Color(62, 199, 62);
            g.setColor(grassGreen);
            g.fillRect(0, 600, windowWidth, 100);

            // draw player
            g.setColor(Color.RED);
            g.fillRect(player.x, player.y, player.width, player.height);
            

            //create the bricks
            g.setColor(Color.RED);
            for (Rectangle brick : bricks) {
                    g.fillRect(brick.x, brick.y, brick.width, brick.height);
                }
            
            //create the cions
            g.setColor(Color.YELLOW);
            for (Rectangle coin : coins) {
                    g.fillRect(coin.x, coin.y, coin.width, coin.height);
                }
            
            //create the coffee
            g.setColor(Color.BLACK);
            for (Rectangle coffee : coffees) {
                    g.fillRect(coffee.x, coffee.y, coffee.width, coffee.height);
                }
            
            // Energy
            g.setFont(new Font("Impact", Font.PLAIN, 20));
            String energyString = "" + energy;
            g.drawString(energyString, 20, 620);
            
            // Create the energy bar
            g.setColor(Color.RED);
            g.fillRect(10, 625, 220, 50);
            g.setColor(Color.GREEN);
            g.fillRect(15, 630, (int) energy, 40);
            
        }

        if (buttonPressed == 2) {
            //to see of its working b/c pic not working
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, windowWidth, windowHeight);
            //game over screen
            g.drawImage(gameOver, 0, 0, windowWidth, windowHeight, null);
        }

        if (buttonPressed == 3) {
            //to see of its working b/c pic not working
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, windowWidth, windowHeight);
            //score screen
            g.drawImage(scoreScreen, 0, 0, windowWidth, windowHeight, null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Impact", Font.PLAIN, 30));
            String scoreString = "" + score;
            g.drawString(scoreString, 500, 430);
        }

        // GAME DRAWING ENDS HERE
    }
    

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // initial things to do before game starts

        
        // END INITIAL THINGS TO DO
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            if(buttonPressed == 1){
            // move the player left or right 
            if (left) {
                moveX = -2;
                playerMoving = true;
            } else if (right) {
                moveX = 2;
                playerMoving = true;
            } else {
                moveX = 0;
                playerMoving = false;
            }
            frameCount++;

            if (frameCount >= 1) {
                // gravity pulling player down
                moveY = moveY + 1;
                frameCount = 0;
                // go through all blocks
            for(Rectangle coin: coins){
                // gravity pulling the coins down
                coin.y = 2 + coin.y;
            }
            // go through all blocks
            for(Rectangle brick: bricks){
            //move the brick
            brick.y = brick.y + 2;
            }
            // go through all blocks
            for(Rectangle coffee: coffees){
            //move the brick
            coffee.y = coffee.y + 2;
            }
            }
            

            //jumping
            // jump being pressed and not in the air
            if (jump && !prevJump && !inAir) {
                // make a big change in y direction
                moveY = -20;
                inAir = true;
                playerMoving = true;
            }
            // keeps track of jump key changes
            prevJump = jump;

            // move the player
            player.x = player.x + moveX;
            player.y = player.y + moveY;
            
            // Energy
            if(playerMoving){
            energy = energy -.25;
            }

            // if feet of player become lower than the ground   
            if (player.y + player.height > 600) {
                // stops the falling
                player.y = 600 - player.height;
                moveY = 0;
                inAir = false;
            }
            
            // go through all blocks
            for(Rectangle brick: bricks){
                // is the player hitting a block
                if(player.intersects(brick)){
                    //  if yes player is hit
                    crushed = true;
                }
            }
            
            // go through all blocks
            for(Rectangle coin: coins){
                // is the player hitting a block
                if(player.intersects(coin)){
                    // if yes player is hit
                    score = score + 1;
                }
            }
            
            // go through all blocks
            for(Rectangle coffee: coffees){
                // is the player hitting a block
                if(player.intersects(coffee)){
                    // if yes player is hit
                    energy = energy + 1;
                }
            }
            
            // If no energy game over
            if(energy == 0){
                buttonPressed = 2;
            }
            
            //if a brick hits the player game over
            if(crushed){
                buttonPressed = 2;
            }
            }
            
            
            
            
            
            // GAME LOGIC ENDS HERE 
            // update the drawing (calls paintComponent)
            repaint();

            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if (deltaTime > desiredTime) {
                //took too much time, don't wait
            } else {
                try {
                    Thread.sleep(desiredTime - deltaTime);
                } catch (Exception e) {
                };
            }
        }
    }
    
    
    public class Brick {

        static final int time = 5;
        int x, y;

        public Brick(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void drawBricks(Graphics g) {
            g.setColor(Color.RED);
            g.fillRect(x, y, 40, 20);
        }

        public void moveBricks() {
            if (y == windowHeight) {
                y = 0;
            } else {
                y += time;
            }
        }
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("Crushed!");

        // creates an instance of my game
        FinalGame2 game = new FinalGame2();
        // sets the size of my game
        game.setPreferredSize(new Dimension(windowWidth, windowHeight));
        // adds the game to the window
        frame.add(game);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        //add the listeners
        frame.addKeyListener(game); //keyboard
        game.addMouseListener(game); // mouse
        game.addMouseMotionListener(game); // mouse

        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            left = true;
        } else if (key == KeyEvent.VK_RIGHT) {
            right = true;
        } else if (key == KeyEvent.VK_SPACE) {
            jump = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int key = ke.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            left = false;
        } else if (key == KeyEvent.VK_RIGHT) {
            right = false;
        } else if (key == KeyEvent.VK_SPACE) {
            jump = false;
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
        // if holding down the left button
        if (me.getButton() == MouseEvent.BUTTON1) {
            buttonPressed = buttonPressed + 1;
        }
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
