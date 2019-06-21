package com.graphe.test;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JRadioButton;

/*
 * @ Object : Ville, ville .
 * @ Param : poids, représente la valeur du poids associé à un sommet, par défaut il est infini.
 * @ Param : nom, nom de la ville.
 * @ Param : villeDest,  Liste des villes de destination possibles et la distance de celles ci.
 * @ Param : villeOrigine, ville par laquelle est passée l'algorithme pour calculer un chemin.
 */
public class Ville {

	int poids = Integer.MAX_VALUE;
	JRadioButton bouton;
	String nom;
	Map<Ville, Integer> villesDest = new HashMap<Ville, Integer>();
	Ville villeOrigine;
	
	/* Constructeur */
	Ville(String nom) {
		this.nom = nom;
	}
	/* description de l'bojet */
	@Override
	public String toString() {
		return String.format("Ville [nom=%s]", nom);
	}
	/* getters et setters */
	public void setVilleDest(Ville ville, Integer distance) {
		this.villesDest.put(ville, distance);
	}

	public Ville getVilleOrigine() {
		return this.villeOrigine;
	}

	public void setVilleOrigine(Ville ville) {
		this.villeOrigine = ville;
	}

	public JRadioButton getBouton() {
		return bouton;
	}

	public void setBouton(JRadioButton bouton) {
		this.bouton = bouton;
	}
}
