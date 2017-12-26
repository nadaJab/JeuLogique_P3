package jeu;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	private static Jeu jeu;
	private static int mode;
	private static int choix;	
	private static int choixRejouer;
	private static Scanner sc;
	private static Logger logger1 =  LogManager.getLogger(Main.class);
	
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

	/**
	 * Cette méthode teste la saisie de l'utilisateur qui doit saisir soit : 1 , 2 ou 3 selon le mode choisi
	 * @return  mode, un entier
	 * @throws IllegalArgumentException
	 */
	public static int saisieMode() throws IllegalArgumentException {
		
		sc = new Scanner(System.in);
		mode= sc.nextInt();

		if (mode < 1 || mode > 3 ) {
			throw new IllegalArgumentException("Vous devez saisir un chiffre : 1, 2 ou 3 !!"); }
		return mode;
	}

	/**
	 * Cette méthode permet de lancer le jeu en fonction du mode choisit par le joueur. 
	 * @param J, de type Jeu
	 */
	public static void choixMode(Jeu J) {

		boolean saisiOk = false; 

		do {
			System.out.println(afficherMode());

			try {
				saisieMode();
				saisiOk = true;	

				switch(mode) {

				case 1 :
					J.devinerChallenger();
					
					break;

				case 2 :
					J.devinerDefenseur();

					break;

				case 3 :
					J.devinerDuel();
					break;

				}

			}catch (IllegalArgumentException e) {
				
				System.out.println(e.getMessage());
				logger1.error(e.getMessage());
			}
			catch (InputMismatchException e) {

				System.out.println("Erreur de saisie");
				logger1.error("Erreur de saisie");
			}

		}while(!saisiOk);
	}

	/**
	 * Cette méthode teste la saisie du joueur qui doit saisir soit 1 ou 2 selon le jeu choisi.
	 * @return choix
	 * @throws IllegalArgumentException
	 * @throws InputMismatchException
	 */
	public static int saisieCorrecte() throws IllegalArgumentException {
		sc = new Scanner(System.in);
		choix = sc.nextInt();

		if (choix < 1 || choix > 2 ) {
			throw new IllegalArgumentException("Vous devez saisir 1 ou 2 !!"); }
		return choix;
	}

	/**
	 * Cette méthode permet de lancer le jeu que le joueur a choisit, soit le jeu PlusMoins ou le jeu Mastermind.
	 * @param choix, un entier
	 * @param jeu
	 */
	public static void lancerJeu() {
		boolean saisiOk = false; 

		do	 {
			System.out.println( menuJeu());

			try {
				saisieCorrecte();
				saisiOk = true; 

			}catch (IllegalArgumentException e) {
				
				System.out.println(e.getMessage());
				logger1.error(e.getMessage());
			}
			catch (InputMismatchException e) {

				System.out.println("Erreur de saisie");
				logger1.error("Erreur de saisie");
			}
		}while(!saisiOk);

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

	/**
	 * Cette méthode teste la saisie du joueur qui doit saisir soit 1, 2 ou 3.
	 * @return choixRejouer, un entier
	 * @throws IllegalArgumentException
	 */
	public static int saisiFinPartie() throws IllegalArgumentException {
		sc = new Scanner(System.in);
		choixRejouer= sc.nextInt();

		if (choixRejouer < 1 || choixRejouer > 3 ) {
			throw new IllegalArgumentException("Vous devez saisir un chiffre : 1, 2 ou 3 !!"); }
		return choixRejouer;
	}

	/**
	 * Cette méthode s'affiche à la fin du partie. Elle permet au joueur de lancer une nouvelle partie, 
	 * un nouveau jeu ou de quitter le jeu. 
	 */
	public static void finPartie() {

		boolean saisiOk = false; 

		do {
			System.out.println(afficherFinPartie());
			try {

				saisiFinPartie();
				saisiOk = true;

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

			}catch (IllegalArgumentException e) {
				
				System.out.println(e.getMessage());
				logger1.error(e.getMessage());
			}
			catch (InputMismatchException e) {

				System.out.println("Erreur de saisie");
				logger1.error("Erreur de saisie");
			}

		}while(!saisiOk);
	}


	public static void main (String[] args) {

		lancerJeu();
	}      
}


