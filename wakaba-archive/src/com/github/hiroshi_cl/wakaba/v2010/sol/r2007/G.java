package com.github.hiroshi_cl.wakaba.v2010.sol.r2007;
import java.lang.*;
import java.util.*;

class G {
  public static void main(String[] args) {
    new G().run();
  }

  class S {
    int a,b,c;

    S(int A, int B, int C) { a=A; b=B; c=C;}

    int toInt() { return a | (b << 8) | (c << 16); }
  }
  final int X = 9999;

  ArrayList<S> next(char[] map, S s) {
    final byte[] nlist = {-16, -1, 0, 1, 16};
    ArrayList<S> r = new ArrayList<S>();
    for (int i=0; i<5; i++) {
      for (int j=0; j<5; j++) {
        for(int k=0; k<5; k++) {
          int na = s.a+nlist[i];
          int nb = s.b+nlist[j];
          int nc = s.c+nlist[k];
          if (s.c==X) {
            if (s.b==X) {
              if (map[na]=='#') continue;
              r.add(new S(na, s.b, s.c));
            }
            else {
              if (na==nb) continue;
              if (map[na]=='#' || map[nb]=='#') continue;
              if (na==s.b && nb==s.a) continue;
              r.add(new S(na, nb, s.c));
            }
          }
          else {
            if (na==nb || nb==nc || nc==na) continue;
            if (map[na]=='#' || map[nb]=='#' || map[nc]=='#') continue;
            if (na==s.b && nb==s.a) continue;
            if (nb==s.c && nc==s.b) continue;
            if (nc==s.a && na==s.c) continue;
            r.add(new S(na, nb, nc));
          }
        }
      }
    }
    return r;
  }

  int search(char[] map, S s, S g) {
    int count;
    ArrayList<S> ns = new ArrayList<S>();
    ArrayList<S> ng = new ArrayList<S>();
    Map<Integer,Integer> cs = new TreeMap<Integer,Integer>();
    Map<Integer,Integer> cg = new TreeMap<Integer,Integer>();
    Queue<S> qs = new LinkedList<S>();
    Queue<S> qg = new LinkedList<S>();
    qs.add(s);
    qg.add(g);
    cs.put(s.toInt(),0);
    cg.put(g.toInt(),0);

    if (s.toInt()==g.toInt()) return 0;

    for (;;) {
      assert(!qs.isEmpty());
      assert(!qg.isEmpty());
      // from start
      s = qs.poll();
      count = cs.get(s.toInt());
      ns = next(map, s);
      for(S e:ns) {
        if (!cs.containsKey(e.toInt())) {
          if (cg.containsKey(e.toInt())) return count + cg.get(e.toInt()) + 1;
          cs.put(e.toInt(), count+1);
          qs.offer(e);
        }
      }
      // from goal
      g = qg.poll();
      count = cg.get(g.toInt());
      ng = next(map, g);
      for(S e:ng) {
        if (!cg.containsKey(e.toInt())) {
          if (cs.containsKey(e.toInt())) return count + cs.get(e.toInt()) + 1;
          cg.put(e.toInt(), count+1);
          qg.offer(e);
        }
      }
    }
  }

  public void run() {
    Scanner sc = new Scanner(System.in);
    char[] map = new char[256];

    for(;;) {
      int a=X,b=X,c=X,A=X,B=X,C=X;
      int w = sc.nextInt();
      int h = sc.nextInt();
      Arrays.fill(map, '#');
      sc.nextLine();

      if (h==0||w==0) break;
      for(int i=0;i<h;i++) {
        String line = sc.nextLine();
        for(int j=0;j<w;j++) {
          int p = i*16+j;
          map[p] = ' ';
          switch(line.charAt(j)) {
          case 'a': a=p; break;
          case 'b': b=p; break;
          case 'c': c=p; break;
          case 'A': A=p; break;
          case 'B': B=p; break;
          case 'C': C=p; break;
          case '#': map[p]='#'; break;
          default:
          }
        }
      }
      System.out.println(search(map, new S(a,b,c), new S(A,B,C)));
    }
  }
}



