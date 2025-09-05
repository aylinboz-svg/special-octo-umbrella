public abstract class DMehrscheibenIsolierverglasung {
	
	protected double d1;
	protected double d2;
	protected double SZR1;
	protected double a;
	protected double b;
	
	public double getD1() {
		return this.d1;
	}

	public double getD2() {
		return this.d2;
	}
	
	public double getDSZR1() {
		return this.SZR1;
	}
	
	public double getA() {
		return this.a;
	}
	
	public double getB() {
		return this.b;
	}
	
	public abstract double[][][] getVerticalLines();
}
