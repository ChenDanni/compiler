/**
 * Created by chendanni on 16/11/15.
 */
package Utility;

import Entity.Core;
import Entity.First;
import Entity.Production;
import Entity.Sign;

import java.util.ArrayList;

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
        System.out.print(p.left + " = ");
        for (int i = 0;i < p.right.size();i++){
            System.out.print(p.right.get(i).sign);
        }

        System.out.print(" , ");
        for (int i = 0;i < p.tags.size();i++){
            System.out.print(p.tags.get(i) + "/");
        }
        System.out.println();
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


}
