public class BBemessung{
	
	private DMehrscheibenIsolierverglasung mehrscheiben;


asd
	private double a;
	private double b;
	private double d1;
	private double d2;
	private double d3;
	private double SZR1;
	private double SZR2;
	
	private double Bv;
	
	private double E;
	private double fk;
	
	private double Lastfall2;
	private double Lastfall3;
	private double Lastfall4;
	private double Lastfall5;
	
	private double WinddruckWk;
	private double WindsogWk;
	
	public BBemessung(DMehrscheibenIsolierverglasung ms, double elastizitaetsmodul, double festigkeit) {
	        this.a    = ms.getA();
	        this.b    = ms.getB();
	        this.d1   = ms.getD1();
	        this.d2   = ms.getD2();
	        this.SZR1 = ms.getDSZR1();
	        this.E    = elastizitaetsmodul;
	        this.fk   = festigkeit;
	        if (ms instanceof GDreifachIsolierverglasung d) {
	            this.d3   = d.getD3();
	            this.SZR2 = d.getSZR2();
	        } else {
	            this.d3   = 0;
	            this.SZR2 = 0;
	        }
	}
	
	public double berechneBv() {
		double B1 = (a/b);
		
		if (B1 == 1.0) {
			this.Bv = 0.0194;
		} else if (B1 == 0.9) {
			this.Bv = 0.0237;
		} else if (B1 == 0.8) {
			this.Bv = 0.0288;
		}else if (B1 == 0.7) {
			this.Bv = 0.0350;
		}else if (B1 == 0.6) {
			this.Bv = 0.0421;
		}else if (B1 == 0.5) {
			this.Bv = 0.0501;
		}else if (B1 == 0.4) {
			this.Bv = 0.0587;
		}else if (B1 == 0.3) {
			this.Bv = 0.0676;
		}else if (B1 == 0.2) {
			this.Bv = 0.0767;
		}else if (B1 == 0.1) {
			this.Bv = 0.0857;
		}else interpoliereBv(B1);
		
		return Bv;
	}
	
	// Bv
	private void interpoliereBv(double B1) {	
	double[]B1Liste = {1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1};
	double[]BvListe = {0.0194,0.0237,0.0288,0.0350,0.042,0.0501,0.0587,0.0676,0.0767, 0.0857};
	
	for (int i = 0; i < B1Liste.length-1; i++) {
	if (B1 > B1Liste[i+1] && B1 <= B1Liste[i]) {
			this.Bv = BvListe[i] + ((B1 - B1Liste[i])/(B1Liste[i+1]-B1Liste[i])) * (BvListe[i+1]-BvListe[i]);
			break;
			}
		}
	}
	
	// Querschnitt
	public double berechneA() {
		double A = a * b;
        return A;
    }
	
	// Volumen Scheibe1
	public double berechneVolumenD1() {
		double A = this.berechneA();
		double Bv = this.berechneBv();


		double v1 = ((A) * Bv * Math.pow((a), 4))/(E * Math.pow((d1), 3));
		return v1;
	}
	
	// Volumen Scheibe2
	public double berechneVolumenD2() {
		double A = this.berechneA();
		double Bv = this.berechneBv();
		double v2 = (A * Bv * Math.pow(a, 4))/(E * Math.pow(d2, 3));
		return v2;
	}
	
	// Volumen Scheibe2
	public double berechneVolumenD3() {
		double A = this.berechneA();
		double Bv = this.berechneBv();
		double v3 = (A * Bv * Math.pow(a, 4))/(E * Math.pow(d3, 3));
		return v3;
	}

	// Scheibenzwischenraum1
	public double Scheibenzwischenraumvolumen1() {
		double VSZR1 = a * b * SZR1;
		return VSZR1;
	}
	
	// Scheibenzwischenraum2
	public double Scheibenzwischenraumvolumen2() {
		double VSZR2 = a * b * SZR2;
		return VSZR2;
	}
	
	// alpha1
	public double alpha1() {
		double v1 = this.berechneVolumenD1();
		double VSZR1 = this.Scheibenzwischenraumvolumen1();
		double alpha1 = (v1 * 100)/VSZR1;
		return alpha1;
	}
	
	// alpha1plus
	public double alpha1plus() {
		double v2 = this.berechneVolumenD2();
		double VSZR1 = this.Scheibenzwischenraumvolumen1();
		double alpha1plus = (v2 * 100)/VSZR1;
		return alpha1plus;
	}
	
	// alpha2
	public double alpha2() {
		double v2 = this.berechneVolumenD2();
		double VSZR2 = this.Scheibenzwischenraumvolumen2();
		double alpha2 = (v2 * 100)/VSZR2;
		return alpha2;
	}
	
	// alpha2plus
	public double alpha2plus() {
		double v3 = this.berechneVolumenD3();
		double VSZR1 = this.Scheibenzwischenraumvolumen1();
		double alpha2plus = (v3 * 100)/VSZR1;
		return alpha2plus;
	}
	
	// phi1
	public double phi1() {
		double alpha1 = this.alpha1();
		double alpha1plus = this.alpha1plus();
		double phi1 = 1/(1+ alpha1 + alpha1plus);
		return phi1;
	}
	
	// phi2
	public double phi2() {
		double alpha2 = this.alpha2();
		double alpha2plus = this.alpha2plus();
		double phi2 = 1/(1+ alpha2 + alpha2plus);
		return phi2;
	}
	
	// beta
	public double beta() {
		double phi1 = this.phi1();
		double alpha1plus = this.alpha1plus();
		double phi2 = this.phi2();
		double alpha2 = this.alpha2();
		double beta = 1 - phi1 * alpha1plus * phi2 * alpha2;
		return beta;
	}
	
	//Lastfall 2
	public double Lastfall2() {
		double Lastfall2 = this.Lastfall2;
		return Lastfall2;
	}
	public void setLastfall2(double lastfall2) {
	    this.Lastfall2 = lastfall2;
	}
	
	// Lastfall 3
	public double Lastfall3() {
		double Lastfall3 = this.Lastfall3;
		return Lastfall3;
	}
	public void setLastfall3(double lastfall3) {
	    this.Lastfall3 = lastfall3;
	}
		
	// Lastfall 4
	public double Lastfall4() {
		double Lastfall4 = this.Lastfall4;
		return Lastfall4;
	}
	public void setLastfall4(double lastfall4) {
	    this.Lastfall4 = lastfall4;
	}
		
	// Lastfall 5
	public double Lastfall5() {
		double Lastfall5 = this.Lastfall5;
		return Lastfall5;
	}
	public void setLastfall5(double lastfall5) {
	    this.Lastfall5 = lastfall5;
	}	
	// Lastfall 7
	public double WinddruckWk() {
		double WinddruckWk = this.WinddruckWk; 
		return WinddruckWk;
	}
	
	public void setWinddruckWk(double winddruckWk) {
	    this.WinddruckWk = winddruckWk;
	}
	// Lastfall 8
	public double WindsogWk() {
		double WindsogWk = this.WindsogWk;
		return WindsogWk;
	}
	
	public void setWindsogWk(double windsogWk) {
	    this.WindsogWk = windsogWk;
	}
	
	// Lastkombination deltaP1
	// für Lastfall 2
	public double deltaP12() {
		double deltaP12;
		if (d3 > 0) {
			// Dreifach-Isolierverglasung
			double phi1 = this.phi1();
			double phi2 = this.phi2();
			double alpha1plus = this.alpha1plus();
			double beta = this.beta();
			deltaP12 = Lastfall2 * phi1 * ((1 + phi2 * alpha1plus) / beta);
		} else {
			// Zweifach-Isolierverglasung (vereinfachte Formel)
			double phi1 = this.phi1();
			deltaP12 = Lastfall2 * phi1;
		}
		return deltaP12;
	}
	
	// Lastkombination deltaP1
	// für Lastfall 3
	public double deltaP13() {
		double deltaP13;
		if (d3 > 0) {
			// Dreifach-Isolierverglasung
			double phi1 = this.phi1();
			double phi2 = this.phi2();
			double alpha1plus = this.alpha1plus();
			double beta = this.beta();
			deltaP13 = Lastfall3 * phi1 * ((1 + phi2 * alpha1plus) / beta);
		} else {
			// Zweifach-Isolierverglasung (vereinfachte Formel)
			double phi1 = this.phi1();
			deltaP13 = Lastfall3 * phi1;
		}
		return deltaP13;
	}
	
