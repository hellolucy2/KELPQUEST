package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private Game game;

    public GamePanel(Game game){

        mouseInputs = new MouseInputs(this);
        this.game = game;

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize(){
        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);

        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        System.out.println("size- " + GAME_WIDTH + " " + GAME_HEIGHT);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        game.render(g);

    }

  public void updateGame(){

  }

  public Game getGame(){
        return game;
  }

}

