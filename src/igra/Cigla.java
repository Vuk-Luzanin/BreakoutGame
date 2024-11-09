package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Cigla extends AktivnaFigura {
	
	private int width;
	private int height;
	private boolean pogodjena = false;

	public Cigla(Scena s,  double centerX,  double centerY, int p, int width, int height) {
		super(s, centerX, centerY, Color.RED, p);
		this.width = width;
		this.height = height;
		nit.start();
	}

	@Override
	public void draw() {
		Graphics g = scena.getGraphics();
		Color prev = g.getColor();
		g.setColor(color);
		g.fillRect((int)centerX - width/2, (int)centerY - height/2, width, height);
		g.setColor(prev);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public synchronized void pogodi() {
		color = Color.GRAY;
		pogodjena = true;
		notifyAll();
	}
	
	public synchronized boolean isPogodjena() {
		return pogodjena;
	}

	@Override
	public char getOznaka() {
		return 'C';
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (!pogodjena || !work) {
						wait();
					}
				}
				if (isPogodjena()) {
					pomeri(0,  5);
				}
				if (centerY - height/2 > scena.getHeight()) unisti(); 
				scena.repaint(); 
				Thread.sleep(period); 
			}	
		} catch (InterruptedException e) {}
	}

	@Override
	public void pomeri(double dx, double dy) {
		centerX += dx;
		centerY += dy;
	}


}





