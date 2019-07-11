package com.graphe.test;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JRadioButton;

/*
 * @ Object : Ville, ville .
 * @ Param : poids, valeur du poids associé à un sommet, par défaut il est infini.
 * @ Param : bouton,  bouton cliquable présent sur la carte auquel est associé la ville
 * @ Param : nom, nom de la ville.
 * @ Param : villeDest,  Liste des villes de destination possibles et la distance de celles ci.
 * @ Param : villeOrigine, ville par laquelle est passée l'algorithme pour calculer un chemin.
 */
public class Ville {

	public int poids = Integer.MAX_VALUE;
	public JRadioButton bouton;
	public String nom;
	public Map<Ville, Integer> villesDest = new HashMap<Ville, Integer>();
	public Ville villeOrigine;
	
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
