package com.github.hiroshi_cl.wakaba.v2010.lib.string.parsing;

/*
 * 四則演算と、括弧を実装している。
 * 
 * Expr   = Term{(+|-)Term}
 * Term   = Fact{(*|/)Fact}
 * Fact   = (Expr)|number
 * number = {digit}
 * 
 * の定義に乗っ取り、再帰的降下型で書いている。
 * 
 * 参考サイト:
 * 10分で書ける、お手軽パーザー　http://fxp.hp.infoseek.co.jp/arti/parser.html
 * ALGORITHM NOTE          http://algorithms.blog55.fc2.com/blog-entry-154.html
 * 
 * verified : pku1460  
 */
public class Calculator {
	char[] cs;
	int p;
	Calculator(String s){
		cs=(s+"=").toCharArray();
	}
	int calc(){
		p=0;
		return Expr();
	}
	int Expr(){
		int value=Term();
		while(cs[p]=='+'||cs[p]=='-'){
			if(cs[p]=='+'){
				p++;
				value+=Term();
			}
			else{
				p++;
				value-=Term();
			}
		}
		return value;
	}
	int Term(){
		int value=Fact();
		while(cs[p]=='*'||cs[p]=='/'){
			if(cs[p]=='*'){
				p++;
				value*=Fact();
			}
			else{
				p++;
				int div=Fact();
				if(div==0)return Integer.MAX_VALUE;
				value/=div;
			}
		}
		return value;
	}
	int Fact(){
		int value=0;
		if(cs[p]=='('){
			p++;
			value=Expr();
			p++;
		}
		else{
			while(Character.isDigit(cs[p])){
				value*=10;
				value+=cs[p]-'0';
				p++;
			}
		}
		return value;
	}
}
