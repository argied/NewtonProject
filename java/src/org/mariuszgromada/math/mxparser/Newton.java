package org.mariuszgromada.math.mxparser;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Newton {
	//this is the main program that uses methods newtonMethod() and round()
	public static void main (String [] args) {
		Scanner input = new Scanner(System.in);

		System.out.printf("%35s%2s\n"," ","==========================================================================================" );
		System.out.printf("%70s%2s\n"," ","NEWTON'S METHOD");
		System.out.printf("%62s%2s\n"," ","by: Ditchon, Donio, Panilan");
		System.out.printf("%68s%2s\n"," ","ENCE2A | SY 2021-22");
		System.out.printf("%35s%2s\n"," ","==========================================================================================" );
		System.out.printf("%46s%2s\n"," ","This program approximates the value of x using Newton's Method. ");

		System.out.printf("\n%35s%2s"," ","Enter function: ");
		String func = input.nextLine();//input function
		System.out.printf("%35s%2s"," ","Enter value of x: ");
		String valueX = input.nextLine();//your x0 but in string
		System.out.printf("%35s%2s"," ","Enter value of Ea: ");
		double z = input.nextDouble();//absolute error 
		System.out.printf("%35s%2s"," ","Enter max. decimal place: ");
		int rounding = input.nextInt();

		newtonMethod(func,valueX,z,rounding);//calling newtonMethod

		System.out.printf("\n\n%35s%2s"," ","Continue(any key) or Exit(e)? ");
		String choice = input.next();
		switch (choice) {//making choice to whether terminate or continue the operation
		case "e":
			System.out.printf("%35s%2s\n"," ","Nice Job!");
			input.close();
			break;
		default:
			System.out.println("\n\n"); 
			main(null);
			input.close();
			break;
		}
	}	

	static double newtonMethod(String func,String valueX, double z,int rounding) {			
		String dFormat = Integer.toString(rounding);

		if (rounding == 1) dFormat = "#.#";//when rounding = 1, it will limit the decimal places to 1
		if (rounding == 2) dFormat = "#.##";//when rounding = 2, it will limit the decimal places to 2, so on
		if (rounding == 3) dFormat = "#.###";
		if (rounding == 4) dFormat = "#.####";
		if (rounding == 5) dFormat = "#.#####";
		if (rounding == 6) dFormat = "#.######";
		if (rounding == 7) dFormat = "#.#######";
		if (rounding == 8) dFormat = "#.########";
		if (rounding == 9) dFormat = "#.#########";
		if (rounding == 10) dFormat = "#.##########";
		if (rounding <1 || rounding >10) {System.out.printf("%35s%2s"," ","OOPS! Only from 1-10. Try again \n");main(null);}

		DecimalFormat df = new DecimalFormat(dFormat); 
		df.setRoundingMode(RoundingMode.HALF_UP);

		Function f = new Function ("f(x) = "+func); 
		Argument x = new Argument ("x = "+valueX);
		Expression fOrig = new Expression ("f(x)", f, x);//parsing original function
		Expression fPrime = new Expression ("der("+func+", x, "+valueX+")");//parsing first derivative of the function

		System.out.printf("\n%35s%2s"," ","TABULATED RESULTS");
		System.out.printf("\n%35s%2s\n"," ","==========================================================================================" );
		System.out.printf("%50s%11s%15s%15s%15s%15s\n", "Iteration", "x0", "x1", "f(x0)", "f'(x0)","Ea");
		System.out.printf("%35s%2s\n"," ","==========================================================================================" );

		double RfOrig = round(fOrig.calculate(), rounding);
		double RfPrime = round(fPrime.calculate(),rounding);

		double x0 = Double.parseDouble(valueX);
		double Rx0 = round(x0, rounding);

		double x1 =  Rx0 - (RfOrig/RfPrime);
		double Rx1 = round(x1, rounding);

		double Ea = Math.abs(Rx1 - Rx0);

		System.out.printf("%46d%15s%15s%15s%15s\n", 1, df.format(x0), df.format(x1), df.format(fOrig.calculate()), df.format(fPrime.calculate()));
		int i =2;
		while (Ea>z) {//looping the iterations from 2 to nth
			Argument test1 = new Argument ("x = "+String.valueOf(Rx1));
			fOrig = new Expression ("f(x)", f, test1);
			fPrime = new Expression ("der("+func+", x, "+String.valueOf(Rx1)+")");

			RfOrig = round(fOrig.calculate(), rounding);
			RfPrime = round(fPrime.calculate(),rounding);

			x0=x1;
			Rx0 = round(x0, rounding);
			x1 =  Rx0 - (RfOrig/RfPrime);
			Rx1 = round(x1, rounding);
			Ea = Math.abs(Rx1 - Rx0);

			System.out.printf("%46d%15s%15s%15s%15s%15s\n", i, df.format(x0), df.format(x1), df.format(fOrig.calculate()), df.format(fPrime.calculate()), df.format(Ea));
			i++;            
			if (i>100) {System.out.printf("%35s%2s\n"," ","Seems to be infinite");break;}
			if (Ea ==0)break;
		}
		System.out.printf("%35s%2s\n"," ","==========================================================================================" );
		System.out.printf("\n%35s%2s\n"," ","At iteration #"+(i-1)+", the root is approximately "+df.format(x1));//displaying the approximate value of x

		return 0;
	}

	//a static method to round decimals in a specific place
	static double round(double value, int places) {
		if (places < 0) 
			throw new IllegalArgumentException();
		BigDecimal bd = new BigDecimal(Double.toString(value));		
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}