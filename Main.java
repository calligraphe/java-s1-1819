package partiel1819;

public class Main {
	private Carte[] cartes;
	private int nbCartes;

	public Main() {
		nbCartes = 5;
		cartes = new Carte[nbCartes]; 	// reserve 5 places
		cartes[0] = new Carte(); 		// initialise la 1e carte
		int i = 1; 						// indice pour la 2e carte
		while (i < nbCartes) { 			// tantque on n'a pas initialisee toutes (4) les cartes
			cartes[i] = new Carte(); 	// creer la prochaine carte
			boolean unique = true; 		// on suppose que la carte creee est unique
			for (int j = 0; j < i; j++) { // on verifie s'il est unique en comparant avec toutes les precedentes
				if (cartes[i].equals(cartes[j])) // si on trouve la meme
					unique = false;
			}
			if (unique) // si la carte creee est unique, on prepare indice pour la prochaine
				i++;
		}
		ordonne(); // trie les cartes
	}
	
	// test constructor
	public Main(Carte c1, Carte c2, Carte c3, Carte c4, Carte c5) {
		nbCartes = 5; cartes = new Carte[nbCartes];
		cartes[0] = c1; cartes[1] = c2; cartes[2] = c3; 
		cartes[3] = c4; cartes[4] = c5; ordonne(); 
	}
	
	private void ordonne() {
		// Bubble sort
		int n = nbCartes - 1;
		while (n > 0) {
			for (int i = 0; i < n; i++) {
				if (cartes[i].compareTo(cartes[i+1]) > 0) {
					Carte tmp = cartes[i];
					cartes[i] = cartes[i+1];
					cartes[i+1] = tmp;
				}
			}
			n--;
		}
	}
	
	public int[] repartitionValeurs() {
		int[] tmp = new int[nbCartes]; // [null, null, null, null, null]
		for (int i = 0; i < nbCartes; i++) 
			tmp[i] = 1; 			// [1, 1, 1, 1, 1]
		
		int i = 0;
		while (i < nbCartes - 1) { 						 // pour chaque carte
			for (int j = i + 1; j < nbCartes; j++) { 	 // comparer avec toutes les prochaines
				if (cartes[i].compareTo(cartes[j]) == 0  // ex: pour [3♠, 5♦, 7♦, 7♣, 13♥]
						&& tmp[i] != 0 && tmp[j] != 0) { // [1, 1, 1, 1, 1]
					tmp[i]++;							 // [1, 1, 2, 1, 1]
					tmp[j]--;							 // [1, 1, 2, 0, 1]
				}
			}
			i++;
		}
		
		// preparation de taille de resultat
		int nbIndices = 0;
		for (int k = 0; k < tmp.length; k++) { 	// ex: pour [3♠, 5♦, 7♦, 7♣, 13♥]
			if (tmp[k] != 0)					// nbIndices est 4, donc
				nbIndices++;					// [null, null, null, null]
		}
		
		// insertion des valeurs
		int[] res = new int[nbIndices];
		i = 0;
		for (int k = 0; k < tmp.length; k++) {
			if (tmp[k] != 0) {					// [null, null, null, null] devient
				res[i] = tmp[k];				// [1, 1, 2, 1]
				i++;
			}
		}
		
		return res;
	}
	
	public boolean estSuite() {
		for (int i = 0; i < nbCartes - 1; i++) 
			if (cartes[i].compareTo(cartes[i+1]) != -1)
				return false;
		return true;
	}
	
	public boolean estCouleur() {
		for (int i = 0; i < nbCartes - 1; i++) 
			if (cartes[i].getCouleur() != cartes[i+1].getCouleur())
				return false;
		return true;
	}
	