	// Lastkombination deltaP1
	// für Lastfall 4
	public double deltaP14() {
		double deltaP14;
		if (d3 > 0) {
			// Dreifach-Isolierverglasung
			double phi1 = this.phi1();
			double phi2 = this.phi2();
			double alpha1plus = this.alpha1plus();
			double beta = this.beta();
			deltaP14 = Lastfall4 * phi1 * ((1 + phi2 * alpha1plus) / beta);
		} else {
			// Zweifach-Isolierverglasung (vereinfachte Formel)
			double phi1 = this.phi1();
			deltaP14 = Lastfall4 * phi1;
		}
		return deltaP14;
	}
	
	// Lastkombination deltaP1
	// für Lastfall 5
	public double deltaP15() {
		double deltaP15;
		if (d3 > 0) {
			// Dreifach-Isolierverglasung
			double phi1 = this.phi1();
			double phi2 = this.phi2();
			double alpha1plus = this.alpha1plus();
			double beta = this.beta();
			deltaP15 = Lastfall5 * phi1 * ((1 + phi2 * alpha1plus) / beta);
		} else {
			// Zweifach-Isolierverglasung (vereinfachte Formel)
			double phi1 = this.phi1();
			deltaP15 = Lastfall5 * phi1;
		}
		return deltaP15;
	}
	
	// Lastkombination deltaP1
	// für Lastfall 7	
	public double deltaP17() {
		double deltaP17;
		if (d3 > 0) {
			// Dreifach-Isolierverglasung
			double phi1 = this.phi1();
			double alpha1 = this.alpha1();
			double beta = this.beta();
			deltaP17 = (WinddruckWk * (phi1 * alpha1)/beta);
		} else {
			// Zweifach-Isolierverglasung (vereinfachte Formel)
			double phi1 = this.phi1();
			double alpha1 = this.alpha1();
			deltaP17 = WinddruckWk * phi1 * alpha1;
		}
		return deltaP17;
	}
	
	// Lastkombination deltaP1
	// für Lastfall 8
	public double deltaP18() {
		double deltaP18;
		if (d3 > 0) {
			// Dreifach-Isolierverglasung
			double phi1 = this.phi1();
			double alpha1 = this.alpha1();
			double beta = this.beta();
			deltaP18 = (WindsogWk * (phi1 * alpha1)/beta);
		} else {
			// Zweifach-Isolierverglasung (vereinfachte Formel)
			double phi1 = this.phi1();
			double alpha1 = this.alpha1();
			deltaP18 = WindsogWk * phi1 * alpha1;
		}
		return deltaP18;
	}
	
	// Lastkombination deltaP2
	// für Lastfall 2
	public double deltaP22() {
		double deltaP12;
		if (d3 > 0) {
			// Dreifach-Isolierverglasung
			double phi1 = this.phi1();
			double phi2 = this.phi2();
			double alpha2 = this.alpha2();
			double beta = this.beta();
			deltaP12 = Lastfall2 * phi2 * ((1 + phi1 * alpha2)/(beta));
		} else {
			// Zweifach-Isolierverglasung (vereinfachte Formel)
			deltaP12 = 0;
		}
		return deltaP12;	
	}
	
	// Lastkombination deltaP2
	// für Lastfall 3
	public double deltaP23() {
		double deltaP13;
		if (d3 > 0) {
			// Dreifach-Isolierverglasung
			double phi1 = this.phi1();
			double phi2 = this.phi2();
			double alpha2 = this.alpha2();
			double beta = this.beta();
			deltaP13 = Lastfall3 * phi2 * ((1 + phi1 * alpha2)/(beta));
		} else {
			// Zweifach-Isolierverglasung (vereinfachte Formel)
			deltaP13 = 0;
		}
		return deltaP13;	
	}
	
	// Lastkombination deltaP2
	// für Lastfall 4
	public double deltaP24() {
		double deltaP14;
		if (d3 > 0) {
			// Dreifach-Isolierverglasung
			double phi1 = this.phi1();
			double phi2 = this.phi2();
			double alpha2 = this.alpha2();
			double beta = this.beta();
			deltaP14 = Lastfall4 * phi2 * ((1 + phi1 * alpha2)/(beta));
		} else {
			// Zweifach-Isolierverglasung (vereinfachte Formel)
			deltaP14 = 0;
		}
		return deltaP14;	
	}
	
	// Lastkombination deltaP2
	// für Lastfall 5
	public double deltaP25() {
		double deltaP15;
		if (d3 > 0) {
			// Dreifach-Isolierverglasung
			double phi1 = this.phi1();
			double phi2 = this.phi2();
			double alpha2 = this.alpha2();
			double beta = this.beta();
			deltaP15 = Lastfall5 * phi2 * ((1 + phi1 * alpha2)/(beta));
		} else {
			// Zweifach-Isolierverglasung (vereinfachte Formel)
			deltaP15 = 0;
		}
		return deltaP15;	
	}
	
	// Lastkombination deltaP2
	// für Lastfall 7	
	public double deltaP27() {
		double deltaP27;
		if (d3 > 0) {
			// Dreifach-Isolierverglasung
			double phi1 = this.phi1();
			double phi2 = this.phi2();
			double alpha1 = this.alpha1();
			double alpha2 = this.alpha2();
			double beta = this.beta();
			deltaP27 = (WinddruckWk * (phi1 * alpha1 * phi2 * alpha2)/beta);
		} else {
			// Zweifach-Isolierverglasung (vereinfachte Formel)

			deltaP27 = 0;
		}
		return deltaP27;
	}	

	// Lastkombination deltaP2
	// für Lastfall 8	
	public double deltaP28() {
		double deltaP28;
		if (d3 > 0) {
			// Dreifach-Isolierverglasung
			double phi1 = this.phi1();
			double phi2 = this.phi2();
			double alpha1 = this.alpha1();
			double alpha2 = this.alpha2();
			double beta = this.beta();
			deltaP28 = (WindsogWk * (phi1 * alpha1 * phi2 * alpha2)/beta);
		} else {
			// Zweifach-Isolierverglasung (vereinfachte Formel)

			deltaP28 = 0;
		}
		return deltaP28;
	}		

	
	// pres
	// Lastfall 2
	public double pres12() {
		double pres12 = -this.deltaP12();
		return pres12;
	}
	
	public double pres22() {
		double deltaP12 = this.deltaP12();
		double deltaP22 = this.deltaP22();
		double pres22 = deltaP12 - deltaP22;
		return pres22;
	}
	
	public double pres32() {
		double pres32 = this.deltaP22();
		return pres32;
	}
	
	// pres
	// Lastfall 3
	public double pres13() {
		double pres13 = -this.deltaP13();
		return pres13;
	}
	
	public double pres23() {
		double deltaP13 = this.deltaP13();
		double deltaP23 = this.deltaP23();
		double pres23 = deltaP13 - deltaP23;
		return pres23;
	}
	
	public double pres33() {
		double pres33 = this.deltaP23();
		return pres33;
	}
	
	// pres
	// Lastfall 4
	public double pres14() {
		double pres14 = -this.deltaP14();
		return pres14;
	}
	public double pres24() {
		double deltaP14 = this.deltaP14();
		double deltaP24 = this.deltaP24();
		double pres24 = deltaP14 - deltaP24;
		return pres24;
	}
	public double pres34() {
		double pres34 = this.deltaP24();
		return pres34;
	}
	
	// pres
	// Lastfall 5
	public double pres15() {
		double pres15 = -this.deltaP15();
			return pres15;
	}
	
	public double pres25() {
		double deltaP15 = this.deltaP15();
		double deltaP25 = this.deltaP25();
		double pres25 = deltaP15 - deltaP25;
		return pres25;
	}
	
	public double pres35() {
		double pres35 = this.deltaP25();
		return pres35;
	}
	
	// pres
	// Lastfall 7
	public double pres17() {
		double deltaP17 = this.deltaP17();
		double WinddruckWk = this.WinddruckWk();
		double pres17 = WinddruckWk - deltaP17;
		return pres17;
	}
	
	public double pres27() {
		double deltaP17 = this.deltaP17();
		double deltaP27 = this.deltaP27();
		double pres27 = deltaP17 - deltaP27;
		return pres27;
	}
	
	public double pres37() {
		double deltaP27 = this.deltaP27();
		double pres37 = deltaP27;
		return pres37;
	}
	
	// pres
	// Lastfall 8
	public double pres18() {
		double deltaP18 = this.deltaP18();
		double WindsogWk = this.WindsogWk();
		double pres18 = WindsogWk - deltaP18;
		return pres18;
	}
	public double pres28() {
		double deltaP18 = this.deltaP18();
		double deltaP28 = this.deltaP28();
		double pres28 = deltaP18 - deltaP28;
		return pres28;
	}
	
