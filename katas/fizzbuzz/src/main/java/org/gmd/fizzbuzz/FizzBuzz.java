package org.gmd.fizzbuzz;

import java.util.ArrayList;
import java.util.List;

/**
 * Fizz Buzz Kata
 * 
 */
public class FizzBuzz {
	public List<String> execute() {
		ArrayList<String> values = new ArrayList<String>(100);
		for (int i = 0; i < 100; i++) {
			boolean isFizz = (i + 1) % 3 == 0;
			boolean isBuzz = (i + 1) % 5 == 0;
			if (isFizz && isBuzz) {
				values.add("FizzBuzz");
			} else if (isFizz) {
				values.add("Fizz");
			} else if (isBuzz) {
				values.add("Buzz");
			} else {
				values.add(String.valueOf(i+1));
			}
		}
		return values;
	}
}
