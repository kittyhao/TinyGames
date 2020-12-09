package brickbreaker;

import javax.swing.JFrame;
import java.awt.*;

public class BrickBreaker {
    public static void main(String[] args){
        JFrame gameFrame = new JFrame();

        GamePlay gamePlay = new GamePlay();

        //Set the dimension of the window
        gameFrame.setBounds(10, 10, 700, 600);
        gameFrame.setTitle("It's a Brick Breaker");
        //Game window not resizable
        gameFrame.setResizable(false);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the interface
        gameFrame.add(gamePlay);

    }
}