	public double pres38() {
		double deltaP28 = this.deltaP28();
		double pres38 = deltaP28;
		return pres38;
	}
	
	// Lamda Tabelle
	public double berechneLamda() {
		double berechneLamda = b / a;
	    return berechneLamda;
	    }	
	
	// Lastkombinationen GZT
	// Scheibe 1 (außen):
	public double bestimmeQGzt1außen() {
		double pres12 = this.pres12();
		double qGzt1außen = 1.35 * pres12;
		return qGzt1außen;
	}
	
	public double bestimmeQGzt2außen() {
		double pres12 = this.pres12();
		double pres13 = this.pres13();
		double qGzt2außen = 1.35 * pres12 + 1.5 * pres13;
		return qGzt2außen;
	}
	
	public double bestimmeQGzt3außen() {
		double pres12 = this.pres12();
		double pres13 = this.pres13();
		double pres18 = this.pres18();	
		double qGzt3außen = 1.35 * pres12 + 1.5 * ((pres13 + (0.6 * pres18)));
		return qGzt3außen;
	}
	
	public double bestimmeQGzt4außen() {
		double pres12 = this.pres12();
		double pres13 = this.pres13();
		double pres18 = this.pres18();	
		double qGzt4außen = 1.35 * pres12 + 1.5 * ((pres13 + pres18));
		return qGzt4außen;
	}
	
	public double bestimmeQGzt5außen() {
		double pres14 = this.pres14();
		double qGzt5außen = 1.35 * pres14;
		return qGzt5außen;
	}
	
	public double bestimmeQGzt6außen() {
		double pres14 = this.pres14();
		double pres15 = this.pres15();
		double qGzt6außen = 1.35 * pres14 + 1.5 * pres15;
		return qGzt6außen;
	}
	
	public double bestimmeQGzt7außen() {
		double pres14 = this.pres14();
		double pres15 = this.pres15();
		double pres17 = this.pres17();	
		double qGzt7außen = 1.35 * pres14 + 1.5 * ((pres15 + (0.6 * pres17)));
		return qGzt7außen;
	}
	
	public double bestimmeQGzt8außen() {
		double pres14 = this.pres14();
		double pres15 = this.pres15();
		double pres17 = this.pres17();	
		double qGzt8außen = 1.35 * pres14 + 1.5 * ((pres15 + pres17));
		return qGzt8außen;
	}
		
	public double bestimmeMAXqGZTaußen() {
		double pres12 = this.pres12();
		double qGzt1 = 1.35 * pres12;
		double qGzt1Kmod = Math.abs(qGzt1/0.25);		
							
		double pres13 = this.pres13();
		double qGzt2 = 1.35 * pres12 + 1.5 * pres13;
		double qGzt2Kmod = Math.abs(qGzt2/0.4);
				
		double pres18 = this.pres18();	
		double qGzt3 = 1.35 * pres12 + 1.5 * ((pres13 + (0.6 * pres18)));
		double qGzt3Kmod = Math.abs(qGzt3/0.7);
		double qGzt4 = 1.35 * pres12 + 1.5 * ((pres13 + pres18));
		double qGzt4Kmod = Math.abs(qGzt4/0.7);
				
		double pres14 = this.pres14();
		double qGzt5 = 1.35 * pres14;
		double qGzt5Kmod = Math.abs(qGzt5/0.25);
				
		double pres15 = this.pres15();
		double qGzt6 = 1.35 * pres14 + 1.5 * pres15;
		double qGzt6Kmod = Math.abs(qGzt6/0.4);
			
		double pres17 = this.pres17();	
		double qGzt7 = 1.35 * pres14 + 1.5 * ((pres15 + (0.6 * pres17)));
		double qGzt7Kmod = Math.abs(qGzt7/0.7);
		double qGzt8 = 1.35 * pres14 + 1.5 * ((pres15 + pres17));
		double qGzt8Kmod = Math.abs(qGzt8/0.7);
							
		double bestimmeMAXqGZT;
				
		if (qGzt1Kmod>qGzt2Kmod && qGzt1Kmod>qGzt3Kmod && qGzt1Kmod>qGzt4Kmod && qGzt1Kmod>qGzt5Kmod && qGzt1Kmod>qGzt6Kmod && qGzt1Kmod>qGzt7Kmod && qGzt1Kmod>qGzt8Kmod) {
			bestimmeMAXqGZT = qGzt1;
					
		} else if (qGzt2Kmod>qGzt1Kmod && qGzt2Kmod>qGzt3Kmod && qGzt2Kmod>qGzt4Kmod && qGzt2Kmod>qGzt5Kmod && qGzt2Kmod>qGzt6Kmod && qGzt2Kmod>qGzt7Kmod && qGzt2Kmod>qGzt8Kmod) {
			bestimmeMAXqGZT = qGzt2;
					
		} else if (qGzt3Kmod>qGzt1Kmod && qGzt3Kmod>qGzt2Kmod && qGzt3Kmod>qGzt4Kmod && qGzt3Kmod>qGzt5Kmod && qGzt3Kmod>qGzt6Kmod && qGzt3Kmod>qGzt7Kmod && qGzt3Kmod>qGzt8Kmod) {
			bestimmeMAXqGZT = qGzt3;
					
		} else if (qGzt4Kmod>qGzt1Kmod && qGzt4Kmod>qGzt2Kmod && qGzt4Kmod>qGzt3Kmod && qGzt4Kmod>qGzt5Kmod && qGzt4Kmod>qGzt6Kmod && qGzt4Kmod>qGzt7Kmod && qGzt4Kmod>qGzt8Kmod) {
			bestimmeMAXqGZT = qGzt4;
					
		} else if (qGzt5Kmod>qGzt1Kmod && qGzt5Kmod>qGzt2Kmod && qGzt5Kmod>qGzt3Kmod && qGzt5Kmod>qGzt4Kmod && qGzt5Kmod>qGzt6Kmod && qGzt5Kmod>qGzt7Kmod && qGzt5Kmod>qGzt8Kmod) {
			bestimmeMAXqGZT = qGzt5;
					
		} else if (qGzt6Kmod>qGzt1Kmod && qGzt6Kmod>qGzt2Kmod && qGzt6Kmod>qGzt3Kmod && qGzt6Kmod>qGzt4Kmod && qGzt6Kmod>qGzt5Kmod && qGzt6Kmod>qGzt7Kmod && qGzt6Kmod>qGzt8Kmod) {
			bestimmeMAXqGZT = qGzt6;
				
		} else if (qGzt7Kmod>qGzt1Kmod && qGzt7Kmod>qGzt2Kmod && qGzt7Kmod>qGzt3Kmod && qGzt7Kmod>qGzt4Kmod && qGzt7Kmod>qGzt5Kmod && qGzt7Kmod>qGzt6Kmod && qGzt7Kmod>qGzt8Kmod) {
			bestimmeMAXqGZT = qGzt7;
				
		}  else {
			bestimmeMAXqGZT = qGzt8;
		
		}
		return bestimmeMAXqGZT;
	}
	
	public double bestimmKmodGZTaußen() {
		double qmaxgzt = this.bestimmeMAXqGZTaußen();
		double pres12 = this.pres12();
		double qGzt1 = 1.35 * pres12;
		double qGzt1Kmod = Math.abs(qGzt1/0.25);		
							
		double pres13 = this.pres13();
		double qGzt2 = 1.35 * pres12 + 1.5 * pres13;
		double qGzt2Kmod = Math.abs(qGzt2/0.4);
				
		double pres18 = this.pres18();	
		double qGzt3 = 1.35 * pres12 + 1.5 * ((pres13 + (0.6 * pres18)));
		double qGzt3Kmod = Math.abs(qGzt3/0.7);
		double qGzt4 = 1.35 * pres12 + 1.5 * ((pres13 + pres18));
		double qGzt4Kmod = Math.abs(qGzt4/0.7);
				
		double pres14 = this.pres14();
		double qGzt5 = 1.35 * pres14;
		double qGzt5Kmod = Math.abs(qGzt5/0.25);
				
		double pres15 = this.pres15();
		double qGzt6 = 1.35 * pres14 + 1.5 * pres15;
		double qGzt6Kmod = Math.abs(qGzt6/0.4);
			
		double pres17 = this.pres17();	
		double qGzt7 = 1.35 * pres14 + 1.5 * ((pres15 + (0.6 * pres17)));
		double qGzt7Kmod = Math.abs(qGzt7/0.7);
		double qGzt8 = 1.35 * pres14 + 1.5 * ((pres15 + pres17));
		double qGzt8Kmod = Math.abs(qGzt8/0.7);
							
		double bestimmeKmodGZT;
				
		if (qGzt1 == qmaxgzt) {
			bestimmeKmodGZT= 0.25;
					
		} else if (qGzt2 == qmaxgzt) {
			bestimmeKmodGZT= 0.4;
		
		} else if (qGzt3 == qmaxgzt) {
			bestimmeKmodGZT= 0.7;
					
		} else if (qGzt4 == qmaxgzt) {
			bestimmeKmodGZT= 0.7;
			
		} else if (qGzt5 == qmaxgzt) {
			bestimmeKmodGZT= 0.25;
					
		} else if (qGzt6  == qmaxgzt) {
			bestimmeKmodGZT= 0.4;
				
		} else if (qGzt7  == qmaxgzt) {
			bestimmeKmodGZT= 0.7;
				
		}  else {
			bestimmeKmodGZT = 0.7;
		
		}
		return bestimmeKmodGZT;
	}
	
