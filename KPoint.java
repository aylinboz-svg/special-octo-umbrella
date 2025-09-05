public class KPoint {
	private double x;
	private double y;

	public KPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public String toString() {
		return "Punkt{x=" + this.x + ", y=" + this.y + "}";
	}
}
