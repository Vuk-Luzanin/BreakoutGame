package igra;

import java.awt.Color;

public abstract class AktivnaFigura extends Figura implements Runnable {
	
	protected int period;			//ms
	protected Thread nit = new Thread(this);
	protected boolean work = false; 
	
	public AktivnaFigura(Scena s, double centerX, double centerY, Color color, int p) { 
		super(s, centerX, centerY, color);
		this.period = p;
	}

	public synchronized void pokreni() {
		work = true;
		notifyAll();
	}
	
	public synchronized void zaustavi() {
		if (nit != null)
			nit.interrupt();
	}
	
	public synchronized void unisti() {
		super.unisti();
		zaustavi();
	}	
	

}