	public double berechnePSternaußen() {
	    double q = this.bestimmeMAXqGZTaußen();
	    double berechnePStern = Math.abs((1 / E) * Math.pow(a / d1, 4) * q);
	    return berechnePStern;
	}
	
	public double interpolateKSigmaZweischrittaußen() {
        double pStern = berechnePSternaußen();
        double lambda = berechneLamda();
        return interpolierek(pStern, lambda);
	}
	
	public double SigmaDaußen() {
		double kSigma = this.interpolateKSigmaZweischrittaußen();
		double q = this.bestimmeMAXqGZTaußen();
		double SigmaD = (kSigma * q * a * 1000 * a * 1000)/((d1 * 1000 * d1 * 1000)*1000);
		return SigmaD;
	}
	
	public double Rdaußen() {
		double kmod = this.bestimmKmodGZTaußen(); 
		double kc = 1.8;
		double beiwert = 1.8;
		double Rdaußen = ((kmod * kc * fk)/beiwert);
		return Rdaußen;
	}
	
	public double NachweisGZTaußen() {
		double SigmaD = this.SigmaDaußen();
		double Rdaußen = this.Rdaußen();
		double NachweisGZT = Math.abs(SigmaD) / Math.abs(Rdaußen);
		return NachweisGZT;
	}
	
	// Lastkombinationen GZT
	// Scheibe 2 (mitte):
	public double bestimmeQGzt1mitte() {
		double pres22 = this.pres22();
		double qGzt1mitte = 1.35 * pres22;
		return qGzt1mitte;
	}
	
	public double bestimmeQGzt2mitte() {
		double pres22 = this.pres22();
		double pres23 = this.pres23();
		double qGzt2mitte = 1.35 * pres22 + 1.5 * pres23;
		return qGzt2mitte;
	}
	
	public double bestimmeQGzt3mitte() {
		double pres22 = this.pres22();
		double pres23 = this.pres23();
		double pres28 = this.pres28();	
		double qGzt3mitte = 1.35 * pres22 + 1.5 * ((pres23 + (0.6 * pres28)));
		return qGzt3mitte;
	}
	
	public double bestimmeQGzt4mitte() {
		double pres22 = this.pres22();
		double pres23 = this.pres23();
		double pres28 = this.pres28();	
		double qGzt4mitte = 1.35 * pres22 + 1.5 * ((pres23 + pres28));
		return qGzt4mitte;
	}
	
	public double bestimmeQGzt5mitte() {
		double pres24 = this.pres24();
		double qGzt5mitte = 1.35 * pres24;
		return qGzt5mitte;
	}
	
	public double bestimmeQGzt6mitte() {
		double pres24 = this.pres24();
		double pres25 = this.pres25();
		double qGzt6mitte = 1.35 * pres24 + 1.5 * pres25;
		return qGzt6mitte;
	}
	
	public double bestimmeQGzt7mitte() {
		double pres24 = this.pres24();
		double pres25 = this.pres25();
		double pres27 = this.pres27();	
		double qGzt7mitte = 1.35 * pres24 + 1.5 * ((pres25 + (0.6 * pres27)));
		return qGzt7mitte;
	}
	
	public double bestimmeQGzt8mitte() {
		double pres24 = this.pres24();
		double pres25 = this.pres25();
		double pres27 = this.pres27();	
		double qGzt8mitte = 1.35 * pres24 + 1.5 * ((pres25 + pres27));
		return qGzt8mitte;
	}

	public double bestimmeMAXqGZTmitte() {
		double pres22 = this.pres22();
		double qGzt1 = 1.35 * pres22;
		double qGzt1Kmod = Math.abs(qGzt1/0.25);		
							
		double pres23 = this.pres23();
		double qGzt2 = 1.35 * pres22 + 1.5 * pres23;
		double qGzt2Kmod = Math.abs(qGzt2/0.4);
				
		double pres28 = this.pres28();	
		double qGzt3 = 1.35 * pres22 + 1.5 * ((pres23 + (0.6 * pres28)));
		double qGzt3Kmod = Math.abs(qGzt3/0.7);
		double qGzt4 = 1.35 * pres22 + 1.5 * ((pres23 + pres28));
		double qGzt4Kmod = Math.abs(qGzt4/0.7);
				
		double pres24 = this.pres24();
		double qGzt5 = 1.35 * pres24;
		double qGzt5Kmod = Math.abs(qGzt5/0.25);
				
		double pres25 = this.pres25();
		double qGzt6 = 1.35 * pres24 + 1.5 * pres25;
		double qGzt6Kmod = Math.abs(qGzt6/0.4);
			
		double pres27 = this.pres27();	
		double qGzt7 = 1.35 * pres24 + 1.5 * ((pres25 + (0.6 * pres27)));
		double qGzt7Kmod = Math.abs(qGzt7/0.7);
		double qGzt8 = 1.35 * pres24 + 1.5 * ((pres25 + pres27));
		double qGzt8Kmod = Math.abs(qGzt8/0.7);
							
		double bestimmeMAXqGZTmitte;
				
		if (qGzt1Kmod>qGzt2Kmod && qGzt1Kmod>qGzt3Kmod && qGzt1Kmod>qGzt4Kmod && qGzt1Kmod>qGzt5Kmod && qGzt1Kmod>qGzt6Kmod && qGzt1Kmod>qGzt7Kmod && qGzt1Kmod>qGzt8Kmod) {
			bestimmeMAXqGZTmitte = qGzt1;
					
		} else if (qGzt2Kmod>qGzt1Kmod && qGzt2Kmod>qGzt3Kmod && qGzt2Kmod>qGzt4Kmod && qGzt2Kmod>qGzt5Kmod && qGzt2Kmod>qGzt6Kmod && qGzt2Kmod>qGzt7Kmod && qGzt2Kmod>qGzt8Kmod) {
			bestimmeMAXqGZTmitte = qGzt2;
					
		} else if (qGzt3Kmod>qGzt1Kmod && qGzt3Kmod>qGzt2Kmod && qGzt3Kmod>qGzt4Kmod && qGzt3Kmod>qGzt5Kmod && qGzt3Kmod>qGzt6Kmod && qGzt3Kmod>qGzt7Kmod && qGzt3Kmod>qGzt8Kmod) {
			bestimmeMAXqGZTmitte = qGzt3;
					
		} else if (qGzt4Kmod>qGzt1Kmod && qGzt4Kmod>qGzt2Kmod && qGzt4Kmod>qGzt3Kmod && qGzt4Kmod>qGzt5Kmod && qGzt4Kmod>qGzt6Kmod && qGzt4Kmod>qGzt7Kmod && qGzt4Kmod>qGzt8Kmod) {
			bestimmeMAXqGZTmitte = qGzt4;
					
		} else if (qGzt5Kmod>qGzt1Kmod && qGzt5Kmod>qGzt2Kmod && qGzt5Kmod>qGzt3Kmod && qGzt5Kmod>qGzt4Kmod && qGzt5Kmod>qGzt6Kmod && qGzt5Kmod>qGzt7Kmod && qGzt5Kmod>qGzt8Kmod) {
			bestimmeMAXqGZTmitte = qGzt5;
					
		} else if (qGzt6Kmod>qGzt1Kmod && qGzt6Kmod>qGzt2Kmod && qGzt6Kmod>qGzt3Kmod && qGzt6Kmod>qGzt4Kmod && qGzt6Kmod>qGzt5Kmod && qGzt6Kmod>qGzt7Kmod && qGzt6Kmod>qGzt8Kmod) {
			bestimmeMAXqGZTmitte = qGzt6;
				
		} else if (qGzt7Kmod>qGzt1Kmod && qGzt7Kmod>qGzt2Kmod && qGzt7Kmod>qGzt3Kmod && qGzt7Kmod>qGzt4Kmod && qGzt7Kmod>qGzt5Kmod && qGzt7Kmod>qGzt6Kmod && qGzt7Kmod>qGzt8Kmod) {
			bestimmeMAXqGZTmitte = qGzt7;
				
		}  else {
			bestimmeMAXqGZTmitte = qGzt8;
		
		}
		return bestimmeMAXqGZTmitte;
		}
	
