package demographic_data;

import java.util.regex.Pattern;

import org.hamcrest.Matcher;

class ThreadUsingThreadClass {
	public static void main(String args[]){
	    Pattern pattern = Pattern.compile("Quick (.*) jumps");
	    java.util.regex.Matcher matcher = pattern.matcher("Quick brown fox jumps over the wall.");
	    boolean matchFound = matcher.find();
	    if(matchFound) {
	          System.out.println("Match found: "+ matcher.group(1));
	    } else {
	          System.out.println("Match not found");
	    }
	}
	}




		   
