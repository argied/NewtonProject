package org.mariuszgromada.math.mxparser;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Newton {
	//this is the main program
	public static void main (String [] args) {		
		Scanner input = new Scanner(System.in);
		DecimalFormat df = new DecimalFormat("#.####"); 
		df.setRoundingMode(RoundingMode.HALF_UP);
        
		System.out.printf("%35s%2s\n"," ","<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>" );
		System.out.printf("%70s%2s\n"," ","NEWTON'S METHOD");
		System.out.printf("%62s%2s\n"," ","by: Ditchon, Donio, Panilan");
		System.out.printf("%68s%2s\n"," ","ENCE2A | SY 2021-22");
		System.out.printf("%35s%2s\n"," ","<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>" );
		System.out.printf("%46s%2s\n"," ","This program approximates the value of x using Newton's Method. ");
		
		System.out.printf("\n%35s%2s"," ","Enter function: ");
		String func = input.nextLine();
		System.out.printf("%35s%2s"," ","Enter value of x: ");
		String valueX = input.nextLine();
		System.out.printf("%35s%2s"," ","Enter value of Ea: ");
		double z = input.nextDouble();
		
		Function f = new Function ("f(x) = "+func);
		Argument x = new Argument ("x = "+valueX);
		Expression fOrig = new Expression ("f(x)", f, x);

		System.out.printf("\n%35s%2s\n"," ","INITIAL VALUES");
		System.out.printf( "%35s%2s\n"," ",fOrig.getExpressionString() + " = " + round( fOrig.calculate(), 4));
		
		Expression fPrime = new Expression ("der("+func+", x, "+valueX+")");
		System.out.printf("%35s%2s\n"," ","f'(x)" + " = " + round( fPrime.calculate(),4));
		
		System.out.printf("\n%35s%2s\n"," ","<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>" );
		System.out.printf("%50s%11s%15s%15s%15s%15s\n", "Iteration", "x0", "x1", "f(x0)", "f'(x0)","Ea");
		System.out.printf("%35s%2s\n"," ","<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>" );

		double x0 = Double.parseDouble(valueX);
		double x1 =  x0 - ((round(fOrig.calculate(), 4)/round(fPrime.calculate(),4)));
		double Ea = Math.abs(x1 - x0);
		
		System.out.printf("%46d%15s%15s%15s%15s\n", 1, df.format(x0), df.format(x1), df.format(fOrig.calculate()), df.format(fPrime.calculate()));
		int i =2;
		while (Ea>z) {
			String newX1 = String.valueOf(round(x1, 4));
			Argument test1 = new Argument ("x = "+newX1);
			fOrig = new Expression ("f(x)", f, test1);
			fPrime = new Expression ("der("+func+", x, "+newX1+")");
			double m=x1;
			x1 =  round(m, 4) - ((round(fOrig.calculate(), 4)/round(fPrime.calculate(),4)));
			Ea = Math.abs(round(x1, 4) - round(m, 4));
			System.out.printf("%46d%15s%15s%15s%15s%15s\n", i, df.format(m), df.format(x1), df.format(fOrig.calculate()), df.format(fPrime.calculate()), df.format(Ea));
			i++;            
			if (i>100) {System.out.printf("%35s%2s\n"," ","Seems to be infinite");break;}
			if (Ea ==0)break;
		}
		System.out.printf("%35s%2s\n"," ","<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>" );
		System.out.printf("\n%35s%2s\n"," ","At iteration #"+(i-1)+", the root is approximately "+df.format(x1));
		
		System.out.printf("\n\n\n%35s%2s"," ","Press any key to continue or Press e to exit ");
		String choice = input.next();
		switch (choice) {
			case "e":System.out.printf("%35s%2s\n"," ","Nice Job!");input.close();break;
			default:System.out.println(); main(null);input.close();break;
		}
	}

	//a static method to round decimals in a specific place
	static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();
		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