	public double bestimmKmodGZTmitte() {
		double qmaxgzt = this.bestimmeMAXqGZTaußen();
		double pres22 = this.pres22();
		double qGzt1 = 1.35 * pres22;
		double qGzt1Kmod = Math.abs(qGzt1/0.25);		
							
		double pres23 = this.pres23();
		double qGzt2 = 1.35 * pres22 + 1.5 * pres23;
		double qGzt2Kmod = Math.abs(qGzt2/0.4);
				
		double pres28 = this.pres28();	
		double qGzt3 = 1.35 * pres22 + 1.5 * ((pres23 + (0.6 * pres28)));
		double qGzt3Kmod = Math.abs(qGzt3/0.7);
		double qGzt4 = 1.35 * pres22 + 1.5 * ((pres23 + pres28));
		double qGzt4Kmod = Math.abs(qGzt4/0.7);
				
		double pres24 = this.pres24();
		double qGzt5 = 1.35 * pres24;
		double qGzt5Kmod = Math.abs(qGzt5/0.25);
				
		double pres25 = this.pres25();
		double qGzt6 = 1.35 * pres24 + 1.5 * pres25;
		double qGzt6Kmod = Math.abs(qGzt6/0.4);
			
		double pres27 = this.pres27();	
		double qGzt7 = 1.35 * pres24 + 1.5 * ((pres25 + (0.6 * pres27)));
		double qGzt7Kmod = Math.abs(qGzt7/0.7);
		double qGzt8 = 1.35 * pres24 + 1.5 * ((pres25 + pres27));
		double qGzt8Kmod = Math.abs(qGzt8/0.7);
							
		double bestimmeKmodGZT;
				
		if (qGzt1 == qmaxgzt) {
			bestimmeKmodGZT= 0.25;
					
		} else if (qGzt2 == qmaxgzt) {
			bestimmeKmodGZT= 0.4;
		
		} else if (qGzt3 == qmaxgzt) {
			bestimmeKmodGZT= 0.7;
					
		} else if (qGzt4 == qmaxgzt) {
			bestimmeKmodGZT= 0.7;
			
		} else if (qGzt5 == qmaxgzt) {
			bestimmeKmodGZT= 0.25;
					
		} else if (qGzt6  == qmaxgzt) {
			bestimmeKmodGZT= 0.4;
				
		} else if (qGzt7  == qmaxgzt) {
			bestimmeKmodGZT= 0.7;
				
		}  else {
			bestimmeKmodGZT = 0.7;
		
		}
		return bestimmeKmodGZT;
	}
	
	public double berechnePSternmitte() {
	    double q = this.bestimmeMAXqGZTmitte();
	    double berechnePStern = Math.abs((1 / E) * Math.pow(a / d1, 4) * q);
	    return berechnePStern;
	}
	
	public double interpolateKSigmaZweischrittmitte() {
        double pStern = berechnePSternmitte();
        double lambda = berechneLamda();
        return interpolierek(pStern, lambda);
	}
	
	public double SigmaDmitte() {
		double kSigma = this.interpolateKSigmaZweischrittmitte();
		double q = this.bestimmeMAXqGZTmitte();
		double SigmaD = (kSigma * q * a * 1000 * a * 1000)/((d2 * 1000 * d2 * 1000)*1000);
		return SigmaD;
	}
	
	public double Rdmitte() {
		double kmod = this.bestimmKmodGZTmitte(); 
		double kc = 1.8;
		double beiwert = 1.8;
		double Rdmitte = ((kmod * kc * fk)/beiwert);
		return Rdmitte;
	}
	
	public double NachweisGZTmitte() {//Nachweiswert anzeugen ob erfüllt ist oder nicht
		double SigmaD = this.SigmaDmitte();
		double Rdmitte = this.Rdmitte();
		double NachweisGZT = Math.abs(SigmaD) / Math.abs(Rdmitte);
		return NachweisGZT;
	}
	
	// Lastkombinationen GZT
	// Scheibe 3(innen)
	public double bestimmeQGzt1innen() {
		double pres32 = this.pres32();
		double qGzt1innen = 1.35 * pres32;
		return qGzt1innen;
	}
	
	public double bestimmeQGzt2innen() {
		double pres32 = this.pres32();
		double pres33 = this.pres33();
		double qGzt2innen = 1.35 * pres32 + 1.5 * pres33;
		return qGzt2innen;
	}
	
	public double bestimmeQGzt3innen() {
		double pres32 = this.pres32();
		double pres33 = this.pres33();
		double pres38 = this.pres38();	
		double qGzt3innen = 1.35 * pres32 + 1.5 * ((pres33 + (0.6 * pres38)));
		return qGzt3innen;
	}
	
	public double bestimmeQGzt4innen() {
		double pres32 = this.pres32();
		double pres33 = this.pres33();
		double pres38 = this.pres38();	
		double qGzt4innen = 1.35 * pres32 + 1.5 * ((pres33 + pres38));
		return qGzt4innen;
	}
	
	public double bestimmeQGzt5innen() {
		double pres34 = this.pres34();
		double qGzt5innen = 1.35 * pres34;
		return qGzt5innen;
	}
	
	public double bestimmeQGzt6innen() {
		double pres34 = this.pres34();
		double pres35 = this.pres35();
		double qGzt6innen = 1.35 * pres34 + 1.5 * pres35;
		return qGzt6innen;
	}
	
	public double bestimmeQGzt7innen() {
		double pres34 = this.pres34();
		double pres35 = this.pres35();
		double pres37 = this.pres37();	
		double qGzt7innen = 1.35 * pres34 + 1.5 * ((pres35 + (0.6 * pres37)));
		return qGzt7innen;
	}
	
	public double bestimmeQGzt8innen() {
		double pres34 = this.pres34();
		double pres35 = this.pres35();
		double pres37 = this.pres37();	
		double qGzt8innen = 1.35 * pres34 + 1.5 * ((pres35 + pres37));
		return qGzt8innen;
	}
	
