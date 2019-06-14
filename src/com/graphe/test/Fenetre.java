package com.graphe.test;

import javax.swing.JFrame;

public class Fenetre extends JFrame{

	public Fenetre() {
		
		this.setTitle("Théorie des graphes");
		this.setSize(687, 700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setContentPane(new PanneauImage());
		this.setVisible(true);
		

	}

}
