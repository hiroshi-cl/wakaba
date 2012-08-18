package com.github.hiroshi_cl.wakaba.v2010.sol.rp2009;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.math.BigInteger.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class D {
	void debug(Object... os) {
//		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("D.txt"));
		} catch (Exception e) {
		}
		for (;;) {
			int n = sc.nextInt();
			if(n==0)return;
			Entry[] es = new Entry[n];
			for (int i = 0; i < n; i++) {
				int x=sc.nextInt(),y=sc.nextInt();
				es[i] = new Entry(x,y,sc.next().charAt(0)=='x',i);
			}
			HashMap<P,P> map=new HashMap<P, P>();
			for (int i = 0; i < n; i++) {
				map.put(new P(es[i].xs[0],es[i].ys[0]),new P(i,0));
				map.put(new P(es[i].xs[1],es[i].ys[1]),new P(i,1));
			}
			int[] dx={1,0,-1,0};
			int[] dy={0,1,0,-1};
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < 2; j++) {
					for(int d=0;d<4;d++){
						P np = new P(es[i].xs[j] + dx[d] , es[i].ys[j]+dy[d]);
						if(map.containsKey(np) ){
							P is = map.get(np);
							if(is.x!=i)
							es[i].list[j][is.y].add(es[is.x]);
						}
					}
				}
			}

//			for (int i = 0; i < n; i++) {
//				for (int j = i+1; j < n; j++) {
//					for (int i2 = 0; i2 < 2; i2++) {
//						for (int j2 = 0; j2 < 2; j2++) {
//							int dx = abs(es[i].xs[i2]-es[j].xs[j2]);
//							int dy = abs(es[i].ys[i2]-es[j].ys[j2]);
//							if(dx==0 && dy==1 || dx==1 && dy==0){
//								es[i].list[i2][j2].add(es[j]);
//								es[j].list[j2][i2].add(es[i]);
//								debug(i,j,i2,j2);
//								debug(j,i,j2,i2);
//							}
//						}
//					}
//				}
//			}

			boolean can = true;
			for (int i = 0; i < n; i++) {
				if (!es[i].dfs()) {
					can = false;
					break;
				}
			}
			debug();
			System.out.println(can ? "Yes" : "No");
		}
	}
//	has
	class P {
		int x,y;
		P(int x,int y){
			this.x=x;
			this.y=y;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			P other = (P) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
		private D getOuterType() {
			return D.this;
		}

	}

	class Entry {
		// Arraylist
		Entry(int x,int y,boolean yoko,int id) {
			list = new ArrayList[2][2];
			for (int i = 0; i < 2; i++)
				for (int j = 0; j < 2; j++) {
					list[i][j] = new ArrayList<Entry>();
				}

			xs[0] = x;
			ys[0] = y;
			xs[1] = yoko ? x+1 : x;
			ys[1] = yoko ? y : y+1;
			this.id=id;
			// }list[i] = new ArrayList<Entry>();

		}
		int id;
		int[] xs=new int[2],ys=new int[2];
		int[] val=new int[2];
//		int x, y;
		boolean check = false;
		ArrayList<Entry>[][] list;
//		int id = -1;

		boolean dfs(){
			if(check)return true;
			check = true;
			val[0]=0;;
			val[1]=1;
			Stack<Entry> stack=new Stack<Entry>();
			stack.push(this);
			while(!stack.isEmpty()){
				Entry e = stack.pop();
				for (int i = 0; i < 2; i++) {
					for (int j = 0; j < 2; j++) {
						for(Entry ne:e.list[i][j]){
							if(ne.check){
								if(e.val[i] != ne.val[j]){
									debug("!" , e.id, ne.id , i,j);
									return false;
								}
							}
							else{
								ne.check=true;
								ne.val[j] = e.val[i];
								ne.val[1-j]= 1-e.val[i];
								stack.push(ne);
							}
						}
					}
				}
			}
			return true;

//			stack.push(this);
//			while(!stack.isEmpty()) {
//				Entry e = stack.pop();
//				if(e.id!=-1) continue;
//				boolean determined = false;
//				for(Entry g:list[0]) {
//					if(g.id != -1) {
//						e.id = 1-g.id;
//						determined = true;
//					}
//				}
//				for(Entry)
//			}
//			return true;
		}
	}

	public static void main(String[] args) {
		new D().run();
	}
}
