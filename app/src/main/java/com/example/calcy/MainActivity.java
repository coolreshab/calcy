package com.example.calcy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements GreenAdapter.ButtonListener{

    private RecyclerView rv;
    private GreenAdapter ga;
    private ArrayList<Character>symbols=new ArrayList<>();
    private Map<Character,Integer>pri=new HashMap<Character, Integer>();
    private TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=findViewById(R.id.rv);
        display=(TextView)findViewById(R.id.textView);
        populate();
        ga=new GreenAdapter(symbols,this);
        rv.setAdapter(ga);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager lm=new GridLayoutManager(this,4);
        rv.setLayoutManager(lm);
    }
    private void populate(){
        symbols.add('0');
        symbols.add('1');
        symbols.add('2');
        symbols.add('3');
        symbols.add('4');
        symbols.add('5');
        symbols.add('6');
        symbols.add('7');
        symbols.add('8');
        symbols.add('9');
        symbols.add('+');
        symbols.add('-');
        symbols.add('*');
        symbols.add('/');
        symbols.add('(');
        symbols.add(')');
        symbols.add('^');
        symbols.add('D');
        symbols.add('C');
        symbols.add('=');
        pri.put('^',3);
        pri.put('*',2);
        pri.put('/',2);
        pri.put('+',1);
        pri.put('-',1);
        pri.put('(',0);
    }
    @Override
    public void onCLickButton(int id) {
        //Toast.makeText(this,"yeahhhh",Toast.LENGTH_SHORT).show();
        if(id>=0 && id<=16){
            display.append(String.valueOf(symbols.get(id)));
        }
        else if(id==17){
            String d=display.getText().toString();
            if(d!=null)
                display.setText(d.substring(0,d.length()-1));
        }
        else if(id==18){
            display.setText("");
        }
        else{
            String eval=display.getText().toString();
            Stack<Character>st1;
            Stack<Double>st2;
            st1=new Stack<Character>();
            st2=new Stack<Double>();
            int i;
            char x,y;
            boolean ac,flag=true;
            double last=0,a,b;
            i=0;
            while(i<eval.length()){
                last=0;
                ac=false;
                while(i<eval.length() && Character.isDigit(eval.charAt(i))){
                    last*=10;
                    last+=(eval.charAt(i)-'0');
                    i++;
                    ac=true;
                }
                if(ac)
                    st2.push(last);
                if(i==eval.length())
                    break;
                x=eval.charAt(i);
                if(x=='(')
                    st1.push(x);
                else if(x==')'){
                    while(!st1.empty() && st1.peek()!='('){
                        y=st1.pop();
                        if(st2.size()<2) {
                            flag = false;
                            break;
                        }
                        b=st2.pop();
                        a=st2.pop();
                        a=solve(a,b,y);
                        st2.push(a);
                    }
                    if(!flag || st1.empty() || st1.peek()!='(') {
                        flag = false;
                        break;
                    }
                    st1.pop();
                }
                else{
                    if(st1.empty())
                        st1.push(x);
                    else{
                        while(!st1.empty() && pri.get(st1.peek())>=pri.get(x)){
                            if(st2.size()<2) {
                                flag=false;
                                break;
                            }
                            b=st2.pop();
                            a=st2.pop();
                            y=st1.pop();
                            a=solve(a,b,y);
                            st2.push(a);
                        }
                        if(!flag)
                            break;
                        st1.push(x);
                    }
                }
                i++;
            }
            while(!st1.empty()){
                if(st2.size()<2) {
                    flag=false;
                    break;
                }
                b=st2.pop();
                a=st2.pop();
                y=st1.pop();
                if(y=='(') {
                    flag = false;
                    break;
                }
                a=solve(a,b,y);
                st2.push(a);
            }
            if(st2.size()!=1)
                flag=false;
            if(!flag)
                display.setText("Wrong Input");
            else
                display.setText(String.valueOf(st2.pop()));
        }
    }
    double solve(double a,double b,char x){
        if(x=='+')
            return a+b;
        else if(x=='-')
            return a-b;
        else if(x=='/')
            return a/b;
        else if(x=='*')
            return a*b;
        else
            return Math.pow(a,b);
    }
}
