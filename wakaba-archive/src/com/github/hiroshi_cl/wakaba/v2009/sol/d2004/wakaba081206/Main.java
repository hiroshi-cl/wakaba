package com.github.hiroshi_cl.wakaba.v2009.sol.d2004.wakaba081206;
//2004 Japan Domestic 4~6
// 未完成
import java.util.*;
import static java.util.Arrays.*;
import static java.lang.Math.*;
public class Main {
	public static void main(String[] args) {
		new F().run();
	}
	static void debug(Object... o){
		System.err.println(deepToString(o));
	}
	static class D{
		void run(){
			Scanner sc=new Scanner(System.in);
			for(;;){
				int n=sc.nextInt();
				if(n==0)return;
				double[] xs=new double[n],ys=new double[n];
				for(int i=0;i<n;i++){
					xs[i]=sc.nextDouble();
					ys[i]=sc.nextDouble();
				}

			}
		}
	}
	static class E{
		void run(){
			Scanner sc=new Scanner(System.in);
			int D=sc.nextInt();
			for(int o=0;o<D;o++){
				int N=sc.nextInt();
				double[] B=new double[N+2],H=new double [N+2];
				for(int i=1;i<=N;i++){
					B[i]=sc.nextDouble()*30;
					H[i]=sc.nextDouble();
				}
				B[0]=0;B[N+1]=100*30;
				H[0]=50;H[N+1]=50;
				int M=sc.nextInt();
				double[] F=new double[M],A=new double[M];
				for(int i=0;i<M;i++){
					F[i]=sc.nextDouble()*30;A[i]=sc.nextDouble();
				}
				int L=sc.nextInt();
				int[] jagu=new int[M];
				for(int i=0;i<M;i++){
					for(int j=1;j<=N+1;j++){
						if(F[i]<B[j]){
							jagu[i]=j-1;
							break;
						}
					}
				}
				double[] size=new double[N+1];
				debug(B);
				for(int i=0;i<=N;i++){
					size[i]=B[i+1]-B[i];
				}
								debug(size);
								System.err.println();
				for(int oo=0;oo<L;oo++){
					int  P=sc.nextInt()*30 , T=sc.nextInt();
					double[] tank=new double[N+1];
					for(int i=0;i<M;i++){
						tank[jagu[i]] += A[i] * T;
					}
					
					LinkedList<Tank> l=new LinkedList<Tank>();
					for(int i=0;i<=N;i++){
						Tank t=new Tank(size[i],tank[i],H[i],H[i+1]);
//							t.show();
						l.add(t);
					}
					System.err.println();
					for(;;){
						boolean ok=true;
						for(int i=0;i<l.size();i++){
							Tank nt=l.get(i);
							if(!( (nt.tank <= nt.size * nt.h1 ) && (nt.tank <= nt.size * nt.h2 ))){
								ok=false;
								if(nt.h1 < nt.h2){
									if( (nt.tank - nt.size *  nt.h1) + l.get(i-1).tank < nt.h1 * l.get(i-1).size ){
										l.get(i-1).tank += nt.tank - nt.size * nt.h1;
										nt.tank = nt.size * nt.h1;
									}
									else{
										double sum=nt.tank + l.get(i-1).tank;
										l.get(i-1).tank =sum/2;nt.tank=sum/2;
										l.get(i-1).Merge(nt);
										l.remove(i);
									}
								}
								else{
									if(nt.h1==50 && nt.h2==50){
										ok=true;
										nt.tank=30*100*50;
										break;
									}
									if( (nt.tank - nt.size *  nt.h2) + l.get(i+1).tank < nt.h2 * l.get(i+1).size ){
										l.get(i+1).tank += nt.tank - nt.size * nt.h2;
										nt.tank = nt.size * nt.h2;
									}
									else{
										double sum=nt.tank + l.get(i+1).tank;
										l.get(i+1).tank =sum/2;nt.tank=sum/2;
										nt.Merge(l.get(i+1));
										l.remove(i+1);
									}
								}
							}
						}
						if(ok)break;
					}
					
					int sum=0;
					for(int i=0;i<l.size();i++)
						debug (i,l.get(i).tank,l.get(i).tank/l.get(i).size,l.get(i).size,l.get(i).h1,l.get(i).h2);
					for(int i=0;i<l.size();i++){
						sum+=l.get(i).size;
						if(P < sum){
							System.err.printf("%.3f%n",l.get(i).tank/l.get(i).size);
							break;
						}
					}
				}
			}
		}
		class Tank{
			double size,tank;
			double h1,h2;
			Tank(double size,double tank,double h1,double h2){
				this.size=size;this.tank=tank;
				this.h1=h1;this.h2=h2;
			}
			void Merge(Tank t){
				size=size+t.size;
				tank=tank+t.tank;
				h2=t.h2;
			}
			void show(){
				debug(size,tank/size,h1,h2);
			}
		}
	}
	static class F{
		void run(){
			Scanner sc=new Scanner(System.in).useDelimiter("[\\s-]+");
			for(;;) {
				int N = sc.nextInt();
				if (N==0) break;
				String cross1[] = new String[N];
				String cross2[] = new String[N];
				for(int i=0;i<N;i++) {
					cross1[i] = sc.next();
					cross2[i] = sc.next();
					//debug(cross1[i],cross2[i]);
				}
				process(N,cross1,cross2);
				int M = sc.nextInt(); debug(M);
				System.out.println(snum);
				debug(map);
				debug(arr);

				for(Map.Entry<String, Integer> f : map.entrySet()) {
					System.err.print(f.getKey()+":");
					for(Map.Entry<String, Integer> e : map.entrySet())
						if(arr[f.getValue()][e.getValue()])
							System.err.print(e.getKey()+",");
					System.err.println();
				}
				debug(color);
				for(int m=0;m<M;m++) {
					String q1=sc.next();
					String q2=sc.next();
					//debug(q1,q2);
					if (match(q1,q2)) System.out.println("YES"); else System.out.println("NO");
				}
			}
		}
		boolean arr[][];
		int color[];
		int snum;
		Map<String,Integer> map;
		void process(int N,String[] cr1, String cr2[]) {
			//			for(int i=0;i<N;i++) System.out.println(cr1[i]+";"+cr2[i]);
			map = new HashMap<String,Integer>();
			snum=0;
			for(int i=0;i<N;i++){
				if(!map.containsKey(cr1[i])){

					map.put(cr1[i], snum);					snum++;
				}
				if(!map.containsKey(cr2[i])){

					map.put(cr2[i], snum);					snum++;
				}
			}
			color = new int[snum];
			arr = new boolean[snum][snum];
			for(int i=0;i<N;i++){
				arr[map.get(cr1[i])][map.get(cr2[i])] = true;
			}
			int ct=1,cy=2; //coloring
			for(int i=0;i<snum;i++) {
				if(color[i]==0) {
					col(N,i,ct,cy);
					ct+=2;
					cy+=2;
				}
			}
			for(int k=0;k<snum;k++) {
				for(int i=0;i<snum;i++) {
					for(int j=0;j<snum;j++) {
						if (arr[i][k] & arr[k][j]) arr[i][j] = true;
					}
				}
			}

		}
		void col(int N, int i,int cc, int cn) {
			if (color[i] == 0) {
				color[i] = cc;
				for(int j=0;j<snum;j++) {
					if (arr[i][j] || arr[j][i]) col(N,j,cn,cc);
				}
			}			
		}

		boolean match(String q1, String q2) {
			//debug(map,q1,q2);
			if (!map.containsKey(q1)) return false;
			if (!map.containsKey(q2)) return false;
			int t1=map.get(q1), t2=map.get(q2);
			if(color[t1] == color[t2]) return false;
			if(!(Math.max(color[t1],color[t2]) %2 == 0 && Math.abs(color[t1]-color[t2])==1)) return false;
			if (arr[t1][t2]||(!arr[t1][t2]&&!arr[t2][t1])) return true;
			return false;
		}
	}
}
