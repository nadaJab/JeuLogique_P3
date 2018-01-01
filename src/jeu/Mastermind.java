package jeu;
import java.util.ArrayList;
import java.util.Arrays;

public class Mastermind extends Jeu{	
	
	private String strComparer = "";
	
	private int n = 10; 
	private int index = 0; 

	private int[] tabIndice;
	private int[] combinaison;
	
	private ArrayList<int[]> listePos;

	public Mastermind() {

		// récupération des nbEssaiMastermind et nbCaseMastermind dans le cas de lecture du fichier config
		// sinon mettre des valeurs par defaults
		if(!properties.isEmpty()) {
			
			this.nbEssai = Integer.parseInt(properties.getProperty("nbEssaiMastermind"));
			this.nbCase = Integer.parseInt(properties.getProperty("nbCaseMastermind"));
			logger.info("Récupération des nombres de cases et d'essai");
		}
		else {
			this.nbEssai = 10;
			this.nbCase = 4;
			
			logger.error("erreur lors de la lecture du fichier properties");
			logger.info("Récupération des nombres de cases et d'essai par défaut");
		}		
	}

	/**
	 * 
	 */
	public int[] genCombiOrdinateur() {
		
		// dans le cas du premier passage dans la fct on initialise notre liste de proposition
		if(listePos == null) {
			
			listePos = new ArrayList<int[]>((int)(Math.pow(nbCase, 10)));
		}
		
		int tabCombiOrdinateur[] = new int[nbCase];

		// si notre liste est vide on la rempli sinon affine notre sélection pour avoir une liste plus précise 
		if(listePos.size() == 0) { 

			creerlistePos(index);
		}
		else {

			affinerListePos();	
		}

		int indiceAlea = (int)(Math.random()*listePos.size()); 	// Choix de l'indice dans l'ensemble des combinaisons (aléatoire)

		tabCombiOrdinateur = listePos.get(indiceAlea);

		return tabCombiOrdinateur;
	}

	/**
	 * Cette méthode permet de créer toutes les combinaisons possibles en fonction
	 * du nombre de cases et de chiffres (entre 0 et 9).
	 * Construction recursive des listes possibles.
	 * @return litePos[][]
	 */
	public void creerlistePos(int index) {

		// initialisation du tableau combinaison
		if(combinaison == null) {
			
			combinaison = new int[nbCase];
		}
		
		if (index >= nbCase) {

			// mettre le contenu du tableau combinaison dans un nouveau tableau pour avoir une nouvelle référence
			// a fin de sauvegarder toutes les possibilitées
			int[] combinaison2 = new int[nbCase];

			System.arraycopy(combinaison, 0, combinaison2, 0, nbCase); 

			listePos.add(combinaison2);

			return;
		}

		for(int i=0 ; i < n; i++) {

			combinaison[index]=i;
			creerlistePos(index+1);
		}
	}

	
	/**
	 * Cette méthode retourne une liste plus affiner en supprimant toutes les propositions qui sont différentes du
	 * résultat précédent. 
	 * @return listePos
	 */
	public void affinerListePos() {

		//Cette variable permet de sauvegerder le contenu de la variable <strComparer> avant qu'il soit modifier.
		String strComparerCombiProp = strComparer; 

		if(!strComparerCombiProp.equals("")) {
			for(int i = 0; i < listePos.size(); i++) {

				//Comparer la proposition de l'ordinateur avec toutes les combinaisons possibles qui existent dans l'ArrayList.
				String strComparerPropPossi = resultatComparer(listePos.get(i),propositionOrdi);  

				if(!strComparerPropPossi.equals(strComparerCombiProp)) {

					// On supprime toutes les combinaisons qui n'ont pas le même résultat de comparaison
					//que la proposition avec la combinaison secrète.
					listePos.remove(i);
					i--;
				}
			}
		}
	}

	/**
	 * Comparaison de deux combinaisons indiquant le nombre de chiffres bien placés et le nombre de chiffres mal placés
	 * @param combiSecrete 
	 * @param combiEssai
	 * @return resultat
	 */
	public String resultatComparer(int combiEssai[], int combiSecrete[]) {

		tabIndice = new int[nbCase]; // tableau d'indice avec "2" si le chiffre est bien placé ou "1" si le chiffre est présent 
		ArrayList<Integer> indiceExist = new ArrayList<Integer>(); //ArrayList pour sauvegarder les indices du tableau des élements trouvés (placé, présent)
		strComparer = "";

		for(int i = 0; i < combiEssai.length ; i++) {

			if(combiEssai[i] == combiSecrete[i]) {   // tester si il y'a des chiffres bien placés
				tabIndice[i] = 2;	
				indiceExist.add(i);
			}
		}

		for(int i = 0; i < combiSecrete.length ; i++) {
			for(int j = 0; j < combiEssai.length ; j++) {

				if( (combiEssai[j] == combiSecrete[i]) && (!indiceExist.contains(j)) && (tabIndice[i] != 2) && (tabIndice[i] != 1) ){

					tabIndice[i] = 1;
					indiceExist.add(j);
				}
			}
		}
		strComparer = TabindiceString();
		return strComparer;
	}


	/**
	 * Cette méthode retourne le résultat de la comparaison entre 
	 * une combinaison secrète et une proposition donnée ( bien placé, présent)
	 * à travers le contenu du tableau indice.
	 * @see resultatComparer() 
	 * @return str
	 */
	public String TabindiceString() {

		int tabVide[] = new int[nbCase];
		String str="";
		String str1 ="";
		String str2 ="";
		int nb=0;
		int nb1=0;

		if(Arrays.equals(tabIndice, tabVide)) {
			str ="Il n'existe aucun chiffre";	
		}
		else {
			for(int i = 0; i < tabIndice.length ; i++) {

				if(tabIndice[i]== 2) {
					nb++;
					str1 = nb + " bien placé(s)";
				}
				else if(tabIndice[i]== 1) {
					nb1++;
					str2 = nb1 + " présent(s) ";	
				}
			}
			str = str1 + " "  + str2;
		}

		return str;
	}

	
	public void setStrComparer(String strResuOrdi) {

		strComparer = strResuOrdi;
	}

} 




