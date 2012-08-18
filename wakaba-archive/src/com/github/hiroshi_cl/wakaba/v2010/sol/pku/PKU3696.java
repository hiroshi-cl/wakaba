package com.github.hiroshi_cl.wakaba.v2010.sol.pku;
import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
public class PKU3696 {
	static final int INF=1<<29;
	static final double EPS = 1e-10;
	int signum(double d) {
		return (d < -EPS) ? -1 : (d > EPS) ? 1 : 0;
	}
	public static void main(String[] args){
		new PKU3696().run();
	}
	void run(){
		Scanner sc=new Scanner(System.in);
		for(int o=1;;o++){
			long n=sc.nextLong();
			if(n==0)return;
			System.out.printf("Case %d: ",o);
			if(n%5==0||n%16==0){
				System.out.println(0);
				continue;
			}
			while(n%2==0)n/=2;
			System.out.println(calcmin(n));
		}
	}
	long calcmin(long n){
		long m=totient(n*9);
		long i;
		long res=m;
		if(mod(10,1,n*9)==1)return 1;
		for(i=2;i*i<=m;i++){
			if(m%i==0){
				if(mod(10,i,n*9)==1)return i;
				if(mod(10,m/i,n*9)==1)res=min(res,m/i);
			}
		}
		return res;
	}
	long mod(long a,long b,long n){
		a%=n;
		long res=1;
		for(;b>0;b>>=1){
			if((b&0x1)==1)res=product(res, a, n);
			a=product(a, a, n);
		}
		return res;
	}
	long product(long x,long y,long n){
		if(x<=Integer.MAX_VALUE&&y<=Integer.MAX_VALUE)return x*y%n;
		long res=0;
		long nx=x%n;
		for(;y>0;y>>=1){
			if((y&0x1)==1)res=(res+nx)%n;
			nx=(nx<<1)%n;
		}
		return res;
	}
	long totient(long n){
		if(n==0)return 0;
		long ans=n;
		for(int i=2;i*i<=n;i++){
			if(n%i==0){
				ans-=ans/i;
				while(n%i==0)n/=i;
			}
		}
		if(n>1)ans-=ans/n;
		return ans;
	}
	void debug(Object... o){
		System.err.println(deepToString(o));
	}
}