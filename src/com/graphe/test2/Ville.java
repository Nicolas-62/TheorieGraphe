package com.graphe.test2;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JRadioButton;

public class Ville {
	
	int poids = Integer.MAX_VALUE;
	JRadioButton bouton;
	String nom;
	Map<Ville, Integer>villesDest = new HashMap<Ville, Integer>();
	Ville villeOrigine;
	
	Ville(String nom){
		this.nom=nom;
	}
	@Override
	public String toString() {
		return String.format("Ville [nom=%s]", nom);
	}
	public void setVilleDest(Ville ville, Integer distance) {
		this.villesDest.put(ville, distance);
	}
	public Ville getVilleOrigine() {
		return this.villeOrigine;
	}
	public void setVilleOrigine(Ville ville) {
		this.villeOrigine=ville;
	}
	
	public JRadioButton getBouton() {
		return bouton;
	}
	public void setBouton(JRadioButton bouton) {
		this.bouton = bouton;
	}
}
