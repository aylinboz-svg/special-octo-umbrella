import inf.v3d.obj.*;
import inf.v3d.view.*;

public class LGraphics {

	private DMehrscheibenIsolierverglasung section;
	private double x;
	private double y;

	public LGraphics(DMehrscheibenIsolierverglasung section,BBemessung bemessung) {
		this.section = section;
	}
	
	public double getCenterOffset() {
		double[][][] lines = this.section.getVerticalLines();
		if (lines.length == 0) return 0;
		
		double minX = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		
		for (double[][] line : lines) {
			double x = line[0][0];
			if (x < minX) minX = x;
			if (x > maxX) maxX = x;
		}
		
		return -(minX + maxX) / 2;
	}

	public void moveTo(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void drawVerglasung(ViewerPanel vp) {
	    double[][][] lines = this.section.getVerticalLines();

	    for (double[][] line : lines) {
	        Polyline p = new Polyline();
	        p.addVertex(line[0][0] + x, line[0][1] + y, 0);
	        p.addVertex(line[1][0] + x, line[1][1] + y, 0);
	        vp.addObject3D(p);
	    }
	}
	
	
	public void drawD1(ViewerPanel vp) {
		
		String text = "d";
		
	    double d1   = section.getD1();
	    double SZR1 = section.getDSZR1();
	    double x1   =(-d1-(SZR1/2))*7;
	    double x2   =(-(SZR1/2))*7;
	    double h    = 0.009;
	    double xCenter = 0.5 * (x1 + x2);
	    double textW   = text.length() * 0.72 * h; 
	    double x       = xCenter - textW/2;          

	    vp.addObject3D(new Text(x, -0.026, 0.001, h, text));
	}
	
	public void draw1(ViewerPanel vp) {
		
	    String text = "1";

	    double d1 = section.getD1();
	    double SZR1 = section.getDSZR1();
	    double x1 =(-d1-(SZR1/2))*7;
	    double x2 =(-(SZR1/2))*7;
	    double h = 0.004;
	    double xCenter = (0.5 * (x1 + x2)) + 0.001;
	    double textW   = text.length() * 0.72 * h;
	    double x       = xCenter - textW / 2;
	    double xShift  = 0.005;
	    double yShift  = -0.002;

	    vp.addObject3D(new Text(x + xShift, -0.026 + yShift, 0.001, h, text));
	}

	public void drawDSzr1(ViewerPanel vp) {
		
		String text = "d";

		double SZR1 = section.getDSZR1();
	    double x2 =(-(SZR1/2))*7;
		double x3 =((SZR1/2))*7;
	    double h = 0.009;
	    double xCenter = 0.5 * (x3 + x2);
		double textW = text.length() * 0.72 * h; 
		double x = xCenter - textW / 2;

		vp.addObject3D(new Text(x, -0.026, 0.001, h, text));
	}

	public void draw2(ViewerPanel vp) {
		
	    String text = "SZR1";

		double SZR1 = section.getDSZR1();
	    double x2 =(-(SZR1/2))*7;
		double x3 =((SZR1/2))*7;
	    double h = 0.004;
	    double xCenter = 0.5 * (x3 + x2) + 0.001;
	    double textW   = text.length() * 0.72 * h;
	    double x       = xCenter - textW / 2;
	    double xShift  = 0.009;   
	    double yShift  = -0.002; 
	    
	    vp.addObject3D(new Text(x + xShift, -0.026 + yShift, 0.001, h, text));
	}
	   
	public void drawD2(ViewerPanel vp) {
		
	    String text = "d";

	    double d2 = section.getD2();
	    double SZR1 = section.getDSZR1();
	    double x3 =((SZR1/2))*7;
	    double x4 =((SZR1/2)+d2)*7;
	    double h = 0.009;
	    double xCenter = 0.5 * (x3 + x4);
	    double textW   = text.length() * 0.72 * h;
	    double x       = xCenter - textW/2;

	    vp.addObject3D(new Text(x, -0.026, 0.001, h, text));	
	}

	public void draw3(ViewerPanel vp) {
		
	    String text = "2";

	    double d2 = section.getD2();
	    double SZR1 = section.getDSZR1();
	    double x3 =((SZR1/2))*7;
	    double x4 =((SZR1/2)+d2)*7;
	    double h = 0.004;
	    double xCenter = 0.5 * (x3 + x4) + 0.001;
	    double textW   = text.length() * 0.72 * h;
	    double x       = xCenter - textW / 2;
	    double xShift  = 0.005;  
	    double yShift  = -0.002;

	    vp.addObject3D(new Text(x + xShift, -0.026 + yShift, 0.001, h, text));
	}
	
	public void drawDSzr2(ViewerPanel vp) {
	    if (!(section instanceof GDreifachIsolierverglasung s3)) return;
	   
	    String text = "d";

	    double d2 = section.getD2();
	    double SZR1 = section.getDSZR1();
	    double SZR2 = s3.getSZR2();
	    double x4 =((SZR1/2)+d2)*7;
	    double x5 =((SZR1/2)+d2+SZR2)*7;
	    double h = 0.009;
	    double xCenter = 0.5 * (x4 + x5);
	    double textW   = text.length() * 0.72 * h; 
	    double x       = xCenter - textW/2;

	    vp.addObject3D(new Text(x, -0.026, 0.001, h, text));
	}
	
	public void draw4(ViewerPanel vp) {
		if (!(section instanceof GDreifachIsolierverglasung s3)) return;
	    
		String text = "SZR2";

		double d2 = section.getD2();
		double SZR1 = section.getDSZR1();
		double SZR2 = s3.getSZR2();
		double x4 =((SZR1/2)+d2)*7;
		double x5 =((SZR1/2)+d2+SZR2)*7;
	    double h = 0.004;
	    double xCenter = 0.5 * (x4 + x5) + 0.001;
	    double textW   = text.length() * 0.72 * h;
	    double x       = xCenter - textW / 2;
	    double xShift  = 0.009;   
	    double yShift  = -0.002;  
	    
	    vp.addObject3D(new Text(x + xShift, -0.026 + yShift, 0.001, h, text));
	}

	public void drawD3(ViewerPanel vp) {
	    if (!(section instanceof GDreifachIsolierverglasung s3)) return;
	    
	    String text = "d";

	    double d2 = section.getD2();
	    double d3 = s3.getD3();
	    double SZR1 = section.getDSZR1();
	    double SZR2 = s3.getSZR2();
		double x5 =((SZR1/2)+d2+SZR2)*7;
	    double x6 =((SZR1/2)+d2+SZR2+d3)*7;
	    double h = 0.009;
	    double xCenter = 0.5 * (x5 + x6);
	    double textW   = text.length() * 0.72 * h;  
	    double x       = xCenter - textW/2;

	    vp.addObject3D(new Text(x, -0.026, 0.001, h, text));
	}
	
	public void draw5(ViewerPanel vp) {
		 if (!(section instanceof GDreifachIsolierverglasung s3)) return;
		 
		 String text = "3";
		    
		 double d2 = section.getD2();
		 double d3 = s3.getD3();
		 double SZR1 = section.getDSZR1();
		 double SZR2 = s3.getSZR2();
		 double x5 =((SZR1/2)+d2+SZR2)*7;
		 double x6 =((SZR1/2)+d2+SZR2+d3)*7;
		 double h = 0.004;
		 double xCenter = 0.5 * (x5 + x6) + 0.001;
		 double textW   = text.length() * 0.72 * h;
		 double x       = xCenter - textW / 2;
		 double xShift  = 0.005; 
		 double yShift  = -0.002; 
		 
		 vp.addObject3D(new Text(x + xShift, -0.026 + yShift, 0.001, h, text));
	}
}
