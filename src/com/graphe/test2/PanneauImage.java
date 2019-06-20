package com.graphe.test2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanneauImage extends JPanel{
	
	public void paintComponent(Graphics g) {
	   
	    
//	        g.drawImage(img, 0, 0, this);
	        //Pour une image de fond
	        
	        g.drawLine(368, 96, 370, 175);
	        Font font = new Font("Courier", Font.BOLD, 20);
		    g.setFont(font);
		    g.setColor(Color.red);
		    g.drawLine(368, 96, 370, 175);
		    //Pour une image de fond
	        //g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	       		
	}
}
