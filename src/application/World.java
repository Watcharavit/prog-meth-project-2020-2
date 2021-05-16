package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import entity.base.Being;
import entity.base.StillObject;
import gui.Sprite;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class World {
	private GraphicsContext gc;
	private Canvas canvas;
	
	private Tile[][] tiles;
	private HashSet<Being> beings;
	
	private int width;
	private int height;
	private final int tileSize;
	
	public World(int width, int height, int tileSize, Canvas canvas) {
		
		this.tiles = new Tile[width][height];
		MapGenerator.generateMap(this.tiles);
		
		this.width = width;
		this.height = height;
		this.tileSize = tileSize;
		
		this.canvas = canvas;
		this.gc = this.canvas.getGraphicsContext2D();
		
		this.beings = new HashSet<Being>();
		
		this.rerenderAll();
	}
	
	public void addBeing(Being being) {
		this.beings.add(being);
		this.rerenderBeing(being);
	}
	
	public void removeBeing(Being being) {
		double x = being.getX();
		double y = being.getY();
		if (this.beings.remove(being)) {
			this.rerenderAround(x, y);
		}
	}
	
	public Tile getTile(int x, int y) {
		return this.tiles[x][y];
	}
	
	public void setTileObject(int x, int y, StillObject object) {
		this.tiles[x][y].setObject(object);
		this.rerenderTile(x, y);
	}
	
	private void redrawTile(int x, int y) {
		StillObject object = this.tiles[x][y].getObject();
		object.getSprite().drawTopLeft(gc, x*tileSize, y*tileSize);
	}
	
	private void redrawBeing(Being being) {
		Sprite sprite = being.getSprite();
		sprite.drawCenter(gc, being.getX()*tileSize, being.getY()*tileSize);
	}
	
	public void rerenderTile(int x, int y) {
		this.redrawTile(x, y);
		for (Being being : this.beings) {
			double beingX = being.getX();
			double beingY = being.getY();
			if (beingX > x - 0.5 && beingX < x + 0.5 && beingY > y - 0.5 && beingY < y + 0.5) {
				this.redrawBeing(being);
			}
		}
	}

	public void rerenderAround(double centerX, double centerY) {
		int floorX = (int) Math.floor(centerX);
		int x = (centerX - floorX > 0.5) ? floorX : floorX - 1;
		int floorY = (int) Math.floor(centerY);
		int y = (centerY - floorY > 0.5) ? floorY : floorY - 1;
		
		for (int i = x; i <= x+1; i++) {
			for (int j = y; j <= y+1; j++) {
				this.redrawTile(i, j);
			}
		}
		
		for (Being otherBeing : this.beings) {
			double otherBeingX = otherBeing.getX();
			double otherBeingY = otherBeing.getY();
			if (otherBeingX > x - 0.5 && otherBeingX < x + 2.5 && otherBeingY > y - 0.5 && otherBeingY < y + 2.5) {
				this.redrawBeing(otherBeing);
			}
		}
	}
	
	public void rerenderBeing(Being being) {
		double beingX = being.getX();
		double beingY = being.getY();
		this.rerenderAround(beingX, beingY);
	}
	
	public void rerenderAll() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				this.redrawTile(i, j);
			}
		}
		for (Being being : this.beings) {
			this.redrawBeing(being);
		}
	}
}
