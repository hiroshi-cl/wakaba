package com.github.hiroshi_cl.wakaba.v2009.sol.d2004.wakaba081221;
//icpc 2004 domestic D
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
public class Main{
	public static void main(String[] args) {
		new D().run();
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
					xs[i]=sc.nextDouble();ys[i]=sc.nextDouble();
				}
				int res=1;
				for(int i=0;i<n;i++){
					for(int j=i+1;j<n;j++){
						double[] rs=calc(xs[i],ys[i],xs[j],ys[j]);
						if(rs==null)continue;
						for(int k=0;k<2;k++){
							double nx=rs[k*2],ny=rs[k*2+1];
							int num=2;
							for(int l=0;l<n;l++){
								if(l==i || l==j)continue;
								if(hypot(nx-xs[l], ny-ys[l]) < 1)num++;
							}
							if(num>res)res=num;
						}
					}
				}
				System.out.println(res);
			}
		}
		double[] calc(double x1,double y1,double x2,double y2){
			double r=hypot(x1-x2,y1-y2);
			if(r>2)return null;
			return new double[]{
					(x1+x2)/2 + (y2-y1)/r*sqrt(1-(r*r/4)) , (y1+y2)/2 + (x1-x2)/r*sqrt(1-(r*r/4)) ,
					(x1+x2)/2 - (y2-y1)/r*sqrt(1-(r*r/4)) , (y1+y2)/2 - (x1-x2)/r*sqrt(1-(r*r/4)) 
			};
		}
		double hypot(double x,double y){
			return sqrt(x*x+y*y);
		}
	}
}
