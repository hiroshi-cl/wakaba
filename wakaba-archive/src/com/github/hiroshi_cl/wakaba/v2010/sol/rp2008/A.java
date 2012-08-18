package com.github.hiroshi_cl.wakaba.v2010.sol.rp2008;

import static java.util.Arrays.*;

import java.io.*;
import java.util.*;

public class A {
    public static void main(String[] args) throws Exception{
//        System.setIn(new BufferedInputStream(new FileInputStream("A.txt")));
        new A().run();
    }
    static void debug(Object... o){
        System.err.println(deepToString(o));
    }
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
//        debug(p);
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
//        debug(res);
        return res;
    }
}
