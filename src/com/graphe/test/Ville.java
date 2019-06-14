package com.graphe.test;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JRadioButton;

public class Ville {
	
	int poids = Integer.MAX_VALUE;
	JRadioButton bouton;
	int x;
	int y;
	String nom;
	Map<String, Integer>villesDest = new HashMap<String, Integer>();
	Map<String, Integer>villesSource = new HashMap<String, Integer>();
	
	Ville(String nom, int x, int y){
		this.nom=nom;
		this.bouton = new JRadioButton(nom);
		this.bouton.setBackground(Color.WHITE);
		this.x=x;
		this.y=y;
	}
	public void setVilleDest(String nom, Integer distance) {
		this.villesDest.put(nom, distance);
	}
	public void setVilleOrigin(String nom, Integer distance) {
		this.villesSource.put(nom, distance);
	}	
	
}
