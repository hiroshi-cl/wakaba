package com.github.hiroshi_cl.wakaba.v2010.sol.sc2009.d3;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class A {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		for (int o = 1;; o++) {
			try {
				sc = new Scanner(new File(o + ".in"));
				System.setOut(new PrintStream(o + ".out"));
			} catch (Exception e) {
				return;
			}
			int n=sc.nextInt();
			int[] is=new int[n];
			for(int i=0;i<n;i++){
				is[i]=sc.nextInt();
			}
			int M=256;
			double min = 1<<28;
			int rs=-1,rc=-1,ra=-1;
			for (int S = 0; S < 16; S++) {
				for (int A = 0; A < 16; A++) {
					for (int C = 0; C < 16; C++) {
						int[] os=new int[n];
						int r = S;
						for (int i = 0; i < n; i++) {
							r = (A*r+C)%M;
							os[i] = (is[i] + r)%M;
						}
						int[] count=new int[M];
						for (int i = 0; i < n; i++) {
							count[os[i]]++;
						}
//						debug(count);
						double H = 0;
						for (int i = 0; i < M; i++) if(count[i]>0){
							H -= (double)count[i]/n * log ((double)count[i]/n);
						}
						if(min > H){
							min=H;
							rs=S;
							ra=A;
							rc=C;
						}
					}
				}
			}
			System.out.println(rs+" "+ra+" "+rc);

			Scanner sc1, sc2;
			try {
				sc1 = new Scanner(new File(o + ".out"));
				sc2 = new Scanner(new File(o + ".diff"));
				boolean ok = true;
				while (sc1.hasNext()) {
					if (!sc1.next().equals(sc2.next())) {
						ok = false;
						break;
					}
				}
				if (sc2.hasNext()) ok = false;
				System.err.println(ok);
			} catch (Exception e) {}
		}
	}
	public static void main(String[] args) {
		new A().run();
	}
}
