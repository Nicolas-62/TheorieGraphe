package com.graphe.test;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
/*
 * Programme qui calcule le chemin optimal entre deux villes selon l'algorithme de Ford.
 */
public class TheorieGrapheBuilder {
	/* compteur incrémenté au clic sur une ville */
	private int nbBoutonClic = 0;
	private JFrame frame;
	private Ville villeDepart;
	private Ville villeArrivee;
	/* liste qui référence l'ensemble des villes instanciées */
	private List<Ville> listVilles = new ArrayList<Ville>();
	private JTextField inputResultat = new JTextField();
	private JTextArea txtErreur = new JTextArea();

	/*
	 * @ Fonction : Alogithme de Ford qui calcule le chemin le plus court
	 * 
	 * @ Param : Ville, ville d'origine
	 */
	public void calcChemin(Ville ville) {
		/* si deux villes on été selectionnées */
		if (nbBoutonClic == 2) {
			/* si la ville traitée n'est pas la ville d'arrivée */
			if (!ville.equals(villeArrivee)) {
				/*
				 * on parcours les villes de destination de la ville passée en paramètre celles
				 * ci sont associées à une valeur ; la distance entre ces deux villes.
				 */
				for (Map.Entry<Ville, Integer> villeDest : ville.villesDest.entrySet()) {
					/*
					 * si le poids de la ville de destination moins le poids de la ville traitée est
					 * supérieur à la valeur de la distance entre ces deux villes, on affecte à la
					 * ville de destination le poids de la ville traitée additionnée de la valeur de
					 * cette distance.
					 */
					if (villeDest.getKey().poids - ville.poids > villeDest.getValue()) {

						villeDest.getKey().poids = villeDest.getValue() + ville.poids;
						/*
						 * On attribut à la ville de destination comme ville d'origine, la ville passée
						 * en paramètre
						 */
						villeDest.getKey().setVilleOrigine(ville);
						/*
						 * debogage : affiche le nouveau poids de la ville de destination et sa ville
						 * d'origine
						 */
//						System.out.println(villeDest.getKey().nom + " " + (villeDest.getKey().poids));
//						System.out.println("ville origine : " + villeDest.getKey().getVilleOrigine());
						/*
						 * Pour chaque ville de destination (celles ci possèdent aussi des villes de
						 * destination) on effectue la même opération.
						 */
						calcChemin(villeDest.getKey());
					}
				}
			}
			/* si deux villes n'ont pas été sélectionnées on affiche un message d'erreur */
		} else {
			txtErreur.setVisible(true);
		}
	}

	/*
	 * @ Fonction : Affiche les villes traversées pour aller à la ville d'arrivée
	 * 
	 * @ Param : Ville, ville traversée
	 */
	public void afficherChemin(Ville ville) {
		try {
			if (ville != null) {
				System.out.println(ville.nom);
				ville.bouton.setBackground(Color.RED);
				afficherChemin(ville.villeOrigine);
			}
		} catch (StackOverflowError e) {
			System.out.println("boucle infini");
		}
	}

