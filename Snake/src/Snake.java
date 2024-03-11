import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; //store segments of the snakes body
import java.util.Random; //get random x and y values to place food
import javax.swing.*;


//KeyListener will listen to user inputs on arrow keys
public class Snake extends JPanel implements ActionListener, KeyListener{ //extend Jpanel, inheriting new class of Snake that will take on JPanel
    private class Tile{
        int x;
        int y;

//constructor    
        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }


    }  //class that only the game can access
    int boardWidth;
    int boardHeight;
    int tileSize = 25; //tile/pixel size of food


    Tile snakeHead; //Snake
    ArrayList<Tile> snakeBody;

    Tile food; //Food
    Random random;

    Timer gameLoop; //Logic
    int velocityX;
    int velocityY;
    boolean gameOver = false;


    //constructor
    Snake(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(12, 12);
        snakeBody = new ArrayList<Tile>(); //array list to store snakes body length

        food = new Tile(4, 4);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0; 

        gameLoop = new Timer(100, this);
        gameLoop.start();


    }
    public void paintComponent(Graphics g){ //used for drawing
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) { //define draw function
        //food
        g.setColor(Color.pink);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        
        //snake
         g.setColor(Color.blue);
         g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize, true);

         //snake body
         for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize);
         }
         //Score
         g.setFont(new Font ("Arial", Font.PLAIN, 16));
         if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
         }
         else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize = 16, tileSize);
         }
    }

    public void placeFood() { //function will randomly place food
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardHeight/tileSize);
    }
// Detect collision between snakes head and food
    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        //eating food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile (food.x, food.y));
            placeFood();
        }

        //Add to snake's body upon eating pixel
        for (int i = snakeBody.size()-1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) { //if the player has not eaten a tile yet
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else { //if the player has already eaten one pixel
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //game over conditions
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            //colide with snake head
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }

            if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth ||
                snakeHead.y*tileSize <0 || snakeHead.y*tileSize > boardHeight) {
                gameOver = true;
            }
        } //if snake colides with body
    }
    @Override //every 100ms we will call actionPerformed-- will paint every 100ms
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop(); //will stop the game when game over conditions are met
        }
    }

    //Define Key Presses
    @Override 
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    //not needed but must be defined
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

}
