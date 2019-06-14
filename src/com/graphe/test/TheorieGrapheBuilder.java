package com.graphe.test;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;


public class TheorieGrapheBuilder {
	
	private int nbBoutonClic;
	private JFrame frame;
	private Ville villeSource;
	private Ville villeDest;
	private List<Ville> listVilles = new ArrayList<Ville>();
	
	
	public void calcChemin(Ville ville) {
		// on parcour les villes de destination de la ville d'origine
		for(Map.Entry<String, Integer> entry : ville.villesDest.entrySet()) {
			// on recherche les villes de destination présentes dans la liste des villes
			// et on leur associe au poids la ditance correspondante
			int poidsActuel = VilleGetByNom(entry.getKey()).poids;
			// si le poids actuel est > au poids 
			if(poidsActuel > entry.getValue()) {
				VilleGetByNom(entry.getKey()).poids = entry.getValue();
			}
			System.out.println(VilleGetByNom(entry.getKey()).nom+" "+(VilleGetByNom(entry.getKey()).poids));
			calcChemin(VilleGetByNom(entry.getKey()));
			
			
			
		}
			
			
//for (Map.Entry<String, User> entry : map2.entrySet()) {
//	String key = entry.getKey();
//	// getValue() appel de la methode toString de l'objet (qui affiche le prenom
//	// ici)
//	User value = entry.getValue();
//	System.out.println(key + " = " + value);
//}			
	}
	public Ville VilleGetByNom(String nom) {
		for(Ville ville : listVilles) {
			if(ville.nom == nom) {
				return ville;
			}
		}		
		return null;
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
					window.villeSource= window.VilleGetByNom("amiens");
					window.villeSource.poids=0;
					window.calcChemin(window.villeSource);
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
		
		PanneauImage panneauImage =  new PanneauImage();
		frame.setContentPane(panneauImage);
		
		panneauImage.setLayout(null);
		
		// amiens
		// nom : amiens, coordonnées : x = 368px y = 96px
		Ville amiens = new Ville("amiens", 368, 96);
		// position bouton x = 344px y = 106px largeur = 69px hauteur = 23px
		amiens.bouton.setBounds(344,  106,  69, 23);
		// ajout à la liste de villes de destination : le nom de la ville, sa distance par rapport à amiens
		amiens.setVilleDest("paris", 100);
		amiens.setVilleDest("reims", 90);
		amiens.bouton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				nbBoutonClic++;
				villeSource = amiens;
			}
		});
		listVilles.add(amiens);
		panneauImage.add(amiens.bouton);
		
		// paris
		Ville paris = new Ville("paris", 372, 179);
		paris.bouton.setBounds(348, 184, 69, 23);
		paris.setVilleOrigin("amiens", 100);
		paris.setVilleDest("troyes", 150);
		paris.bouton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				nbBoutonClic++;
			}
		});
		listVilles.add(paris);
		panneauImage.add(paris.bouton);
		
		// reims
		Ville reims = new Ville("reims", 457, 148);
		reims.bouton.setBounds(432, 152, 69, 23);
		reims.setVilleOrigin("amiens", 90);
		reims.setVilleDest("troyes", 140);
		reims.setVilleDest("paris", 100);
		reims.bouton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				nbBoutonClic++;
				
			}
		});
		listVilles.add(reims);
		panneauImage.add(reims.bouton);
		
		//troyes
		Ville troyes = new Ville("troyes", 463, 222);
		troyes.bouton.setBounds(426,  222,  69, 23);
		troyes.setVilleOrigin("paris", 150);
		troyes.setVilleOrigin("reims", 140);
		troyes.bouton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				nbBoutonClic++;
				villeDest = troyes;
			}
		});
		listVilles.add(troyes);
		panneauImage.add(troyes.bouton);
		
	}
}
