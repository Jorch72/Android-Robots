package me.planetguy.advancedvm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Allows use of constants inline. For example:
 * 
 * print NULL 0
 * 
 * would compile to:
 * 
 * lit 0 0
 * print NULL 0
 * 
 * @author planetguy
 *
 */
public class TransformerInlineConstants implements ScriptTransformer{

	@Override
	public String[] transform(String[] lines) {
		Set<String> seenStrs=new HashSet<String>();
		ArrayList<String> strs=new ArrayList<String>(Arrays.asList(lines));
		for(int i=0; i<strs.size(); i++){
			String[] words=strs.get(i).split(" ");
			for(String s:words){
				if(!(!isLiteral(s)
						||seenStrs.contains(s)
						||s.equals("")
						)){
					seenStrs.add(s);
					if(s.charAt(0)=='\'')
						strs.add(0, "lit "+s+" "+s.substring(1));
					else
						strs.add(0, "lit "+s+" "+s);
				}
			}
		}
		return strs.toArray(new String[0]);
	}

	private boolean isLiteral(String s){
		try{
			Double.parseDouble(s);
			return true;
		}catch(NumberFormatException e){}
		return (
				s.equals("true")||s.equals("false")
				||(s.length()!=0&&s.charAt(0)=='\'')
				);
	}

}
