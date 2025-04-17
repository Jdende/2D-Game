package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // Screen Settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow;// 576 pixels

    // FPS
    int fps = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);


    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000/fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTIme;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTIme = System.nanoTime();

            delta += (currentTIme - lastTime) / drawInterval;
            timer += (currentTIme - lastTime);
            lastTime = currentTIme;

            if (delta >= 1) {
                // 1 UPDATE: update information
                update();
                // 2 DRAW: draw the screen with the updated information
                repaint();
                delta--;
                drawCount++;
            }

//            Show FPS
//            if (timer >= 1000000000) {
//                System.out.println("FPS: " + drawCount);
//                drawCount = 0;
//                timer = 0;
//            }
        }
    }

    public void update() {

        player.update();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        player.draw(g2);

        g2.dispose();
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxScreenRow() {
        return maxScreenRow;
    }

    public int getMaxScreenCol() {
        return maxScreenCol;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
