package com.github.hiroshi_cl.wakaba.v2009.sol.d2005;
import java.util.*;
import java.io.*;
import java.math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class All {
	public static void main(String[] args)throws Exception {
		new A().run();
	}
	static void debug(Object... o){
		System.err.println(deepToString(o));
	}
	static class A{
		int INF=1<<29;
		double EPS=1e-9;
		void run(){
			Scanner sc=new Scanner(System.in);
			int o=sc.nextInt();
			while(o-->0){
				int M=sc.nextInt();
				int m=sc.nextInt(),n=sc.nextInt();
				double max=0;
				for(int i=0;i<n;i++){
					double res=M;
					if(sc.nextInt()==0){
						double d=sc.nextDouble();
						double sub=sc.nextDouble();
						int sum=0;
						for(int j=0;j<m;j++){
							sum+=(int)(res*d);
							res-=sub;
						}
						max=max(max,res+sum);
					}
					else{
						double d=sc.nextDouble();
						double sub=sc.nextDouble();
						for(int j=0;j<m;j++){
							res+=(int)(res*d)-sub;
						}
						max=max(max,res);
					}
				}
				System.out.println((int)max);
			}
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
				int n=sc.nextInt();
				if(n==0)break;
				int[][] xs=new int[n+1][];
				int[][] ys=new int[n+1][];
				int M=sc.nextInt();
				xs[0]=new int[M];
				ys[0]=new int[M];
				for(int j=0;j<M;j++){
					xs[0][j]=sc.nextInt();ys[0][j]=sc.nextInt();
				}
				for(int i=1;i<=n;i++){
					int m=sc.nextInt();
					xs[i]=new int[m];
					ys[i]=new int[m];
					for(int j=0;j<m;j++){
						//						debug(i,j,m);
						xs[i][j]=sc.nextInt();ys[i][j]=sc.nextInt();
					}
				}
				int[][] ls=new int[2][M-1];
				int[][] gs=new int[2][M-2];
				for(int j=0;j<M-1;j++){
					ls[0][j]=abs(xs[0][j]-xs[0][j+1])+abs(ys[0][j]-ys[0][j+1]);
					ls[1][M-j-2]=ls[0][j];
				}
				for(int j=0;j<M-2;j++){
					int x1=xs[0][j+1]-xs[0][j],y1=ys[0][j+1]-ys[0][j],x2=xs[0][j+2]-xs[0][j+1],y2=ys[0][j+2]-ys[0][j+1];
					gs[0][j]=signum(x1*y2-x2*y1);
					gs[1][M-j-3]=gs[0][j]*-1;
				}
				//				debug(ls,gs);
				for(int i=1;i<=n;i++){
					int m=xs[i].length;
					int[] nls=new int[m-1],ngs=new int[m-2];
					if(m==M){
						for(int j=0;j<m-1;j++){
							nls[j]=abs(xs[i][j]-xs[i][j+1])+abs(ys[i][j]-ys[i][j+1]);
						}  
						for(int j=0;j<m-2;j++){
							int x1=xs[i][j+1]-xs[i][j],y1=ys[i][j+1]-ys[i][j],x2=xs[i][j+2]-xs[i][j+1],y2=ys[i][j+2]-ys[i][j+1];
							ngs[j]=signum(x1*y2-x2*y1);
						}
						//						debug(nls,ngs);
						for(int j=0;j<2;j++){
							boolean ok=true;
							for(int k=0;k<m-1;k++){
								if(nls[k]!=ls[j][k])
									ok=false;
							}
							for(int k=0;k<m-2;k++){
								if(ngs[k]!=gs[j][k])
									ok=false;
							}
							if(ok){
								System.out.println(i);
								break;
							}
						}
					}

				}
				System.out.println("+++++");
			}
		}
		int signum(double d){
			return d<-EPS?-1:d>EPS?1:0;
		}
	}
	static class C{
		int INF=1<<29;
		double EPS=1e-9;
		void run(){
			Scanner sc=new Scanner(System.in);
			int o=sc.nextInt();
			char[] ls=new char[]{'m','c','x','i'};
			int[] ps=new int[]{1000,100,10,1};
			while(o-->0){
				char[][] cs=new char[2][];
				for(int i=0;i<2;i++)cs[i]=sc.next().toCharArray();
				int[] ns=new int[2];
				for(int i=0;i<2;i++)
					for(int j=0;j<cs[i].length;j++){
						int d=1;
						if('0'<=cs[i][j] && cs[i][j] <='9'){
							d=cs[i][j]-'0';j++;
						}
						for(int k=0;k<4;k++){
							if(cs[i][j]==ls[k]){
								ns[i]+=d*ps[k];
							}
						}
					}
				//				debug(ns);
				int n=ns[0]+ns[1];
				int[] ts=new int[4];
				int r=10;
				for(int i=3;i>=0;i--){
					ts[i]=n%r;
					n/=r;
				}
				//				debug(ts);
				String s="";
				for(int i=0;i<4;i++){
					if(ts[i]==0)continue;
					if(ts[i]==1){
						s=s+ls[i];
					}
					else
						s+=ts[i]+""+ls[i];
				}
				System.out.println(s);
			}
		}
		int signum(double d){
			return d<-EPS?-1:d>EPS?1:0;
		}
	}
	static class D{
		int INF=1<<20;
		double EPS=1e-9;
		void run(){
			Scanner sc=new Scanner(System.in);
			for(;;){
				int n=sc.nextInt(),m=sc.nextInt(),p=sc.nextInt(),a=sc.nextInt()-1,b=sc.nextInt()-1;
				if(n+m+p==0)break;
				double[][] f=new double[m][m];
				for(int i=0;i<m;i++)fill(f[i],INF);
				double[] ts=new double[n];
				for(int i=0;i<n;i++){
					ts[i]=sc.nextInt();
				}
				sort(ts);
				for(int i=0;i<p;i++){
					int x=sc.nextInt()-1,y=sc.nextInt()-1;
					double z=sc.nextDouble();
					f[x][y]=z;
					f[y][x]=z;
				}
				//				for(int i=0;i<m;i++)
				//				debug(f[i]);
				double[][][] dp=new double[n+1][m][1<<n];
				for(int i=0;i<n+1;i++)for(int j=0;j<m;j++)fill(dp[i][j],INF);
				dp[0][a][0]=0;
				for(int i=0;i<n;i++){
					for(int j=0;j<m;j++){
						for(int k=0;k<(1<<n);k++){
							if(dp[i][j][k]<INF){
								for(int j2=0;j2<m;j2++){
									if(j==j2)continue;
									if(f[j][j2]<INF){
										for(int l=0;l<n;l++){
											if((k&(1<<l))==0){
												dp[i+1][j2][k|(1<<l)] = min(dp[i+1][j2][k|(1<<l)],dp[i][j][k] + f[j][j2]/ts[l]);
											}
										}
									}
								}
							}
						}
					}
				}
				double res=INF;
				for(int i=0;i<n+1;i++){
					for(int k=0;k<(1<<n);k++){
						res=min(res,dp[i][b][k]);
					}
					//					for(int j=0;j<m;j++)
					//					debug(i,j,dp[i][j]);
				}
				System.out.println(res==INF?"Impossible":res);
			}
		}
		int signum(double d){
			return d<-EPS?-1:d>EPS?1:0;
		}
	}
	static class E{
		int nextInt(){
			int res=0;
			int c=0;
			do{
				try{
					c=System.in.read();
					if(c==-1)return -1;
					if(c=='-')return -nextInt();
				}catch (Exception e) {
				}
			}while(c<'0'||c>'9');
			do{
				res*=10;
				res+=c-'0';
				try{
					c=System.in.read();
				}catch (Exception e) {
				}
			}while('0'<=c&&c<='9');
			return res;
		}
		int MAXLENGTH=9;
		String next(){
			char[] cs=new char[MAXLENGTH];
			int c=0;
			do{
				try{
					c=System.in.read();
					if(c==-1)return null;
				}catch (Exception e) {
				}
			}while(Character.isWhitespace(c));
			int i=0;
			do{
				cs[i++]=(char)c;
				try{
					c=System.in.read();
				}catch (Exception e) {
				}
			}while(!Character.isWhitespace(c));
			return new String(cs,0,i);
		}
		int INF=1<<29;
		double EPS=1e-9;
		int R,T;
		void run() {
			for(;;){
				int n=nextInt();T=nextInt();R=nextInt();
				if(n+T+R==0)return;
				long ct=System.currentTimeMillis();

				Robot[] robots=new Robot[n];
				for(int i=0;i<n;i++){
					robots[i]=new Robot(i,next());
					int t=nextInt();//0
					robots[i].pxs[0]=nextInt();
					robots[i].pys[0]=nextInt();
					for(;;){
						int nt=nextInt();
						int nvx=nextInt(),nvy=nextInt();
						for(;t<nt;t++){
							robots[i].vxs[t]=nvx;
							robots[i].vys[t]=nvy;
							robots[i].pxs[t+1]=robots[i].pxs[t]+robots[i].vxs[t];
							robots[i].pys[t+1]=robots[i].pys[t]+robots[i].vys[t];
						}
						if(nt==T)break;
					}
				}
				ArrayList<Entry>es=new ArrayList<Entry>();
				for(int i=0;i<n;i++){
					for(int j=i+1;j<n;j++){
						for(int t=0;t<T;t++){
							Entry e=robots[i].timeOfChange(robots[j], t);
							if(e!=null){
								es.add(e);
							}
						}
					}
				}
				simu(robots,es);
				ArrayList<String> res=new ArrayList<String>();
				for(int i=0;i<n;i++){
					if(robots[i].hasData)res.add(robots[i].nickname);
				}
				sort(res);
				for(String s:res){
					System.out.println(s);
				}
			}
		}
		void simu(Robot[] robots,ArrayList<Entry> es){
			int n=robots.length;
			sort(es);
			robots[0].hasData=true;
			double t=0;
			for(int i=0;i<n;i++){
				robots[i].nt=t;
				robots[i].npx=robots[i].pxs[0];
				robots[i].npy=robots[i].pys[0];
			}
			HashSet<Integer> is=new HashSet<Integer>();
			for(int i=1;i<n;i++)is.add(i);
			for(int k=0;k<n;k++){
				for(int i=0;i<n;i++){
					if(robots[i].hasData){
						for(int j:is){
							if(robots[i].near(robots[j])){
								robots[j].hasData=true;
							}
						}
					}
				}
			}
			for(int i=1;i<n;i++)if(robots[i].hasData)is.remove(i);
			for(Entry e:es){
				for(int i=0;i<n;i++){
					if(robots[i].hasData)is.remove(i);
				}
				for(int i=0;i<n;i++){
					robots[i].go(e.t);
				}
				for(int i=0;i<n;i++){
					if(robots[i].hasData){
						for(int j:is){
							if(robots[i].near(robots[j])){
								robots[j].hasData=true;
							}
						}
					}
				}
			}
			for(int i=0;i<n;i++){
				robots[i].nt=T;
				robots[i].npx=robots[i].pxs[T];
				robots[i].npy=robots[i].pys[T];
			}
			for(int k=0;k<n;k++){
				for(int i=0;i<n;i++){
					if(robots[i].hasData){
						for(int j=0;j<n;j++){
							if(robots[i].near(robots[j])){
								robots[j].hasData=true;
							}
						}
					}
				}
			}
		}
		void search(Robot[] robots,int id){
			int n=robots.length;
			HashSet<Integer> is=new HashSet<Integer>();
			for(int i=0;i<n;i++)if(!robots[i].hasData)is.add(i);
			Queue<Integer>q=new LinkedList<Integer>();
			q.offer(id);
			is.remove(id);
			while(!q.isEmpty()){
				id=q.poll();
				if(is.isEmpty())break;
				for(int j=0;j<is.size();j++){
					if(is.isEmpty())break;
					for(int i:is){
						if(robots[i].near(robots[id])){
							is.remove(i);
							q.offer(i);
							break;
						}
					}
				}
			}
		}
		class Entry implements Comparable<Entry>{
			double t;
			int i,j;
			public int compareTo(Entry o) {
				return t<o.t?-1:1;
			}
			Entry(double t,int i,int j){
				this.t=t;this.i=i;this.j=j;
			}
			public String toString() {
				return t+" "+i+" "+j;
			}
		}
		class Robot{
			int id;
			String nickname;
			boolean hasData=false;
			double npx,npy;
			double nt;
			double[] pxs,pys;
			double[] vxs,vys;
			Robot(int id,String nickname){
				this.id=id;this.nickname=nickname;
				pxs=new double[T+1];pys=new double[T+1];
				vxs=new double[T];vys=new double[T];
			}
			void go(double t){
				if(t>(int)nt+1){
					npx+=(vxs[(int)nt])*((int)nt+1-nt);
					npy+=(vys[(int)nt])*((int)nt+1-nt);
					int i=(int)nt+1;
					for(;i<t-1;i++){
						npx+=vxs[i];
						npy+=vys[i];
					}
					npx+=vxs[i]*(t-i);
					npy+=vys[i]*(t-i);
				}
				else{
					npx+=(vxs[(int)nt])*(t-nt);
					npy+=(vys[(int)nt])*(t-nt);
				}
				nt=t;
			}
			boolean near(Robot rb){
				return (rb.npx-npx)*(rb.npx-npx)+(rb.npy-npy)*(rb.npy-npy)<R*R+EPS;
			}
			Entry timeOfChange(Robot rb,double td){
				td+=EPS;
				int ti=(int)(td);
				double 
				px  =    pxs[ti] + vxs[ti]*(td-ti),
				py  =    pys[ti] + vys[ti]*(td-ti);
				double 
				opx = rb.pxs[ti] + rb.vxs[ti]*(td-ti),
				opy = rb.pys[ti] + rb.vys[ti]*(td-ti); 
				double dpx=px-opx,dpy=py-opy;
				double 
				dvx = vxs[ti] - rb.vxs[ti],
				dvy = vys[ti] - rb.vys[ti];
				double 
				a=dvx*dvx+dvy*dvy,
				b=dpx*dvx+dpy*dvy,
				c=dpx*dpx+dpy*dpy-R*R;
				if(b*b-a*c<0)return null;
				double dt1=(-b+sqrt(b*b-a*c))/a,dt2=(-b-sqrt(b*b-a*c))/a;
				if(0<=dt1&&dt1<=ti+1-td){
					return new Entry(td+dt1,id,rb.id);
				}
				if(0<=dt2&&dt2<=ti+1-td){
					return new Entry(td+dt2,id,rb.id);
				}
				return null;
			}
		}
	}
	static class F{
		int INF=1<<29;
		double EPS=1e-9;
		void run(){
			Scanner sc=new Scanner(System.in);
			for(;;){
				int w=sc.nextInt(),h=sc.nextInt();
				if(h==0&w==0)break;
				char[][] f=new char[h][w];
				int nx=0,ny=0;
				int[] gx=new int[10],gy=new int[10]; 
				int ng=0;
				int[][] hh=new int[h][w];
				for(int i=0;i<h;i++)fill(hh[i],-1);
				for(int i=0;i<h;i++){
					f[i]=sc.next().toCharArray();
					for(int j=0;j<w;j++){
						if(f[i][j]=='o'){
							nx=i;ny=j;
						}
						if(f[i][j]=='*'){
							gx[ng]=i;gy[ng]=j;
							hh[i][j]=ng;
							ng++;
						}
					}
				}
				int[] dx=new int[]{1,0,-1,0},dy=new int[]{0,1,0,-1};
				Queue<Integer> qx=new LinkedList<Integer>(),qy=new LinkedList<Integer>(),
				qg=new LinkedList<Integer>(),qv=new LinkedList<Integer>();
				qx.offer(nx);qy.offer(ny);qg.offer((1<<ng)-1);qv.offer(0);
				int res=-1;
				int[][][] steps=new int[h][w][(1<<ng)];
				for(int i=0;i<h;i++)for(int j=0;j<w;j++)fill(steps[i][j],INF);
				steps[nx][ny][(1<<ng)-1]=0;
				while(!qx.isEmpty()){
					nx=qx.poll();ny=qy.poll();int g=qg.poll(),v=qv.poll();
					if(g==0){
						res=v;break;
					}
					for(int i=0;i<4;i++){
						int nnx=nx+dx[i],nny=ny+dy[i];
						if(!(0<=nnx && nnx<h && 0<=nny && nny<w))continue;
						if(f[nnx][nny]=='x')continue;
						int nng=g;
						int id=hh[nnx][nny];
						if(f[nnx][nny]=='*')
							if((nng&(1<<id))!=0)nng-=1<<id;
						if(steps[nnx][nny][nng]>v+1){
							steps[nnx][nny][nng]=v+1;
							qx.offer(nnx);qy.offer(nny);
							qg.offer(nng);qv.offer(v+1);
						}
					}
				}
				System.out.println(res);
			}
		}
		int signum(double d){
			return d<-EPS?-1:d>EPS?1:0;
		}
	}
}

