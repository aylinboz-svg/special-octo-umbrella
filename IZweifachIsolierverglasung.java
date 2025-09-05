public class IZweifachIsolierverglasung extends DMehrscheibenIsolierverglasung {

	public IZweifachIsolierverglasung(double d1, double d2, double SZR1,double a,double b) {
		this.d1 = d1;
		this.d2 = d2;
		this.SZR1 = SZR1;
		this.a = a;
		this.b = b;
	}

	public double [][][] getVerticalLines(){
		double höhe = 0.10;
		double höhe1 = -0.005;
		double höhe2 = -0.017;
		
		double x1 = (-d1-(SZR1/2))*7;
		double x2 = (-(SZR1/2))*7;
		double x3 = ((SZR1/2))*7;
		double x4 = ((SZR1/2)+d2)*7;

	return new double [][][] {
		{{x1,0},{x1,höhe}},
		{{x2,0},{x2,höhe}},
		{{x3,0},{x3,höhe}},
		{{x4,0},{x4,höhe}},
			
		{{x1,höhe1},{x1,höhe2}},
		{{x2,höhe1},{x2,höhe2}},
		{{x3,höhe1},{x3,höhe2}},
		{{x4,höhe1},{x4,höhe2}},
			
		{{-0.005+x1,-0.011},{0.005+x4,-0.011}},
		};	
	}
	
	public String toString() {
		return "[Zweifachisolierverglasung: Scheibendicke 1 = " + this.d1 + ", Scheibendicke 2 = " + this.d2 + "]";
	}
}
