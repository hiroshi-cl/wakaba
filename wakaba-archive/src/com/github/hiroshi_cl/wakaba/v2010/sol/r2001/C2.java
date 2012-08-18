package com.github.hiroshi_cl.wakaba.v2010.sol.r2001;
import java.util.*;

public class C2 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		double[] a,b,c,d,ba,dc;
		double[][] p,q;
		while(sc.hasNext()){
		    int N = sc.nextInt();
		    if(N==0) break;
		    
		    a = new double[N+2];
		    b = new double[N+2];
		    c = new double[N+2];
		    d = new double[N+2];
		    ba = new double[N+2];
		    dc = new double[N+2];
		    p = new double[N+2][N+2];
		    q = new double[N+2][N+2];
		    
		    a[0] = b[0] = c[0] = d[0] = 0;
		    a[N+1] = b[N+1] = c[N+1] = d[N+1] = 1;
		    ba[0] = dc[0] = ba[N+1] = dc[N+1] = 0;
		    
		    for(int i=1; i<=N; i++)
		        a[i] = sc.nextDouble();
		    for(int i=1; i<=N; i++)
		        b[i] = sc.nextDouble();
		    for(int i=1; i<=N; i++)
		        c[i] = sc.nextDouble();
		    for(int i=1; i<=N; i++)
		        d[i] = sc.nextDouble();
		    
		    for(int i=1; i<=N; i++) {
		        ba[i] = b[i] - a[i];
		        dc[i] = d[i] - c[i];
		    }
		    
		    for(int i=0; i<N+2; i++) {
		        for(int j=0; j<N+2; j++) {
		            q[i][j] = (a[i]+c[j]*ba[i])/(1.0-dc[j]*ba[i]);
		            p[i][j] = (c[j]+a[i]*dc[j])/(1.0-dc[j]*ba[i]);
		            //System.out.println("(" + p[i][j] + "," + q[i][j] + ")");
		        }
		    }
		    
		    double ms = 0;
		    for(int i=0; i<N+1; i++) {
		        for(int j=0; j<N+1; j++) {
		            double dp1 = p[i][j+1] - p[i][j];
		            double dq1 = q[i+1][j] - q[i][j];
		            double dp2 = p[i+1][j+1] - p[i+1][j];
		            double dq2 = q[i+1][j+1] - q[i][j+1];
		            
		            double s = 
		                Math.abs((1.0-ba[i]*dc[j])*dp1*dq1)
		                + Math.abs((1.0-ba[i+1]*dc[j+1])*dp2*dq2);
		            
		            ms = Math.max(ms, s/2);
		            //System.out.println(s);
		        }
		    }
		    
		    System.out.format("%f%n",ms);
		    
		    //output for eclipse
		    //System.out.format("%f%n",new Double[]{new Double(ms)});
		}
		sc.close();
	}
}
