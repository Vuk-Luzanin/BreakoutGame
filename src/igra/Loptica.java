package igra;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.print.Printable;

public class Loptica extends AktivnaFigura {
	
	private int precnik;
	private boolean work = false;
	private double vx = -1 + Math.random()*2;
	private double vy = -1 + Math.random();
	private int taktovi = 0;
	

	public Loptica(Scena s, double centerX, double centerY, int p, int precnik) {
		super(s, centerX, centerY, Color.GREEN, p);
		this.precnik = precnik;
		nit.start();
		work = true;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					work = true;
					while (!work) {
						wait();
					}
				}
				
				izracunajPomeraj();
				taktovi++;
				if (taktovi %100 == 0) {
					vx = 1.1*vx;
					vy = 1.1*vy;
				}
				
				scena.repaint(); 
				Thread.sleep(period); 
			}	
		} catch (InterruptedException e) {}
	}


	private void izracunajPomeraj() {	
		int sirina = scena.getWidth();
		int visina = scena.getHeight();	
		double w = 0, h = 0, cx = 0, cy = 0;
		//okvir
		if (centerX + vx > sirina - precnik/2 || centerX + vx < precnik/2) {
			vx = -vx;
		}
		if (centerY + vy < precnik/2) {
			vy = -vy;
		}
		if (centerY + vy >= visina - precnik/2) {
			unisti();
		}
		w = scena.igrac.getWidth();
		h = scena.igrac.getHeight();
		cx = scena.igrac.centerX;
		cy = scena.igrac.centerY;
		if (vy>0) {
			if (centerY + precnik/2 > cy - h/2  && (centerX >= cx-w/2 && centerX <= cx+w/2)) {
				vy = -vy;
				pomeri(vx,  vy);
				return;
			}
			if (vx>0) {	//desno
				if (centerX+precnik/2 >= cx-w/2 && centerX+precnik/2 <= cx-w/3 && centerY+precnik/2 >= cy-h/2 && centerY-precnik/2 <= cy+h/2) { 		//leva strana
					vx = -vx;
					pomeri(vx, vy);
					return;
				}
			}
			if (vx<0) {	//levo
				if (centerX-precnik/2 <= cx+w/2 && centerX-precnik/2 >= cx+w/3 && centerY+precnik/2 >= cy-h/2 && centerY-precnik/2 <= cy+h/2) {		//desna strana
					vx = -vx;
					pomeri(vx, vy);
					return;
				}
			}
		}
		
		for (int i = scena.figure.size()-1; i>=0; i--) {
			if (scena.figure.get(i) instanceof Cigla) {
				Cigla c = (Cigla) scena.figure.get(i);
				if (c.isPogodjena()) {continue; }
				cx = c.centerX;
				cy = c.centerY;
				w = c.getWidth();
				h = c.getHeight();
				if (vy < 0 && vx > 0) {	 	//ide gore-desno 
					if (centerX+precnik/2 >= cx-w/2 && centerX+precnik/2 <= cx-w/3 && centerY+precnik/2 >= cy-h/2 && centerY-precnik/2 <= cy+h/2) { 		//leva strana
						vx = -vx;
						c.pogodi(); 
					}else 
					if (centerY-precnik/2 <= cy+h/2 && centerY-precnik/2 >= cy-h/2 && centerX+precnik/2 >= cx-w/2 && centerX-precnik/2 <= cx+w/2) { 		//donja strana
						vy = -vy;
						c.pogodi();
					}
				} else
				if (vy < 0 && vx < 0) {		//ide gore-levo
					if (centerX-precnik/2 <= cx+w/2 && centerX-precnik/2 >= cx+w/3 && centerY+precnik/2 >= cy-h/2 && centerY-precnik/2 <= cy+h/2) {		//desna strana
						vx = -vx;
						c.pogodi();
					}else
					if (centerY-precnik/2 <= cy+h/2 && centerY-precnik/2 >= cy-h/2 && centerX+precnik/2 >= cx-w/2 && centerX-precnik/2 <= cx+w/2) { 		//donja strana
						vy = -vy;
						c.pogodi();
					}
				} else
				if (vy > 0 && vx < 0) {		//ide dole-levo
					if (centerX-precnik/2 <= cx+w/2 && centerX-precnik/2 >= cx+w/3 && centerY+precnik/2 >= cy-h/2 && centerY-precnik/2 <= cy+h/2) {		//desna strana
						vx = -vx;
						c.pogodi();
					}else
					if (centerY+precnik/2 >= cy-h/2 && centerY+precnik/2 <= cy+h/2 && centerX+precnik/2 >= cx-w/2 && centerX-precnik/2 <= cx+w/2) {		//gornja strana
						vy = -vy;
						c.pogodi();
					}
				} else
				if (vy > 0 && vx > 0) {		//ide dole-desno
					if (centerX+precnik/2 >= cx-w/2 && centerX+precnik/2 <= cx-w/3 && centerY+precnik/2 >= cy-h/2 && centerY-precnik/2 <= cy+h/2) { 		//leva strana
						vx = -vx;
						c.pogodi(); 
					}else	
					if (centerY+precnik/2 >= cy-h/2 && centerY+precnik/2 <= cy+h/2 && centerX+precnik/2 >= cx-w/2 && centerX-precnik/2 <= cx+w/2) {		//gornja strana
						vy = -vy;
						c.pogodi();
					}
				}
			}
		}
		pomeri(vx,  vy);
	}

	@Override
	public char getOznaka() {
		return 'L';
	}

	@Override
	public void draw() {
		Graphics g = scena.getGraphics();
		Color prev = g.getColor();
		g.setColor(color);
		g.fillOval((int)centerX-precnik/2, (int)centerY-precnik/2, precnik, precnik);
		g.setColor(prev);
	}

	@Override
	public void pomeri(double dx, double dy) {
		centerX += dx;
		centerY += dy;
	}
	
	

}

