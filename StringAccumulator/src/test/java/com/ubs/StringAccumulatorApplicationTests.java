package com.ubs;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StringAccumulatorApplicationTests {
	private StringAccumulatorApplication app = new StringAccumulatorApplication();
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testBasicNumberAddWithCommaDelimeter() {
		Assert.assertEquals(3, app.add("1,2"));
	}
	
	@Test
	public void testNegativeBasicNumberAddWithCommaDelimeter() {
		Assert.assertNotEquals(5, app.add("1,2"));
	}
	
	@Test
	public void testBasicNumberAddWithNoDelimeter() {
		Assert.assertEquals(1, app.add("1"));
	}
	
	@Test
	public void testBasicNumberAddWithEmptyString() {
		Assert.assertEquals(5, app.add("2,,3"));
	}
	
	@Test
	public void testBasicNumberAddWithPureEmptyString() {
		Assert.assertEquals(0, app.add(""));
	}
	
	@Test
	public void testBasicNumberAddWithUnknownNumbers() {
		Assert.assertEquals(22, app.add("1,4,5,2,,7,,3"));
	}
	
	@Test
	public void testNegativeBasicNumberAddWithUnknownNumbers() {
		Assert.assertNotEquals(23, app.add("1,4,5,2,,7,,3"));
	}
	
	@Test
	public void testBasicNumberAddWithNewLineChar() {
		Assert.assertEquals(6, app.add("1\n2,3"));
	}
	
	@Test
	public void testBasicNumberAddWithSpecifiedSingleDelimiter() {
		Assert.assertEquals(3, app.add("//;\n1;2"));
	}
	
	@Test
	public void testBasicNumberAddWithSpecifiedSingleDelimiterandIgnoreGreaterThanThousand() {
		Assert.assertEquals(1003, app.add("//;\n1;2;1000;1001"));
	}
	
	@Test
	public void testBasicNumberAddWithSpecifiedSingleMultiCharDelimiterandIgnoreGreaterThanThousand() {
		Assert.assertEquals(1003, app.add("//abc\n1abc2abc1000abc1001"));
	}
	
	@Test
	public void testBasicNumberAddWithSpecifiedMultipleDelimiter() {
		Assert.assertEquals(6, app.add("//*|%\n1*2%3"));
	}
	
	@Test
	public void testBasicNumberAddWithSpecifiedMultipleDelimiterAndNegativeNumbers() {
		try {
			app.add("//*|%\n-1*2%-3");
		}catch (NegativeNumberException e){
			Assert.assertEquals("Negatives not allowed[-1, -3]", e.getMessage());
		}
	}
	
	@Test
	public void testBasicNumberAddWithSpecifiedMultipleMultiCharacterDelimiter() {
		Assert.assertEquals(6, app.add("//***|%%%\n1***2%%%3"));
	}

}