	public String figure() {
		if (estSuite() && estCouleur())
			return "quinte flush";
		
		if (repartitionValeurs()[0] == 4 || repartitionValeurs()[1] == 4) // [4, 1] ou [1, 4]
			return "carre";
		
		if (repartitionValeurs().length == 2) // [3, 2] ou [2, 3]
			return "full";
		
		if (estCouleur())
			return "couleur";
		
		if (estSuite())
			return "suite";
		
		for (int i = 0; i < repartitionValeurs().length; i++)
			if (repartitionValeurs()[i] == 3)	// [3, 1, 1] ou [1, 3, 1] ou [1, 1, 3]
				return "brelan";
		
		if (repartitionValeurs().length == 3) // [2, 2, 1] ou [2, 1, 2] ou [1, 2, 2]
			for (int i = 0; i < repartitionValeurs().length; i++)
				if (repartitionValeurs()[i] == 2)
					return "double pair";
		
		for (int i = 0; i < repartitionValeurs().length; i++)
			if (repartitionValeurs()[i] == 2)
				return "pair";
		
		return "carte"; // else
	}
	
	public String toString() {
		String res = "[";
		for (int i = 0; i < nbCartes; i++) {
			res += cartes[i].toString() + ", ";
		}
		return res.substring(0, res.length() - 2) + "] : " + figure();
	}

	
	
	
	// tests
	public static void main(String[] args) {
		// Random
		Main m1 = new Main();
		System.out.println(m1.toString());
		
		// quinte flush
		Carte a1 = new Carte('♥', 5);
		Carte a2 = new Carte('♥', 6);
		Carte a3 = new Carte('♥', 8);
		Carte a4 = new Carte('♥', 9);
		Carte a5 = new Carte('♥', 7);
		System.out.println((new Main(a1, a2, a3, a4, a5)).toString());
		
		// carre
		Carte b1 = new Carte('♥', 5);
		Carte b2 = new Carte('♥', 11);
		Carte b3 = new Carte('♣', 11);
		Carte b4 = new Carte('♣', 11);
		Carte b5 = new Carte('♦', 11);
		System.out.println((new Main(b1, b2, b3, b4, b5)).toString());
		
		// full
		Carte c1 = new Carte('♥', 5);
		Carte c2 = new Carte('♥', 6);
		Carte c3 = new Carte('♣', 6);
		Carte c4 = new Carte('♣', 5);
		Carte c5 = new Carte('♦', 5);
		System.out.println((new Main(c1, c2, c3, c4, c5)).toString());
		
		// couleur
		Carte e1 = new Carte('♥', 5);
		Carte e2 = new Carte('♥', 6);
		Carte e3 = new Carte('♥', 6);
		Carte e4 = new Carte('♥', 11);
		Carte e5 = new Carte('♥', 11);
		System.out.println((new Main(e1, e2, e3, e4, e5)).toString());
		
		// suite
		Carte f1 = new Carte('♥', 5);
		Carte f2 = new Carte('♥', 6);
		Carte f3 = new Carte('♣', 7);
		Carte f4 = new Carte('♣', 8);
		Carte f5 = new Carte('♦', 9);
		System.out.println((new Main(f1, f2, f3, f4, f5)).toString());
		
		// brelan
		Carte g1 = new Carte('♥', 6);
		Carte g2 = new Carte('♥', 6);
		Carte g3 = new Carte('♣', 6);
		Carte g4 = new Carte('♣', 11);
		Carte g5 = new Carte('♦', 10);
		System.out.println((new Main(g1, g2, g3, g4, g5)).toString());
		
		// pair
		Carte h1 = new Carte('♥', 5);
		Carte h2 = new Carte('♥', 6);
		Carte h3 = new Carte('♣', 14);
		Carte h4 = new Carte('♣', 11);
		Carte h5 = new Carte('♦', 11);
		System.out.println((new Main(h1, h2, h3, h4, h5)).toString());
		
		// double pair
		Carte d1 = new Carte('♥', 5);
		Carte d2 = new Carte('♥', 6);
		Carte d3 = new Carte('♣', 6);
		Carte d4 = new Carte('♣', 11);
		Carte d5 = new Carte('♦', 11);
		System.out.println((new Main(d1, d2, d3, d4, d5)).toString());
		
		// carte
		Carte i1 = new Carte('♥', 5);
		Carte i2 = new Carte('♥', 6);
		Carte i3 = new Carte('♣', 8);
		Carte i4 = new Carte('♣', 11);
		Carte i5 = new Carte('♦', 14);
		System.out.println((new Main(i1, i2, i3, i4, i5)).toString());
	}

}
