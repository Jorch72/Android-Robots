package me.planetguy.advancedvm;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class ScriptTest {

	public static void main(String[] args) throws Exception{
		Script s=VMContext.root.compile(
				  "lit x 0\n"
				+ "label :top\n"
				+ "+ x 1 x\n"
				+ "print  x this\n"
				+ "!= test x 10\n"
				+ "->?  :top this test\n"
				+ "print  'Hello this\n"
				, null);
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(b);
		o.writeObject(s);

		try{
			while(s.canStep()){
				s.step();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		ByteArrayInputStream i=new ByteArrayInputStream(b.toByteArray());
		ImperativeScript s2=(ImperativeScript) new ObjectInputStream(i).readObject();

		try{
			while(s2.canStep()){
				s2.step();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		VMContext ss=new VMContext(VMContext.root);
		ss.setScriptClass(StackScript.class);
		Script stkscript=ss.compile("+ 11 5 + 3 13 = ==", null);
		((StackScript)stkscript).setOutput(new Stream(){

			@Override
			public void write(Object o) {
				System.out.println(o);
			}
			
		});
		
		try{
			while(stkscript.canStep())
				stkscript.step();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
