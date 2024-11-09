package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends Figura {
	
	private int width;
	private int height;

	public Igrac(Scena s, int width, int height) {
		super(s, s.getWidth()/2, s.getHeight()-20-height/2, Color.BLUE);
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw() {
		Graphics g = scena.getGraphics();
		Color prev = g.getColor();
		g.setColor(color);
		g.fillRect((int)centerX - width/2, (int)centerY - height/2, width, height);
		g.setColor(prev);
	}

	@Override
	public char getOznaka() {
		return 'I';
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	@Override
	public void pomeri(double dx, double dy) {
		centerX = (dx > 0 ? Math.min(scena.getWidth()- width/2 -1, centerX+dx) : Math.max(width/2, centerX+dx));
	}


}
