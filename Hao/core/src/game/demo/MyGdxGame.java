package game.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.lang.Math;
import java.util.Vector;
import game.demo.Bullet;

public class MyGdxGame extends ApplicationAdapter {

	//Player player; // null obj which not consist any method. --> it just save a variable __> different from c++.
	// In later we have to player = new Player() in somewhere--> but not remove new Player()
	static Player player = new Player();
	Assets assets = new Assets();
	Vector<Bullet> bullet_arr = new Vector<>();
	Vector<Enemy> enemy_arr = new Vector<>();
	Background background = new Background();
	Waves waves=new Waves();
	int Wave=0;

	//	Texture sprite_bullet;
//	//Input in; // interface class --> abstract class --> we cannot call obj of this class.
	@Override// we override function create in ApplicationAdapter.
	public void create() {
		assets.load();
		player.create();
		background.create();
		background.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

	}
	public void render(){

		Gdx.gl.glClearColor(0, 0, 0, 255);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		background.render();
		player.render_player();
		if(player.fire()){
			player.Bullet_Call(bullet_arr);
		}
		if(waves.Wave_Come()){
			waves.Wave_Call(enemy_arr,bullet_arr,Wave);
		}
		Enemy.render(enemy_arr);
		Enemy.fire(enemy_arr,bullet_arr);
		Bullet.render(bullet_arr);
		Gdx.app.log("FPS", Integer.toString(Gdx.graphics.getFramesPerSecond()));
	}
	public void dispose(){
		player.dispose();
		System.out.println("out");
	}
}