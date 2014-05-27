package me.planetguy.advancedvm;

import java.util.ArrayList;
import java.util.Random;

public class TransformerStructuredProgramming implements ScriptTransformer {

	@Override
	public String[] transform(String[] lines) {
		ArrayList<String> newLines=new ArrayList<String>();
		Random r=new Random();
		for(String line:lines){
			String[] words=line.split(" ");
			if(words[0].equals("while")){
				
			}else if(words[0].equals("end")){
				
			}else{
				newLines.add(line);
			}
		}
		return (String[]) newLines.toArray();
	}

}
