package com.github.hiroshi_cl.wakaba.v2009.sol.rp2008;
import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;
public class Main {
	public static void main(String[] args) throws Exception{
		System.setIn(new BufferedInputStream(new FileInputStream("E3.txt")));
		new E().run();
	}
	static void debug(Object... o){
		System.err.println(deepToString(o));
	}
	static class A{
		static boolean[] p;
		void run(){
			p=new boolean[1000001];
			fill(p,true);
			for(int i=2;i<=1000000;i++){
				if(p[i]){
					for(int j=2;j*i<=1000000;j++){
						p[j*i]=false;
					}
				}
			}
//			debug(p);
			Scanner sc=new Scanner(System.in);
			for(;;){
				int a=sc.nextInt(),b=sc.nextInt();
				if(a==0 && b==0)return;
				System.out.println(calc(a)<calc(b)?"b":"a");
			}
		}
		int calc(int n){
			int res=0;
			for(int i=1000000;i>=2;i--){
				if(p[i] && (n%i==0 )){
					res=i;
					n/=i;
					break;
				}
			}
			for(int i=res-1;i>=2;i--){
				if(p[i] && (n%i==0)){
					res-=i;
					n/=i;

				}
			}
//			debug(res);
			return res;
		}
	}
	static class B{
		void run() {
			Scanner sc = new Scanner(System.in);
			while(true) {
				int x = sc.nextInt();
				int y = sc.nextInt();
				if((x | y) == 0)
					break;
				int d = gcd(x, y);
				x /= d;
				y /= d;
//				debug(x, y);
				if(y == 1) {
					System.out.println("0 0");
					continue;
				}
				int r = y;
				int b = 1;
				while(r % 2 == 0) {
					r /= 2;
					b *= 2;
				}
				while(r % 5 == 0) {
					r /= 5;
					b *= 5;
				}
//				debug(r,b);
				int cnt1 = 0;
				if(r > 1) {
					int rem = 10;
					for(; cnt1 < r; cnt1++, rem = (rem % r) * 10)
						if(rem % r == 1)
							break;
					cnt1++;
				}
				int cnt2 = 0;
				if(b > 1) {
					int rem = x * 10;
					for(; cnt2 < b; cnt2++, rem = (rem % b) * 10) {
//						debug(rem, b);
						if(rem % b == 0)
							break;
					}
					cnt2++;
				}
//				debug(x, y, cnt2, cnt1, (double) x / y);
				System.out.println(cnt2 + " " + cnt1);
			}
		}
		int gcd(int x, int y){
			if(x < y) {
				int t = x;
				x = y;
				y = t;
			}
			if(x % y == 0)
				return y;
			else
				return gcd(y, x % y);
		}
	}
	static class C{
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
//			Node(double[] x){
//				this.x=x[0];this.y=x[1];
//			}
			public int compareTo(Node o) {
				return new Double(x).compareTo(o.x);
			}
		}
	}
	static class D{
		void run(){
			Scanner sc=new Scanner(System.in);
		}
	}
	static class E{
		int max=384*2;
		void run() throws Exception{
//			System.setOut(new PrintStream(new FileOutputStream("E16.out")));
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in),1<<13);
			int N = Integer.parseInt(br.readLine());
			int[] len=new int[]{
					64,  32, 16,  8, 4, 2, 1, 
					96,  48, 24, 12, 6, 3, 
					112, 56, 28, 14, 7,
					120, 60, 30, 15, 
					124, 62, 31, 
					126, 63,
					127
			};
			String[] onpu=new String[]{
				"R1","R2" , "R4" , "R8", "R16" , "R32" , "R64" ,
				"R1.","R2.", "R4.","R8.","R16.","R32.",
				"R1..", "R2..", "R4..", "R8..", "R16..",
				"R1...", "R2...", "R4...", "R8...",
				"R1....", "R2....", "R4....",
				"R1.....", "R2.....",
				"R1......"
			};
			String[] dp=new String[max];
			dp[0]="R1.A";
//			debug(dp[0]);
			for(int i=1;i<max;i++){
				String ns=null;
				for(int j=0;j<onpu.length;j++){
					int ni=i-len[j];
					if(ni>=0){
						String s1=dp[ni]+onpu[j];
						if(ns==null || isleast(s1,ns)){
							ns=s1;
						}
					}
				}
				for(int j=0;j<onpu.length;j++){
					int ni=i-len[j];
					if(ni>=0){
						String s1=onpu[j]+dp[ni];
						if(isleast(s1,ns)){
							ns=s1;
						}
					}
				}
				dp[i]=ns;
			}

//			debug(dp);
			
			for(int n = 0; n < N; n++) {
				String[] rests = br.readLine().split("R");
				int[] dots = new int[rests.length-1];
				for(int i = 1; i < rests.length; i++)
					for(int j = rests[i].length() - 1; j >= 0 && rests[i].charAt(j) == '.'; j--)
						dots[i-1]++;
//				debug(rests, dots);
				int[] nums = new int[rests.length-1];
				for(int i = 1; i < rests.length; i++)
					nums[i-1] = Integer.parseInt((rests[i].split("\\."))[0]);
				int sum = 0;
				for(int i = 0; i < nums.length; i++) {
					sum += 64 / nums[i];
					sum += (64 / nums[i]) - ((64 / nums[i]) >> dots[i]);
				}
//				debug(sum);
				int mod=sum;

				StringBuffer f=new StringBuffer("");
				for(;mod>=max;mod-=192){
					f.append("R1.R1.");
				}
//				debug(rests, sum, mod, max);
				String res=dp[mod].replace("R1.A", f.toString());
				System.out.println(res);
			}
		}
		boolean isleast(String s1,String s2){
			if(s1.length() < s2.length())return true;
			if(s1.length() > s2.length())return false;
			return s1.compareTo(s2)<0;
		}
	}
	static class F{
		void run(){
			Scanner sc=new Scanner(System.in);
		}
	}
	static class G{
		void run(){
			Scanner sc=new Scanner(System.in);
		}
	}
	static class H{
		void run(){
			Scanner sc=new Scanner(System.in);
		}

	}
	static class I{
		void run(){
			Scanner sc=new Scanner(System.in);
		}

	}
	static class J{
		void run(){
			Scanner sc=new Scanner(System.in);
		}
	}
}
