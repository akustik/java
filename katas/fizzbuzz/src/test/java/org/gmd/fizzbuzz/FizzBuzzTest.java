package org.gmd.fizzbuzz;

import java.util.List;


import org.junit.Assert;
import org.junit.Test;

public class FizzBuzzTest {

	@Test
	public void firstElementIsOne() {
		FizzBuzz fizzBuzz = new FizzBuzz();
		List<String> values = fizzBuzz.execute();
		Assert.assertEquals("1", values.get(0));
	}
	
	@Test
	public void thirdElementIsFizz() {
		FizzBuzz fizzBuzz = new FizzBuzz();
		List<String> values = fizzBuzz.execute();
		Assert.assertEquals("Fizz", values.get(2));
	}
	
	@Test
	public void fifthElementIsBuzz() {
		FizzBuzz fizzBuzz = new FizzBuzz();
		List<String> values = fizzBuzz.execute();
		Assert.assertEquals("Buzz", values.get(4));
	}
	
	@Test
	public void fifteenthElementIsFizzBuzz() {
		FizzBuzz fizzBuzz = new FizzBuzz();
		List<String> values = fizzBuzz.execute();
		Assert.assertEquals("FizzBuzz", values.get(14));
	}
	
	@Test
	public void executionIsQuickEnough() {
		long start = System.currentTimeMillis();
		for(int i=0; i<100000;i++){
			FizzBuzz fizzBuzz = new FizzBuzz();
			fizzBuzz.execute();
		}
		long end = System.currentTimeMillis();
		Assert.assertTrue(String.format("The operation was not" +
				" quick enough, took %dms", end - start), end - start < 1000);
	}

}