	public double bestimmeMAXqGZTinnen() {
		double pres32 = this.pres32();
		double qGzt1 = 1.35 * pres32;
		double qGzt1Kmod = Math.abs(qGzt1/0.25);		
								
		double pres33 = this.pres33();
		double qGzt2 = 1.35 * pres32 + 1.5 * pres33;
		double qGzt2Kmod = Math.abs(qGzt2/0.4);
					
		double pres38 = this.pres38();	
		double qGzt3 = 1.35 * pres32 + 1.5 * ((pres33 + (0.6 * pres38)));
		double qGzt3Kmod = Math.abs(qGzt3/0.7);
		double qGzt4 = 1.35 * pres32 + 1.5 * ((pres33 + pres38));
		double qGzt4Kmod = Math.abs(qGzt4/0.7);
					
		double pres34 = this.pres34();
		double qGzt5 = 1.35 * pres34;
		double qGzt5Kmod = Math.abs(qGzt5/0.25);
					
		double pres35 = this.pres35();
		double qGzt6 = 1.35 * pres34 + 1.5 * pres35;
		double qGzt6Kmod = Math.abs(qGzt6/0.4);
				
		double pres37 = this.pres37();	
		double qGzt7 = 1.35 * pres34 + 1.5 * ((pres35 + (0.6 * pres37)));
		double qGzt7Kmod = Math.abs(qGzt7/0.7);
		double qGzt8 = 1.35 * pres34 + 1.5 * ((pres35 + pres37));
		double qGzt8Kmod = Math.abs(qGzt8/0.7);
								
		double bestimmeMAXqGZTinnen;
					
		if (qGzt1Kmod>qGzt2Kmod && qGzt1Kmod>qGzt3Kmod && qGzt1Kmod>qGzt4Kmod && qGzt1Kmod>qGzt5Kmod && qGzt1Kmod>qGzt6Kmod && qGzt1Kmod>qGzt7Kmod && qGzt1Kmod>qGzt8Kmod) {
			bestimmeMAXqGZTinnen = qGzt1;
						
		} else if (qGzt2Kmod>qGzt1Kmod && qGzt2Kmod>qGzt3Kmod && qGzt2Kmod>qGzt4Kmod && qGzt2Kmod>qGzt5Kmod && qGzt2Kmod>qGzt6Kmod && qGzt2Kmod>qGzt7Kmod && qGzt2Kmod>qGzt8Kmod) {
			bestimmeMAXqGZTinnen = qGzt2;
						
		} else if (qGzt3Kmod>qGzt1Kmod && qGzt3Kmod>qGzt2Kmod && qGzt3Kmod>qGzt4Kmod && qGzt3Kmod>qGzt5Kmod && qGzt3Kmod>qGzt6Kmod && qGzt3Kmod>qGzt7Kmod && qGzt3Kmod>qGzt8Kmod) {
			bestimmeMAXqGZTinnen = qGzt3;
						
		} else if (qGzt4Kmod>qGzt1Kmod && qGzt4Kmod>qGzt2Kmod && qGzt4Kmod>qGzt3Kmod && qGzt4Kmod>qGzt5Kmod && qGzt4Kmod>qGzt6Kmod && qGzt4Kmod>qGzt7Kmod && qGzt4Kmod>qGzt8Kmod) {
			bestimmeMAXqGZTinnen = qGzt4;
						
		} else if (qGzt5Kmod>qGzt1Kmod && qGzt5Kmod>qGzt2Kmod && qGzt5Kmod>qGzt3Kmod && qGzt5Kmod>qGzt4Kmod && qGzt5Kmod>qGzt6Kmod && qGzt5Kmod>qGzt7Kmod && qGzt5Kmod>qGzt8Kmod) {
			bestimmeMAXqGZTinnen = qGzt5;
						
		} else if (qGzt6Kmod>qGzt1Kmod && qGzt6Kmod>qGzt2Kmod && qGzt6Kmod>qGzt3Kmod && qGzt6Kmod>qGzt4Kmod && qGzt6Kmod>qGzt5Kmod && qGzt6Kmod>qGzt7Kmod && qGzt6Kmod>qGzt8Kmod) {
			bestimmeMAXqGZTinnen = qGzt6;
					
		} else if (qGzt7Kmod>qGzt1Kmod && qGzt7Kmod>qGzt2Kmod && qGzt7Kmod>qGzt3Kmod && qGzt7Kmod>qGzt4Kmod && qGzt7Kmod>qGzt5Kmod && qGzt7Kmod>qGzt6Kmod && qGzt7Kmod>qGzt8Kmod) {
			bestimmeMAXqGZTinnen = qGzt7;
					
		}  else {
			bestimmeMAXqGZTinnen = qGzt8;
			
		}
		return bestimmeMAXqGZTinnen;
		}
	
	public double bestimmKmodGZTinnen() {
		double qmaxgzt = this.bestimmeMAXqGZTinnen();
		double pres32 = this.pres32();
		double qGzt1 = 1.35 * pres32;
		double qGzt1Kmod = Math.abs(qGzt1/0.25);		
								
		double pres33 = this.pres33();
		double qGzt2 = 1.35 * pres32 + 1.5 * pres33;
		double qGzt2Kmod = Math.abs(qGzt2/0.4);
					
		double pres38 = this.pres38();	
		double qGzt3 = 1.35 * pres32 + 1.5 * ((pres33 + (0.6 * pres38)));
		double qGzt3Kmod = Math.abs(qGzt3/0.7);
		double qGzt4 = 1.35 * pres32 + 1.5 * ((pres33 + pres38));
		double qGzt4Kmod = Math.abs(qGzt4/0.7);
					
		double pres34 = this.pres34();
		double qGzt5 = 1.35 * pres34;
		double qGzt5Kmod = Math.abs(qGzt5/0.25);
					
		double pres35 = this.pres35();
		double qGzt6 = 1.35 * pres34 + 1.5 * pres35;
		double qGzt6Kmod = Math.abs(qGzt6/0.4);
				
		double pres37 = this.pres37();	
		double qGzt7 = 1.35 * pres34 + 1.5 * ((pres35 + (0.6 * pres37)));
		double qGzt7Kmod = Math.abs(qGzt7/0.7);
		double qGzt8 = 1.35 * pres34 + 1.5 * ((pres35 + pres37));
		double qGzt8Kmod = Math.abs(qGzt8/0.7);
							
		double bestimmeKmodGZT;
				
		if (qGzt1 == qmaxgzt) {
			bestimmeKmodGZT= 0.25;
					
		} else if (qGzt2 == qmaxgzt) {
			bestimmeKmodGZT= 0.4;
		
		} else if (qGzt3 == qmaxgzt) {
			bestimmeKmodGZT= 0.7;
					
		} else if (qGzt4 == qmaxgzt) {
			bestimmeKmodGZT= 0.7;
			
		} else if (qGzt5 == qmaxgzt) {
			bestimmeKmodGZT= 0.25;
					
		} else if (qGzt6  == qmaxgzt) {
			bestimmeKmodGZT= 0.4;
				
		} else if (qGzt7  == qmaxgzt) {
			bestimmeKmodGZT= 0.7;
				
		}  else {
			bestimmeKmodGZT = 0.7;
		
		}
		return bestimmeKmodGZT;
	}
	
	public double berechnePSterninnen() {
		double q = this.bestimmeMAXqGZTinnen();
		double berechnePStern = Math.abs((1 / E) * Math.pow(a / d1, 4) * q);
		return berechnePStern;
	}
	
	public double interpolateKSigmaZweischrittinnen() {
        double pStern = berechnePSterninnen();
        double lambda = berechneLamda();
        return interpolierek(pStern, lambda);
	}
	
	private double interpolierek(double pStern, double lambda) {
        double[] lamdaWerte = {1.00, 1.25, 1.50, 1.75, 2.00, 3.00};
        int[] pSternWerte = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 60, 70, 80, 90, 100, 120, 140, 160, 180, 200, 250, 300, 350, 400, 450, 500};
        double[][] tabelenWerte = {
                {0.272, 0.383, 0.475, 0.548, 0.603, 0.711},
                {0.271, 0.382, 0.470, 0.538, 0.586, 0.706},
                {0.270, 0.378, 0.450, 0.512, 0.557, 0.694},
                {0.267, 0.355, 0.414, 0.470, 0.532, 0.676},
                {0.261, 0.331, 0.384, 0.437, 0.486, 0.654},
                {0.251, 0.310, 0.356, 0.405, 0.453, 0.628},
                {0.244, 0.291, 0.336, 0.379, 0.426, 0.603},
                {0.239, 0.278, 0.320, 0.354, 0.401, 0.580},
                {0.234, 0.271, 0.306, 0.334, 0.380, 0.559},
                {0.231, 0.267, 0.296, 0.323, 0.360, 0.538},
                {0.227, 0.263, 0.290, 0.318, 0.346, 0.519},
                {0.222, 0.256, 0.283, 0.312, 0.326, 0.482},
                {0.218, 0.252, 0.277, 0.306, 0.319, 0.452},
                {0.213, 0.247, 0.272, 0.302, 0.315, 0.426},
                {0.209, 0.243, 0.268, 0.298, 0.312, 0.403},
                {0.206, 0.239, 0.264, 0.294, 0.308, 0.382},
                {0.201, 0.234, 0.258, 0.289, 0.302, 0.353},
                {0.197, 0.230, 0.254, 0.285, 0.298, 0.346},
                {0.193, 0.226, 0.250, 0.282, 0.294, 0.343},
                {0.190, 0.223, 0.247, 0.279, 0.289, 0.341},
                {0.188, 0.220, 0.244, 0.276, 0.287, 0.338},
                {0.182, 0.215, 0.237, 0.271, 0.281, 0.332},
                {0.178, 0.211, 0.232, 0.266, 0.275, 0.326},
                {0.175, 0.208, 0.228, 0.261, 0.270, 0.322},
                {0.172, 0.206, 0.225, 0.257, 0.266, 0.318},
                {0.170, 0.204, 0.222, 0.253, 0.263, 0.315},
                {0.167, 0.202, 0.219, 0.250, 0.259, 0.312}
        };

        int pIndex = -1, lIndex = -1;
        for (int i = 0; i < pSternWerte.length - 1; i++) {
            if (pStern >= pSternWerte[i] && pStern <= pSternWerte[i + 1]) {
                pIndex = i;
                break;
            }
        }

        for (int j = 0; j < lamdaWerte.length - 1; j++) {
            if (lambda >= lamdaWerte[j] && lambda <= lamdaWerte[j + 1]) {
                lIndex = j;
                break;
            }
        }

        if (pIndex == -1 || lIndex == -1) {
            System.out.println("Werte außerhalb der Tabelle");
            return -1.0;
        }

