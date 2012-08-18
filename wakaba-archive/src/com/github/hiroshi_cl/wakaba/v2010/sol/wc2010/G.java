package com.github.hiroshi_cl.wakaba.v2010.sol.wc2010;
import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
public class G {
	public void run() {
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		L[][] ls=new L[n][4];
		for(int i=0;i<n;i++){
			P[] ps=new P[4];
			int x0 = sc.nextInt(),y0=sc.nextInt(),x1=sc.nextInt(),y1=sc.nextInt();
			ps[0] = new P(x0,y0);
			ps[1] = new P(x1,y0);
			ps[2] = new P(x1,y1);
			ps[3] = new P(x0,y1);
			for(int j=0;j<4;j++){
				ls[i][j] = new L(ps[j],ps[(j+1)%4]);
			}
		}
		for(int i=0;i<4;i++){
			
		}
	}
	class CmpX implements Comparator<L>{
		@Override
		public int compare(L o1, L o2) {
			// TODO 自動生成されたメソッド・スタブ
			return 0;
		}
	}

	// class CmpY implements Comparator<L>{
	//
	// }
	class L {
		L to;
		P p1,p2;
		public L(P p1, P p2) {
			super();
			this.p1 = p1;
			this.p2 = p2;
		}
		
	}
	class P implements Comparable<P>{
		int x,y;
		@Override
		public int compareTo(P o) {
			// TODO 自動生成されたメソッド・スタブ
			int d = x-o.x;
			return d==0 ? y-o.y:d;
		}
		public P(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
	}
}
