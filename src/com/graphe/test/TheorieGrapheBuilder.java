package com.graphe.test;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JButton;

public class TheorieGrapheBuilder {

	private int nbBoutonClic=0;
	private JFrame frame;
	private Ville villeDepart;
	private Ville villeArrivee;
	private List<Ville> listVilles = new ArrayList<Ville>();

	/*
	 * @ Param : Ville, ville d'origine
	 */
	public void calcChemin(Ville ville) {
		// on parcours les villes de destination de la ville passée en paramètre
		for (Map.Entry<String, Integer> villeDest : ville.villesDest.entrySet()) {
			/*
			 * On recherche les villes de destination présentes dans la liste des villes
			 */
			Ville villePresente = listVillesGetByNom(villeDest.getKey());
			/*
			 * si son poids actuel est > a la valeur de la distance entre la ville passée en
			 * paramètre et la ville de destination, on affecte à ce poids la valeur de
			 * cette distance.
			 */
			if (villePresente.poids > villeDest.getValue()) {
				villePresente.poids = villeDest.getValue();
				/*
				 * On attribut à la ville de destination comme ville d'origine, la ville passée
				 * en paramètre
				 */
				villePresente.setVilleOrigine(ville);
				System.out.println(listVillesGetByNom(villeDest.getKey()).nom + " "
						+ (listVillesGetByNom(villeDest.getKey()).poids));
				/*
				 * Pour chaque ville de destination (celles ci possèdent aussi des villes de
				 * destination) on effectue la même opération.
				 */
				calcChemin(listVillesGetByNom(villeDest.getKey()));
			}
		}
		afficherChemin(villeArrivee);
	}
	public void afficherChemin(Ville ville) {
		ville.bouton.setBackground(Color.RED);
		afficherChemin(ville.villeOrigine);
	}

	public Ville listVillesGetByNom(String nom) {
		for (Ville ville : listVilles) {
			if (ville.nom == nom) {
				return ville;
			}
		}
		return null;
	}
	public void setVilleClic(Ville ville) {
		System.out.println(nbBoutonClic);
		if(nbBoutonClic==1) {
			villeArrivee=ville;
			nbBoutonClic++;
		}else if(nbBoutonClic==0){
			villeDepart=ville;
			villeDepart.poids=0;
			nbBoutonClic=1;
		}else {
			for(Ville v : listVilles) {
				v.bouton.setSelected(false);
				v.poids=Integer.MAX_VALUE;
			}
			ville.bouton.setSelected(true);
			villeDepart=ville;
			villeDepart.poids=0;
			nbBoutonClic=1;
		}		
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TheorieGrapheBuilder window = new TheorieGrapheBuilder();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TheorieGrapheBuilder() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		// taille de la fenêtre 687
		frame.setSize(687, 700);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		PanneauImage panneauImage = new PanneauImage();
		frame.setContentPane(panneauImage);

		panneauImage.setLayout(null);

		// amiens
		// nom : amiens, coordonnées : x = 368px y = 96px
		Ville amiens = new Ville("amiens", 368, 96);
		// position bouton x = 344px y = 106px largeur = 69px hauteur = 23px
		amiens.bouton.setBounds(344, 106, 69, 23);
		// ajout à la liste de villes de destination : le nom de la ville, sa distance
		// par rapport à amiens
		amiens.setVilleDest("paris", 100);
		amiens.setVilleDest("reims", 90);
		amiens.bouton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVilleClic(amiens);
			}
		});
		listVilles.add(amiens);
		panneauImage.add(amiens.bouton);

		// paris
		Ville paris = new Ville("paris", 372, 179);
		paris.bouton.setBounds(348, 184, 69, 23);
//		paris.setVilleOrigin("amiens", 100);
		paris.setVilleDest("troyes", 150);
		paris.bouton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVilleClic(paris);
			}
		});
		listVilles.add(paris);
		panneauImage.add(paris.bouton);

		// reims
		Ville reims = new Ville("reims", 457, 148);
		reims.bouton.setBounds(432, 152, 69, 23);
//		reims.setVilleOrigin("amiens", 90);
		reims.setVilleDest("troyes", 140);
		reims.setVilleDest("paris", 100);
		reims.bouton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVilleClic(reims);
			}
		});
		listVilles.add(reims);
		panneauImage.add(reims.bouton);

		// troyes
		Ville troyes = new Ville("troyes", 463, 222);
		troyes.bouton.setBounds(426, 222, 69, 23);
//		troyes.setVilleOrigin("paris", 150);
//		troyes.setVilleOrigin("reims", 140);
		troyes.bouton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVilleClic(troyes);
			}
		});
		listVilles.add(troyes);
		panneauImage.add(troyes.bouton);
		
		JButton btnTrouverChemin = new JButton("Trouver chemin");
		btnTrouverChemin.setBounds(10, 11, 110, 23);
		btnTrouverChemin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				calcChemin(villeDepart);
			}
		});		
		panneauImage.add(btnTrouverChemin);

	}
}