        double p1 = pSternWerte[pIndex];
        double p2 = pSternWerte[pIndex + 1];
        double f1 = tabelenWerte[pIndex][lIndex];
        double f2 = tabelenWerte[pIndex + 1][lIndex];
        double f3 = tabelenWerte[pIndex][lIndex + 1];
        double f4 = tabelenWerte[pIndex + 1][lIndex + 1];

        double interpP_lambda1 = f1 + (pStern - p1) / (p2 - p1) * (f2 - f1);
        double interpP_lambda2 = f3 + (pStern - p1) / (p2 - p1) * (f4 - f3);

        double l1 = lamdaWerte[lIndex];
        double l2 = lamdaWerte[lIndex + 1];
        double kSigmaK = interpP_lambda1 + (lambda - l1) / (l2 - l1) * (interpP_lambda2 - interpP_lambda1);

        return kSigmaK;
    }

	public double SigmaDinnen() {
		double kSigma = this.interpolateKSigmaZweischrittinnen();
		double q = this.bestimmeMAXqGZTinnen();
		double SigmaD = (kSigma * q * a * 1000 * a * 1000)/((d3 * 1000 * d3 * 1000)*1000);
		return SigmaD;
	}

	public double Rdinnen() {
		double kmod = this.bestimmKmodGZTinnen(); 
		double kc = 1.8;
		double beiwert = 1.8;
		double Rdinnen = ((kmod * kc * fk)/beiwert);
		return Rdinnen;
	}
	
	public double NachweisGZTinnen() {//Nachweiswert anzeugen ob erfüllt ist oder nicht
		double SigmaD = this.SigmaDinnen();
		double Rdinnen = this.Rdinnen();
		double NachweisGZT = Math.abs(SigmaD) / Math.abs(Rdinnen);
		return NachweisGZT;
	}

	// Lastkombinationen GZG
	// Scheibe 1 (außen):
	public double bestimmeQGzg1außen() {
		double pres12 = this.pres12();
		double pres13 = this.pres13();		
		double pres18 = this.pres18();	
		double qGzg1außen  = Math.abs(pres12 + pres13 + 0.6 * pres18);
		return qGzg1außen;
	}
		
	public double bestimmeQGzg2außen() {
		double pres12 = this.pres12();
		double pres13 = this.pres13();		
		double pres18 = this.pres18();	
		double qGzg2außen  = Math.abs(pres12 + pres13 + pres18);
		return qGzg2außen;
	}
		
	public double bestimmeQGzg3außen() {
		double pres14 = this.pres14();
		double pres15 = this.pres15();
		double pres17 = this.pres17();
		double qGzgaußen= Math.abs(pres14 + pres15 + 0.6 * pres17);
		return qGzgaußen;
	}
	
	public double bestimmeQGzg4außen() {
		double pres14 = this.pres14();
		double pres15 = this.pres15();
		double pres17 = this.pres17();
		double qGzg4außen= Math.abs(pres14 + pres15 + pres17);
		return qGzg4außen;
	}
		
	public double bestimmeMAXqGZGaußen() {
		double pres12 = this.pres12();
		double pres13 = this.pres13();		
		double pres18 = this.pres18();	
		double qGzg1außen  = Math.abs(pres12 + pres13 + 0.6 * pres18);
		double qGzg2außen  = Math.abs(pres12 + pres13 + pres18);
			
		double pres14 = this.pres14();
		double pres15 = this.pres15();
		double pres17 = this.pres17();	
		double qGzg3außen  = Math.abs(pres14 + pres15 + 0.6 * pres17);
		double qGzg4außen  = Math.abs(pres14 + pres15 + pres17);
			
		double bestimmeMAXqGZGaußen;
			
		if (qGzg1außen>qGzg2außen && qGzg1außen>qGzg3außen && qGzg1außen>qGzg4außen) {
			bestimmeMAXqGZGaußen = qGzg1außen;
						
		} else if (qGzg2außen>qGzg1außen && qGzg2außen>qGzg3außen && qGzg2außen>qGzg4außen) {
			bestimmeMAXqGZGaußen = qGzg2außen;
						
		} else if (qGzg3außen>qGzg1außen && qGzg3außen>qGzg2außen && qGzg3außen>qGzg4außen) {
			bestimmeMAXqGZGaußen = qGzg3außen;
					
		}  else {
			bestimmeMAXqGZGaußen = qGzg4außen;
			
		}
		return bestimmeMAXqGZGaußen;
		}
		
	public double berechnePSternaußenGZG() {
		double q = this.bestimmeMAXqGZGaußen();
		double berechnePStern = Math.abs((1 / E) * Math.pow(a / d1, 4) * q);
		return berechnePStern;
	}
		
	public double interpolateKSigmaZweischritt1außen() {
	    double pStern = berechnePSternaußenGZG();
	    double lambda = berechneLamda();
	    return interpolierekW(pStern, lambda);
	}
		
	// Lastkombinationen GZG
	// Scheibe 2 (mitte):
	
	public double bestimmeQGzg1mitte() {
		double pres22 = this.pres22();
		double pres23 = this.pres23();		
		double pres28 = this.pres28();	
		double qGzg1mitte  = Math.abs(pres22 + pres23 + 0.6 * pres28);
		return qGzg1mitte;
	}
				
	public double bestimmeQGzg2mitte() {
		double pres22 = this.pres22();
		double pres23 = this.pres23();		
		double pres28 = this.pres28();	
		double qGzg2mitte  = Math.abs(pres22 + pres23 + pres28);
		return qGzg2mitte;
		}
				
	public double bestimmeQGzg3mitte() {
		double pres24 = this.pres24();
		double pres25 = this.pres25();
		double pres27 = this.pres27();
		double qGzg3mitte= Math.abs(pres24 + pres25 + 0.6 * pres27);
		return qGzg3mitte;
	}

	public double bestimmeQGzg4mitte() {
		double pres24 = this.pres24();
		double pres25 = this.pres25();
		double pres27 = this.pres27();
		double qGzg4mitte= Math.abs(pres24 + pres25 + pres27);
		return qGzg4mitte;
	}
	
	public double bestimmeMAXqGZGmitte() {
		double pres22 = this.pres22();
		double pres23 = this.pres23();		
		double pres28 = this.pres28();	
		double qGzg1mitte  = Math.abs(pres22 + pres23 + 0.6 * pres28);
		double qGzg2mitte  = Math.abs(pres22 + pres23 + pres28);
					
		double pres24 = this.pres24();
		double pres25 = this.pres25();
		double pres27 = this.pres27();
		double qGzg3mitte= Math.abs(pres24 + pres25 + 0.6 * pres27);
		double qGzg4mitte= Math.abs(pres24 + pres25 + pres27);
					
		double bestimmeMAXqGZGmitte;
					
		if (qGzg1mitte>qGzg2mitte && qGzg1mitte>qGzg3mitte && qGzg1mitte>qGzg4mitte) {
			bestimmeMAXqGZGmitte = qGzg1mitte;
								
		} else if (qGzg2mitte>qGzg1mitte && qGzg2mitte>qGzg3mitte && qGzg2mitte>qGzg4mitte) {
			bestimmeMAXqGZGmitte = qGzg2mitte;
								
		} else if (qGzg3mitte>qGzg1mitte && qGzg3mitte>qGzg2mitte && qGzg3mitte>qGzg4mitte) {
			bestimmeMAXqGZGmitte = qGzg3mitte;
							
		}  else {bestimmeMAXqGZGmitte = qGzg4mitte;
					
		}
		return bestimmeMAXqGZGmitte;
		}
				
	public double berechnePSternmitteGZG() {
		double q = this.bestimmeMAXqGZGmitte();
		double berechnePStern = Math.abs((1 / E) * Math.pow(a / d2, 4) * q);
		return berechnePStern;
	}
				
	public double interpolateKSigmaZweischritt1mitte() {
		double pStern = berechnePSternmitteGZG();
		double lambda = berechneLamda();
		return interpolierekW(pStern, lambda);
	}
				
	// Lastkombinationen GZG
	// Scheibe 3 (innen):
	public double bestimmeQGzg1innen() {
		double pres32 = this.pres32();
		double pres33 = this.pres33();		
		double pres38 = this.pres38();	
		double qGzg1innen  = Math.abs(pres32 + pres33 + 0.6 * pres38);
		return qGzg1innen;
	}
				
	public double bestimmeQGzg2innen() {
		double pres32 = this.pres32();
		double pres33 = this.pres33();		
		double pres38 = this.pres38();
		double qGzg2innen  = Math.abs(pres32 + pres33 + pres38);
		return qGzg2innen;
	}
				
	public double bestimmeQGzg3innen() {
		double pres34 = this.pres34();
		double pres35 = this.pres35();
		double pres37 = this.pres37();
		double qGzg3innen= Math.abs(pres34 + pres35 + 0.6 * pres37);
		return qGzg3innen;
	}
				
	public double bestimmeQGzg4innen() {
		double pres34 = this.pres34();
		double pres35 = this.pres35();
		double pres37 = this.pres37();
		double qGzg4innen= Math.abs(pres34 + pres35 + pres37);
		return qGzg4innen;
	}
	
	public double bestimmeMAXqGZGinnen() {
		double pres32 = this.pres32();
		double pres33 = this.pres33();		
		double pres38 = this.pres38();	
		double qGzg1innen  = Math.abs(pres32 + pres33 + 0.6 * pres38);
		double qGzg2innen  = Math.abs(pres32 + pres33 + pres38);
					
		double pres34 = this.pres34();
		double pres35 = this.pres35();
		double pres37 = this.pres37();
		double qGzg3innen= Math.abs(pres34 + pres35 + 0.6 * pres37);
		double qGzg4innen= Math.abs(pres34 + pres35 + pres37);
					
		double bestimmeMAXqGZGinnen;
					
		if (qGzg1innen>qGzg2innen && qGzg1innen>qGzg3innen && qGzg1innen>qGzg4innen) {
			bestimmeMAXqGZGinnen = qGzg1innen;
								
		} else if (qGzg2innen>qGzg1innen && qGzg2innen>qGzg3innen && qGzg2innen>qGzg4innen) {
			bestimmeMAXqGZGinnen = qGzg2innen;
								
		} else if (qGzg3innen>qGzg1innen && qGzg3innen>qGzg2innen && qGzg3innen>qGzg4innen) {
			bestimmeMAXqGZGinnen = qGzg3innen;
							
		}  else {bestimmeMAXqGZGinnen = qGzg4innen;
					
		}
		return bestimmeMAXqGZGinnen;
		}
				
	public double berechnePSterninnenGZG() {
		double q = this.bestimmeMAXqGZGinnen();
		double berechnePStern = Math.abs((1 / E) * Math.pow(a / d3, 4) * q);
		return berechnePStern;
	}
				
	public double interpolateKSigmaZweischritt1innen() {
        double pStern = berechnePSterninnenGZG();
        double lambda = berechneLamda();
        return interpolierekW(pStern, lambda);
    }
	
	private double interpolierekW(double pStern, double lambda) {
        double[] lamdaWerte = {1.00, 1.25, 1.50, 1.75, 2.00, 3.00};
        int[] pSternWerte = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 60, 70, 80, 90, 100, 120, 140, 160, 180, 200, 250, 300, 350, 400, 450, 500};
        double[][] tabelenWerte1 = {
                {0.0464, 0.0685, 0.0878, 0.1030, 0.1150, 0.1390},
                {0.0457, 0.0672, 0.0861, 0.0993, 0.1140, 0.1380},
                {0.0445, 0.0643, 0.0820, 0.0957, 0.1100, 0.1360},
                {0.0430, 0.0607, 0.0763, 0.0893, 0.1010, 0.1330},
                {0.0413, 0.0568, 0.0708, 0.0832, 0.0953, 0.1290},
                {0.0397, 0.0533, 0.0662, 0.0784, 0.0898, 0.1250},
                {0.0376, 0.0503, 0.0623, 0.0738, 0.0848, 0.1200},
                {0.0360, 0.0477, 0.0588, 0.0698, 0.0805, 0.1150},
                {0.0347, 0.0455, 0.0559, 0.0663, 0.0767, 0.1110},
                {0.0332, 0.0434, 0.0533, 0.0632, 0.0733, 0.1070},
                {0.0320, 0.0417, 0.0512, 0.0608, 0.0702, 0.1040},
                {0.0298, 0.0385, 0.0472, 0.0563, 0.0651, 0.0975},
                {0.0280, 0.0360, 0.0441, 0.0524, 0.0608, 0.0923},
                {0.0264, 0.0338, 0.0414, 0.0493, 0.0573, 0.0874},
                {0.0251, 0.0320, 0.0392, 0.0467, 0.0543, 0.0833},
                {0.0239, 0.0304, 0.0373, 0.0443, 0.0516, 0.0795},
                {0.0221, 0.0278, 0.0342, 0.0406, 0.0474, 0.0733},
                {0.0204, 0.0258, 0.0316, 0.0376, 0.0438, 0.0682},
                {0.0191, 0.0241, 0.0295, 0.0351, 0.0409, 0.0639},
                {0.0179, 0.0227, 0.0278, 0.0330, 0.0386, 0.0605},
                {0.0171, 0.0215, 0.0263, 0.0313, 0.0365, 0.0573},
                {0.0153, 0.0191, 0.0234, 0.0278, 0.0324, 0.0510},
                {0.0139, 0.0173, 0.0211, 0.0252, 0.0294, 0.0463},
                {0.0128, 0.0161, 0.0195, 0.0233, 0.0270, 0.0426},
                {0.0119, 0.0155, 0.0181, 0.0228, 0.0251, 0.0396},
                {0.0112, 0.0151, 0.0170, 0.0224, 0.0235, 0.0371},
                {0.0106, 0.0147, 0.0160, 0.0200, 0.0222, 0.0349}
        };

        int pIndex = -1, lIndex = -1;
        for (int i = 0; i < pSternWerte.length - 1; i++) {
            if (pStern >= pSternWerte[i] && pStern <= pSternWerte[i + 1]) {
                pIndex = i;
                break;
            }
        }

        for (int j = 0; j < lamdaWerte.length - 1; j++) {
            if (lambda >= lamdaWerte[j] && lambda <= lamdaWerte[j + 1]) {
                lIndex = j;
                break;
            }
        }

        if (pIndex == -1 || lIndex == -1) {
            System.out.println("Werte außerhalb der Tabelle");
            return -1.0;
    }

        double p1 = pSternWerte[pIndex];
        double p2 = pSternWerte[pIndex + 1];
        double f1 = tabelenWerte1[pIndex][lIndex];
        double f2 = tabelenWerte1[pIndex + 1][lIndex];
        double f3 = tabelenWerte1[pIndex][lIndex + 1];
        double f4 = tabelenWerte1[pIndex + 1][lIndex + 1];

        double interpP_lambda1 = f1 + (pStern - p1) / (p2 - p1) * (f2 - f1);
        double interpP_lambda2 = f3 + (pStern - p1) / (p2 - p1) * (f4 - f3);

        double l1 = lamdaWerte[lIndex];
        double l2 = lamdaWerte[lIndex + 1];
        double kSigmak = interpP_lambda1 + (lambda - l1) / (l2 - l1) * (interpP_lambda2 - interpP_lambda1);

        return kSigmak;
	}
	
	public double wdAußen() {
		double kSigmak = this.interpolateKSigmaZweischritt1außen();
		double q = this.bestimmeMAXqGZGaußen();
		double wdAußen = (kSigmak * Math.pow(a, 4) * q*1000)/(E * Math.pow(d1, 3));
		return wdAußen;
	} 
	
	public double wdMitte() {
		double kSigmak = this.interpolateKSigmaZweischritt1mitte();
		double q = this.bestimmeMAXqGZGmitte();
		double wdMitte = (kSigmak * Math.pow(a, 4) * q*1000)/(E * Math.pow(d2, 3));
		return wdMitte;
	} 
	
	public double wdInnen() {
		double kSigmak = this.interpolateKSigmaZweischritt1innen();
		double q = this.bestimmeMAXqGZGinnen();
		double wdInnen = (kSigmak * Math.pow(a, 4) * q*1000)/(E * Math.pow(d3, 3));
		return wdInnen;
	} 
	
	public double cD() {
		double cD = (a*1000)/100;
		return cD;
	}
	
	public double NachweisGZGaußen() {
		double wdAußen = this.wdAußen();
		double cD = this.cD();
		double NachweisGZGaußen = Math.abs(wdAußen)/Math.abs(cD);
		return NachweisGZGaußen;
	}
	
	public double NachweisGZGmitte() {
		double wdMitte = this.wdMitte();
		double cD = this.cD();
		double NachweisGZGmitte = Math.abs(wdMitte)/Math.abs(cD);
		return NachweisGZGmitte;
	}
	
	public double NachweisGZGinnen() {
		double wdInnen = this.wdInnen();
		double cD = this.cD();
		double NachweisGZGinnen = Math.abs(wdInnen)/Math.abs(cD);
		return NachweisGZGinnen;
	}
}
