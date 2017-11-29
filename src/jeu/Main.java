package jeu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	private static Jeu jeu;
	private static Mode mod;
	
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
	 
	 public static void sortir(){
	        System.out.println("Merci et au revoir");
	        System.exit(0);
	    }
	 
	 public static void lancerJeu(Jeu J) {
		 
			Scanner sc = new Scanner(System.in);
	        System.out.println(afficherMode());
	        
         	int mode = mod.getMode(); 
	        
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
	 }
	 
       public static void main (String[] args) {
    	   
    	 Scanner sc = new Scanner(System.in);
    	 System.out.println( menuJeu());
    	 int choix = 0;
    	 
    	 try {
    		 
    	    choix = sc.nextInt();
    	 
    	 }catch (InputMismatchException e) {
         	System.out.println(" Vous devez saisir 1 ou 2 !!");
         }
    	 
         if(choix == 1) {
         	jeu = new PlusMoins();
         	lancerJeu(jeu);
         }	
         else if(choix == 2) {
          	jeu = new Mastermind();
          	lancerJeu(jeu);
         } 	
    }      
}


