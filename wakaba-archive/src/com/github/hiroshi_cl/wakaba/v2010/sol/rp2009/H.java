package com.github.hiroshi_cl.wakaba.v2010.sol.rp2009;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.math.BigInteger.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class H {
	void debug(Object... os) {
//		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("H.txt"));
		} catch (Exception e) {
		}
		for(;;){
			char[] cs=sc.next().toCharArray();
			if(cs.length==1 && cs[0]=='0')return;
			int n = cs.length;
			int[] is=new int[n];
			for (int i = 0; i < n; i++) {
				is[i]=cs[i]-'0';
			}
			int[] mod=new int[n+1];
			mod[0] = 0;
			long res = 0;
			int[] nonzero = new int[11];
			nonzero[0]=1;
			for (int i = 0; i < n; i++) {
				mod[i+1] = mod[i] + (i%2==0 ? is[i] : 11-is[i]);
				if(mod[i+1]>=11)mod[i+1]-=11;
				res += nonzero[mod[i+1]];
				if(i==n-1 || is[i+1] != 0)nonzero[mod[i+1]]++;
			}
			debug(mod);
			System.out.println(res);
		}
	}

	public static void main(String[] args) {
		new H().run();
	}
}
