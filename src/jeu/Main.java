package jeu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	private static Jeu jeu;
	//private static Scanner sc = new Scanner(System.in);
	private static int mode;
	private static int choix;	
	private static int choixRejouer;
	private static boolean saisieOk = true; 

	public static String menuJeu() {
		String str;
		str = "*****************************\n";
		str += "********BIENVENU AU JEU******\n";
		str += "*****************************\n";
		str += "A quel jeu vous souhaitez jouer ? \n";
		str += "Choix 1 : Recherche +/- \n";
		str += "Choix 2 : Mastermind \n";

		return str;
	} 


	public static String afficherMode() {
		String str;
		str =  "*****************************\n";
		str +=  "Mode -1- : Mode challenger \n";
		str += "Mode -2- : Mode Défenseur \n";
		str += "Mode -3- : Mode Duel \n";
		str +=  "*****************************";

		return str;	
	}

	public static String afficherFinPartie() {
		String str;
		str =  "*****************************\n";
		str +=  "Vous voulez :  \n";
		str += " 1 : Rejouer au jeu \n";
		str += " 2 : Lancer un autre jeu \n";
		str += " 3 : Quitter l'application \n";
		str +=  "*****************************";

		return str; 
	}

	public static void sortir(){
		System.out.println("Merci et au revoir");
		System.exit(0);
	}


	public static int saisieMode() throws IllegalArgumentException {
		Scanner cc = new Scanner(System.in);
		mode= cc.nextInt();

		if (mode < 1 || mode > 3 ) {
			throw new IllegalArgumentException("Vous devez saisir un chiffre : 1, 2 ou 3 !!"); }
		return mode;
	}

	public static void choixMode(Jeu J) {

		System.out.println(afficherMode());

		do {
			try {
				saisieOk = true;	
				saisieMode();

				switch(mode) {

				case 1 :
					J.devinerChallenger();
					System.out.println(J.toStringChallenger());

					break;

				case 2 :
					J.devinerDefenseur();
					System.out.println(J.toStringDefenseur());

					break;

				case 3 :
					J.devinerDuel();
					System.out.println(J.toStringDuel());
					break;

				}

			}catch (IllegalArgumentException e) {
				saisieOk = false; 
				System.out.println(e.getMessage());
			}
			catch (InputMismatchException e) {
				saisieOk = false;
				System.out.println("Erreur de saisi");
			}

		}while(!saisieOk);
	}

	/**
	 * Cette méthode teste la saisie de l'utilisateur qui doit saisir soit le chiffre 1 ou 2
	 * @return choix
	 * @throws IllegalArgumentException
	 * @throws InputMismatchException
	 */
	public static int saisieCorrecte() throws IllegalArgumentException {
		Scanner sc = new Scanner(System.in);
		choix = sc.nextInt();

		if (choix < 1 || choix > 2 ) {
			throw new IllegalArgumentException("Vous devez saisir 1 ou 2 !!"); }
		return choix;
	}

	public static void lancerJeu() {

		System.out.println( menuJeu());

		do	 {

			try {
				saisieOk = true; 
				saisieCorrecte();

			}catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				saisieOk = false; 
			}
			catch (InputMismatchException e) {
				saisieOk = false;
				System.out.println("Erreur de saisi");
			}
		}while(!saisieOk);


		if(choix == 1) {
			jeu = new PlusMoins();
			choixMode(jeu);

		}	
		else if(choix == 2) {
			jeu = new Mastermind();
			choixMode(jeu);
		} 	
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {}

		finPartie(); 
	} 

	public static void finPartie() {

		Scanner sc = new Scanner(System.in);
		System.out.println(afficherFinPartie());
		choixRejouer = sc.nextInt();

		switch(choixRejouer) {
		case 1 :
			if(choix == 1) {
				jeu = new PlusMoins();
				choixMode(jeu);
			}	
			else if(choix == 2) {
				jeu = new Mastermind();
				choixMode(jeu);
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}

			finPartie();
			break;

		case 2 :
			lancerJeu();
			break;

		case 3 :
			sortir();
			break;
		}
	}

	public static void main (String[] args) {

		lancerJeu();
	}      
}


