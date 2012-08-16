package com.github.hiroshi_cl.wakaba.v2009.sol.tc;//600
public class Tetrahedron {
	public String exists(String[] dd){
		int[][] as=new int[4][4];
		for(int i=0;i<4;i++){
			String[] ds=dd[i].split(" ");
			for(int j=0;j<4;j++){
				as[i][j]=Integer.valueOf(ds[j]);
			}
		}
		int a=as[1][2],
			b=as[2][0],
			c=as[0][1],
			d=as[3][0],
			e=as[3][1],
			f=as[3][2];
		return tetrahedron2_144(a, b, c, d, e, f)>=0?"YES":"NO";
	}
	int tetrahedron2_144(int a,int b,int c,int d,int e,int f) {//誤差回避のため、体積の２乗の１４４倍を返す。
		int a2 = a*a,b2=b*b,c2=c*c,d2=d*d,e2=e*e,f2=f*f;
		return   a2 * d2 * (b2 + c2 + e2 + f2 - a2 - d2)
			   + b2 * e2 * (c2 + a2 + f2 + d2 - b2 - e2)
			   + c2 * f2 * (a2 + b2 + d2 + e2 - c2 - f2)
			   - a2 * b2 * c2
			   - f2 * e2 * a2
			   - d2 * f2 * b2
			   - d2 * c2 * e2;
	}
}
