package partiel1819;

public class Carte {
	private int valeur; // [2, 14]
	private char couleur; // ♦, ♣, ♥, ♠
	
	// Methode random() est static, car on a un acces directe: Math.random(), pas besoin d'instaciation
	// Elle est public, car on peut avoir un acces a partir de n'importe quelle classe (pas besoin d'etre un sous-classe)

	public Carte() {
		// Initialisation de valeur
		int minVal = 2;
		int maxVal = 14;
		valeur = minVal + (int)(Math.random() * ((maxVal - minVal) + 1));
		
		// Initialisation de couleur
		int minCouleur = 1;
		int maxCouleur = 4;
		int c = minCouleur + (int)(Math.random() * ((maxCouleur - minCouleur) + 1));
		switch (c) {
			case 1: couleur = '♦';
					break;
			case 2: couleur = '♣';
					break;
			case 3: couleur = '♥';
					break;
			case 4: couleur = '♠';
					break;
		}
	}

	public Carte(char couleur, int valeur) {
		if (valeur < 2 || valeur > 14 || 
				(couleur != '♦' && couleur != '♣' && couleur != '♥' && couleur != '♠'))
			throw new IllegalArgumentException();
		this.valeur = valeur;
		this.couleur = couleur;
	}
	
	public int getValeur() { return valeur; }
	public char getCouleur() { return couleur; }
	
	public boolean equals(Object obj) {
		if (!(obj instanceof Carte))
			throw new IllegalArgumentException();
		return ((Carte) obj).getValeur() == valeur
				&& ((Carte) obj).getCouleur() == couleur;
	}
	
	public int compareTo(Carte c) { return valeur - c.getValeur(); }
	public String toString() { return "" + valeur + couleur; }
	
	
	
	// tests
	public static void main(String[] args) {
		Carte c1 = new Carte();
		Carte c2 = new Carte();
		System.out.println(c1.toString());
		System.out.println(c2.toString());
		System.out.println(c1.compareTo(c2));
		System.out.println(c1.equals(c2));
	}

}
