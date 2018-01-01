package jeu;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class Jeu {

	protected static Logger logger =  LogManager.getLogger(Main.class);

	protected int nbCase;
	protected int nbEssai;

	private Scanner sc = new Scanner(System.in);
	protected Properties properties = new Properties();

	private boolean modeDv = true; //Variable booléenne pour activer ou non le mode développeur


	private int combiSecreteOrdi[] ;
	protected int propositionOrdi[] ;

	protected int combiSecreteHumain[] ;
	private int propositionHumain[] ;


	/**
	 * Constructeur de la classe Jeu 
	 * Lecture du fichier config.properties.
	 */
	protected Jeu() {

		InputStream input = null;

		try {

			input = new FileInputStream("resources/config.properties");

			// télècharger le fichier properties 
			properties.load(input);

			this.modeDv = Boolean.parseBoolean(properties.getProperty("modeDv"));
			/**
			 * log
			 */
			logger.info("Lecture du fichier properties avec succée");

		} catch (final IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();

				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}  
	}

	/**
	 * Cette méthode permet de générer la combinaison de l'ordinateur.
	 */
	public abstract int[] genCombiOrdinateur();

	/**
	 * Cette méthode permet au joueur humain de saisir sa combinaison 
	 * @return strCombiHumain
	 * @throws StrSaisieException 
	 * @throws StrTailleException 
	 */
	public int[] saisieCombiHumain() throws StrSaisieException, StrTailleException {

		String strCombiHumain = "";
		int tabCombiHumain[] = new int[nbCase];

		try {

			strCombiHumain = sc.nextLine();

			Integer.parseInt(strCombiHumain);

		}catch (NumberFormatException e) {

			throw new StrSaisieException(strCombiHumain + " Ce n'est pas un nombre !");
		}

		if(strCombiHumain.length() != nbCase) {

			throw new StrTailleException(" Le nombre de case est différent de celui attendu ! ");
		}
		
		//Conversion de la strCombiHumain en un tableau
		for(int i = 0; i < strCombiHumain.length(); i++) {

			tabCombiHumain[i] = Character.getNumericValue(strCombiHumain.charAt(i));

		}
		return tabCombiHumain;
	}

	/**
	 * Cette méthode permet de comparer entre deux combinaisons.
	 * @param combiEssai[]
	 * @param combiSecrete[]
	 * @return comparerRes, boolean.
	 */    
	public boolean comparer(int combiEssai[], int combiSecrete[]) {

		boolean comparerRes = false;

		if(Arrays.equals(combiEssai, combiSecrete)){

			comparerRes = true;
		}

		return comparerRes;
	}

	/**
	 * Cette méthode retourne un résultat de type String de la comparaison 
	 * de deux combinaisons.   
	 * @param combiEssai
	 * @param combiSecrete
	 * @return variable de type String
	 */
	public abstract String resultatComparer(int combiEssai[], int combiSecrete[]);

	/**
	 * Cette méthode est spécifique pour le <<mode challenger>>.
	 * Elle permet au joueur humain de deviner la combinaison secrète de l'ordinateur.
	 */
	public void devinerChallenger() {

		int Essai = 0;
		boolean comparerRes = false;
		boolean saisieOk = false;

		combiSecreteOrdi = genCombiOrdinateur(); 

		/**
		 * log
		 */
		logger.info("Combinaison secrète : " + Arrays.toString(combiSecreteOrdi).replaceAll("\\[|\\]|,|\\s", ""));

		System.out.println("L'ordinateur a donné sa combinaison secrète");

		if(modeDv) {

			System.out.println(Arrays.toString(combiSecreteOrdi).replaceAll("\\[|\\]|,|\\s", ""));

		}

		System.out.println("Donner votre proposition !! ");

		do {
			do {
				try {

					propositionHumain = saisieCombiHumain();
					saisieOk = true;

					comparerRes = comparer(propositionHumain, combiSecreteOrdi);

					String str=resultatComparer(propositionHumain, combiSecreteOrdi);
					System.out.println("Proposition : " + Arrays.toString(propositionHumain).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + str +"\n");

					/**
					 * log
					 */
					logger.info("Proposition : " + Arrays.toString(propositionHumain).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + str +"\n");

					Essai ++;

				} catch (StrSaisieException e) {

					System.out.println(e.getMessage());
					logger.error(e.getMessage());
				}
				catch (StrTailleException e) {

					System.out.println(e.getMessage());
					logger.error(e.getMessage());
				} 

			}while( !saisieOk );  //jusqu'à une saisie sans erreur

		}while(!(comparerRes ||  Essai > nbEssai)); //jusqu'à un résultat de comparaison vrai ou un nombre d'essai atteint.

		// affichage du résultat
		System.out.println(toStringChallenger(comparerRes));

		/**
		 *log 
		 */
		logger.info(toStringChallenger(comparerRes));
	}

	/**
	 *Cette méthode permet d'afficher le résultat du jeu en mode Challenger.
	 *@return str , une variable de type String
	 */
	public String toStringChallenger(boolean comparerRes) {

		String str = " ";

		if(comparerRes) {
			str =  "Bravo!! Vous avez deviné la combinaison secrète!!\n";
		}
		else if(!comparerRes) {
			str = "Aiie!! Vous avez échoué !!\n"; 
			str += "(Combinaison secrète : " + Arrays.toString(combiSecreteOrdi).replaceAll("\\[|\\]|,|\\s", "") + ")";
		}
		return str;
	}

	/**
	 * Cette méthode est spécifique pour le <<mode défenseur>>.
	 * Elle permet à l'ordinateur de deviner la combinaison secrète de joueur humain.
	 **/ 
	public void devinerDefenseur() {

		String str="";
		boolean comparerRes = false;
		int Essai = 0;
		boolean saisieOk = false;

		do {
			System.out.println("Donner votre combinaison secrète");
			try {

				combiSecreteHumain = saisieCombiHumain();
				saisieOk = true;

				/**
				 * log
				 */
				logger.info("Combinaison secrète : " + Arrays.toString(combiSecreteHumain).replaceAll("\\[|\\]|,|\\s", ""));

			} catch (StrSaisieException e) {

				System.out.println(e.getMessage());
				logger.error(e.getMessage());
			}
			catch (StrTailleException e) {

				System.out.println(e.getMessage());
				logger.error(e.getMessage());
			}

		}while( !saisieOk );  //répété jusqu'à une saisie sans erreur

		do {

			propositionOrdi = genCombiOrdinateur();

			comparerRes = comparer(propositionOrdi, combiSecreteHumain);
			str = resultatComparer(propositionOrdi, combiSecreteHumain);

			System.out.println("Proposition : " + Arrays.toString(propositionOrdi).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + str +"\n");		

			/**
			 * log
			 */
			logger.info("Proposition : " + Arrays.toString(propositionOrdi).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + str +"\n");

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}

			Essai ++;

		}while(!(comparerRes ||  Essai > nbEssai)); //jusqu'à un résultat de comparaison vrai ou un nombre d'essai atteint.

		// affichage du résultat
		System.out.println(toStringDefenseur(comparerRes));

		/**
		 *log 
		 */
		logger.info(toStringChallenger(comparerRes));
	}

	/**
	 *Cette méthode permet d'afficher le résultat du jeu en mode Defenseur.
	 * @return str , une variable de type String
	 */
	public String toStringDefenseur(boolean comparerRes) {

		String str = " ";

		if(comparerRes) {
			str = "Aiie!! Vous avez echoué l'ordinateur a trouvé votre combinaison secrète!!\n";
		}
		else if(!comparerRes) {
			str = "Bravo!! Vous avez gagné, l'ordinateur n'a pas trouvé votre combinaison secrète!!\n";
		}
		return str;
	}
	
	/**
	 * Cette méthode permet de sauvegarder le résultat du comparaison
	 * entre la combiSecreteHumain et la propositionOrdi.
	 * Elle permet en <mode duel> d'éviter la confusion entre le résultat de comparaison (combiecreteOrdi, combiEsaiHumain)
	 * et celle voulue. Dans le but d'améliorer l’intelligence artificielle de l'ordinateur.    
	 * @param strResuOrdi
	 */
	public abstract void setStrComparer(String strResuOrdi);

	/**
	 * Cette méthode est spécifique pour le <<mode duel>>.
	 * Elle permet à l'ordinateur de deviner la combinaison secrète de joueur humain 
	 * et aussi elle permet au joueur humain de deviner la combinaison secrète de l'ordinateur.
	 * Le premier qui trouve la combinaison secrète de l'autre gagne. 
	 * @param combiSecrete , donné par le joueur humain
	 * @param combiSecrete1, donné par lejoueur ordinateur
	 * @param combiEssai, proposition du joueur ordinateur
	 * @param combiEssai1, proposition du joueur humain
	 * @param saisieOk, variable boolean pour tester si le joueur humain a saisie une proposition sans erreur 
	 * @exception StrSaisieException, StrTailleException 
	 **/
	public void devinerDuel() {

		String str1 = "";
		String strResuOrdi = "";
		int Essai = 0;
		boolean saisieOk = false;
		boolean comparerRes = false;
		boolean comparerRes2 = false;

		System.out.println("Donner votre combinaison secrète");

		do {
			try {
				combiSecreteHumain = saisieCombiHumain();
				saisieOk = true;

				/**
				 * log
				 */
				logger.info("Combinaison secrète du joueur: " + Arrays.toString(combiSecreteHumain).replaceAll("\\[|\\]|,|\\s", ""));

			} catch (StrSaisieException e) {

				System.out.println(e.getMessage());
				logger.error(e.getMessage());

			}
			catch (StrTailleException e) {

				System.out.println(e.getMessage());
				logger.error(e.getMessage());
			} 

		}while(!saisieOk);

		combiSecreteOrdi = genCombiOrdinateur();
		System.out.println("L'ordinateur a donné sa combinaison secrète");

		/**
		 * log
		 */
		logger.info("Combinaison secrète de l'ordinateur : " + Arrays.toString(combiSecreteOrdi).replaceAll("\\[|\\]|,|\\s", ""));

		if(modeDv) {

			System.out.println(Arrays.toString(combiSecreteOrdi).replaceAll("\\[|\\]|,|\\s", ""));
		}

		System.out.println("C'est parti !!");

		do {
			System.out.println((Essai+1) + " tour");
			System.out.println("Donner votre proposition !! ");

			logger.info((Essai+1) + " tour");

			do {
				try {
					propositionHumain = saisieCombiHumain();
					saisieOk = true;

					comparerRes = comparer(propositionHumain,combiSecreteOrdi);

					str1 = resultatComparer(propositionHumain, combiSecreteOrdi);
					System.out.println("Proposition : " + Arrays.toString(propositionHumain).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + str1 +"\n");

					/**
					 * log
					 */
					logger.info("Proposition du joueur: " + Arrays.toString(propositionHumain).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + str1 +"\n");

					if(!comparerRes) {

						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {}

						setStrComparer(strResuOrdi);
						propositionOrdi = genCombiOrdinateur();
						comparerRes2 = comparer(propositionOrdi, combiSecreteHumain);


						System.out.println("Proposition de l'ordinateur");	
						strResuOrdi = resultatComparer(propositionOrdi, combiSecreteHumain);
						System.out.println("Proposition : " + Arrays.toString(propositionOrdi).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + strResuOrdi +"\n");				

						/**
						 * log
						 */
						logger.info("Proposition de l'ordinateur: " + Arrays.toString(propositionOrdi).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + strResuOrdi +"\n");

						Essai++;
					}	
				} catch (StrSaisieException e) {

					System.out.println(e.getMessage());
					logger.error(e.getMessage());
				}
				catch (StrTailleException e) {

					System.out.println(e.getMessage());
					logger.error(e.getMessage());
				} 

			}while(!saisieOk);

		}while(!(comparerRes || comparerRes2 ||  Essai > nbEssai));

		System.out.println(toStringDuel(comparerRes, comparerRes2));
		logger.info(toStringDuel(comparerRes, comparerRes2));
	}

	/**
	 * Cette méthode permet d'afficher le résultat du jeu en mode Duel.
	 * @return str , une variable de type String
	 */
	public String toStringDuel(boolean comparerRes, boolean comparerRes2) {

		String str="";

		if(comparerRes && !comparerRes2) {
			str =  "Bravo!! Vous avez deviné la combinaison secrète de l'ordinateur !!\n";
			str += "\nL'ordinateur a échoué !!";
		}

		else if(!comparerRes && comparerRes2) {
			str = "Aiie!! Vous avez echoué, l'ordinateur a trouvé votre combinaison secrète le premier !!\n";
		}

		else if(!comparerRes && !comparerRes2) {
			str = "Aiie!! Vous avez échoué les deux!!\n"; 
			str += "(Combinaison secrète ordinateur : " + Arrays.toString(combiSecreteOrdi).replaceAll("\\[|\\]|,|\\s", "") + ")\n";
			str += "\n(Votre combinaison secrète  : " + Arrays.toString(combiSecreteHumain).replaceAll("\\[|\\]|,|\\s", "") + ")";
		}
		return str;	
	}

}

class StrSaisieException extends NumberFormatException {

	private static final long serialVersionUID = 1L;

	public StrSaisieException(String msg) {
		super(msg);
	}

	public StrSaisieException() {
		super(" Il faut un nombre entier !!");
	}
}

class StrTailleException extends Exception {

	private static final long serialVersionUID = 1L;

	public StrTailleException(String msg) {
		super(msg);
	}

	public StrTailleException() {
		super(" Le nombre de case est différent de celui attendu ! ");
	}
}
