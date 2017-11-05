package tools;

import javax.xml.parsers.DocumentBuilder;

public class StackOverflowSurfer {
	
	private static StackOverflowSurfer mInstance = null;
	
	public StackOverflowSurfer(){
		
	}
	
	public static StackOverflowSurfer getInstance(){
		if(mInstance == null){
			mInstance = new StackOverflowSurfer();
		}
		return mInstance;
	}
	
	public String getPageSourceAsString(){
		return null;
	}
}
