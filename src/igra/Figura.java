package igra;

import java.awt.Color;

public abstract class Figura {
	
	protected Scena scena;
	protected double centerX;
	protected double centerY;
	protected Color color;

	public Figura(Scena s, double centerX, double centerY, Color color) {
		super();
		this.scena = s;
		this.centerX = centerX;
		this.centerY = centerY;
		this.color = color;
		
		scena.dodaj(this);
	}
	
	public synchronized void unisti() {
		scena.ukloni(this);
	}	
	
	public abstract void draw();
	
	public abstract char getOznaka();
	
	public abstract void pomeri(double dx, double dy);

}