//prva verzija - losa
/*private void izracunajPomeraj() {	
		int sirina = scena.getWidth();
		int visina = scena.getHeight();	
		if (centerX + vx > sirina - precnik/2 || centerX + vx < precnik/2) {
			vx = -vx;
		}
		if (centerY + vy < precnik/2) {
			vy = -vy;
		}
		if (centerY + vy >= visina - precnik/2) {
			unisti();
		}
		if (centerY + vy > scena.igrac.centerY - scena.igrac.getHeight()/2 - precnik/2 && (centerX + vx >= scena.igrac.centerX-scena.igrac.getWidth()/2- precnik/2 && centerX <= scena.igrac.centerX+scena.igrac.getWidth()/2- precnik/2)) {
			vy = -vy;
		}
		if (centerY + vy + precnik/4 >= scena.igrac.centerY - scena.igrac.getHeight()/2 && centerY + vy + precnik/4 <= scena.igrac.centerY + scena.igrac.getHeight()/2) {
			if (centerX + vx + precnik/4 >= scena.igrac.centerX - scena.igrac.getWidth()/2 && centerX + vx + precnik/4 <= scena.igrac.centerX + scena.igrac.getWidth()/2) {
				vx = -vx;
				//vy = -vy;
			}
		} 
	
		double w = 0, h = 0, cx = 0, cy = 0;
		for (int i = scena.figure.size()-1; i>=0; i--) {
			if (scena.figure.get(i) instanceof Cigla) {
				Cigla c = (Cigla) scena.figure.get(i);
				if (c.isPogodjena()) continue; 
				cx = c.centerX;
				cy = c.centerY; 
				w = c.getWidth();
				h = c.getHeight();
				if (centerX + vx + precnik/4 >= cx - w/2 && centerX + vx + precnik/4 <= cx - w/2 + precnik/4) {				//leva ivica 
					if (centerY + vy + precnik/4 >= cy - h/2 && centerY + vy + precnik/4 <= cy + h/2) {
						vx = -vx;
						c.pogodi();
					}
				}
				if (centerX + vx + precnik/4 <= cx + w/2 && centerX + vx + precnik/4 >= cx + w/2 - precnik/4) {
					if (centerY + vy + precnik/4 >= cy - h/2 && centerY + vy + precnik/4 <= cy + h/2) {
						vx = -vx;
						c.pogodi();
					}
				}
				if (centerY + vy + precnik/4 >= cy - h/2 && centerY + vy + precnik/4 <= cy - h/2 + precnik/4) {
					if (centerX + vx + precnik/4 >= cx - w/2 && centerX + vx + precnik/4 <= cx + w/2) {
						vy = -vy;
						c.pogodi();
					}
				}
				if (centerY + vy <= cy + h/2 && centerY + vy >= cy + h/2 - precnik/4) {
					if (centerX + vx + precnik/4 >= cx - w/2 && centerX + vx + precnik/4 <= cx + w/2) {
						vy = -vy;
						c.pogodi();
					}
				}
				
			}
		}
	
		pomeri(vx,  vy);
	}*/




