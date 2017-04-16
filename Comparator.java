import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Comparator{
	/*
	*A Comparator osztály, amely egy expected?.txt-t és egy output?.txt-t
	*hasonlít össze abban a mappában ahol elindítják.
	*
	*@param args: A program elindításakor 1-9-ig meg kell adni, hogy melyik
	*tesztet hasonlítsa össze
	*/
	public static void main(String[] args) throws FileNotFoundException {
		if(args.length > 1 || args.length == 0){
			System.out.println("HIBAS BEMENET");
		}
		int choice = Integer.parseInt(args[0]);
		if (choice <1 || choice > 9)
		{
			System.out.println("HIBAS SZAM");
			System.exit(1);
		}
		System.out.println("Expected -------------------- Result");
		Scanner test1 = new Scanner(new File("expected" + args[0] + ".txt"));
		Scanner test2 = new Scanner(new File("output" + args[0] + ".txt"));
		String t1;
		String t2;
		
		while (test1.hasNextLine() && test2.hasNextLine()){
			t1 = test1.nextLine();	
			t2 = test2.nextLine();
			if(t1.equals(t2)){
				System.out.println(t1 + "\tOK\t" + t2);
			}
			else 
				System.out.println(t1 + "\tWRONG\t" + t2);
		}
		if(test1.hasNextLine() && !test2.hasNextLine()){
			System.out.println("WRONG 2. file has too few lines");
		}
		if(!test1.hasNextLine() && test2.hasNextLine()){
			System.out.println("WRONG 1. file has too few lines");
		}
	}

}
