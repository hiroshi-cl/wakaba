package com.github.hiroshi_cl.wakaba.v2010.sol.sc2009.d2;
import java.util.*;
class Natu09D {
  public static void main(String[] args) {
    new Natu09D().run();
  }

  void run() {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    byte[][] init = new byte[n][2];
    for(int i=0;i<n;i++) {
      init[i][0] = sc.nextByte();
      init[i][1] = sc.nextByte();
    }
    System.out.println(search(new Box(init)));
  }

  int search(Box src) {
    Queue<Node> cQ = new PriorityQueue<Node>();
    Queue<Node> cR = new PriorityQueue<Node>();
    Map<Box, Integer> mapQ = new HashMap<Box,Integer>();
    Map<Box, Integer> mapR = new HashMap<Box,Integer>();

    cQ.offer(new Node(src, 0, false));
    {
      byte[][] goal = new byte[src.size()][2];
      for(int i=0;i<goal.length;i++) goal[i][0] = goal[i][1] = (byte)(i+1);
      cR.offer(new Node(new Box(goal), 0, true));
    }

    for(;;) {
      int count;
      count = 0;
      while(count < 10) {
        Node node = cQ.poll();
        if(mapQ.containsKey(node.box)) continue;
        if(mapR.containsKey(node.box)) return mapR.get(node.box) + node.swap;
        mapQ.put(node.box, node.swap);
        for(int i=0;i<src.size()-1;i++) {
          for(int j=0;j<2;j++) {
            for(int k=0;k<2;k++) {
              Node nn = new Node(new Box(node.box, i, i+1, j, k), node.swap+1, false);
              if (nn.dist() > node.dist()+2) continue;
              if (mapQ.containsKey(nn.box)) continue;
              cQ.offer(nn);
            }
          }
        }
        count++;
      }
      count = 0;
      while(count < 10) {
        Node node = cR.poll();
        if(mapR.containsKey(node.box)) continue;
        if(mapQ.containsKey(node.box)) return mapQ.get(node.box) + node.swap;
        mapR.put(node.box, node.swap);
        for(int i=0;i<src.size()-1;i++) {
          for(int j=0;j<2;j++) {
            for(int k=0;k<2;k++) {
              Node nn = new Node(new Box(node.box, i, i+1, j, k), node.swap+1, true);
              if (nn.dist() < node.dist()+2) continue;
              if (mapR.containsKey(nn.box)) continue;
              cR.offer(nn);
            }
          }
        }
        count++;
      }
      System.out.println(cQ.size() + " " + cR.size());
    }
  }

  class Node implements Comparable<Node>{
    Box box;
    int swap;
    boolean rev;

    public int compareTo(Node o) {
      return rev ? o.dist() - dist() : dist() - o.dist();
    }

    int dist() {
      int r = 0;
      for(int i=0; i<box.size(); i++) {
        int d0 = Math.abs(box.box[i][0]-(i+1));
        int d1 = Math.abs(box.box[i][1]-(i+1));
        r += d0*d0+d1*d1;
      }
      return r+swap*2;
    }

    Node(Box box, int swap, boolean rev) {
      this.box = box;
      this.swap = swap;
      this.rev = rev;
    }

    public int hashCode() {
      return box.hashCode();
    }
    public boolean equals(Object o) {
      return box.equals(((Node)o).box);
    }
  }

  class Box {
    byte[][] box;

    Box(byte[][] b) {
      box = new byte[b.length][2];
      for(int i=0;i<b.length;i++) {
        box[i][0] = b[i][0];
        box[i][1] = b[i][1];
      }
      normalize();
    }

    Box(Box b, int i, int j, int ix, int jx) {
      this(b.box);
      byte tmp = box[i][ix];
      box[i][ix] = box[j][jx]; box[j][jx] = tmp;
      normalize();
    }

    void normalize() {
      for(int i=0;i<box.length;i++){
        if(box[i][0] > box[i][1]) {
          byte tmp = box[i][0];
          box[i][0] = box[i][1]; box[i][1] = tmp;
        }
      }
    }

    int size() {
      return box.length;
    }

    public int hashCode() {
      int r = 55;
      for(int i=0;i<box.length;i++) r ^= (box[i][0] + box[i][1]) << i;
      return r;
    }
    public boolean equals(Object o) {
      Box other = (Box)o;
      if (other.box.length != box.length) return false;
      for(int i=0;i<box.length;i++) {
        if (box[i][0] != other.box[i][0]) return false;
        if (box[i][1] != other.box[i][1]) return false;
      }
      return true;
    }
  }
}
