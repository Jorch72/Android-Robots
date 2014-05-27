package me.planetguy.advancedvm;


@SuppressWarnings("rawtypes")
public class StandardLibrary {
	
	public static double PRECISION = 0.01;

	@ScriptAPI("+")
	public static double add(Scripted s, double a, double b){
		return a+b;
	}
	
	@ScriptAPI("-")
	public static double sub(Scripted s, double a, double b){
		return a-b;
	}
	
	@ScriptAPI("*")
	public static double mult(Scripted s, double a, double b){
		return a*b;
	}
	
	@ScriptAPI("/")
	public static double div(Scripted s, double a, double b){
		return a/b;
	}
	
	@ScriptAPI
	public static void print(Scripted s, Object o){
		s.print(o);
	}
	
	@ScriptAPI("->")
	public static void jump(Scripted s, double index){
		((me.planetguy.advancedvm.ImperativeScript) s.getScript()).jump((int) index);
	}
	
	@ScriptAPI("->?")
	public static void jumpif(Scripted s, double index, boolean test){
		if(test)
			jump(s, index);
	}
	
	@ScriptAPI("==")
	public static boolean eq(Scripted s, Object a, Object b){
		if(a==null)
			return b==null;
		return b!=null&&b.equals(a);
	}
	
	@ScriptAPI("!=")
	public static boolean noteq(Scripted s, Object a, Object b){
		return !eq(s, a, b);
	}
	
	@ScriptAPI(">")
	public static boolean greater(Scripted s, Comparable<Comparable> a, Comparable b){
		return a.compareTo(b)>0;
	}
	
	@ScriptAPI("<")
	public static boolean less(Scripted s, Comparable<Comparable> a, Comparable b){
		return a.compareTo(b)<0;
	}
	
	@ScriptAPI(">=")
	public static boolean greatereq(Scripted s, Comparable<Comparable> a, Comparable b){
		return a.compareTo(b)>=0;
	}
	
	@ScriptAPI("<=")
	public static boolean lesseq(Scripted s, Comparable<Comparable> a, Comparable b){
		return a.compareTo(b)<=0;
	}
	
	@ScriptAPI("<-?->")
	public static boolean within(Scripted s, double d1, double d2, double precision){
		return Math.abs(d1-d2)<precision;
	}
	
	@ScriptAPI("~=")
	public static boolean fuzzyeq(Scripted s, double d1, double d2){
		return Math.abs(d1-d2)<PRECISION;
	}
	
	@ScriptAPI("&")
	public static boolean and(Scripted s, boolean b1, boolean b2){
		return b1&&b2;
	}
	
	@ScriptAPI("|")
	public static boolean or(Scripted s, boolean b1, boolean b2){
		return b1||b2;
	}
	
	@ScriptAPI("!")
	public static boolean not(Scripted s, boolean b){
		return !b;
	}
	
	@ScriptAPI("=")
	public static Object ident(Scripted s, Object o){
		return o;
	}
	
	@ScriptAPI
	public static void setoutput(Scripted scr, Stream s){
		((StackScript) scr.getScript()).setOutput(s);
	}
	
	@ScriptAPI
	public static double randint(Scripted s, double min, double max){
		return (int)(min+Math.random()*(max+1-min));
	}
	
}
