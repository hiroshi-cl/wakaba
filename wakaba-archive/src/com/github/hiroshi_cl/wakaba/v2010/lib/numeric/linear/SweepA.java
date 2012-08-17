package com.github.hiroshi_cl.wakaba.v2010.lib.numeric.linear;
import static java.lang.Math.*;

import java.util.Arrays;
public class SweepA {
	double EPS = 1e-6;
	boolean eq(double a,double b){
		return abs(a-b)<EPS;
	}
	void sweep(double[][] mat){// 行基本変形を用いて掃き出す。元の行列を書き換えている。
		int m=mat.length, n=mat[0].length;
		for(int l=0,k=0;l<n&&k<m;l++,k++){
			int p = -1;
			for(int i=k;i<m;i++){
				if( !eq(mat[i][l],0) && (p<0 || abs(mat[i][l]) > abs(mat[p][l]))){
					p = i;
				}
			}
			if(p<0){
				k--;continue;
			}
			double[] t=mat[k];mat[k]=mat[p];mat[p]=t;
			for(int j=n-1;j>=l;j--){
				mat[k][j]/=mat[k][l];
			}
			for(int i=k+1;i<m;i++){
				for(int j=n-1;j>=l;j--){
					mat[i][j] -= mat[i][l] * mat[k][j];
				}
			}
		}
	}
	double[] solve(double[][] mat){// matはsweepによって書き換えられる。解なしの判定はしていない
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

	public static void main(String[] args) {
		SweepA test = new SweepA();
		double[][] mat = { 
				{1,0,-1,0}, // x   - z    = 0
				{2,2,-3,-1},//2x+2y-3z- w = 0
				{2,0,0,2},  //2x      -2w = 0
				{0,1,-1,0}  //    y- z    = 0
		};
		double[] res = test.solve(mat);
		System.out.println(Arrays.toString(res));
	}
}
