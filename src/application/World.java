package application;

import java.util.ArrayList;

import entity.base.Being;
import entity.base.StillObject;
import gui.DrawUtil;
import gui.Sprite;
import gui.Tile;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class World extends Canvas {
	private GraphicsContext gc;
	
	private Tile[][] tiles;
	private ArrayList<Being> beings;
	
	private int width;
	private int height;
	private int tileSize;
	
	public World(Tile[][] tiles, int tileSize) {
		super();
		
		this.width = tiles[0].length * tileSize;
		this.height = tiles.length * tileSize;
		this.tileSize = tileSize;
		
		super.setWidth(this.width);
		super.setHeight(height);
		
		this.tiles = tiles;
		this.beings = new ArrayList<Being>();
		this.gc = this.getGraphicsContext2D();
	}
	
	private void redrawTile(GraphicsContext gc, int x, int y) {
		StillObject object = this.tiles[x][y].getObject();
		DrawUtil.drawSprite(gc, x*this.tileSize, y*this.tileSize, object.getSprite().getIndex());
	}
	
	private void redrawBeing(GraphicsContext gc, Being being) {
		DrawUtil.drawSprite(gc, being.getX(), being.getY(), being.getSprite().getIndex());
	}
	
	protected void rerenderTile(GraphicsContext gc, int x, int y) {
		this.redrawTile(gc, x, y);
		for (Being being : this.beings) {
			double beingX = being.getX() / this.tileSize;
			double beingY = being.getY() / this.tileSize;
			if (beingX > x - 0.5 && beingX < x + 0.5 && beingY > y - 0.5 && beingY < y + 0.5) {
				this.rerenderBeing(gc, being);
				break;
			}
		}
	}
	
	protected void rerenderBeing(GraphicsContext gc, Being being) {
		double beingX = being.getX();
		double beingY = being.getY();
		
		int floorX = (int) Math.floor(beingX);
		int x = (beingX - floorX > 0.5) ? floorX : floorX - 1;
		int[] xPos = {x, x + 1};
		int floorY = (int) Math.floor(beingX);
		int y = (beingY - floorY > 0.5) ? floorY : floorY - 1;
		int[] yPos = {y, y + 1};
		
		for (int i : xPos) {
			for (int j : yPos) {
				this.redrawTile(gc, i, j);
			}
		}
		
		for (Being otherBeing : this.beings) {
			double otherBeingX = otherBeing.getX() / this.tileSize;
			double otherBeingY = otherBeing.getY() / this.tileSize;
			if (otherBeingX > x - 0.5 && otherBeingX < x + 2.5 && otherBeingY > y - 0.5 && otherBeingY < y + 2.5) {
				this.rerenderBeing(gc, otherBeing);
			}
		}
		
		this.redrawBeing(gc, being);
	}
}
