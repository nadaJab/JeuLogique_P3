package jeu;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

//import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import org.apache.log4j.Logger;

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
	//private Logger logger = Logger.getLogger(Jeu.class);

	protected int nbCase = 4;
	protected int nbEssai;
	private Scanner sc = new Scanner(System.in);
	protected Properties properties = new Properties();

	private boolean modeDv;

	private int combiSecrete[] =  new int[getNbCase()];
	private int combiEssai[] =   new int[getNbCase()];

	private int combiSecrete1[] =  new int[getNbCase()];
	private int combiEssai1[] =  new int[getNbCase()];
	private String str = "";

	protected int minMax[][] = new int[getNbCase()][2];

	private boolean comparerRes = false;
	private boolean comparerRes2 = false;


	protected Jeu() {

		InputStream input = null;

		try {

			input = new FileInputStream("resources/config.properties");

			// télècharger le fichier properties 
			properties.load(input);


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
		this.modeDv = Boolean.parseBoolean(properties.getProperty("modeDv"));

	}

	/**
	 * Cette méthode retourne le nombre de case à utiliser pour chaque jeu.
	 * @return nbCase
	 */
	public int getNbCase() {
		return nbCase;
	}

	/**
	 * Cette méthode retourne le nombre d'essai pour chaque jeu.
	 * @return nbEssai
	 */
	public int getNbEssai() {
		return nbEssai;
	}

	public abstract int[][] affinerMaxMin(int[] proposition1) ;

	/**
	 * Cette méthode permet de remplir un tableau qui reprèsente une combinaison  donné par l'ordinateur.
	 * Ensuite, convertir ce tableau en un String.
	 * @return strCombiOrdinateur
	 */
	public int[] genCombiOrdinateur() {
		minMax = affinerMaxMin(combiEssai);
		int tabCombiOrdinateur[] = new int[getNbCase()];

		for(int i = 0; i < tabCombiOrdinateur.length; i++) {
			tabCombiOrdinateur[i] = (int) (Math.random() * (minMax[i][1] - minMax[i][0] +1)) + minMax[i][0];	
		}
		return tabCombiOrdinateur;
	}

	/**
	 * Cette méthode permet joueur humain de saisir un nombre sous forme d'une chaine de caractère.
	 * puis la convertir vers un tableau entier. 
	 * @return strCombiHumain
	 * @throws StrSaisieException 
	 * @throws StrTailleException
	 */
	public int[] saisieCombiHumain() throws StrSaisieException, StrTailleException {

		String strCombiHumain = " ";
		int tabCombiHumain[] = new int[getNbCase()];

		try {

			strCombiHumain = sc.nextLine();

			Integer.parseInt(strCombiHumain);

		}catch (NumberFormatException e) {

			throw new StrSaisieException(strCombiHumain + " Ce n'est pas un nombre !");
		}

		if(strCombiHumain.length() != getNbCase()) {

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
		combiSecrete = genCombiOrdinateur(); 

		System.out.println("L'ordinateur a donné sa combinaison secrète");

		if(modeDv) {

			System.out.println(Arrays.toString(combiSecrete).replaceAll("\\[|\\]|,|\\s", ""));

		}

		System.out.println("Donner votre proposition !! ");

		do {

			try {
				combiEssai = saisieCombiHumain();

				comparerRes = comparer(combiEssai, combiSecrete);

				if(comparerRes == false) {
					String str=resultatComparer(combiEssai, combiSecrete);
					System.out.println("Proposition : " + Arrays.toString(combiEssai).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + str +"\n");
				}

				Essai ++;

			} catch (StrSaisieException e) {
				
				logger.error(e.getMessage());
			}
			catch (StrTailleException e) {

				logger.error(e.getMessage());
			} 

		}while(!(comparerRes ||  Essai > getNbEssai()));
	}

	/**
	 *Cette méthode permet d'afficher le résultat du jeu en mode Challenger.
	 *@return str , une variable de type String
	 */
	public String toStringChallenger() {

		String str = " ";

		if(comparerRes) {
			str =  "Bravo!! Vous avez deviné la combinaison secrète!!\n";
		}
		else if(!comparerRes) {
			str = "Aiie!! Vous avez échoué !!\n"; 
			str += "(Combinaison secrète : " + Arrays.toString(combiSecrete).replaceAll("\\[|\\]|,|\\s", "") + ")";
		}
		return str;
	}

	/**
	 * Cette méthode est spécifique pour le <<mode défenseur>>.
	 * Elle permet à l'ordinateur de deviner la combinaison secrète de joueur humain.
	 **/ 
	public void devinerDefenseur() {

		int Essai = 0;
		boolean saisieOk = false;

		do {
			System.out.println("Donner votre combinaison secrète");
			try {

				combiSecrete = saisieCombiHumain();
				saisieOk = true;

			} catch (StrSaisieException e) {
				logger.error(e.getMessage());
			}
			catch (StrTailleException e) {
				logger.error(e.getMessage());
			}
		}while( !saisieOk );  

		do {

			combiEssai = genCombiOrdinateur();

			comparerRes2 = comparer(combiEssai, combiSecrete);

			if(!comparerRes2) {

				str=resultatComparer(combiEssai, combiSecrete);

				System.out.println("Proposition : " + Arrays.toString(combiEssai).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + str +"\n");			}

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}

			Essai ++;

		}while(!(comparerRes2 ||  Essai > getNbEssai()));

	}

	/**
	 *Cette méthode permet d'afficher le résultat du jeu en mode Defenseur.
	 * @return str , une variable de type String
	 */
	public String toStringDefenseur() {

		String str = " ";

		if(comparerRes2) {
			str = "Aiie!! Vous avez echoué l'ordinateur a trouvé votre combinaison secrète!!\n";
		}
		else if(!comparerRes2) {
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

		int Essai = 0;
		boolean saisieOk = false;
		System.out.println("Donner votre combinaison secrète");

		do {
			try {
				combiSecrete = saisieCombiHumain();
				saisieOk = true;

			} catch (StrSaisieException e) {

				logger.error(e.getMessage());

			}
			catch (StrTailleException e) {

				logger.error(e.getMessage());

			} 
		}while(!saisieOk);

		System.out.println(Arrays.toString(combiSecrete).replaceAll("\\[|\\]|,|\\s", ""));

		combiSecrete1 = genCombiOrdinateur();
		System.out.println("L'ordinateur a donné sa combinaison secrète");

		if(modeDv) {

			System.out.println(Arrays.toString(combiSecrete1).replaceAll("\\[|\\]|,|\\s", ""));

		}

		System.out.println("C'est partie !!");
		do {

			System.out.println((Essai+1) + " eme tours");	

			System.out.println("Donner votre proposition !! ");

			do {
				try {
					combiEssai1 = saisieCombiHumain();
					saisieOk = true;

					comparerRes = comparer(combiEssai1, combiSecrete1);

					if (!comparerRes) {

						str=resultatComparer(combiEssai, combiSecrete);
						System.out.println("Proposition : " + Arrays.toString(combiEssai1).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + str +"\n");

						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {}

						System.out.println("Proposition de l'ordinateur");	

						combiEssai = genCombiOrdinateur();
						comparerRes2 = comparer(combiEssai, combiSecrete);

						if(!comparerRes2) {

							str=resultatComparer(combiEssai, combiSecrete);
							System.out.println("Proposition : " + Arrays.toString(combiEssai).replaceAll("\\[|\\]|,|\\s", "") + " --> Réponse : " + str +"\n");				}
					}

					Essai ++;		
				} catch (StrSaisieException e) {

					logger.error(e.getMessage());
				}
				catch (StrTailleException e) {

					logger.error(e.getMessage());

				} 
			}while(!saisieOk);

		}while(!(comparerRes || comparerRes2 ||  Essai > getNbEssai()));
	}

	/**
	 * Cette méthode permet d'afficher le résultat du jeu en mode Duel.
	 * @return str , une variable de type String
	 */
	public String toStringDuel() {

		String str=" ";

		if(comparerRes && !comparerRes2) {
			str =  "Bravo!! Vous avez deviné la combinaison secrète de l'ordinateur !!\n";
			str += "\n L'ordinateur a échoué !!";
		}

		else if(!comparerRes && comparerRes2) {
			str = "Aiie!! Vous avez echoué, l'ordinateur a trouvé votre combinaison secrète le premier !!\n";
		}

		else if(!comparerRes && !comparerRes2) {
			str = "Aiie!! Vous avez échoué les deux!!\n"; 
			str += "(Combinaison secrète ordinateur : " + Arrays.toString(combiSecrete1).replaceAll("\\[|\\]|,|\\s", "") + ")\n";
			str += "\n(Votre combinaison secrète  : " + Arrays.toString(combiSecrete).replaceAll("\\[|\\]|,|\\s", "") + ")";
		}
		return str;	
	}

}
