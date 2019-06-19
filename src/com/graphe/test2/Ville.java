package com.graphe.test2;

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
	Map<Ville, Integer>villesDest = new HashMap<Ville, Integer>();
//	Map<String, Integer>villesSource = new HashMap<String, Integer>();
	Ville villeOrigine;
	
	Ville(String nom, int x, int y){
		this.nom=nom;
		this.bouton = new JRadioButton(nom);
		this.bouton.setBackground(Color.WHITE);
		this.x=x;
		this.y=y;
	}
	@Override
	public String toString() {
		return String.format("Ville [nom=%s]", nom);
	}
	public void setVilleDest(Ville ville, Integer distance) {
		this.villesDest.put(ville, distance);
	}
//	public void setVilleOrigin(String nom, Integer distance) {
//		this.villesSource.put(nom, distance);
//	}
	public void setVilleOrigine(Ville ville) {
		this.villeOrigine=ville;
	}
	public Ville getVilleOrigine() {
		return this.villeOrigine;
	}
	
}
