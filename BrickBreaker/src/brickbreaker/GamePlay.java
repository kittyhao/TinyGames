package brickbreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0; //Initial score is zero.

    private int totalBricks = 28;

    private Timer timer;
    private int delay = 8;

    private int playPosX = 310;
    private int ballPosX = 350;
    private int ballPosY = 530;
    private int ballDirX = -1;
    private int ballDirY = -2;

    private MapGenerator bricks;

    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        timer.start();

        bricks = new MapGenerator(4,7);
    }

    //Draw game interface
    public void paint(Graphics g){
        // Background
        g.setColor(Color.darkGray);
        g.fillRect(1,1,692, 592);

        // Border
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(683,0,3,592);

        // Score board
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        // Bricks
        bricks.draw((Graphics2D)g);

        // Paddle
        g.setColor(Color.cyan);
        g.fillRect(playPosX, 550, 100, 8);

        // Ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        if(totalBricks == 0) {
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(Color.yellow);
            g.setFont(new Font("serif", Font.BOLD, 60));
            g.drawString("You Win!", 150, 300);

            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 220, 400);

        } else if(ballPosY > 570) {
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 60));
            g.drawString("Game Over, Score: " + score, 100, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 220, 400);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //timer.start();

        // Process the ball event
        if(play){
            // Ball move after game started
            ballPosX += ballDirX;
            ballPosY += ballDirY;

            // Collision with the border
            if(ballPosX < 6) {
                ballDirX = -ballDirX;
            }

            if(ballPosX > 660) {
                ballDirX = -ballDirX;
            }

            if(ballPosY < 3) {
                ballDirY = -ballDirY;
            }

            // Collision with the paddle
            if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playPosX, 550, 100, 8))) {
                ballDirY = -ballDirY;
            }

            // Collision with the brick
            A: for(int i = 0; i < bricks.mapRow; i++){
                for(int j = 0; j < bricks.mapCol; j++) {
                    if(bricks.brickMap[i][j] > 0) {
                        int bx = j * bricks.brickWidth + 80; // x coordinate of the brick
                        int by = i * bricks.brickHeight + 50; // y coordinate of the brick

                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = new Rectangle(bx, by, bricks.brickWidth, bricks.brickHeight);

                        if (ballRect.intersects(brickRect)) {
                            bricks.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if(ballPosX + 19 <= brickRect.x || ballPosX - 1 >= brickRect.x + brickRect.width) {
                                ballDirX = -ballDirX;
                            } else {
                                ballDirY = -ballDirY;
                            }

                            break A;
                        }
                    }
                }
            }
        }

        repaint();
    }

    // Method not used
    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }

    // Accept key input
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            play = true;
            playPosX += 20;
            if(playPosX > 580){
                playPosX = 580;
            }
            //System.out.println(playPosX);
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT)
        {
            play = true;
            playPosX -= 20;
            if(playPosX < 3){
                playPosX = 6;
            }
            //System.out.println(playPosX);
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play) {
                // Reset all the variables
                playPosX = 310;
                ballPosX = 350;
                ballPosY = 530;
                ballDirX = -1;
                ballDirY = -2;
                score = 0;
                totalBricks = 28;
                bricks = new MapGenerator(4,7);
            }
        }
    }


}
