package com.github.hiroshi_cl.wakaba.v2009.sol.d2008;
import java.util.*;
import java.io.*;
import static java.util.Arrays.*;
// 怪しい
public class D {
	static int[][] f;
	static int[][][] ans;
	static int[] c;
	static final int INF=1<<25;
	static int[] dx={0,1,0,-1,0} , dy={1,0,-1,0,0};
	static int h;
	static int w;
	static boolean flag=false;
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("D.in"));
//		System.setOut(new PrintStream("D.out"));
		sc = new Scanner(System.in);
		while(true){
			w=sc.nextInt();
			h=sc.nextInt();
			if(w==0)break;
			f=new int[h][w];
			ans=new int[4][h][w];
			for(int i=0;i<h;i++){
				for(int j=0;j<w;j++){
					f[i][j]=sc.nextInt();
				}
			}
			c=new int[4];
			for(int i=0;i<4;i++){
				c[i]=sc.nextInt();
			}
			for(int i=0;i<4;i++){
				for(int j=0;j<h;j++){
					Arrays.fill(ans[i][j], INF);
				}
			}
//			debug(ans);
			ans[0][0][0]=0;
			initialize(ans);
			debug(ans);
//			for(int i=0;i<4;i++)debug(ans[i]);
			int min=-1;
			while(true){
				int nmin=INF;
				for(int tempd=0;tempd<4;tempd++){
					for(int i=0;i<h;i++){
						for(int j=0;j<w;j++){
							int temp=ans[tempd][i][j];
							if(temp<=min)continue;
							nmin=Math.min(nmin, temp);
						}
					}
				}
				if(min==nmin)break;
//				debug(nmin,min);
				min=nmin;
				
				//debug(ans);
				for(int tempd=0;tempd<4;tempd++){
					for(int i=0;i<h;i++){
						for(int j=0;j<w;j++){
							if(ans[tempd][i][j]==nmin){
//								debug(tempd,i,j,ans[tempd][i][j]);
								update(tempd,i,j);
								debug(ans);
							}
						}
					}
				}
//				debug(ans);
			}
			int a=Math.min(ans[0][h-1][w-1],ans[1][h-1][w-1]);
			System.out.println(a);
		}
	}
	static void initialize(int[][][] ans){
		int nd=0,nx=0,ny=0;
		int cost=0;
		while(true){
			nd=f[nx][ny]==4 ? 4 :(f[nx][ny]+nd)%4;
			if(nd==4)break;
			nx=nx+dx[nd] ; ny=ny+dy[nd];
			if(nx<0 || h<=nx || ny<0 || w<=ny)break;
			ans[nd][nx][ny]=cost;
		}
	}
	static void update(int d,int x,int y){
		boolean[][][] check=new boolean [4][h][w];
		for(int tempd=0;tempd<4;tempd++){
			int nd=tempd;
			int nx=x ,ny=y;
			if(tempd!=d){
				int cost=ans[d][x][y]+c[nd];
//				ans[nd][x][y]=Math.min(ans[nd][x][y], cost);
				loop: while(true){
					nd=f[nx][ny]==4 ? 4 :(f[nx][ny]+nd)%4;
					if(nd==4)break;
					nx=nx+dx[nd] ; ny=ny+dy[nd];
					if(nx<0 || h<=nx || ny<0 || w<=ny)break;
//debug(nd,nx,ny,check[nd][nx][ny]);
					if(check[nd][nx][ny])
						break loop;
//					else
//						debug(check[nd][nx][ny]);

					check[nd][nx][ny]=true;
					if(ans[nd][nx][ny]>cost){
						ans[nd][nx][ny]=cost;
						flag=false;
					}
				}
			}
		}
	}
	static void debug(Object... o){
		System.out.println(deepToString(o));
	}
}
