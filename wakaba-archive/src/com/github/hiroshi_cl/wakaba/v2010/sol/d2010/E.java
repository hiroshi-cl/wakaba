package com.github.hiroshi_cl.wakaba.v2010.sol.d2010;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class E {
	public static void main(String[] args) {
		new E().run();
	}

	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			String s = getClass().getSimpleName();
			sc = new Scanner(new File(s));
			System.setOut(new PrintStream(new File(s + ".out")));
		} catch (Exception e) {
		}
		for (;;) {
			int n = sc.nextInt(), a = sc.nextInt(), s = sc.nextInt(), g = sc
					.nextInt();
			if((n|a|s|g)==0)return;
//			String[][] f = new String[n][n];
//			for (int i = 0; i < a; i++) {
//				int x = sc.nextInt(),y=sc.nextInt();
//				String st = sc.next();
//				if(f[x][y]==null || st.compareTo(f[x][y])<0){
//					f[x][y] = st;
//				}
////				f[sc.nextInt()][sc.nextInt()] = sc.next();
//			}
//			for (int k = 0; k < n; k++) {
//				for (int i = 0; i < n; i++) {
//					for (int j = 0; j < n; j++) {
//						if (f[i][k] != null && f[k][j] != null) {
//							String nxt = f[i][k] + f[k][j];
////							debug(nxt.length());
//							if (f[i][j] == null || nxt.compareTo(f[i][j]) < 0) {
//								if(nxt.length() > 1000 || f[i][k].startsWith(MIN) || f[k][j].startsWith(MIN)){
////									f[i][j] = MIN;
//									f[i][j] = nxt.substring(0,1000);
//								}else{
//									f[i][j] = nxt;
//								}
//							}
//						}
//					}
//				}
//			}
//			String res = f[s][g];
//			if (f[s][g] == null || f[s][g].startsWith(MIN)) {
//				System.out.println("NO");
//				continue;
//			}
////			boolean ok = true;loop:
//			for (int k = 0; k < n; k++) {
//				for (int i = 0; i < n; i++) {
//					for (int j = 0; j < n; j++) {
//						if (f[i][k] != null && f[k][j] != null) {
//							String nxt = f[i][k] + f[k][j];
////							debug(nxt.length());
//							if (f[i][j] == null || nxt.compareTo(f[i][j]) < 0) {
//								if(nxt.length() > 1000 || f[i][k].startsWith(MIN) || f[k][j].startsWith(MIN)){
////									f[i][j] = MIN;
//									f[i][j] = nxt.substring(0,1000);
//								}else{
//									f[i][j] = nxt;
//								}
//							}
//						}
//					}
//				}
//			}
//			if(!res.equals(f[s][g]) || f[s][g].length() >= 1000){
//				System.out.println("NO");
//			}else{
//				System.out.println(res);
//			}
			int[] ss = new int[a];
			int[] ts=new int[a];
			String[] str = new String[a];
			for (int i = 0; i < a; i++) {
				ss[i] = sc.nextInt();
				ts[i] = sc.nextInt();
				str[i] = sc.next();
			}
			int len = 250;
			String[][] dp = new String[n][len*2];
			dp[s][0] = "";
			for (int i = 0; i < len; i++) {
				for (int j = 0; j < a; j++) {
					int ns = ss[j];
					int nt = ts[j];
					String nstr = str[j];
					int d = nstr.length();
					if(i>=d && dp[ns][i-d] !=null){
						String nxt = dp[ns][i-d] + nstr;
						if(dp[nt][i]==null || nxt.compareTo(dp[nt][i]) < 0){
							dp[nt][i] = nxt;
						}
					}
				}
			}
			String res = null;
			for (int i = 0; i < len; i++) {
				if(res == null || (dp[g][i]!=null && dp[g][i].compareTo(res) < 0)){
					res = dp[g][i];
				}
			}
//			String[][] dp = new String[n][len];
			for (int i = len; i < len*2; i++) {
				for (int j = 0; j < a; j++) {
					int ns = ss[j];
					int nt = ts[j];
					String nstr = str[j];
					int d = nstr.length();
					if(i>=d && dp[ns][i-d] !=null){
						String nxt = dp[ns][i-d] + nstr;
						if(dp[nt][i]==null || nxt.compareTo(dp[nt][i]) < 0){
							dp[nt][i] = nxt;
						}
					}
				}
			}
			String res2 = null;
			for (int i = 0; i < len*2; i++) {
				if(res2 == null || (dp[g][i]!=null && dp[g][i].compareTo(res2) < 0)){
					res2 = dp[g][i];
				}
			}
			if(res==null || !res.equals(res2)){
				System.out.println("NO");
			}
			else{
				System.out.println(res);
			}

		}
	}
	String MIN = "MIN";
}
