package com.github.hiroshi_cl.wakaba.v2009.sol.d2008;
import java.util.*;
import java.io.*;
public class C {
	static void e(boolean x){if (!x) {System.err.println("E");throw new Error();}}
	static abstract class T{
		abstract int eval(int p, int q, int r);
	}
	static class N extends T{
		String op;
		T o1; T o2;
		public String toString(){return "("+op+o1.toString()+(o2==null?"":o2)+")";}
		N(String s, T t1, T t2) { op = s; o1 = t1; o2 = t2;}
		int eval(int p, int q,int r) {
			int x = o1.eval(p,q,r);
			int y = o2==null ? -1 : o2.eval(p,q,r);

			if (op.equals("*")) {
				e(y!=-1);
				if(x==0||y==0) return 0;
				else if(x==1||y==1) return 1;
				else return 2;
			}
			else if(op.equals("+")) {
				e(y!= -1);
				if(x==2||y==2) return 2;
				if(x==1||y==1) return 1;
				else return 0;
			}
			else if(op.equals("-")) {
				e(y==-1);
				return 2-x;
			}
			else {
				e(false);
				return -1;
			}
		}
	}
	static class L extends T{
		String o;
		public String toString(){return o;}
		L(String s) { o = s; }
		int eval(int p, int q, int r) {
			if(o.equals("0")) return 0;
			if(o.equals("1")) return 1;
			if(o.equals("2")) return 2;			
			if(o.equals("P")) return p;
			if(o.equals("Q")) return q;
			if(o.equals("R")) return r;
			e(false);
			return -1;
		}
	}
	
	static String ss;
	static int ix;
	static int pqr;
	
	static T parse() {
		T r;
		//System.out.println("!");
		T r1 = term();
		e(r1!=null);
		ix++;
		
		if (ix >= ss.length()) return r1;

		if (ss.charAt(ix) == '*') {
			ix++;
			T r2 = term();
			r = new N("*",r1,r2);
			return r;
		}
		else if (ss.charAt(ix) == '+') {
			ix++;
			T r2 = term();
			r = new N("+",r1,r2);
			return r;
		}
		//System.out.println(ss.charAt(ix)+";"+ix);
		e(false);
		return null;
	}
	static T term() {
		//System.out.println("&");
		if (ss.charAt(ix) == '-') {
			ix++;
			T r = term();
			return new N("-",r,null);
		}
		else
			return factor();
	}
	static T factor() {
		if (ss.charAt(ix) == '(') {
			ix++;
			T r = parse();
			ix++;
			e(ss.charAt(ix) == ')');
			return r;
		}
		else if(ss.charAt(ix)=='0' ||ss.charAt(ix) == '1' || ss.charAt(ix) == '2') {
			return new L(String.valueOf(ss.charAt(ix)));
		}
		else {
		//	System.out.println("#");
			pqr++;
			return new L(String.valueOf(ss.charAt(ix)));
		}
	}
	
	public static void main(String[] args) throws Exception {
//		Scanner sc = new Scanner(new File("C.in"));
//		System.setOut(new PrintStream("C.out"));
		Scanner sc = new Scanner(System.in);
		for(String exp = sc.nextLine(); !exp.equals("."); exp = sc.nextLine()) {
			exp.replaceAll("--", "");
			ss = exp;
			ix=0;
			pqr=0;
			T r=parse();
			//System.out.println(r);
			int sum=0;
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					for(int k=0;k<3;k++){
						//System.out.println(i+" "+j+" "+k+" "+r.eval(i,j,k));
						if(r.eval(i,j,k) == 2) sum++;
					}
				}
			}
			
			System.out.println(sum);
		}
	}
}
