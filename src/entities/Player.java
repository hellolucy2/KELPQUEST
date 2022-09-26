package entities;

import main.Game;
import utils.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.Directions.*;
import static utils.Constants.Directions.DOWN;
import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.*;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 40;
    private int playerAction = RUNNING;
    private boolean left, up, right, down, jump;
    private boolean moving = false, attacking = false;
    private float playerSpeed = 2.0f;
    private int [][] lvlData;
    private float xDrawOffset = 11 * Game.SCALE;
    private float yDrawOffset = 1 * Game.SCALE;


    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y,40 *Game.SCALE, 59 * Game.SCALE);
    }

    public void update(){

        updatePos();
        updateAnimationTick();

        setAnimation();

    }

    public void render(Graphics g){
        g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), width, height, null);
        drawHitbox(g);

    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmounts(playerAction)){
                aniIndex = 0;
                attacking  = false;
            }
        }
    }

    public void setAnimation() {
        int startAni = playerAction;

        if (moving) {
            playerAction = RUNNING;
        }
        else {
            playerAction = IDLE;
        }

        if(attacking){
            playerAction = ATTACK;
        }
        
        if(startAni != playerAction) {
            resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    public void updatePos(){

        moving = false;

        if (jump)
            jump();
        if(!left && !right && !inAir)
            return;

        float xSpeed = 0;

        if(left)
            xSpeed -= playerSpeed;
        if (right)
            xSpeed += playerSpeed;

        if(inAir){
            if(CanMoveHere(hitbox.x,hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData))
            {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }
            else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if(airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);

            }

        }else
            updateXPos(xSpeed);

        moving = true;

    }

    private void jump() {
        if(inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {

        if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        }else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }

    }

    private void loadAnimations() {

            BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

            animations = new BufferedImage[3][6];

            for (int j = 0; j < animations.length; j++) {
                for (int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = img.getSubimage(i * 50, j * 50, 50, 50);
                }
            }

    }

    public void loadLvlData(int[][] lvlData){
        this.lvlData = lvlData;
    }


    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttacking(boolean attacking){
        this.attacking = attacking;

    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump (boolean jump){
        this.jump = jump;
    }
}

