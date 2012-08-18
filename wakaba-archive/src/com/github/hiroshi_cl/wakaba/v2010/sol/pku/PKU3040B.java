package com.github.hiroshi_cl.wakaba.v2010.sol.pku;
import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
public class PKU3040B {
	static final double EPS = 1e-10;
	int signum(double d) {
		return (d < -EPS) ? -1 : (d > EPS) ? 1 : 0;
	}
	public static void main(String[] args){
		new PKU3040B().run();
	}
	void run(){
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt(),c=sc.nextInt();
		Entry[] es=new Entry[n];
		for(int i=0;i<n;i++)es[i]=new Entry(sc.nextLong(),sc.nextLong());
		sort(es);
		System.out.println(calc(es,c));
	}
	int calc(Entry[] es,int c){
		int n=es.length;
		long[] sums=new long[n];
		sums[0]=es[0].v*es[0].b;
		for(int i=0;i<n-1;i++){
			sums[i+1]=sums[i]+es[i+1].v*es[i+1].b;
		}
		if(sums[n-1]<c)return 0;
		int tc=c;
		long[] is=new long[n];
		for(int i=n-1;i>=0;i--){
			is[i] = min(es[i].b , tc / es[i].v );
			tc -= is[i] * es[i].v;
			if(tc>0&&es[i].b>0&&es[i].v>tc){
				if(i==0||sums[i-1]<tc){
					is[i]+=1;
					tc-=es[i].v;
					break;
				}
			}
		}
		if(tc>0)return 0;
		long res=Long.MAX_VALUE;
		for(int i=0;i<n;i++){
			if(is[i]>0)
				res=min(res,es[i].b/is[i]);
		}
		for(int i=0;i<n;i++){
			es[i].b-=res*is[i];
		}
		return (int)res+calc(es,c);
	}
	class Entry implements Comparable<Entry>{
		long v,b;
		Entry(long v,long b){
			this.v=v;this.b=b;
		}
		public int compareTo(Entry o) {
			return (int)(v-o.v);
		}
		public String toString() {
			return v+" "+b;
		}
	}
	void debug(Object... o){
		System.err.println(deepToString(o));
	}
}
