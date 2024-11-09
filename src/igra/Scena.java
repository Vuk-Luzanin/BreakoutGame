package igra;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.ArrayList;

public class Scena extends Canvas {
	
	ArrayList<Figura> figure = new ArrayList<Figura>();
	Igrac igrac = null;

	public Scena() {}
	
	@Override
	public void paint(Graphics g) {
		for (int i = 0; i<figure.size(); i++) {
			figure.get(i).draw();
		}
	}

	public synchronized void dodaj(Figura f) {
		figure.add(f);
	}
	
	public synchronized Figura dohvatiFiguru(int i) {
		return figure.get(i);
	}
	
	public synchronized boolean ukloni(Figura f) {
		return figure.remove(f);
	}
	
	public void pokreni() {
		for (Figura f : figure) {
			if (f instanceof AktivnaFigura) {
				AktivnaFigura f1 = (AktivnaFigura) f;
				f1.pokreni();
			}
		}
	}
	
	public void zaustavi() {
		for (Figura f : figure) {
			if (f instanceof AktivnaFigura) {
				AktivnaFigura f1 = (AktivnaFigura) f;
				f1.zaustavi();
			}
		}
	}

}
