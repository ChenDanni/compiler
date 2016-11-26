/**
 * Created by chendanni on 16/11/15.
 */
package Utility;

import Entity.*;

import java.util.ArrayList;
import java.util.Stack;

public class PrintOut {

    public void printSign(Sign s){
        System.out.println(s.sign + " " + s.type);
    }

    public void printSigns(ArrayList<Sign> s){
        System.out.println("---------------------");
        for (int i = 0;i < s.size();i++){
            printSign(s.get(i));
        }
        System.out.println("---------------------");
    }

    public void printProdection(Production p){
        if (p == null)
            return;
        System.out.print(p.left + " = ");
        for (int i = 0;i < p.right.size();i++){
            System.out.print(p.right.get(i).sign);
        }

        System.out.print(" ");
        for (int i = 0;i < p.tags.size();i++){
            System.out.print(p.tags.get(i) + "/");
        }
//        System.out.println();
    }

    public void printProductions(ArrayList<Production> ps){
        for (int i = 0;i < ps.size();i++){
            printProdection(ps.get(i));
        }
    }

    public void printFirst(First f){
        System.out.print(f.sign.sign + " : ");
        for (int i = 0;i < f.firsts.size();i++){
            System.out.print(f.firsts.get(i) + "/");
        }
        System.out.println();
    }
    public void printFirsts(ArrayList<First> fs){
        for (int i = 0; i < fs.size();i++){
            printFirst(fs.get(i));
        }
    }

    public void printChars(ArrayList<Character> cs){
        for (int i = 0;i < cs.size();i++){
            System.out.print(cs.get(i) + " ");
        }
        System.out.println();
    }

    public void printCore(Core c){
        System.out.println("--------------");
        for (int i = 0;i < c.cores.size();i++){
            printProdection(c.cores.get(i));
        }
        System.out.println("--------------");
    }
    public void printCores(ArrayList<Core> cs){
        for (int i = 0;i < cs.size();i++){
            printCore(cs.get(i));
        }
    }

    public void printGoto(Goto gt){

        System.out.print(gt.path + "->" + gt.gotoState);
    }

    public void printGotos(ArrayList<Goto> gts){
        for (int i = 0;i < gts.size();i++){
            printGoto(gts.get(i));
        }
    }

    public void printStatement(LRStatement lrs){
        System.out.print(lrs.name + "       [SHIFT]: ");
        for (int i = 0;i < lrs.shift.size();i++){
            System.out.print(lrs.shift.get(i).path + "_S" + lrs.shift.get(i).gotoState + "  |  ");
        }
        System.out.print("            [REDUCE]: ");
        printProdection(lrs.reduce);
//        System.out.print("  |  ");
        System.out.print("           [GOTO]: ");
        for (int i = 0;i < lrs.go_to.size();i++){
            printGoto(lrs.go_to.get(i));
            System.out.print("  |  ");
        }
        System.out.println();

    }

    public void printStatements(ArrayList<LRStatement> ss){
        for (int i = 0;i < ss.size();i++){
            printStatement(ss.get(i));
        }
    }

    public void printStateStack(Stack stack){
        Stack temp = new Stack();
        while (!stack.empty()){
            int s = (int)stack.pop();
            temp.push(s);
        }
        System.out.print("[STATE STACK] ");
        while (!temp.empty()){
            int t = (int) temp.pop();
            stack.push(t);
            System.out.print(t + " ");
        }
        System.out.print("    ");
    }
    public void printSymbolStack(Stack stack){
        Stack temp = new Stack();
        while (!stack.empty()){
            char s = (char)stack.pop();
            temp.push(s);
        }
        System.out.print("[SYMBOL STACK] ");
        while (!temp.empty()){
            char t = (char) temp.pop();
            stack.push(t);
            System.out.print(t + " ");
        }
        System.out.print("    ");
    }
    public void printInput(int i,char[] cs){
        System.out.print("[INPUT] ");
        for (int j = i; j < cs.length; j++) {
            System.out.print(cs[j] + " ");
        }
//        System.out.println();
    }
}
