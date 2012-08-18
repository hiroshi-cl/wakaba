package com.github.hiroshi_cl.wakaba.v2010.sol.dp2008;
import java.util.*;
public class A {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Scanner sc=new Scanner(System.in);
		for(;;){
			int N=sc.nextInt(),M=sc.nextInt(),P=sc.nextInt();
			if(N==0){
				break;
			}
			int temp=0;
			int xm=0;
			for(int i=0;i<N;i++){
				int x=sc.nextInt();
				temp+=x;
				if(i==M-1){
					xm=x;
				}
			}
			System.out.println(xm==0? 0:temp*(100-P)/xm);
		}
	}

}