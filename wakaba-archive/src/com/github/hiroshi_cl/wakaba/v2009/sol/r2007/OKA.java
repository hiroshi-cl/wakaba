package com.github.hiroshi_cl.wakaba.v2009.sol.r2007;
import java.util.*;

import static java.util.Arrays.*;
import static java.lang.Math.*;
import static java.util.Collections.*;
public class OKA {
	public static void main(String[] args) {
		new H().run();
	}
	static void debug(Object... o){
		System.err.println(deepToString(o));
	}
	static class A{
		void run(){
			Scanner sc=new Scanner(System.in);
			
		}
	}
	static class B{
		void run(){
			Scanner sc=new Scanner(System.in);
			
		}
	}
	static class C{
		void run(){
			Scanner sc=new Scanner(System.in);
			
		}
	}
	static class D{
		void run(){
			Scanner sc=new Scanner(System.in);
			
		}
	}
	static class E{
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
				//				debug(a);
				//				debug(lines,signs);
				int m=a.size();
				double[][] f=new double[m][m];
				int sid=0 , gid=0;
				for(int i=0;i<m;i++){
					if(a.get(i) . equals(start))sid=i;
					if(a.get(i) . equals(goal))gid=i;
				}
				//				debug(sid,gid);
				init(f,lines,signs,a);
				//				debug(f);
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
				//				debug(bef);
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
	static class F{
		void run(){
			Scanner sc=new Scanner(System.in);

		}
	}
	static class G{
		void run(){
			Scanner sc=new Scanner(System.in);
			for(;;){
				int w=sc.nextInt(),h=sc.nextInt();
				int n=sc.nextInt();
				sc.nextLine();
				if(w+h+n==0)return;
				String[] tmpss=new String[h];
				for(int i=0;i<h;i++)tmpss[i]=sc.nextLine();
				char[][] cs=new char[16][16];
				for(int i=0;i<16;i++)fill(cs[i],'#');
				int[] xs=new int[n],ys=new int[n];
				int[] gxs=new int[n] , gys=new int[n];
				for(int i=0;i<16;i++){
					for(int j=0;j<16;j++){
						if(i<h && j<w){
							cs[i][j]=tmpss[i].charAt(j);
							for(int k=0;k<n;k++){
								if(cs[i][j]=='A'+k){
									xs[k]=i ; ys[k]=j;
								}
								if(cs[i][j]=='a'+k){
									gxs[k]=i;gys[k]=j;
								}
							}
						}
					}
				}
				//				for(int i=0;i<16;i++)debug(new String(cs[i]));
				//				debug();
				w=16;h=16;
				HashMap<Integer, Integer> hm=new HashMap<Integer, Integer>();
				int ini=0;
				int goal=0;
				for(int i=0;i<n;i++){
					ini += (1<<(i*8)) * (xs[i]+16*ys[i]);
					goal +=(1<<(i*8)) * (gxs[i]+16*gys[i]);
				}
				Queue<Integer> q=new LinkedList<Integer>();
				q.offer(ini);
				hm.put(ini, 0);
				while(!q.isEmpty()){
					int nc=q.poll();
					if(nc==goal){
						System.out.println(hm.get(nc));
						break;
					}

				}
			}
		}
	}
	static class H{
		void run(){
			Scanner sc=new Scanner(System.in);
			for(;;){
				String[] ss=new String[1001];
				int n=0;
				for(n=0;;n++){
					ss[n]=sc.next();
					if(ss[n].equals("."))break;
				}
				if(n==0)return;
				Program pr=new Program();
				int i=0;
				for(i=0;i<n;i++){
					if(!pr.run(ss[i])){
						System.out.println(i+1);
						break;
					}
				}
				if(i==n){
					System.out.println(0);
				}
			}
		}
		class Program{
			int[] size=new int[26*2];
			int[][] ids=new int[26*2][1000];
			int[] maxids=new int[26*2];     //定義済みidの個数
			int[][] vals=new int[26*2][1000];
			Program(){
				fill(size,-1);
			}
			boolean run(final String s){
//				System.err.println();
//				System.err.println("run("+s+")");
				String[] ss=s.split("=");
				if(ss.length==1){
					size[name(ss[0])] = Integer.valueOf(ss[0].substring(2,s.length()-1));
//					System.err.println("setsize "+name(ss[0])+" -> " + size[name(ss[0])]);
					return true;
				}
				else{
					int right=-1;

					int[] names=new int[2];
					names[0]=name(ss[0]);
					int[] t_ids=new int[2];
					t_ids[0]=calcId(ss[0]);
					if(isNum(ss[1].charAt(0))){
						right=Integer.valueOf(ss[1]);
					}
					else{
						names[1]=name(ss[1]);
						t_ids[1]=calcId(ss[1]);
						if(t_ids[1]<0)return false;
						right=calcVal(names[1], t_ids[1]);
						if(right<0)return false;
						if(size[names[1]]<0){
//							debug(names[1]+"is not valid");
							return false;
						}
					}
					return setVal(names[0],t_ids[0],right);
				}
			}
			boolean setVal(int name,int id,int val){ //idが範囲を超えていたら、false を返す。
				if(size[name]<0){
//					System.err.println(name+" is not defined");
					return false;
				}
				if(id>=size[name]){
//					System.err.println(name+"["+id+"]'s index is too big");
					return false;
				}
				for(int i=0;i<maxids[name];i++){
					if(ids[name][i]==id){
						vals[name][i]=val;
//						debug(name+"[" +id+"] = "+val);
//						debug(vals[name],ids[name],maxids[name]);
						return true;
					}
				}
//				debug(name+"[" +id+"] = "+val);
				ids[name][maxids[name]] = id;
				vals[name][maxids[name]] = val;
				maxids[name]++;
//				debug(vals[name]);debug(ids[name]);debug(maxids[name]);
				return true;
			}
			int calcVal(int name,int id){ //バグってたら、-1を返す。
				if(id>=size[name]){
//					System.err.println(name+"["+id+"] is index of arrays error");
					return -1;
				}
				for(int i=0;i<maxids[name];i++){
					if(ids[name][i]==id){
						return vals[name][i];
					}
				}
//				System.err.println(name+"["+id+"] is not defined");
				return -1;
			}
			int calcId(String s){ //バグってたら、-1を返す。返す値のチェックのみ、行わない。
//				System.err.println("calculating "+s);
				String t=s.substring(2,s.length()-1);
				//				debug(t);
				if(isNum(t.charAt(0))){
//					debug(s+"'s id="+Integer.valueOf(t));
					return Integer.valueOf(t);
				}
				int name=name(t);
				int id=calcId(t);
//				System.err.println("calcVal("+name+","+id+")="+calcVal(name,id));
				if(id<0)return -1;
				return calcVal(name,id);
			}
			int name(String s){
				return name2int(s.charAt(0));
			}
			int name2int(char c){
				if(Character.isLowerCase(c)){
					return c-'a';
				}
				else{
					return c-'A'+26;
				}
			}
		}
		boolean isNum(char c){
			return '0'<=c && c<='9';
		}
	}
	static class I{
		void run(){
			Scanner sc=new Scanner(System.in);
			
		}
	}
	static class J{
		void run(){
			Scanner sc=new Scanner(System.in);
			
		}
	}
}
