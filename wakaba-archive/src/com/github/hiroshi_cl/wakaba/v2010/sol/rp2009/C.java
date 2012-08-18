package com.github.hiroshi_cl.wakaba.v2010.sol.rp2009;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.math.BigInteger.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class C {
	void debug(Object... os) {
//		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("C.txt"));
		} catch (Exception e) {
		}
		long[] ds=new long[12];
		ds[0]=0;
		for (int m = 1; m < 12; m++) {
			ds[m] = ds[m-1] + 9 * pow(10,m-1) * m;
		}
		debug(ds);
		for(;;){
			long n=sc.nextInt();int k=sc.nextInt();
			if(n==0 && k==0)return;
			int m=-1;
			for (int i = 11; i >= 0; i--) {
				if(n > ds[i]){
					m=i+1;
					break;
				}
			}
			debug(m);
			long b = (n-ds[m-1]+m-1)/m +  pow(10,m-1)-1;
			int bi =(int)( (n-ds[m-1]-1)%m);
			debug(b,bi);
			String s = "";
			s += Long.toString(b).substring(bi);
			while(s.length()<k) {
				s+=++b;
			}
			System.out.println(s.substring(0,k));
		}
	}
	long pow(long a,int b){
		long res=1;
		for (int i = 0; i < b; i++) {
			res *= a;
		}
		return res;
	}

	public static void main(String[] args) {
		new C().run();
	}
}
