class FiguresGeometriques {

	public static void main(String[] args) {
		// A COMPLETER
		   RectangleColore r = new RectangleColore(1.2,3.4,12.3,43.2,4);
	        r.affiche();

	        Cercle c = new Cercle (2.3, 4.5, 12.2);
	        c.affiche();
	        
	        System.out.println(c.estInterieur(3.5, 4.6));
		
	}
}

class Figure {
	//Coordonnées du centre de la figure
	private double x;
	private double y;
	
	public Figure(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void affiche() {
		System.out.println(x + " ; " + y);
	}
	
}

class Rectangle extends Figure {

	private double largeur;
	private double longueur;

	public Rectangle(double x, double y, double largeur, double longueur) {
		super(x, y);
		this.largeur = largeur;
		this.longueur = longueur;
	}

	public double surface() {
		return largeur * longueur;
	}

	public double getLongueur() {
		return longueur;
	}

	public double getLargeur() {
		return largeur;
	}

	public void setLargeur(double l) {
		largeur = l;
	}

	public void setLongueur(double l) {
		longueur = l;
	}
}

class RectangleColore extends Rectangle {
	
	private int couleur;
	
	public RectangleColore(double x, double y, double largeur, double longueur,  int c) {
		super(x, y, largeur, longueur);
		couleur = c;
	}
	
	public int getCouleur() {
		return couleur;
	}
}

class Cercle extends Figure {
	// abscisse du centre
	private double x;
	// ordonnée du centre
	private double y;
	private double rayon;

	public Cercle(double x, double y, double r) {
		super(x,y);
		rayon = r;
	}
	
	public void affiche() {
		super.affiche();
		System.out.println("rayon = " + rayon);
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setCentre(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double surface() {
		return Math.PI * rayon * rayon;
	}

	public boolean estInterieur(double x, double y) {
		return (((x - this.x) * (x - this.x) + (y - this.y) * (y - this.y))
					<= rayon * rayon);
	}

	public double getRayon() {
		return rayon;
	}

	public void setRayon(double r) {
		if (r < 0.0) r = 0.0;
		rayon = r;
	}
}