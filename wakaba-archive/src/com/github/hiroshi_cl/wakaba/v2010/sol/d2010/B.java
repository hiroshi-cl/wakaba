package com.github.hiroshi_cl.wakaba.v2010.sol.d2010;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;
public class B {
	public static void main(String[] args) {
		new B().run();
	}
	void debug(Object...os){
		System.err.println(deepToString(os));
	}
	int INF=1<<28;
	void run(){
		Scanner sc = new Scanner(System.in);
		try{
			String s = getClass().getSimpleName();
			sc = new Scanner(new File(s));
			System.setOut(new PrintStream(new File(s+".out")));
		}catch (Exception e) {
		}
		for(;;){
			w=sc.nextInt();h=sc.nextInt();
			if((w|h)==0)return;
			int[][] ds = new int[w*h][w*h];
			for (int i = 0; i < w*h; i++) {
				for (int j = 0; j < w*h; j++) {
					ds[i][j] = INF;
				}
			}
			for (int i = 0; i < h*2-1; i++) {
				for (int j = 0; j < w-(1-i%2); j++) {
					if(i%2==0){
						if(sc.nextInt()==0){
							int x=toi(i/2,j),y=toi(i/2,j+1);
							ds[x][y]=ds[y][x] = 1;
						}
					}else{
						if(sc.nextInt()==0){
							int x=toi(i/2,j),y=toi(i/2+1,j);

							ds[x][y]=ds[y][x] = 1;
						}
					}
				}
			}
			for (int k = 0; k < w*h; k++) {
				debug(k);
				for (int i = 0; i < w*h; i++) {
					for (int j = 0; j < w*h; j++) {
						if(ds[i][j]>ds[i][k]+ds[k][j])
							ds[i][j] = ds[i][k]+ds[k][j];
					}
				}
			}

			System.out.println(ds[toi(0,0)][toi(h-1,w-1)]==INF ? 0:ds[toi(0,0)][toi(h-1,w-1)]+1);
		}
	}
	int w,h;
	int x(int id){
		return id/w;
	}
	int y(int id){
		return id%w;
	}
	int toi(int x,int y){
		return x*w+y;
	}
}
