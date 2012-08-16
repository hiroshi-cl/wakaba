package com.github.hiroshi_cl.wakaba.v2009.sol.d2008.wakaba;
import java.util.*;

/*
 * はまった点
 * -単純に深さ優先しようとすると現在いる場所からしか探索しないことになるので突起が複数ある場合確実にまずくなる．
 * -対となる破片は対を作った時点でどちらの固定点側に属するかどうか決定している．対を作って後から属する方を決めるのは間違い．
 * -同じ回転方向でも基準点が異なれば見え方が異なるので，逆行列を用意する必要がある．
 * -点a側の点pに対応するb側の点をqとすると，q = b + T(p-a)．この時 p = a + (T^-1)(q-b)
*/

class Main {
  public static void main(String[] args) {
    new F().run();
  }
}

class F {
  void run() {
    Scanner sc = new Scanner(System.in);
    loop: for(;;) {
      int w = sc.nextInt();
      int h = sc.nextInt();
      if(w==0||h==0) break loop;
      boolean[][] cmap = new boolean[h][w];
      P[][] rmap = new P[h][w];
      ArrayList<P> cs = new ArrayList<P>();

      for(int i=0;i<h;i++) {
        for(int j=0;j<w;j++) {
          cmap[i][j] = sc.nextInt() == 1;
        }
      }

      for(int i=0;i<h;i++) {
        for(int j=0;j<w;j++) {
          if (cmap[i][j]) {
            P p = new P(i,j);
            cs.add(p);
            rmap[i][j] = p;
          }
          else {
            rmap[i][j] = null;
          }
        }
      }
      for(int i=0;i<h;i++) {
        for(int j=0;j<w;j++) {
          if (rmap[i][j] != null) {
            if (i>0 && rmap[i-1][j] != null) rmap[i][j].addNext(rmap[i-1][j]);
            if (j>0 && rmap[i][j-1] != null) rmap[i][j].addNext(rmap[i][j-1]);
            if (i<h-1 && rmap[i+1][j] != null) rmap[i][j].addNext(rmap[i+1][j]);
            if (j<w-1 && rmap[i][j+1] != null) rmap[i][j].addNext(rmap[i][j+1]);
          }
        }
      }
      int n = cs.size();
      if (n%2!=0) {System.out.println("NO"); continue loop; }

      for(int i=1;i<n;i++) {
        for(int r=0;r<8;r++) {
          for(P p : cs) p.checked = false;
          for(P p : cs) p.color = 0;
          cs.get(0).checked = true;
          cs.get(i).checked = true;
          cs.get(0).color = 1;
          cs.get(i).color = -1;
          if (test(cs, rmap, w, h, r, 1, cs.get(i))) {
            System.out.println ("YES");
            continue loop;
          }
        }
      }
      System.out.println("NO");
    }
  }

  boolean test(ArrayList<P> cs, P[][] rmap, int w, int h, int r, int ix, P obase) {
    if (ix == cs.size()/2) return isConnected(cs);
    for(P p : cs) {
      if (p.checked) continue;
      P o1 = getPair(rmap, w, h, r, cs.get(0), obase, p, false);
      if (testPair(p,o1)) {
        p.checked = o1.checked = true;
        p.color = 1;
        o1.color = -1;
        boolean r1 = test(cs, rmap, w, h, r, ix+1, obase);
        p.checked=o1.checked=false;
        p.color=o1.color=0;
        if (r1) return true;
      }

      P o2 = getPair(rmap, w, h, r, obase, cs.get(0), p, true);
      if (testPair(p,o2)) {
        p.checked = o2.checked = true;
        o2.color = 1;
        p.color = -1;
        boolean r2 = test(cs, rmap, w, h, r, ix+1, obase);
        p.checked=o2.checked=false;
        p.color=o2.color=0;
        if (r2) return true;
      }
      return false;
    }
    return false; // not come here
  }

  boolean testPair(P p, P o) {
    if (o==null) return false;
    if (p==o) return false;
    if (o.checked) return false;
    return true;
  }

  P getPair(P[][] rmap, int w, int h, int r, P p0, P pi, P p,boolean inverse) {
    int rx, ry;
    if(!inverse) {
      rx = rot[r][0]*(p.x-p0.x) + rot[r][1]*(p.y-p0.y);
      ry = rot[r][2]*(p.x-p0.x) + rot[r][3]*(p.y-p0.y);
    }
    else {
      rx = inv[r][0]*(p.x-p0.x) + inv[r][1]*(p.y-p0.y);
      ry = inv[r][2]*(p.x-p0.x) + inv[r][3]*(p.y-p0.y);
    }
    
    int nx = pi.x+rx;
    int ny = pi.y+ry;
    if (nx<0 || nx>=h || ny<0 || ny>=w) return null;
    return rmap[nx][ny];
  }

  boolean isConnected(ArrayList<P> cs) {
    int n = cs.size();
    for(P p : cs) {
      p.dfs_checked = false;
    }
    if (dfs(cs.get(0)) == n/2) return true;
    return false;
  }

  int dfs(P cp) {
    int res = 1;
    cp.dfs_checked = true;
    for(P np : cp.next) {
      if (np.color != cp.color) continue;
      if (np.dfs_checked) continue;
      res+=dfs(np);
    }
    return res;
  }

  int[][] rot = {
    {1,0,0,1}, {0,1,-1,0}, {-1,0,0,-1}, {0,-1,1,0},
    {1,0,0,-1}, {0,1,1,0}, {-1,0,0,1}, {0,-1,-1,0}};
  int[][] inv = {
    {1,0,0,1}, {0,-1,1,0}, {-1,0,0,-1}, {0,1,-1,0},
    {1,0,0,-1}, {0,1,1,0}, {-1,0,0,1}, {0,-1,-1,0}};
  
  class P {
    int x, y;
    ArrayList<P> next;
    int color;
    boolean checked;
    boolean dfs_checked;
    P(int x, int y) { this.x = x; this.y = y; this.next = new ArrayList<P>();}
    void addNext(P p) { next.add(p); }
  }
}

