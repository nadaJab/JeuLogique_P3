package jeu;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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


public abstract class Jeu {

	private static Logger logger =  LogManager.getLogger(Main.class);
	
	protected int nbCase;
	protected int nbEssai;
	
	private Scanner sc = new Scanner(System.in);
	protected Properties properties = new Properties();

	private boolean modeDv = true; //Variable booléenne qui définit le mode développeur

	
	private int combiSecreteOrdi[] ;
	protected int combiEssaiOrdi[] ;

	protected int combiSecreteHumain[] ;
	private int combiEssaiHumain[] ;


	/**
	 * Constructeur qui permet la lecture du fichier conf.properties.
	 */
	protected Jeu() {

		InputStream input = null;

		try {

			input = new FileInputStream("resources/config.properties");

			// télècharger le fichier properties 
			properties.load(input);
			
			this.modeDv = Boolean.parseBoolean(properties.getProperty("modeDv"));
		

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
	 * Cette méthode permet de remplir un tableau qui reprèsente une combinaison  donné par l'ordinateur.
	 */
	public abstract int[] genCombiOrdinateur();

	/**
	 * Cette méthode permet d'enregistrer le résultat du comparaison
	 * entre la combiSecreteHumain et la combiEssaiOrdi.
	 * Elle permet en <mode duel> d'éviter la confusion entre le résultat de comparaison (combiecreteOrdi, combiEsaiHumain)
	 * et celle voulue. Dans le but d'améliorer l’intelligence artificielle de l'ordinateur.    
	 * @param strResuOrdi
	 */
	public abstract void setStrComparer(String strResuOrdi);

	/**
	 * Cette méthode permet joueur humain de saisir un nombre sous forme d'une chaine de caractère.
	 * puis la convertir vers un tableau entier. 
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

		for(int i = 0; i < strCombiHumain.length(); i++) {

			tabCombiHumain[i] = Character.getNumericValue(strCombiHumain.charAt(i));
		}

		return tabCombiHumain;
	}


	/**
	 * Cette méthode permet de comparer entre une combinaison secrète donné par un joueur 
	 * et une proposition donné par l'autre joueur. 
	 * @param combiEssai[]
	 * @param combiSecrete[]
	 * @return elle retourne un boolean.
	 */    
	public boolean comparer(int combiEssai[], int combiSecrete[]) {

		boolean comparerRes = false;

		if(Arrays.equals(combiEssai, combiSecrete)){

			comparerRes = true;
		}

		return comparerRes;
	}

	/**
	 * Cette méthode est abstract.Elle retourne un résultat de type String d'une comparaison 
	 * entre une combinaison secrète et une proposition donnée.   
	 * @param combiEssai
	 * @param combiSecrete
	 * @return str
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

		System.out.println("L'ordinateur a donné sa combinaison secrète");

		if(modeDv) {

			System.out.println(Arrays.toString(combiSecreteOrdi).replaceAll("\\[|\\]|,|\\s", ""));

		}

		System.out.println("Donner votre proposition !! ");

		do {
			do {
				try {

					combiEssaiHumain = saisieCombiHumain();
					saisieOk = true;

					comparerRes = comparer(combiEssaiHumain, combiSecreteOrdi);

					String str=resultatComparer(combiEssaiHumain, combiSecreteOrdi);
					System.out.println("Proposition : " + Arrays.toString(combiEssaiHumain).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + str +"\n");
					
					Essai ++;

				} catch (StrSaisieException e) {

					System.out.println(e.getMessage());
					logger.error(e.getMessage());
				}
				catch (StrTailleException e) {

					System.out.println(e.getMessage());
					logger.error(e.getMessage());
				} 

			}while( !saisieOk );  

		}while(!(comparerRes ||  Essai > nbEssai));

		System.out.println(toStringChallenger(comparerRes));
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

			} catch (StrSaisieException e) {

				System.out.println(e.getMessage());
				logger.error(e.getMessage());
			}
			catch (StrTailleException e) {

				System.out.println(e.getMessage());
				logger.error(e.getMessage());
			}

		}while( !saisieOk );  

		do {

			combiEssaiOrdi = genCombiOrdinateur();

			comparerRes = comparer(combiEssaiOrdi, combiSecreteHumain);
			str = resultatComparer(combiEssaiOrdi, combiSecreteHumain);

			System.out.println("Proposition : " + Arrays.toString(combiEssaiOrdi).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + str +"\n");		

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}

			Essai ++;

		}while(!(comparerRes ||  Essai > nbEssai));

		System.out.println(toStringDefenseur(comparerRes));

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

		if(modeDv) {

			System.out.println(Arrays.toString(combiSecreteOrdi).replaceAll("\\[|\\]|,|\\s", ""));
		}

		System.out.println("C'est parti !!");

		do {
			System.out.println((Essai+1) + " tour");
			System.out.println("Donner votre proposition !! ");

			do {
				try {
					combiEssaiHumain = saisieCombiHumain();
					saisieOk = true;

					comparerRes = comparer(combiEssaiHumain,combiSecreteOrdi);

					str1 = resultatComparer(combiEssaiHumain, combiSecreteOrdi);
					System.out.println("Proposition : " + Arrays.toString(combiEssaiHumain).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + str1 +"\n");

					if(!comparerRes) {
						
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {}

					setStrComparer(strResuOrdi);
					combiEssaiOrdi = genCombiOrdinateur();
					comparerRes2 = comparer(combiEssaiOrdi, combiSecreteHumain);


					System.out.println("Proposition de l'ordinateur");	
					strResuOrdi = resultatComparer(combiEssaiOrdi, combiSecreteHumain);
					System.out.println("Proposition : " + Arrays.toString(combiEssaiOrdi).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + strResuOrdi +"\n");				

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
