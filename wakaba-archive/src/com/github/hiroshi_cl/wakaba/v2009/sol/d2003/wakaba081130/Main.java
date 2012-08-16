package com.github.hiroshi_cl.wakaba.v2009.sol.d2003.wakaba081130;

import java.math.BigInteger;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.math.BigInteger.*;
public class Main {
	public static void main(String[] args) {
		new E2().run();
	}
	static void debug(Object... o){
		System.err.println(deepToString(o));
	}
	static class C{
		void run(){
			Scanner sc=new Scanner(System.in);
			for(;;){
				int w=sc.nextInt(),h=sc.nextInt();
				if((w|h)==0)return;
				char[][] c=new char[h][w];
				for(int i=0;i<h;i++){
					c[i]=sc.next().toCharArray();
				}
				BigInteger res=ZERO;
				BigInteger[][] dp=new BigInteger[h][w];
				for(int i=0;i<h;i++){
					for(int j=0;j<w;j++){
						if(Character.isDigit(c[i][j])){
							if(i==0){
								if(j==0){
									dp[i][j]=BigInteger.valueOf(c[i][j]-'0');
								}
								else{
									dp[i][j]=dp[i][j-1].multiply(TEN).add(valueOf(c[i][j]-'0'));

								}

							}
							else if(j==0){
								dp[i][j]=dp[i-1][j].multiply(TEN).add(valueOf(c[i][j]-'0'));
							}
							else{
								dp[i][j]=(dp[i-1][j]).max(dp[i][j-1]).multiply(TEN).add(valueOf(c[i][j]-'0'));
							}
						}
						else{
							dp[i][j]=ZERO;
						}
					}
				}
				for(int i=0;i<h;i++){
					for(int j=0;j<w;j++){
						res=res.max(dp[i][j]);
					}
				}
				System.out.println(res);
			}
		}
	}
	static class D{
		void run(){
			Scanner sc=new Scanner(System.in);
			for(;;){
				int n=sc.nextInt();
				if(n==0)return;
				UnionFind uf=new UnionFind(n);
				double[] xs=new double[n],ys=new double[n],zs=new double[n],rs=new double[n];
				for(int i=0;i<n;i++){
					xs[i]=sc.nextDouble();ys[i]=sc.nextDouble();zs[i]=sc.nextDouble();
					rs[i]=sc.nextDouble();
				}
				PriorityQueue<Edge> q=new PriorityQueue<Edge>();
				for(int i=0;i<n;i++){
					for(int j=i+1;j<n;j++){
						double dis=hypot(hypot(xs[i]-xs[j],ys[i]-ys[j]),zs[i]-zs[j]) - (rs[i]+rs[j]);
						if(dis<=0){
							uf.union(i, j);
						}
						else{
							q.offer(new Edge(i,j,dis));
						}
					}
				}
				double res=0;
				while(!q.isEmpty()){
					Edge e=q.poll();
					if(uf.find(e.u, e.v)){

					}
					else{
						res+=e.length;
						uf.union(e.u, e.v);
					}
				}
				System.out.printf("%.3f%n",res);
			}
		}
		class Edge implements Comparable<Edge>{
			double length;
			int u,v;
			public int compareTo(Edge o) {
				return (int)signum(length-o.length);
			}
			Edge(int u,int v,double dis){
				this.u=u;this.v=v;
				length=dis;
			}
		}
	}
	static class UnionFind {
		int[] tree;
		UnionFind(int size) {
			tree = new int[size];
			Arrays.fill(tree, -1);
		}
		void union(int x, int y) {
			x = root(x);
			y = root(y);
			if(x == y)
				return;
			if(tree[x] > tree[y]) {
				x ^= y; y ^= x; x ^= y;
			}
			tree[x] += tree[y];
			tree[y] = x;
		}
		boolean find(int x, int y) {
			if(root(x) == root(y))
				return true;
			else
				return false;
		}
		int root(int x) {
			if(tree[x] < 0)
				return x;
			else
				return tree[x] = root(tree[x]);
		}
	}
	// 怪しい
	static class E{
		void run(){
			Scanner sc=new Scanner(System.in);
			for(;;){
				int w=sc.nextInt(),h=sc.nextInt();
				if((w|h)==0)return;
				boolean[][] m=new boolean[h][w];

				BitSet fb = new BitSet(100);
				for(int i=0;i<h;i++){
					for(int j=0;j<w;j++){
						m[i][j]=sc.nextInt()==1;
						fb.flip(j*10+i);
					}
				}
				int n=min(w,h);
				boolean[][][] car=new boolean[n][][];
				for(int i=0;i<n;i++){
					car[i]=new boolean[h-i][w-i];
				}
				for(int i=0;i<n;i++){
					for(int j=0;j<h-i;j++){
						for(int k=0;k<w-i;k++){
							boolean ok=true;
							label:for(int j2=0;j2<=i;j2++){
								for(int k2=0;k2<=i;k2++){
									if(!m[j+j2][k+k2]){
										ok=false;
										break label;
									}
								}
							}
							car[i][j][k]=ok;
						}
					}
				}
				//				debug(car);
				for(int i=0;i<n;i++){
					for(int j=0;j<h-i;j++){
						for(int k=0;k<w-i;k++){
							if(car[i][j][k]){
								for(int i2=0;i2<i;i2++){
									for(int j2=0;j2<=i-i2;j2++){
										for(int k2=0;k2<=i-i2;k2++){
											if(car[i2][j+j2][k+k2])
												car[i2][j+j2][k+k2]=false;
										}
									}
								}
							}
						}
					}
				}
				//				debug(car);
				for(int sz=n;sz>0;sz--) {
					Queue<V2> q= new LinkedList<V2>();
					for(int i=0;i<=h-sz;i++) {
						for(int j=0;j<=w-sz;j++) {
							if(car[sz-1][i][j]) q.offer(new V2(sz,i,j));

						}
					}
					int max = q.size();
					Queue<V2> qq=new LinkedList<V2>(q);
					while (!qq.isEmpty()){
						q=qq;
						qq=new LinkedList<V2>();
						for(V2 v : q) {
							BitSet bb = new BitSet(100);
							for(V2 e : q) {bb.or(e.bs);}
							BitSet bbb = ((BitSet) fb.clone());
							bbb.andNot(bb);
							if (bbb.cardinality()!=0) {
								qq.offer(v);
							}
						}
					}
				}
				int res=0;
				for(int i=0;i<n;i++){
					for(int j=0;j<h-i;j++){
						for(int k=0;k<w-i;k++){
							if(car[i][j][k])res++;
						}
					}
				}
				System.out.println(res);
			}
		}
		static class V2{ int x,y; 
		V2(int sz,int X,int Y){x=X;y=Y;bs=new BitSet(10*10);
		for(int i=0;i<sz;i++) for(int j=0;j<sz;j++) bs.flip(i*10+j);
		} 
		BitSet bs;};
	}
	static class E2{
		void run(){
			Scanner sc=new Scanner(System.in);
			for(;;){
				int w=sc.nextInt(),h=sc.nextInt();
				if((w|h)==0)return;
				boolean[][] m=new boolean[h][w];
				for(int i=0;i<h;i++){
					for(int j=0;j<w;j++){
						m[i][j]=sc.nextInt()==1;
					}
				}
				int res2=Integer.MAX_VALUE;
				for(int o=0;o<4;o++){
					//				debug(m);
					int n=min(w,h);
					int[][] car=new int[h][w];
					for(int i=0;i<h;i++){
						for(int j=0;j<w;j++){
							car[i][j]=(maxL(i,j,m,n));
						}
					}
					//				debug(car);

					for(int i=0;i<h;i++){
						for(int j=0;j<w;j++){
							int n1=car[i][j];
							for(int i2=0;i2<n1;i2++){
								for(int j2=0;j2<n1;j2++){
									if(i2==0 && j2==0)continue;
									int n2=car[i+i2][j+j2];
									if(max(i2,j2)<=n1-n2){
										car[i+i2][j+j2]=0;
									}
								}
							}
						}
					}
//					debug(car);
					int[][] num=new int[h][w];
					for(int i=0;i<h;i++){
						for(int j=0;j<w;j++){
							int nn=car[i][j];
							for(int i2=0;i2<nn;i2++){
								for(int j2=0;j2<nn;j2++){
									num[i+i2][j+j2]++;
								}
							}
						}
					}
					int res=0;
					for(int i=0;i<h;i++){
						for(int j=0;j<w;j++){
							int nn=car[i][j];
							boolean ok=false;
							loop:for(int i2=0;i2<nn;i2++){
								for(int j2=0;j2<nn;j2++){
									if(num[i+i2][j+j2]==1){
										ok=true;
										res++;
//										debug(i,j,nn);
										break loop;
									}
								}
							}
							if(!ok){
								for(int i2=0;i2<nn;i2++){
									for(int j2=0;j2<nn;j2++){
										num[i+i2][j+j2]--;
									}
								}
							}
						}
					}
					res2=min(res,res2);
					int t=h;h=w;w=t;
					boolean[][] m2=new boolean[h][w];
					for(int i=0;i<h;i++){
						for(int j=0;j<w;j++){
							m2[i][j]=m[j][h-i-1];
						}
					}
					m=m2;
				}
				System.out.println(res2);
			}
		}
		int maxL(int x,int y,boolean[][] m,int n){
			if(!m[x][y])return 0;
			for(int n2=1;n2<=n;n2++){
				for(int i=0;i<n2;i++){
					for(int j=0;j<n2;j++){
						if(x+i>=m.length || y+j >= m[0].length || !m[x+i][y+j]){
							return n2-1;
						}
					}
				}
			}
			return n;
		}
	}
}