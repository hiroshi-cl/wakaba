package com.github.hiroshi_cl.wakaba.v2010.sol.d2008;
import java.util.*;
import java.io.*;
public class A {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("A.in"));
//		sc = new Scanner(System.in);
		System.setOut(new PrintStream("A.out"));
		for(int n = sc.nextInt(), m = sc.nextInt(); (n | m) > 0; n = sc.nextInt(), m = sc.nextInt() ) {
			int[] cT=new int[n];
			int sT=0;
			for(int i=0;i<n;i++){
				cT[i]=sc.nextInt();
				sT+=cT[i];
				
			}
			Arrays.sort(cT);
			int sH=0;
			int[] cH=new int[m];
			for(int i=0;i<m;i++){
				cH[i]=sc.nextInt();
				sH+=cH[i];
			}
			Arrays.sort(cH);
			int aT=101,aH=101,min=203;
			for(int i=0;i<n;i++){
				for(int j=0;j<m;j++){
					if(sT-cT[i]+cH[j]==sH-cH[j]+cT[i]){
						if(min>cT[i]+cH[j]){
							min=cT[i]+cH[j];
							aT=cT[i];
							aH=cH[j];
						}
					}
				}
			}
			System.out.println(min==203? -1 : aT +" "+aH);
		}
	}
}
