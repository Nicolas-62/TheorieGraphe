package com.graphe.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;


public class Panneau extends JPanel {
	
	// Cette methode est appelée automatiquement
	public void paintComponent(Graphics g) {
		this.setBackground(Color.BLUE);
		int x1 = this.getWidth()/8;
		int y1 = this.getHeight()/4;
		int x2 = this.getWidth()/2;
		int y2 = this.getHeight()/4;		
		g.fillOval(x1, y1, this.getHeight()/4, this.getHeight()/4);
		g.drawOval(x2,y2, this.getHeight()/4, this.getHeight()/4);
		//x1, y1, width, height
		g.drawRect(x1+100, y2+100, 20, 20);
		//x1, y1, width, height, arcWidth, arcHeight
		g.drawRoundRect(x1+150, y2+100, 30, 30, 30, 30);
		g.drawLine(0, 0, this.getWidth(), this.getHeight()/2);// xdepart, ydepart, xfin, yfin
	    int x[] = {20, 30, 50, 60, 60, 50, 30, 20};
	    int y[] = {30, 20, 20, 30, 50, 60, 60, 50};
	    g.drawPolygon(x, y, 8); // 8 = x.length()
	    int xl[] = {0, 100, 150, 160, 160, 150, 130, 120};
	    int yl[] = {0, 20, 20, 30, 50, 60, 60, 50};	    
	    g.drawPolyline(xl,yl,8);
	    g.drawString("Salut gros naze ! ", 60, 60);// string, x, y
	    Font font = new Font("Courier", Font.BOLD, 20);
	    g.setFont(font);
	    g.setColor(Color.red);
	    g.drawString("Héhé !!!", 60, 20);// string, x, y
	}

}
