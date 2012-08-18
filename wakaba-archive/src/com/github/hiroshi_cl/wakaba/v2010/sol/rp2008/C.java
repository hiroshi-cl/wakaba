package com.github.hiroshi_cl.wakaba.v2010.sol.rp2008;

import static java.util.Arrays.*;
import static java.lang.Math.*;

import java.io.*;
import java.util.*;

public class C {
    public static void main(String[] args) throws Exception{
        System.setIn(new BufferedInputStream(new FileInputStream("C.txt")));
        new C().run();
    }
    static void debug(Object... o){
        System.err.println(deepToString(o));
    }
    void run(){
        Scanner sc=new Scanner(System.in);
        for(;;){
            int n=sc.nextInt();
            if(n==0)return;
            int vw=sc.nextInt(),vc=sc.nextInt();
            int[] xs=new int[n],ys=new int[n];
            PriorityQueue<Node> q=new PriorityQueue<Node>();
            for(int i=0;i<n;i++){
                xs[i]=sc.nextInt();ys[i]=sc.nextInt();
                Node node=new Node(xs[i],ys[i]);
                q.offer(node);
            }
            for(int i=0;i<n-1;i++){
                if(ys[i]<ys[i+1]){
                    for(int j=i+1;j<n-1;j++){
                        if(ys[j]>ys[i] && ys[i] > ys[j+1]){
                            q.offer(new Node(calcx(ys[i],xs[j],ys[j],xs[j+1],ys[j+1]),ys[i]));
                            break;
                        }
                    }
                }
            }
            for(int i=n-1;i>0;i--){
                if(ys[i]<ys[i-1]){
                    for(int j=i-1;j>0;j--){
                        if(ys[j]>ys[i] && ys[j-1]<ys[i]){
                            q.offer(new Node(calcx(ys[i],xs[j],ys[j],xs[j-1],ys[j-1]),ys[i]));
                        }
                    }
                }
            }
            Node[] nodes=new Node[q.size()];
            for(int i=0;i<nodes.length;i++){
                nodes[i]=q.poll();
                if(i>0 && nodes[i].y < nodes[i-1].y ){
                    for(int j=i-1;j>=0;j--){
                        if(nodes[j].y==nodes[i].y){
                            nodes[i].right = j;
                            break;
                        }
                    }
                }
            }
            double[] dp=new double[nodes.length];
            dp[0]=0;
            for(int i=1;i<nodes.length;i++){
                if(nodes[i].right>=0){
                    dp[i]=min(dp[i-1]+hypot(nodes[i].x-nodes[i-1].x,nodes[i].y-nodes[i-1].y)/vw ,
                            dp[nodes[i].right] + (nodes[i].x-nodes[nodes[i].right].x) /vc );
                }
                else{
                    dp[i]=dp[i-1]+hypot(nodes[i].x-nodes[i-1].x,nodes[i].y-nodes[i-1].y)/vw;
                }
            }
            System.out.printf("%.06f%n",dp[nodes.length-1]);
        }
    }
    double calcx(int y0,int x1,int y1,int x2,int y2){
        return (y0-y1+0.0)/(y2-y1)*(x2-x1)+x1;
    }
    class Node implements Comparable<Node>{
        double x,y;
        int right;
        Node(double x,double y){
            this.x=x;this.y=y;
            right=-1;
        }
//        Node(double[] x){
//            this.x=x[0];this.y=x[1];
//        }
        public int compareTo(Node o) {
            return new Double(x).compareTo(o.x);
        }
    }

}

