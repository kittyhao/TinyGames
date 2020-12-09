package brickbreaker;

import java.awt.*;

public class MapGenerator {
    public int brickMap[][];
    public int brickWidth;
    public int brickHeight;
    public int mapRow;
    public int mapCol;

    public MapGenerator(int row, int col){
        brickMap = new int[row][col];

        mapRow = row;
        mapCol = col;

        for(int i = 0; i < brickMap.length; i++){
            for(int j = 0; j < brickMap[0].length; j++) {
                brickMap[i][j] = 1;
            }
        }

        brickWidth = 540/col;
        brickHeight = 150/row;
    }

    public void draw(Graphics2D g) {
        //Draw bricks
        for(int i = 0; i < brickMap.length; i++){
            for(int j = 0; j < brickMap[0].length; j++) {
                if(brickMap[i][j] > 0) {
                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }

                g.setStroke(new BasicStroke(3));
                g.setColor(Color.darkGray);
                g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        // If the brick was touched by the ball, set the brick map value to zero.
        if(row < 0 || row >= mapRow || col < 0 || col >= mapCol ) {
            System.out.println("Index out of range.");
            return;
        }
        brickMap[row][col] = value;
    }

}
