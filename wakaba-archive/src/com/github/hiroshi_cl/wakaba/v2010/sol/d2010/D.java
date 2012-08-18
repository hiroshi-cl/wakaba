package com.github.hiroshi_cl.wakaba.v2010.sol.d2010;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;
public class D {
	public static void main(String[] args) {
		new D().run();
	}
	void debug(Object...os){
//		System.err.println(deepToString(os));
	}
	void run(){
		Scanner sc = new Scanner(System.in);
		try{
			String s = getClass().getSimpleName();
			sc = new Scanner(new File(s));
			System.setOut(new PrintStream(new File(s+".out")));
		}catch (Exception e) {
		}

		for(;;) {
			axs.clear();
			ays.clear();
			int w = sc.nextInt();
			int h = sc.nextInt();
			if(w==0 && h==0) break;

			char[][] map = new char[w][h];
			for(int i=0;i<h;i++) {
				String ln = sc.next();
				for(int j=0;j<w;j++) {
					map[j][i] = ln.charAt(j);
				}
			}
			Piece p = null;
			for(int i=0;i<w;i++) {
				if(map[i][h-1] !='.') {
					p = readPiece(map, i, h-1);
					break;
				}
			}
			System.out.println(p==null || map[(int)p.c][h-1] == '.' ? "UNSTABLE" : "STABLE");
		}
	}

	ArrayList<Integer> axs = new ArrayList<Integer>();
	ArrayList<Integer> ays = new ArrayList<Integer>();
	static final int[] dx = { -1, 0, 1, 0 };
	static final int[] dy = { 0, -1, 0, 1 };
	Piece readPiece(char[][] m, int x, int y) {
		char c = m[x][y];
		int h = m[0].length;
		int w = m.length;
		int []xs = new int [4];
		int []ys = new int [4];
		int ns = 0;
//		xs[0] = x;
//		ys[0] = y;
		int xmin = x;
		int xmax = x;

	//	debug("***",x,y);
		Queue<Integer> q1 = new LinkedList<Integer>();
		Queue<Integer> q2 = new LinkedList<Integer>();
		q1.offer(x);
		q2.offer(y);
		while(!q1.isEmpty()) {
			int xx = q1.poll();
			int yy = q2.poll();
			if(exist(xx, yy)) continue;
			xs[ns] = xx;
			ys[ns] = yy;
			axs.add(xx);
			ays.add(yy);
			xmin = min(xmin,xx);
			xmax = max(xmax,xx);
			ns++;
		//	debug(xx,yy);
			//if(ns==4) break;
			for(int i = 0;i<4;i++) {
				int nx = xx+D.dx[i];
				int ny = yy+D.dy[i];
				if(nx<0 || nx >= w) continue;
				if(ny<0 || ny>= h) continue;
				//debug(nx,ny,w,h);
				if(m[nx][ny] != c) continue;
				q1.offer(nx);
				q2.offer(ny);
			}
		}
		//debug(ns);

		ArrayList<Piece> ps = new ArrayList<Piece>();
		double center = 0;
		double weight = 0;

		for(int i=0;i<4;i++) {
			debug(xs[i],ys[i]);
			if(ys[i] > 0 && m[xs[i]][ys[i]-1] != '.' && m[xs[i]][ys[i]-1] != c) {
				ps.add(readPiece(m, xs[i], ys[i]-1));
			}
		}
		for(Piece p : ps) {
			if(p==null) return null;
			if (lt(p.c,xmin) || le(xmax+1.0,p.c)) return null;
			center += p.c*p.weight;
			weight += p.weight;
		}
		Piece result = new Piece();

		for(int i=0;i<4;i++) {
			center += xs[i]+0.5;
			weight+=1.0;
		}
		result.c = (double)center / weight;
		result.weight = weight;

		//debug(c,result.c,result.weight);
		return result;
	}

	boolean exist(int x, int y) {
		for(int i=0;i<axs.size();i++) {
			if(axs.get(i) == x && ays.get(i) == y) return true;
		}
		return false;
	}

	final double EPS = 1e-10;
	boolean lt(double x, double y) {
		return sgn(x-y)<0;
	}
	boolean le(double x,double y) {
		return sgn(x-y)<=0;
	}
	int sgn(double x) {return x<0.0 ? -1 : x==0.0 ? 0 : 1; }

	class Piece {
		//ArrayList<Piece> ps;
		//Piece r;
		double c;
		double weight;
		public Piece() {
			// TODO 自動生成されたコンストラクター・スタブ
		}
	}
}