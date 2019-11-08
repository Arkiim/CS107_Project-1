/* Dans ce programme, les 2 réctangles sont encapsulés dans un objet
 * de type Terrain qui s'occupe de calculer et d'afficher la surface
 * totale. Le mot-clé private a été utilisé le plus possible dans
 * toutes les classes. La classe Rectangle met aussi Ã  disposition des
 * méthodes get et set pour ses variables (il s'agit d'un service que
 * le programmeur de la classe peut offrir Ã  ses utilisateurs s'il le
 * juge utile).
 */

class Surfaces {

	public static void main(String[] args) {
		// Construction d'un terrain:
		Terrain t = new Terrain(1.0, 2.0, 3.0, 4.0);
		t.afficherSurfaceTotale();
	}

}

class Terrain {

	private Rectangle r1;
	private Rectangle r2;

	public Terrain(double l1, double h1, double l2, double h2) {
		// Construction des 2 rectangles dÃ©finissant le terrain:
		r1 = new Rectangle(l1, h1);
		r2 = new Rectangle(l2, h2);
	}

	private double calculerSurfaceTotale() {
		return r1.calculerSurface() + r2.calculerSurface();
	}

	public void afficherSurfaceTotale() {
		double surfaceTotale = calculerSurfaceTotale();
		System.out.println("La surface totale est " + surfaceTotale);
	}
}

class Rectangle {

	private double largeur;
	private double hauteur;

	public Rectangle(double largeur, double hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
	}

	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}

	public double getLargeur() {
		return largeur;
	}

	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}

	public double getHauteur() {
		return hauteur;
	}

	public double calculerSurface() {
		return (largeur * hauteur);
	}
}
