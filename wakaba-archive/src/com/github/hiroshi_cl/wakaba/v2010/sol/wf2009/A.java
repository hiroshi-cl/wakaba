package com.github.hiroshi_cl.wakaba.v2010.sol.wf2009;


import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import java.math.*;
import java.util.*;

public class A {
	Scanner sc = new Scanner();
	public static void main(String[] args) {
		new A().run();
	}
	void solve() {
		int o=0;
		while(true){
			o++;
			int n=sc.nextInt();
			if(n==0)return;
			int[] as=new int[n],bs=new int[n];
			int[] is=new int[n];
			for (int i = 0; i < n; i++) {
				as[i]=sc.nextInt();
				bs[i]=sc.nextInt();
				is[i]=i;
			}
			double res = 0;
			do{
				double left = 0,right = 2000;
				for (int i = 0; i < 100; i++) {
					double mid = (left + right) / 2;
					if(can(as,bs,is,mid)){
						left = mid;
					}
					else{
						right = mid;
					}
				}
				res = max(res,left);
			}while(nextPermutation(is));
			System.out.printf("Case %d: %s%n",o,tos(res));
		}
	}
	String tos(double time){
		return String.format("%d:%02d", (int)time , round(time*60)%60);
	}
	boolean can(int[] as_,int[] bs_,int[] is,double mid){
		int n=as_.length;
		int[] as=new int[n];
		int[] bs=new int[n];
		for (int i = 0; i < n; i++) {
			as[i]=as_[is[i]];
			bs[i]=bs_[is[i]];
		}
		double now = as[0];
		for (int i = 1; i < n; i++) {
			if(now+mid < as[i]){
				now = as[i];
			}
			else if(now+mid>bs[i]){
				return false;
			}
			else{
				now = now+mid;
			}
		}
		return true;
	}

	boolean nextPermutation(int[] is) {
		int n = is.length;
		for (int i = n - 1; i > 0; i--) {
			if (is[i - 1] < is[i]) {
				int j = n;
				while (is[i - 1] >= is[--j]);
				swap(is, i - 1, j);
				rev(is, i, n);
				return true;
			}
		}
		rev(is, 0, n);
		return false;
	}
	void swap(int[] is, int i, int j) {
		int t = is[i];
		is[i] = is[j];
		is[j] = t;
	}
	void rev(int[] is, int s, int t) {
		while (s < --t)
			swap(is, s++, t);
	}
	void run() {
		solve();
	}
	class Scanner {
		int nextInt() {
			try {
				int c = System.in.read();
				while (c != '-' && (c < '0' || '9' < c))
					c = System.in.read();
				if (c == '-')
					return -nextInt();
				int res = 0;
				do {
					res *= 10;
					res += c - '0';
					c = System.in.read();
				} while ('0' <= c && c <= '9');
				return res;
			} catch (Exception e) {
				return -1;
			}
		}
		long nextLong() {
			try {
				int c = System.in.read();
				while (c != '-' && (c < '0' || '9' < c))
					c = System.in.read();
				if (c == '-')
					return -nextLong();
				long res = 0;
				do {
					res *= 10;
					res += c - '0';
					c = System.in.read();
				} while ('0' <= c && c <= '9');
				return res;
			} catch (Exception e) {
				return -1;
			}
		}
		double nextDouble() {
			return Double.parseDouble(next());
		}
		String next() {
			try {
				StringBuilder res = new StringBuilder("");
				int c = System.in.read();
				while (Character.isWhitespace(c))
					c = System.in.read();
				do {
					res.append((char) c);
				} while (!Character.isWhitespace(c = System.in.read()));
				return res.toString();
			} catch (Exception e) {
				return null;
			}
		}
		String nextLine() {
			try {
				StringBuilder res = new StringBuilder("");
				int c = System.in.read();
				while (c == '\r' || c == '\n')
					c = System.in.read();
				do {
					res.append((char) c);
					c = System.in.read();
				} while (c != '\r' && c != '\n');
				return res.toString();
			} catch (Exception e) {
				return null;
			}
		}
	}
}