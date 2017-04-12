package org.gmd.fizzbuzz;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length == 1){
			Integer times = new Integer(args[0]);
			System.out.println("Computing FizzBuzz " + times + " times...");
			for(int i=0; i<times; i++){
				FizzBuzz fizzBuzz = new FizzBuzz();
				fizzBuzz.execute();
			}			
		} else {
			System.out.println("Usage: <times>");
		}
	}

}
