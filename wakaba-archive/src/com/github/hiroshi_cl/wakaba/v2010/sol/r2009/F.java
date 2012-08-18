package com.github.hiroshi_cl.wakaba.v2010.sol.r2009;

import java.math.*;
import java.util.*;

import static java.math.BigInteger.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class F {
	static void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	public static void main(String[] args) {
		new F().run();
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			String s = sc.next();
			char[] ds = s.toCharArray();
			if (ds.length == 1)
				return;
			p = 0;
			map = new HashMap<String, Integer>();
			int k = 0;
			for (int i = 0; i < ds.length; i++) {
				if (Character.isUpperCase(ds[i])) {
					String t = "" + ds[i];
					if (Character.isLowerCase(ds[i + 1])) {
						t += ds[i + 1];
					}
					if (!map.containsKey(t))
						map.put(t, k++);
				}
			}
			s = s.substring(0,s.length()-1);
			String[] ss = s.split("->");
			String left = ss[0];
			String right = ss[1];
			String[] ls = left.split("\\+");
			String[] rs = right.split("\\+");
			int l = ls.length;
			int r = rs.length;
			double[][] mat = new double[k][l + r];
			for (int i = 0; i < l; i++) {
				p=0;
				int[] v = f((ls[i]+".").toCharArray());
				for (int j = 0; j < k; j++) {
					mat[j][i] = v[j];
				}
			}
			for (int i = 0; i <r; i++) {
				p=0;
				int[] v = f((rs[i]+".").toCharArray());
				for (int j = 0; j < k; j++) {
					mat[j][l+i] = -v[j];
				}
			}
			lcm = 1;
			double[] res = solve(mat);
			long[] ret=new long[res.length];
			long gcd = 0;
			for(int i=0;i<res.length;i++){
				ret[i] = (long)round(res[i]*lcm);
				gcd = i==0 ?ret[i] :  gcd(gcd,ret[i]);
			}
			for(int i=0;i<res.length;i++){
				System.out.print((i==0 ? "" : " ") + ret[i]/gcd);
			}
			System.out.println();
		}
	}
	long lcm;
	
	double EPS = 1e-6;
	boolean eq(double a,double b){
		return abs(a-b)<EPS;
	}
	void sweep(double[][] mat){// 行基本変形を用いて掃き出す。元の行列を書き換えている。
		int m=mat.length, n=mat[0].length;
		for(int l=0,k=0;l<n&&k<m;l++,k++){
			int p = -1;
			for(int i=k;i<m;i++){
				if( !eq(mat[i][l],0) && (p<0 || mat[i][l] < mat[p][l])){
					p = i;
				}
			}
			if(p<0){
				k--;continue;
			}
			double[] t=mat[k];mat[k]=mat[p];mat[p]=t;
			lcm = lcm(lcm,(long)round(lcm * mat[k][l]));
			for(int j=n-1;j>=l;j--)
				mat[k][j]/=mat[k][l];
			for(int i=k+1;i<m;i++){
				for(int j=n-1;j>=l;j--){
					mat[i][j] -= mat[i][l] * mat[k][j];
				}
			}
		}
	}
	double[] solve(double[][] mat){// matはsweepによって書き換えられる。解なしの判定はしていない。
		sweep(mat);
		int n=mat[0].length;
		double[] res = new double[n];
		res[n-1] = 1;
		for(int j=n-2;j>=0;j--){
			for(int l = j + 1; l < n; l++) {
				res[j] -= mat[j][l] * res[l]; 
			}
		}
		return res;
	}

	long gcd(long x, long y) {
		return y == 0 ? x : gcd(y, x % y);
	}
	long lcm(long x, long y) {
		return x / gcd(x, y) * y;
	}
	
	HashMap<String, Integer> map;
	int p;
	int[] f(char[] cs) {
		int k = map.size();
		int[] res = new int[k];

		while(true){
			if(cs[p]=='.' || cs[p]==')')break;
			int[] v;
			if(Character.isUpperCase(cs[p])){
				v = new int[k];
				String s = ""+cs[p];
				p++;
				if(Character.isLowerCase(cs[p])){
					s+=cs[p];
					p++;
				}
				v[map.get(s)]++;
			}
			else{
				p++;
				v = f(cs);
				p++;
			}
			int mul = 0;
			while(Character.isDigit(cs[p])){
				mul*=10;
				mul += cs[p++]-'0';
			}
			if(mul==0)mul=1;
			for(int i=0;i<v.length;i++){
				res[i] += v[i] * mul;
			}
		}
		
		return res;
	}

}