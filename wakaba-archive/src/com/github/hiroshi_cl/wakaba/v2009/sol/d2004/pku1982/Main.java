package com.github.hiroshi_cl.wakaba.v2009.sol.d2004.pku1982;
import java.util.*;

class Main {
  public static void main(String[] args) {
    new Main().run();
  }

  void run() {
    int D,N,M,L;
    Scanner sc = new Scanner(System.in);
    D = sc.nextInt();
    for(;D>0;D--) {
      N = sc.nextInt();
      double[] B = new double[N+2];
      double[] H = new double[N+2];
      B[0]=0.0;B[N+1]=100.0;
      H[0]=50.0;H[N+1]=50.0;
      for(int i=1;i<N+1;i++) {
        B[i] = sc.nextDouble();
        H[i] = sc.nextDouble();
      }
      M = sc.nextInt();
      double[] F = new double[M];
      double[] A = new double[M];
      for(int i=0;i<M;i++) {
        F[i] = sc.nextDouble();
        A[i] = sc.nextDouble();
      }
      L = sc.nextInt();
      for(;L>0;L--) {
        double P = sc.nextDouble();
        double T = sc.nextDouble();
        ArrayList<Tank> tank = new ArrayList<Tank>();

        for(int i=0,m=0;i<N+1;i++){
          Tank t = new Tank(H[i],H[i+1],B[i],B[i+1]-B[i]);
          while(m<M && F[m]<B[i+1]) {
            t.hw+=(A[m]*T)/(t.size * 30.0);
            m++;
          }
          tank.add(t);
        }
        ArrayList<Tank> ntank= sim(tank);
        for(Tank e : ntank) {
          if(P<e.pos+e.size) {
            System.out.printf("%.3f\n",e.hw);
            break;
          }
        }
      }
    }
  }

  ArrayList<Tank> sim(ArrayList<Tank> tank) {

    for(;;){
 //     System.out.println("---");
 //     for(Tank e:tank) e.show();
      double minh = Double.MAX_VALUE;
      int mini = -1;
      Tank t,o;

      if (tank.size()==1) {
        if (tank.get(0).hw > 50.0) tank.get(0).hw = 50.0;
        return tank;
      }
      //�����ŏ���T���B
      for(int i=0;i<tank.size();i++) {
        Tank e = tank.get(i);
        if(e.hw < minh && (e.h1 < e.hw || e.h2 < e.hw)) {
          minh = e.hw;
          mini = i;
        }
      }
      if (mini == -1) return tank;
      
      t = tank.get(mini);
      if (t.h1 < t.h2) { // ���ɂ��ӂ��
        o = tank.get(mini-1);
        if (o.h1 > t.h1 && same(o.h2,o.hw)) {
          return merge(tank, mini-1);
        }
        else {
          o.hw+=t.size*(t.hw-t.h1)/o.size;
          t.hw=t.h1;
        }
      }
      else { // �E�ɂ��ӂ��
        o = tank.get(mini+1);
        if (o.h2 > t.h2 && same(o.h1,o.hw)) {
          return merge(tank,mini);
        }
        else {
          o.hw+=t.size*(t.hw-t.h2)/o.size;
          t.hw=t.h2;
        }
      }
    }
  }

  ArrayList<Tank> merge(ArrayList<Tank> old, int ix) {
    ArrayList<Tank> r = new ArrayList<Tank>();

    for(int i=0;i<ix;i++) r.add(old.get(i));
    r.add(old.get(ix).merge(old.get(ix+1)));
    for(int i=ix+2;i<old.size();i++) r.add(old.get(i));

    return sim(r); // tail recursion;
  }

  boolean same(double x, double y) {
    double EPS=0.0000000001;
    return (x-EPS < y) && (y < x+EPS);
  }

  class Tank {
    double h1, h2, pos, size;
    double hw;
    Tank (double h1, double h2, double pos, double size) {
      this.h1 = h1; this.h2 = h2;
      this.pos = pos; this.size = size;
      this.hw = 0.0;
    }
    Tank merge(Tank o) {
      assert(this.h2==o.h1 && this.pos+this.size==o.pos);
      Tank r = new Tank(this.h1, o.h2, this.pos, this.size+o.size);
      r.hw = (this.hw*this.size + o.hw*o.size)/r.size;
      return r;
    }
    void show() {
      System.out.println("h1="+h1+" h2="+h2+" pos="+pos+" size="+size+" hw="+hw);
    }
  }
}
