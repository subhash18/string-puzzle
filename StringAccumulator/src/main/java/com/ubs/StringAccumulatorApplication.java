package com.ubs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;


/**
 * Assumption: In case delimiter is not mentioned for a part of string, default would be comma (,)
 */

@SpringBootApplication
public class StringAccumulatorApplication {
	
	private  List<Integer> negatives = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		SpringApplication.run(StringAccumulatorApplication.class, args);
	}

	public int add(String str) {
		int summation = 0;
		/**
		 * TODO : 3.b. The following input is NOT ok: “1,\n” (don’t need to prove it - just clarifying).
		 */
		
		List<String> strParts = SplitStringIntoParts(str);
		String delimiter = ",";
		if (str.startsWith("//")) {
			delimiter = strParts.get(0).substring(2);
			strParts.remove(0);
		}
		
		for(String part : strParts) {
			List<String> numbersInPart;
			numbersInPart = SplitStringIntoNumbers(delimiter, part);
			
			try {
				summation =  summation + sum(numbersInPart);
			} catch (Exception e) {}
			
		}
		
		if (negatives.size() > 0 )
			throw new NegativeNumberException("Negatives not allowed" + negatives);

		return summation;
	}

	private  List<String> SplitStringIntoParts(String str) {
		Iterable<String> splitedStr = Splitter.on("\n").trimResults().omitEmptyStrings().split(str);
		return Lists.newArrayList(splitedStr);
	}

	
	
	private  List<String> SplitStringIntoNumbers(String delimiter, String str) {
		String[] delims = delimiter.split("\\|");
		List<String> splitedStr = Lists.newArrayList(Splitter.on(delims[0]).trimResults().omitEmptyStrings().split(str));
		
		List<String> tempList = new ArrayList<String>();
		for (int i=1 ; i<delims.length; i++) {
			for (String s : splitedStr) {
				tempList.addAll(splitStringUsingSpecificDelim(delims[i], s));
			}
			splitedStr = tempList;
		}
		return Lists.newArrayList(splitedStr);
	}
	
	private static List<String> splitStringUsingSpecificDelim(String delim, String str){
		return Lists.newArrayList(Splitter.on(delim).trimResults().omitEmptyStrings().split(str));
	}
	
	private  int sum(List<String> strParts) throws Exception {
		List<Integer> lstNegatives = strParts.stream().map(Integer::parseInt).filter(i -> i<0).collect(Collectors.toList());
		if (lstNegatives.size() > 0) {
			negatives.addAll(lstNegatives);
			throw new Exception("Negatives not allowed");
		}
		return strParts.stream().map(Integer::parseInt).filter(i -> i<=1000).reduce(0, Integer::sum);
	}

	
}