package com.github.hiroshi_cl.wakaba.v2010.sol.r2007;
import java.util.*;

import static java.util.Arrays.*;
import static java.lang.Math.*;
import static java.util.Collections.*;
public class E {
	public static void main(String[] args) {
		new E().run();
	}
	static void debug(Object... o){
		System.err.println(deepToString(o));
	}
	void run(){
		Scanner sc=new Scanner(System.in);
		for(;;){
			int n=sc.nextInt();
			if(n==0)return;
			Point start=new Point(sc.nextDouble(),sc.nextDouble()) , goal=new Point(sc.nextDouble(),sc.nextDouble());
			Line[] ls=new Line[n];
			for(int i=0;i<n;i++){
				ls[i]=new Line(new Point(sc.nextDouble(),sc.nextDouble()),new Point(sc.nextDouble(),sc.nextDouble()));
			}
			int lineCount=0;
			ArrayList<Line> lines=new ArrayList<Line>() , signs=new ArrayList<Line>();
			ArrayList<Point> a=new ArrayList<Point>() ;
			for(int i=0;i<n;i++){
				boolean b1=false,b2=false;
				for(int j=0;j<n;j++){
					if(j==i)continue;
					if(ls[j].on(ls[i].p1))b1=true;
					if(ls[j].on(ls[i].p2))b2=true;
				}
				ls[i].isLine = (b1&b2);
				if(ls[i].isLine){
					lineCount++;
					lines.add(ls[i]);
					if(!contains(a,ls[i].p1))a.add(ls[i].p1);
					if(!contains(a,ls[i].p2))a.add(ls[i].p2);
				}
				else
					signs.add(ls[i]);
			}
			//                debug(a);
			//                debug(lines,signs);
			int m=a.size();
			double[][] f=new double[m][m];
			int sid=0 , gid=0;
			for(int i=0;i<m;i++){
				if(a.get(i) . equals(start))sid=i;
				if(a.get(i) . equals(goal))gid=i;
			}
			//                debug(sid,gid);
			init(f,lines,signs,a);
			//                debug(f);
			int[] bef=new int[m];
			for(int i=0;i<m;i++)bef[i]=i;
			double[] f2=new double[m];
			fill(f2,INF);
			f2[sid]=0;
			for(;;){
				boolean changed=false;
				for(int i=0;i<m;i++){
					if(f2[i]<INF){
						for(int j=0;j<m;j++){
							if(f2[i] + f[i][j] < f2[j]){
								f2[j]=f2[i]+f[i][j];
								bef[j]=i;
								changed=true;
							}
						}
					}
				}
				if(!changed)
					break;
			}
			//                debug(bef);
			if(f2[gid]<INF){
				int nid=gid;
				Stack<Integer> s=new Stack<Integer>();
				for(;;){
					s.push(nid);
					if(nid==sid)break;
					nid=bef[nid];
				}
				while(!s.isEmpty()){
					int id=s.pop();
					System.out.println((int)(a.get(id).x+EPS)+" "+(int)(a.get(id).y+EPS));
				}
				System.out.println(0);
			}
			else{
				System.out.println(-1);
			}
		}
	}
	int idOfPoint(ArrayList<Point> ps,Point p){
		for(int i=0;i<ps.size();i++){
			if(ps.get(i).equals(p))return i;
		}
		return -1;
	}
	boolean contains(ArrayList<Point> a , Point p){
		for(Point q:a){
			if(q.equals(p)){
				return true;
			}
		}
		return false;
	}
	int INF=1<<27;
	void init(double[][] f , ArrayList<Line> ls , ArrayList<Line> ss,ArrayList<Point> ps){
		int m=f.length;
		for(int i=0;i<m;i++)fill(f[i],INF);
		for(int i=0;i<m;i++){
			for(int j=0;j<m;j++){
				if(canGo(ps.get(i) , ps.get(j) , ls , ss)){
					f[i][j]=ps.get(i).dis(ps.get(j));
				}
			}
		}
	}
	boolean canGo(Point p1,Point p2,ArrayList<Line> ls , ArrayList<Line> ss){
		for(Line l:ls){
			if(l.on(p1) && l.on(p2)){
				for(Line s:ss){
					Point[] qs=new Point[]{s.p1  , s.p2};
					for(int i=0;i<2;i++){
						Point p3=qs[i%2] , p4=qs[(i+1)%2];
						Point v1=p3.sub(p1) , v2=p2.sub(p1) , v3=p4.sub(p3);
						if( eq ( v1.dot(v2) , v1.length()*v2.length()  )  && signum(v2.length()-v1.length())>=0 && signum(v1.dot(v3)) >=0  ){
							return false;
						}
					}
				}
				Line p1p2=new Line(p1,p2);
				for(Line l2:ls){
					if( !l2.p1.equals(p1) && !l2.p1.equals(p2) && p1p2.on(l2.p1) )return false;
					if( !l2.p2.equals(p1) && !l2.p2.equals(p2) && p1p2.on(l2.p2) )return false;
				}
				return true;
			}
		}
		return false;
	}
	boolean eq(double a,double b){
		return signum(a-b)==0;
	}
	class Line{
		public String toString() {
			return "{"+p1.x+" "+p1.y+" "+p2.x+" "+p2.y+"}";
		}
		boolean isLine;
		Point p1,p2;
		boolean on(Point p3){
			Point v1=p3.sub(p1) , v2=p2.sub(p1);
			return( eq ( v1.dot(v2) , v1.length()*v2.length()  )  && signum(v2.length()-v1.length())>=0 );
		}
		Line(Point p1,Point p2){
			this.p1=p1;this.p2=p2;
			isLine=false;
		}
		double length(){
			return p1.dis(p2);
		}
	}
	double EPS=1e-7;
	class Point{
		public String toString() {
			return "{"+x+" "+y+"}";
		}
		double length(){
			return hypot(x,y);
		}
		double dot(Point o){
			return o.x*x + o.y*y;
		}
		Point sub(Point o){
			return new Point(x-o.x , y-o.y);
		}
		double dis(Point o){
			return hypot(x-o.x,y-o.y);
		}
		double hypot(double x,double y){
			return sqrt(x*x+y*y);
		}
		double x,y;
		int id;
		Point(double x,double y){
			this.x=x;this.y=y;
		}
		boolean equals(Point o){
			return signum(o.x-x)==0 && signum(o.y-y)==0;
		}
	}
	int signum(double d){
		return d<-EPS?-1 : d>EPS?1 : 0;
	}
}

