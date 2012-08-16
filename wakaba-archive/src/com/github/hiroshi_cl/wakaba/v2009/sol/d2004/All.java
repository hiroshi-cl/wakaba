package com.github.hiroshi_cl.wakaba.v2009.sol.d2004;
import java.util.*;
import static java.util.Arrays.*;
import static java.lang.Math.*;
public class All {
	public static void main(String[] args) {
		new C().run();
	}
	static void debug(Object... o){
		System.err.println(deepToString(o));
	}
	static class A{
		void run(){
			Scanner sc=new Scanner(System.in);
			for(;;){
				int n=sc.nextInt(),r=sc.nextInt();
				if(n==0)return;
				int ans=n;
				int[] p=new int[r],c=new int[r];
				for(int i=0;i<r;i++){
					p[i]=sc.nextInt();
					c[i]=sc.nextInt();
				}
				for(int i=r-1;i>=0;i--){
					if(ans>n-p[i]-c[i]+1){
						if(ans>n-c[i])
							ans-=p[i]-1;
						else
							ans+=c[i];
					}
				}
				System.out.println(ans);
			}
		}
	}
	static class B{
		static int[] dx={-1,0,1,0},dy={0,1,0,-1};
		void run() {
			Scanner sc=new Scanner(System.in);
			for(;;){
				int W=sc.nextInt(),H=sc.nextInt();
				if(W==0)break;
				char[][] c=new char[H][W];
				int mx=0,my=0;
				for(int i=0;i<H;i++){
					c[i]=sc.next().toCharArray();
					for(int j=0;j<W;j++){
						if(c[i][j]=='@'){
							mx=i;
							my=j;
						}
					}
				}
				Queue<Integer> qx=new LinkedList<Integer>(),qy=new LinkedList<Integer>();
				qx.offer(mx);
				qy.offer(my);
				int count=1;
				while(!qx.isEmpty()){
					mx=qx.poll();
					my=qy.poll();
					for(int i=0;i<4;i++){
						int nx=mx+dx[i],ny=my+dy[i];
						if(nx>=0 && nx<H && ny>=0 && ny<W && c[nx][ny]=='.'){
							qx.offer(nx);
							qy.offer(ny);
							count++;
							c[nx][ny]='#';
						}
					}
				}
				System.out.println(count);
			}
		}
	}
	static class C{
		void run(){
			Scanner sc=new Scanner(System.in);
			for(;;){
				double p=sc.nextDouble(),q=sc.nextDouble();int a=sc.nextInt(),n=sc.nextInt();
				if(p+q+a+n==0)break;
				System.out.println(calc(p/q,a,n,1,0,0));
			}
		}
		int calc(double d,int a,int n,int before,double val,int res){
//			debug(d,a,n,before,val,res);
			if(signum(val-d)==0)return res+1;
			if(n==0 || signum(val-d)>0)return res;
			for(int i=before;i<=a;i++){
				res+=calc(d,a/i,n-1,i,val+1.0/i,res)-res;
			}
			return res;
		}
		double EPS=1e-9;
		int signum(double d){
			return d<-EPS?-1:d>EPS?1:0;
		}
	}
	static class D{
		void run(){
			Scanner sc=new Scanner(System.in);
			for(;;){
				int n=sc.nextInt();
				if(n==0)return;
				double[] xs=new double[n],ys=new double[n];
				for(int i=0;i<n;i++){
					xs[i]=sc.nextDouble();ys[i]=sc.nextDouble();
				}
				int res=1;
				for(int i=0;i<n;i++){
					for(int j=i+1;j<n;j++){
						double[] rs=calc(xs[i],ys[i],xs[j],ys[j]);
						if(rs==null)continue;
						for(int k=0;k<2;k++){
							double nx=rs[k*2],ny=rs[k*2+1];
							int num=2;
							for(int l=0;l<n;l++){
								if(l==i || l==j)continue;
								if(hypot(nx-xs[l], ny-ys[l]) < 1)num++;
							}
							if(num>res)res=num;
						}
					}
				}
				System.out.println(res);
			}
		}
		double[] calc(double x1,double y1,double x2,double y2){
			double r=hypot(x1-x2,y1-y2);
			if(r>2)return null;
			return new double[]{
					(x1+x2)/2 + (y2-y1)/r*sqrt(1-(r*r/4)) , (y1+y2)/2 + (x1-x2)/r*sqrt(1-(r*r/4)) ,
					(x1+x2)/2 - (y2-y1)/r*sqrt(1-(r*r/4)) , (y1+y2)/2 - (x1-x2)/r*sqrt(1-(r*r/4)) 
			};
		}
		double hypot(double x,double y){
			return sqrt(x*x+y*y);
		}
	}
	static class E{
		int INF=1<<20;
		double EPS=1e-9;
		Tank[] tanks;
		void run(){
			Scanner sc=new Scanner(System.in);
			int o=sc.nextInt();
			while(o-->0){
				int n=sc.nextInt();
				tanks=new Tank[n+2];
				int[] bs=new int[n+3],hs=new int[n+3];
				bs[0]=0;bs[n+1]=100;bs[n+2]=INF;hs[0]=INF;hs[n+1]=50;hs[n+2]=100;
				for(int i=1;i<=n;i++){
					bs[i]=sc.nextInt();
					hs[i]=sc.nextInt();
				}
				int m=sc.nextInt();
				double[] ffs=new double[m],as=new double[m];
				int[] fs=new int[m];
				for(int i=0;i<m;i++){
					ffs[i]=sc.nextDouble();
					for(int j=0;j<=n+1;j++){
						if(ffs[i]<bs[j]){
							fs[i]=j-1;break;
						}
					}
					as[i]=sc.nextDouble()/30;
				}
				int l=sc.nextInt();
				for(int oo=0;oo<l;oo++){
					double p=sc.nextDouble(),T=sc.nextDouble();
					int P=0;
					for(int i=0;i<=n+1;i++){
						if(p<bs[i]){
							P=i-1;break;
						}
					}
					for(int i=0;i<=n+1;i++){
						tanks[i]=new Tank(hs[i],hs[i+1],bs[i+1]-bs[i]);
					}
					for(int i=0;i<m;i++){
						tanks[fs[i]].flux+=as[i];
					}
					//					showTanks();
					double t=0;
					while(signum(T-t)>0){
						double dt=T-t;
						for(int i=0;i<=n;i++){
							Tank tank=tanks[i];
							if(tank.flux==0)continue;
							dt=min(dt,(tank.minh*tank.size-tank.volumeOfWater)/tank.flux);
						}
						//						debug(t,dt);
						flow(dt);
						//						showTanks();
						t+=dt;
					}
					//					showTanks();
					System.out.printf("%.3f%n",tanks[P].volumeOfWater/tanks[P].size);
				}
			}
		}
		void showTanks(){
			int n=tanks.length;
			for(int i=0;i<n;i++){
				debug(tanks[i].minh*tanks[i].size,tanks[i].volumeOfWater,tanks[i].flux,tanks[i].size);
			}
			System.err.println();
		}
		void mergeTank(int i,int j){
			tanks[i].merge(tanks[j]);
			int n=tanks.length;
			for(int k=0;k<n;k++){
				if(k!=j)
					if(tanks[k]==tanks[j]){
						tanks[k]=tanks[i];
					}
			}
			tanks[j]=tanks[i];
			//			System.err.println("tank "+i+" and "+j+" were merged.");
		}
		boolean flow(double t){
			int m=tanks.length;
			for(int i=0;i<m;i++){
				if(i>0)if(tanks[i-1]==tanks[i])continue;
				Tank tank=tanks[i];
				tank.volumeOfWater+=tank.flux*t;
			}
			for(int i=0;i<m;i++){
				if(i>0)if(tanks[i-1]==tanks[i])continue;
				if(tanks[i].flux>0 && tanks[i].isFull()){
					changeCondition(i);
				}
			}
			return false;
		}
		void changeCondition(int i){
			Tank tank=tanks[i];
			if(tank.lh<tank.rh){
				int id=i-1;
				for(int j=i-1;j>=0;j--){
					if(tanks[j]!=tank){
						id=j;break;
					}
				}
				if(tanks[id].isFull() && signum(tanks[id].volumeOfWater-tanks[id].size*tanks[id].rh)==0){
					mergeTank(i,id);
				}
				else{
					tanks[id].flux+=tank.flux;
					tank.flux=0;
				}
			}
			else{
				int id=i+1;
				for(int j=i+1;j<tanks.length;j++){
					if(tanks[j]!=tank){
						id=j;break;
					}
				}
				if(tanks[id].isFull() && signum(tanks[id].volumeOfWater-tanks[id].size*tanks[id].lh)==0){
					mergeTank(i,id);
				}
				else{
					tanks[id].flux+=tank.flux;
					tank.flux=0;
				}
			}
		}
		int signum(double d){
			return d<-EPS?-1:d>EPS?1:0;
		}
		class Tank{
			boolean isFull(){
				if(signum(volumeOfWater-size*minh)==0)return true;
				return false;
			}
			double lh,rh,minh;
			double size,volumeOfWater=0,flux=0;
			Tank(int lh,int rh,double size){
				this.lh=lh;this.rh=rh;this.size=size;
				minh=min(lh,rh);
			}
			void merge(Tank t){
				size+=t.size;
				volumeOfWater+=t.volumeOfWater;
				flux+=t.flux;
				if(t.lh==rh && rh!=50){
					rh=t.rh;
				}
				else{
					lh=t.lh;
				}
				minh=min(lh,rh);
			}
		}
	}
	static class F{
		void run(){
			Scanner sc=new Scanner(System.in).useDelimiter("[\\s-]+");
			for(;;) {
				int N = sc.nextInt();
				if (N==0) break;
				HashMap<String,Integer> h=new HashMap<String, Integer>();
				String cross1[] = new String[N];
				String cross2[] = new String[N];
				int id=0;
				for(int i=0;i<N;i++) {
					cross1[i] = sc.next();
					if(!h.containsKey(cross1[i])){
						h.put(cross1[i],id);
						id++;
					}
					cross2[i] = sc.next();
					if(!h.containsKey(cross2[i])){
						h.put(cross2[i],id);
						id++;
					}
				}
				int m=h.size();
				boolean[][] f=new boolean[m][m];
				boolean[][] g=new boolean[m][m];
				for(int i=0;i<N;i++){
					f[h.get(cross1[i])][h.get(cross2[i])]=true;
					g[h.get(cross1[i])][h.get(cross2[i])]=true;
				}
//				debug(h);
				for(int j=0;j<m;j++){
					label:for(int k=j+1;k<m;k++){
						for(int i=0;i<m;i++){
							if(f[i][j]&&f[k][i] || f[j][i]&&f[i][k])
								continue label;
						}
						for(int i=0;i<m;i++){
							if(f[i][j]&&f[i][k] || f[j][i]&&f[k][i]){
								g[j][k]=g[k][j]=true;
							}
						}
					}
				}
//				for(int i=0;i<m;i++){
//					debug(f[i],g[i]);
//				}
				for(int k=0;k<m;k++){
					for(int i=0;i<m;i++){
						for(int j=0;j<m;j++){
							g[i][j]|=g[i][k]&&g[k][j];
						}
					}
				}
				int[][] color = new int[m][m];
				for(int i=0;i<m;i++) {
					for(int j=0;j<m;j++) {
						if (f[i][j] || f[j][i]) {
							color[i][j] = 1;
						} else {
							color[i][j] = 1<<20;
						}
					}
				}
				for(int k = 0;k < m;k++) {
					for(int i=0;i<m;i++) {
						for(int j=0;j<m;j++){
							color[i][j] = Math.min(color[i][k]+color[k][j], color[i][j]);
						}
					}
				}
				
				int oo=sc.nextInt();
				System.out.println(m);
				for(int o=0;o<oo;o++){
					String ss=sc.next(),st=sc.next();
					if(!h.containsKey(ss) || !h.containsKey(st)){
						System.out.println("NO");
						continue;
					}
					int s=h.get(ss),t=h.get(st);
					if(s==t || (color[s][t]%2 == 0 || color[s][t]>=(1<<20))){
						System.out.println("NO");continue;
					}
					System.out.println(g[s][t]?"YES":"NO");
				}
			}
		}
	}
}
