package com.github.hiroshi_cl.wakaba.v2010.sol.r2007;
import java.util.*;
import java.lang.*;

class UnionFind {
  int[] tree;
  int sz;
  public UnionFind(int size){
    sz = size;
    tree=new int[size];
    Arrays.fill(tree, -1);
  }
  public UnionFind(UnionFind u){
    sz=u.sz;
    tree = new int[sz];
    for(int i=0;i<sz;i++) tree[i] = u.tree[i];
  }
  public void union(int x, int y){
    x = root(x);
    y = root(y);
    if (x==y) return;
    if (tree[x] > tree[y]) { int tmp=x;x=y;y=tmp; }
    tree[x] += tree[y];
    tree[y] = x;
  }
  public boolean find(int x, int y) {
    if(root(x) == root(y)) return true;
    return false;
  }
  public int root(int x) { 
    if(tree[x] < 0) return x;
    return (tree[x] = root(tree[x]));
  }
  public int count() {
    int r=0;
    for(int i=0;i<sz;i++) if(tree[i]<0) r++;
    return r;
  }
}


class P implements Comparable<P> {
  public int x; public int y; public int w; 
  public P(int a, int b, int c) {x=a;y=b;w=c;}
  public int compareTo(P o) { return (int)Math.signum(w-o.w); }
}

class F{
  public static void main(String[] args) {
    new F().run();
  }

  final int MAX = 1000000;
  P[] G;
  int E;
  int V;

  public void run() {
    Scanner sc = new Scanner(System.in);
    for(;;) {
      int n = sc.nextInt();
      int m = sc.nextInt();
      if(n==0&&m==0) break;
      V = n;
      E = m;
//System.out.print(""+V+","+E+";");
      G = new P[E];
      for(int i=0;i<m;i++) {
        int a = sc.nextInt()-1;
        int b = sc.nextInt()-1;
//System.out.println("w"+a + "," + b);
        G[i] = new P(a,b,sc.nextInt());
      }
      Arrays.sort(G);
      int r = search();
      System.out.println(r==MAX ? -1 : r);
    }
  }

  int search() {
    int m = MAX;
    for(int i=0;i<E;i++) {
      int rest=V-1;
      UnionFind u = new UnionFind(V);
      for(int j=i;j<E;j++) {
        P p = G[j];
        if (!u.find(p.x, p.y)) rest--;
        u.union(p.x, p.y);
        if (rest==0) {
          m = Math.min(m,G[j].w-G[i].w);
          break;
        }
      }
    }
    return m;
  }
}