	/*
	 * @ Fonction : Gère la selection des villes au clic des radio boutons qui leur
	 * sont associés
	 * 
	 * @ Param : Ville, ville associée au radio bouton
	 */
	public void setVilleClic(Ville ville) {
		/* On ne peut pas deselectionner un bouton */
		if (!ville.bouton.isSelected()) {
			ville.bouton.setSelected(true);
		} else {
			if (nbBoutonClic == 1) {
				villeArrivee = ville;
				nbBoutonClic++;
			} else if (nbBoutonClic == 0) {
				villeDepart = ville;
				villeDepart.poids = 0;
				nbBoutonClic = 1;
				/*
				 * si déjà deux villes ont été sélectionnées (par exemple après un calcul) on
				 * remet les paramètres en position initial, on selectionne la ville cliquée
				 * comme nouvelle ville de départ
				 */
			} else {
				for (Ville v : listVilles) {
					v.bouton.setSelected(false);
					v.bouton.setBackground(Color.WHITE);
					v.poids = Integer.MAX_VALUE;
					v.setVilleOrigine(null);
				}
				txtErreur.setVisible(false);
				ville.bouton.setSelected(true);
				villeDepart = ville;
				villeDepart.poids = 0;
				villeArrivee = null;
				nbBoutonClic = 1;
			}
		}
		System.out.println(nbBoutonClic);
		System.out.println("ville depart : " + villeDepart);
		System.out.println("ville arrivee : " + villeArrivee);
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
		// * Paramètres de la fenêtre : */
		frame.setSize(860, 720);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		/***********
		 * Creation des villes et de leur bouton sur la carte
		 *************************/

		// amiens
		Ville amiens = new Ville("amiens");
		JRadioButton btAmiens = new JRadioButton("Amiens");
		btAmiens.setBounds(299, 65, 69, 23);
		btAmiens.setBackground(Color.WHITE);
		btAmiens.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(amiens);
			}
		});
		amiens.setBouton(btAmiens);
		listVilles.add(amiens);
		frame.getContentPane().add(btAmiens);
		// paris
		Ville paris = new Ville("paris");
		JRadioButton btParis = new JRadioButton("Paris");
		btParis.setBounds(344, 185, 69, 23);
		btParis.setBackground(Color.WHITE);
		btParis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(paris);
			}
		});
		paris.setBouton(btParis);
		listVilles.add(paris);
		frame.getContentPane().add(btParis);
		// reims
		Ville reims = new Ville("reims");
		JRadioButton btReims = new JRadioButton("Reims");
		btReims.setBounds(430, 106, 69, 23);
		btReims.setBackground(Color.WHITE);
		btReims.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(reims);
			}
		});
		reims.setBouton(btReims);
		listVilles.add(reims);
		frame.getContentPane().add(btReims);
		// troyes
		Ville troyes = new Ville("troyes");
		JRadioButton btTroyes = new JRadioButton("Troyes");
		btTroyes.setBounds(413, 242, 69, 23);
		btTroyes.setBackground(Color.WHITE);
		btTroyes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(troyes);
			}
		});
		troyes.setBouton(btTroyes);
		listVilles.add(troyes);
		frame.getContentPane().add(btTroyes);
		// Lille
		Ville lille = new Ville("lille");
		JRadioButton btLille = new JRadioButton("Lille");
		btLille.setBackground(Color.WHITE);
		btLille.setBounds(400, 7, 55, 23);
		btLille.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(lille);
			}
		});
		lille.setBouton(btLille);
		listVilles.add(lille);
		frame.getContentPane().add(btLille);
		// Metz
		Ville metz = new Ville("metz");
		JRadioButton btMetz = new JRadioButton("Metz");
		btMetz.setBackground(Color.WHITE);
		btMetz.setBounds(529, 119, 55, 23);
		btMetz.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(metz);
			}
		});
		metz.setBouton(btMetz);
		listVilles.add(metz);
		frame.getContentPane().add(btMetz);
		// Strasbourg
		Ville strasbourg = new Ville("Strasbourg");
		JRadioButton btStrasbourg = new JRadioButton("Strasbourg");
		btStrasbourg.setBackground(Color.WHITE);
		btStrasbourg.setBounds(552, 173, 81, 23);
		btStrasbourg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(strasbourg);
			}
		});
		strasbourg.setBouton(btStrasbourg);
		listVilles.add(strasbourg);
		frame.getContentPane().add(btStrasbourg);
		// Rouen
		Ville rouen = new Ville("rouen");
		JRadioButton btRouen = new JRadioButton("Rouen");
		btRouen.setBackground(Color.WHITE);
		btRouen.setBounds(328, 119, 69, 23);
		btRouen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(rouen);
			}
		});
		rouen.setBouton(btRouen);
		listVilles.add(rouen);
		frame.getContentPane().add(btRouen);
		// leHavre
		Ville leHavre = new Ville("leHavre");
		JRadioButton btHavre = new JRadioButton("Le Havre");
		btHavre.setBackground(Color.WHITE);
		btHavre.setBounds(191, 95, 69, 23);
		btHavre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(leHavre);
			}
		});
		leHavre.setBouton(btHavre);
		listVilles.add(leHavre);
		frame.getContentPane().add(btHavre);
		// brest
		Ville brest = new Ville("brest");
		JRadioButton btBrest = new JRadioButton("Brest");
		btBrest.setBackground(Color.WHITE);
		btBrest.setBounds(10, 162, 69, 23);
		btBrest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(brest);
			}
		});
		brest.setBouton(btBrest);
		listVilles.add(brest);
		frame.getContentPane().add(btBrest);
		// lorient
		Ville lorient = new Ville("lorient");
		JRadioButton btLorient = new JRadioButton("Lorient");
		btLorient.setBackground(Color.WHITE);
		btLorient.setBounds(10, 263, 69, 23);
		btLorient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(lorient);
			}
		});
		lorient.setBouton(btLorient);
		listVilles.add(lorient);
		frame.getContentPane().add(btLorient);
		// saintNazaire
		Ville saintNazaire = new Ville("saintNazaire");
		JRadioButton btSaintNazaire = new JRadioButton("Saint-Nazaire");
		btSaintNazaire.setBackground(Color.WHITE);
		btSaintNazaire.setBounds(46, 300, 89, 23);
		btSaintNazaire.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(saintNazaire);
			}
		});
		saintNazaire.setBouton(btSaintNazaire);
		listVilles.add(saintNazaire);
		frame.getContentPane().add(btSaintNazaire);
		// rennes
		Ville rennes = new Ville("rennes");
		JRadioButton btRennes = new JRadioButton("Rennes");
		btRennes.setBackground(Color.WHITE);
		btRennes.setBounds(125, 196, 69, 23);
		btRennes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(rennes);
			}
		});
		rennes.setBouton(btRennes);
		listVilles.add(rennes);
		frame.getContentPane().add(btRennes);
		// nantes
		Ville nantes = new Ville("nantes");
		JRadioButton btNantes = new JRadioButton("Nantes");
		btNantes.setBackground(Color.WHITE);
		btNantes.setBounds(178, 300, 69, 23);
		btNantes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(nantes);
			}
		});
		nantes.setBouton(btNantes);
		listVilles.add(nantes);
		frame.getContentPane().add(btNantes);
		// leMans
		Ville leMans = new Ville("leMans");
		JRadioButton btLeMans = new JRadioButton("Le Mans");
		btLeMans.setBackground(Color.WHITE);
		btLeMans.setBounds(267, 251, 69, 23);
		btLeMans.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(leMans);
			}
		});
		leMans.setBouton(btLeMans);
		listVilles.add(leMans);
		frame.getContentPane().add(btLeMans);
		// orleans
		Ville orleans = new Ville("orleans");
		JRadioButton btOrleans = new JRadioButton("orleans");
		btOrleans.setBackground(Color.WHITE);
		btOrleans.setBounds(282, 213, 69, 23);
		btOrleans.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(orleans);
			}
		});
		orleans.setBouton(btOrleans);
		listVilles.add(orleans);
		frame.getContentPane().add(btOrleans);
		// dijon
		Ville dijon = new Ville("dijon");
		JRadioButton btDijon = new JRadioButton("Dijon");
		btDijon.setBackground(Color.WHITE);
		btDijon.setBounds(439, 291, 69, 23);
		btDijon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(dijon);
			}
		});
		dijon.setBouton(btDijon);
		listVilles.add(dijon);
		frame.getContentPane().add(btDijon);
		// lyon
		Ville lyon = new Ville("lyon");
		JRadioButton btLyon = new JRadioButton("Lyon");
		btLyon.setBackground(Color.WHITE);
		btLyon.setBounds(509, 429, 55, 23);
		btLyon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(lyon);
			}
		});
		lyon.setBouton(btLyon);
		listVilles.add(lyon);
		frame.getContentPane().add(btLyon);
		// clermontFerrand
		Ville clermontFerrand = new Ville("clermontFerrand");
		JRadioButton btClermontFerrand = new JRadioButton("Clermont-Ferrand");
		btClermontFerrand.setBackground(Color.WHITE);
		btClermontFerrand.setBounds(299, 386, 109, 23);
		btClermontFerrand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(clermontFerrand);
			}
		});
		clermontFerrand.setBouton(btClermontFerrand);
		listVilles.add(clermontFerrand);
		frame.getContentPane().add(btClermontFerrand);
		// poitiers
		Ville poitiers = new Ville("poitiers");
		JRadioButton btPoitiers = new JRadioButton("Poitiers");
		btPoitiers.setBackground(Color.WHITE);
		btPoitiers.setBounds(285, 349, 69, 23);
		btPoitiers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(poitiers);
			}
		});
		poitiers.setBouton(btPoitiers);
		listVilles.add(poitiers);
		frame.getContentPane().add(btPoitiers);
		// laRochelle
		Ville laRochelle = new Ville("laRochelle");
		JRadioButton btLaRochelle = new JRadioButton("La Rochelle");
		btLaRochelle.setBackground(Color.WHITE);
		btLaRochelle.setBounds(87, 385, 89, 23);
		btLaRochelle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(laRochelle);
			}
		});
		laRochelle.setBouton(btLaRochelle);
		listVilles.add(laRochelle);
		frame.getContentPane().add(btLaRochelle);
		// bordeaux
		Ville bordeaux = new Ville("bordeaux");
		JRadioButton btBordeaux = new JRadioButton("Bordeaux");
		btBordeaux.setBackground(Color.WHITE);
		btBordeaux.setBounds(125, 466, 81, 23);
		btBordeaux.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(bordeaux);
			}
		});
		bordeaux.setBouton(btBordeaux);
		listVilles.add(bordeaux);
		frame.getContentPane().add(btBordeaux);
		// bayonne
		Ville bayonne = new Ville("bayonne");
		JRadioButton btBayonne = new JRadioButton("Bayonne");
		btBayonne.setBackground(Color.WHITE);
		btBayonne.setBounds(91, 595, 69, 23);
		btBayonne.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(bayonne);
			}
		});
		bayonne.setBouton(btBayonne);
		listVilles.add(bayonne);
		frame.getContentPane().add(btBayonne);
		// toulouse
		Ville toulouse = new Ville("toulouse");
		JRadioButton btToulouse = new JRadioButton("Toulouse");
		btToulouse.setBackground(Color.WHITE);
		btToulouse.setBounds(282, 545, 74, 23);
		btToulouse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(toulouse);
			}
		});
		toulouse.setBouton(btToulouse);
		listVilles.add(toulouse);
		frame.getContentPane().add(btToulouse);
		// perpignan
		Ville perpignan = new Ville("perpignan");
		JRadioButton btPerpignan = new JRadioButton("Perpignan");
		btPerpignan.setBackground(Color.WHITE);
		btPerpignan.setBounds(410, 661, 81, 23);
		btPerpignan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(perpignan);
			}
		});
		perpignan.setBouton(btPerpignan);
		listVilles.add(perpignan);
		frame.getContentPane().add(btPerpignan);
		// annecy
		Ville annecy = new Ville("annecy");
		JRadioButton btAnnecy = new JRadioButton("Annecy");
		btAnnecy.setBackground(Color.WHITE);
		btAnnecy.setBounds(585, 371, 61, 23);
		btAnnecy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(annecy);
			}
		});
		annecy.setBouton(btAnnecy);
		listVilles.add(annecy);
		frame.getContentPane().add(btAnnecy);
		// montpellier
		Ville montpellier = new Ville("montpellier");
		JRadioButton btMontpellier = new JRadioButton("Montpellier");
		btMontpellier.setBackground(Color.WHITE);
		btMontpellier.setBounds(372, 571, 81, 23);
		btMontpellier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(montpellier);
			}
		});
		montpellier.setBouton(btMontpellier);
		listVilles.add(montpellier);
		frame.getContentPane().add(btMontpellier);
		// avignon
		Ville avignon = new Ville("avignon");
		JRadioButton btAvignon = new JRadioButton("Avignon");
		btAvignon.setBackground(Color.WHITE);
		btAvignon.setBounds(435, 531, 69, 23);
		btAvignon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(avignon);
			}
		});
		avignon.setBouton(btAvignon);
		listVilles.add(avignon);
		frame.getContentPane().add(btAvignon);
		// marseille
		Ville marseille = new Ville("marseille");
		JRadioButton btMarseille = new JRadioButton("Marseille");
		btMarseille.setBackground(Color.WHITE);
		btMarseille.setBounds(544, 603, 69, 23);
		btMarseille.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(marseille);
			}
		});
		marseille.setBouton(btMarseille);
		listVilles.add(marseille);
		frame.getContentPane().add(btMarseille);
		// nice
		Ville nice = new Ville("nice");
		JRadioButton btNice = new JRadioButton("Nice");
		btNice.setBackground(Color.WHITE);
		btNice.setBounds(619, 575, 47, 23);
		btNice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVilleClic(nice);
			}
		});
		nice.setBouton(btNice);
		listVilles.add(nice);
		frame.getContentPane().add(btNice);

		/*************** Boutons texte et carte ***************************************/

		/* Carte de fond */
		JLabel carte = new JLabel("");
		carte.setForeground(Color.WHITE);
		carte.setIcon(new ImageIcon("carte.jpeg"));
		carte.setBounds(0, 0, 684, 693);
		frame.getContentPane().add(carte);
		/* background des boutons */
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		panel.setBounds(685, 0, 170, 700);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		/* Bouton pour calculer un chemin */
		JButton btnTrouverChemin = new JButton("Calculer");
		btnTrouverChemin.setBounds(10, 160, 129, 23);
		btnTrouverChemin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (nbBoutonClic != 2) {
					txtErreur.setVisible(true);
				} else {
					villeDepart.poids = 0;
					calcChemin(villeDepart);
					afficherChemin(villeArrivee);
					inputResultat.setText("" + villeArrivee.poids);
				}
			}
		});
		panel.add(btnTrouverChemin);

		/* texte d'instructions */
		JTextArea txtBienvenue = new JTextArea();
		txtBienvenue.setForeground(UIManager.getColor("Button.disabledShadow"));
		txtBienvenue.setBackground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		txtBienvenue.setFont(new Font("Arial", Font.PLAIN, 13));
		txtBienvenue.setText(
				"Bienvenue, \r\nselectionnez deux villes \r\npuis cliquez sur calculer \r\npour obtenir le chemin \r\nle plus court et les\r\nvilles étape.");
		txtBienvenue.setBounds(10, 11, 174, 106);
		panel.add(txtBienvenue);

		/* texte d'erreur */
		txtErreur.setFont(new Font("Arial", Font.PLAIN, 13));
		txtErreur.setBackground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		txtErreur.setForeground(Color.RED);
		txtErreur.setText("Choisissez deux villes !");
		txtErreur.setBounds(10, 128, 174, 21);
		panel.add(txtErreur);
		txtErreur.setVisible(false);

		/* texte distance */
		JLabel txtDistance = new JLabel("Distance : ");
		txtDistance.setForeground(UIManager.getColor("CheckBoxMenuItem.acceleratorSelectionForeground"));
		txtDistance.setFont(new Font("Arial", Font.PLAIN, 13));
		txtDistance.setBounds(10, 230, 63, 14);
		panel.add(txtDistance);
		JLabel txtKm = new JLabel("km");
		txtKm.setFont(new Font("Arial", Font.PLAIN, 13));
		txtKm.setBackground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		txtKm.setForeground(UIManager.getColor("CheckBoxMenuItem.acceleratorSelectionForeground"));
		txtKm.setBounds(136, 230, 48, 14);
		panel.add(txtKm);
		/* distance calculée */
		inputResultat = new JTextField();
		inputResultat.setBounds(73, 227, 53, 20);
		panel.add(inputResultat);
		inputResultat.setColumns(10);

		/* bouton pour recommencer une selection et un calcul de trajet */
		JButton btRecommencer = new JButton("Recommencer");
		btRecommencer.setBounds(10, 194, 129, 23);
		btRecommencer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Ville v : listVilles) {
					v.bouton.setSelected(false);
					v.bouton.setBackground(Color.WHITE);
					v.poids = Integer.MAX_VALUE;
					v.setVilleOrigine(null);
				}
				txtErreur.setVisible(false);
				villeDepart = null;
				villeArrivee = null;
				nbBoutonClic = 0;
			}
		});
		panel.add(btRecommencer);

		/**************
		 * Liaison des villes avec leurs villess de destination.
		 ************************/
		lille.setVilleDest(amiens, 140);
		amiens.setVilleDest(paris, 134);
		amiens.setVilleDest(reims, 168);
		reims.setVilleDest(troyes, 125);
		reims.setVilleDest(lille, 200);
		reims.setVilleDest(metz, 190);
		metz.setVilleDest(strasbourg, 163);
		strasbourg.setVilleDest(troyes, 409);
		troyes.setVilleDest(paris, 178);
		troyes.setVilleDest(dijon, 184);
		troyes.setVilleDest(metz, 252);
		paris.setVilleDest(reims, 144);
		paris.setVilleDest(orleans, 131);
		paris.setVilleDest(leMans, 206);
		rouen.setVilleDest(paris, 234);
		rouen.setVilleDest(amiens, 120);
		leHavre.setVilleDest(rouen, 91);
		leHavre.setVilleDest(rennes, 277);
		brest.setVilleDest(lorient, 134);
		rennes.setVilleDest(lorient, 150);
		rennes.setVilleDest(saintNazaire, 125);
		rennes.setVilleDest(leMans, 154);
		rennes.setVilleDest(brest, 241);
		leMans.setVilleDest(leHavre, 208);
		leMans.setVilleDest(rouen, 210);
		leMans.setVilleDest(poitiers, 203);
		orleans.setVilleDest(troyes, 210);
		orleans.setVilleDest(leMans, 198);
		orleans.setVilleDest(poitiers, 218);
		lorient.setVilleDest(saintNazaire, 145);
		saintNazaire.setVilleDest(nantes, 64);
		nantes.setVilleDest(rennes, 206);
		nantes.setVilleDest(leMans, 182);
		dijon.setVilleDest(strasbourg, 336);
		dijon.setVilleDest(lyon, 196);
		poitiers.setVilleDest(troyes, 418);
		poitiers.setVilleDest(laRochelle, 138);
		poitiers.setVilleDest(bordeaux, 251);
		laRochelle.setVilleDest(leMans, 278);
		laRochelle.setVilleDest(nantes, 137);
		annecy.setVilleDest(dijon, 264);
		annecy.setVilleDest(lyon, 138);
		annecy.setVilleDest(nice, 579);
		lyon.setVilleDest(troyes, 373);
		lyon.setVilleDest(clermontFerrand, 164);
		lyon.setVilleDest(avignon, 228);
		lyon.setVilleDest(nice, 471);
		clermontFerrand.setVilleDest(orleans, 300);
		clermontFerrand.setVilleDest(perpignan, 433);
		bordeaux.setVilleDest(laRochelle, 186);
		bordeaux.setVilleDest(bayonne, 185);
		bordeaux.setVilleDest(clermontFerrand, 369);
		bayonne.setVilleDest(toulouse, 298);
		toulouse.setVilleDest(bordeaux, 245);
		toulouse.setVilleDest(clermontFerrand, 376);
		toulouse.setVilleDest(perpignan, 204);
		perpignan.setVilleDest(bayonne, 494);
		perpignan.setVilleDest(poitiers, 691);
		montpellier.setVilleDest(perpignan, 153);
		montpellier.setVilleDest(clermontFerrand, 333);
		montpellier.setVilleDest(lyon, 302);
		montpellier.setVilleDest(avignon, 95);
		avignon.setVilleDest(annecy, 336);
		avignon.setVilleDest(marseille, 103);
		marseille.setVilleDest(annecy, 422);
		marseille.setVilleDest(montpellier, 169);
		nice.setVilleDest(avignon, 260);
		nice.setVilleDest(marseille, 199);
	}
}
