package igra;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class IgraLoptica extends Frame {
	
	private Scena scena = new Scena();

	public IgraLoptica() {
		super("Igra Loptica");
		setBounds(200, 100, 500, 600);
		setResizable(false);
		add(scena, BorderLayout.CENTER);
		
		requestFocus();
		
		setVisible(true);
		populateWindow();
		
		scena.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Loptica(scena, scena.igrac.centerX, scena.igrac.centerY-scena.igrac.getHeight()/2-5, 10, 10);
				scena.repaint();
			}
		});
		
		scena.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					scena.igrac.pomeri(-10, 0);
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					scena.igrac.pomeri(10, 0);
				} 
				scena.repaint();
			} 
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				scena.zaustavi(); 
				dispose(); 
			} 
		}); 
		
	}
	
	private void populateWindow() {
		int frameWidth = getWidth(); 
		int frameHeight = getWidth(); 
		int ciglaWidth = frameWidth/5-2; 
		int ciglaHeight = frameHeight/20-2; 
		
		for (int i=0; i<3; i++) {
			for (int j=0; j<5; j++) {
				new Cigla(scena, j*frameWidth/5 + ciglaWidth/2+1, i*frameHeight/20 + ciglaHeight/2, 50, ciglaWidth, ciglaHeight);
			}
		}
		scena.igrac = new Igrac(scena, ciglaWidth, ciglaHeight/2);
		(new Loptica(scena, 400.0, 400.0, 10, 10)).pokreni();

		scena.setFocusable(true);
		scena.requestFocus();
		scena.pokreni();
		
	}
	
	
	public static void main(String[] args) {
		new IgraLoptica();
	}
	
	
	

}
