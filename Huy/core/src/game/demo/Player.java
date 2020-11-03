package game.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Vector;

public class Player extends GameObj{
    private SpriteBatch batch;
    private int loaded=0;
    private long t=System.currentTimeMillis()-200;
    private float varyDistance;
    private long rapidity;

    public void create(int width,int height){
        // Scale, if changed screen res
        setScale((float)(0.1*Gdx.graphics.getWidth()),(float)(0.2*Gdx.graphics.getHeight()));
        batch = new SpriteBatch();
        hitboxRadius=10;
        State=true;
        setId(1);
        Texture_Width=width;
        Texture_Height=height;
    }

    public void setId(int id){
        this.id=-id;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public boolean fire(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) Bullet.resetfakebase();
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            if (System.currentTimeMillis() - t > rapidity) {
                t = System.currentTimeMillis();
                loaded = 1;
            } else {
                loaded = 0;
            }
            if (loaded==1) return true;
            else return false;

        }
        else return false;
    }

    public void render_player () { // loop
        if (State) {
            batch.begin();
            batch.draw(Assets.texture_plane,  (x - (this.Texture_Width/2)),  (y - (Texture_Height/2)));
            batch.end();
        }
        input();

    }
    public void dispose () {
        batch.dispose();
    }

    public static void checkCollision(Vector<Bullet> bullet_arr){
        for (int i=0;i<bullet_arr.size();i++) {
            if ((!bullet_arr.elementAt(i).isDed())&&
                    (bullet_arr.elementAt(i).id*MyGdxGame.player.id<0)&&
                    (Math.sqrt(Math.pow((double)bullet_arr.elementAt(i).getX()-MyGdxGame.player.x,2.0)+
                            Math.pow((double)bullet_arr.elementAt(i).getY()-MyGdxGame.player.y,2.0))<
                            (double)bullet_arr.elementAt(i).hitboxRadius+MyGdxGame.player.hitboxRadius)){
                MyGdxGame.player.Execute();
            }
        }
    }

    public void Execute(){
        State=false;
    }

    public void Collision(Vector<PixelCoord> Hail,Vector<Bullet> Barr){
 loop:  for(int i=0;i<Pixel.size();i++){
            for(int j=0;j<Hail.size();j++){
                if(Pixel.elementAt(i).VicinityBullet(Hail.elementAt(j))){
                    Barr.elementAt(Hail.elementAt(j).location_Bullet).Execute();
                    this.Execute();
                    break loop;
                }
            }
        }
    }

    /* ----------------------------------------------------------------------
     * ----------------------------------------------------------------------
     * --------------------------Edit - add more-----------------------------
     * --------------------------Player's data-------------------------------
     * ----------------------------------------------------------------------
     * ----------------------------------------------------------------------*/

    public void input(){
        switch (id) {
            case -1:
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    varyDistance=1;
                    rapidity=50;
                }
                else {
                    varyDistance=5;
                    rapidity=100;
                }
                break;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x-=varyDistance;
        if (x<0) x=0;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x+=varyDistance;
        if (x>Gdx.graphics.getWidth()) x=Gdx.graphics.getWidth();
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) y+=varyDistance;
        if (y>Gdx.graphics.getHeight()) y=Gdx.graphics.getHeight();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) y-=varyDistance;
        if (y<0) y=0;
    }

    public void Bullet_Call(Vector<Bullet> bullet_arr) {              //Furthermore edit here
        switch (Math.abs(id)) {
            case 0:
            {
                Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 0, 30, 0,1);
                Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 0, -30, 0,1);
                Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 30, 0, 0,1);
                Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), -30, 0, 0,1);
                break;
            }
            case 1:
                /*     bullet_arr.addElement(new Bullet(getX(), getY(), 0, 30, 1));*/
            {
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 0, 30, -3,1);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), -5, 29, -3,1);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 5, 29, -3,1);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 0, 25, -3,1);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), -10, 28, -3,1);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 10, 28, -3,1);
                }
                else {
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 0, 30, -1,1);
                    Bullet.Bullet_Reallo(bullet_arr,getX()-3, getY(), -2, 29, -1,1);
                    Bullet.Bullet_Reallo(bullet_arr,getX()+3, getY(), 2, 29, -1,1);
                    Bullet.Bullet_Reallo(bullet_arr,getX()-5, getY(), -5, 28, -1,1);
                    Bullet.Bullet_Reallo(bullet_arr,getX()+5, getY(), 5, 28, -1,1);
                }
            }
            break;
        }
    }
}