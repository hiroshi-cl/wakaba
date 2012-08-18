package com.github.hiroshi_cl.wakaba.v2010.sol.d2010;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;
public class F {
	public static void main(String[] args) {
		new F().run();
	}
	void debug(Object...os){
		System.err.println(deepToString(os));
	}
	void run(){
		Scanner sc = new Scanner(System.in);
		try{
			String s = getClass().getSimpleName();
			sc = new Scanner(new File(s));
			System.setOut(new PrintStream(new File(s+".out")));
		}catch (Exception e) {
		}
		
	}
}
