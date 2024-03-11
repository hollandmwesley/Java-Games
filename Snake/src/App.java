import javax.swing.*;


public class App {
    public static void main(String[] args) throws Exception {
        //Board settings (650px*650px)
        int boardWidth = 650;
        int boardHeight = boardWidth;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);  //Board will launch in the center of screen
        frame.setResizable(false);  //Board is not resizable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //program will terminate on close

        //create instance of Snake Game
        Snake snake = new Snake(boardWidth, boardHeight);
        frame.add(snake); 
        frame.pack();//exclude top panel from board size
        snake.requestFocus(); //snake game will be listening for key presses
    }
}
