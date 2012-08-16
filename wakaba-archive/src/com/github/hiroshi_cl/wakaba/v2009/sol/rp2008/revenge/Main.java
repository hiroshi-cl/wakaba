package com.github.hiroshi_cl.wakaba.v2009.sol.rp2008.revenge;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
public class Main {
	public static void main(String[] args) {
		new D().run();
	}
	static void debug(Object... o){
		System.err.println(Arrays.deepToString(o));
	}
	static class A{
		int INF=1<<29;
		double EPS=1e-9;
		void run(){
			Scanner sc=new Scanner(System.in);
			for(;;){
				int a=sc.nextInt(),b=sc.nextInt();
				if(a+b==0)return;
				int ca=calc(a),cb=calc(b);
//				debug(ca,cb);
				System.out.println(ca>cb ? 'a' : 'b');
			}
		}
		int calc(int num){
			ArrayList<Integer> a=new ArrayList<Integer>();
			for(int p=2;p*p<=num;p++){
				if(num%p==0){
					a.add(p);
					while(num%p==0)num/=p;
				}
			}
			if(num!=1 && !a.contains(num) ) a.add(num);
//			debug(a);
			int n=a.size();
			int res=a.get(n-1);
			for(int i=n-2;i>=0;i--){
				res-=a.get(i);
			}
			return res;
		}
		int signum(double d){
			return d<-EPS?-1:d>EPS?1:0;
		}
	}
	static class B{
		int INF=1<<29;
		double EPS=1e-9;
		void run(){
			Scanner sc=new Scanner(System.in);
			for(;;){
				int x=sc.nextInt(),y=sc.nextInt();
				if(x+y==0)return;
				x%=y;
				int gcd=gcd(x,y);
				y/=gcd;x/=gcd;
				if(x==y){
					System.out.println("0 0");continue;
				}
				int a=0,b=0;
				while(y%2==0){
					a++;y/=2;
				}
				while(y%5==0){
					b++;y/=5;
				}
				System.out.println(max(a,b)+" "+calc(y));
			}
		}
		int calc(int q){
			if(q==1)return 0;
			int tot=totient(q);
			for(int i=1;i*i<=q;i++){
				if(tot%i==0){
					if(pow(10,i,q) == 1){
						return i;
					}
				}
			}
			return tot;
		}
		int pow(int p,int q,int mod){
			int res=1;
			while(q>0){
				if(q%2==1){
					res*=p;res%=mod;
				}
				q/=2;
				p=p*p%mod;
			}
			return res;
		}
		int signum(double d){
			return d<-EPS?-1:d>EPS?1:0;
		}
		int totient(int n){ 
			int res=0;
			for(int i=1;i<n;i++){
				if(gcd(i,n)==1)res++;
			}
			return res;
		}
		int gcd(int a,int b){
			return b==0?a:gcd(b,a%b);
		}
	}
	static class C{
		int INF=1<<29;
		double EPS=1e-9;
		void run(){
			Scanner sc=new Scanner(System.in);
			
		}
		int signum(double d){
			return d<-EPS?-1:d>EPS?1:0;
		}
	}
	static class D{
		int INF=1<<29;
		double EPS=1e-7;
		void run(){
			Scanner sc=new Scanner(System.in);
			for(;;){
				int n=sc.nextInt();
				if(n==0)return;
				Point[] ss=new Point[n],ts=new Point[n];
				for(int i=0;i<n;i++)ss[i]=new Point(sc.nextDouble(),sc.nextDouble());
				for(int i=0;i<n;i++)ts[i]=new Point(sc.nextDouble(),sc.nextDouble());
				if(n==1){
					System.out.println(0);continue;
				}
				double l=ss[0].dist(ss[1]);
				Point a=ss[1].sub(ss[0]);
				if(n==2){
					Point b=ts[1].sub(ts[0]);
					System.out.println(min (acos( a.dot(b)/(a.norm()*b.norm()) * -1) , acos( a.dot(b)/(a.norm()*b.norm()) * 1) ));
				}
				double res=PI;
				for(int i=0;i<n;i++){
					for(int j=0;j<n;j++){
						if(eq(ts[i].dist(ts[j]) , l)){
							Point b=ts[j].sub(ts[i]);
							for(int k=-1;k<=1;k+=2){
								
								double dif = acos( a.dot(b)/(a.norm()*b.norm()) * k);
//								debug(dif);
								for(int p=2;p<n;p++){
									boolean ok=false;
									for(int q=0;q<n;q++){
										if(q==i||q==j)continue;
										if(eq(ts[i].dist(ts[q]) , ss[0].dist(ss[p])) && 
												eq( b.dot(ts[q].sub(ts[i])) , a.dot(ss[p].sub(ss[0])) ) ){
											ok=true;
											debug(i,j,k,p,q,dif);
										}
									}
									if(!ok){
										dif=10;
										break;
									}
								}
								res=min(res,dif);
							}
						}
					}
				}
				System.out.println(res);
			}
		}
		boolean eq(double a,double b){
			return signum(a-b)==0;
		}
		class Point{
			double x,y;
			Point(double x,double y){
				this.x=x;this.y=y;
			}
			double norm(){
				return hypot(x,y);
			}
			double dist(Point o){
				return hypot(o.x-x,o.y-y);
			}
			double hypot(double x,double y){
				return sqrt(x*x + y*y);
			}
			double dot(Point o){
				return x*o.x+y*o.y;
			}
			Point sub(Point o){
				return new Point(x-o.x , y-o.y);
			}
		}
		int signum(double d){
			return d<-EPS?-1:d>EPS?1:0;
		}
	}
	static class E{
		int INF=1<<29;
		double EPS=1e-9;
		void run(){
			Scanner sc=new Scanner(System.in);
			
		}
		int signum(double d){
			return d<-EPS?-1:d>EPS?1:0;
		}
	}
	static class F{
		int INF=1<<29;
		double EPS=1e-9;
		void run(){
			Scanner sc=new Scanner(System.in);
			
		}
		int signum(double d){
			return d<-EPS?-1:d>EPS?1:0;
		}
	}
	static class G{
		int INF=1<<29;
		double EPS=1e-9;
		void run(){
			Scanner sc=new Scanner(System.in);
			
		}
		int signum(double d){
			return d<-EPS?-1:d>EPS?1:0;
		}
	}
	static class H{
		int INF=1<<29;
		double EPS=1e-9;
		void run(){
			Scanner sc=new Scanner(System.in);
			
		}
		int signum(double d){
			return d<-EPS?-1:d>EPS?1:0;
		}
	}
	static class I{
		int INF=1<<29;
		double EPS=1e-9;
		void run(){
			Scanner sc=new Scanner(System.in);
			
		}
		int signum(double d){
			return d<-EPS?-1:d>EPS?1:0;
		}
	}
	static class J{
		int INF=1<<29;
		double EPS=1e-9;
		void run(){
			Scanner sc=new Scanner(System.in);
			
		}
		int signum(double d){
			return d<-EPS?-1:d>EPS?1:0;
		}
	}
}
