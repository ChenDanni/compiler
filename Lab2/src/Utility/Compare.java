/**
 * Created by chendanni on 16/11/15.
 */
package Utility;

import Entity.Core;
import Entity.Production;
import Entity.Sign;

import java.util.ArrayList;

public class Compare {
    PrintOut printOut = new PrintOut();

    public boolean tagIn(char c, ArrayList<Character> tags){
        for (int i = 0;i < tags.size();i++){
            if (c == tags.get(i))
                return true;
        }
        return false;
    }

    public boolean compareSign(Sign s1,Sign s2){
        if ((s1.sign != s2.sign)||(s1.type != s2.type))
            return false;
        return true;
    }

    public int compareProduction(Production p1, Production p2){
//        System.out.println("____");
//        printOut.printProdection(p1);
//        printOut.printProdection(p2);
//        System.out.println("____");
        if (p1.left != p2.left)
            return 0;
        if (p1.right.size() != p2.right.size())
            return 0;
        for (int i = 0;i < p1.right.size();i++){
            if(!compareSign(p1.right.get(i),p2.right.get(i))){
                return 0;
            }
        }
        if (p1.tags.size() != p2.tags.size())
            return 1;
        for (int i = 0;i < p1.tags.size();i++){
            char c = p1.tags.get(i);
            if (!tagIn(c,p2.tags)){
                return 1;
            }
        }
        return 2;
    }

    public ArrayList<Production> addProduction(ArrayList<Production> ps, Production p){
//        printOut.printProductions(ps);
        int co = -1;
        int leftSameIndex = -1;
        for (int i = 0; i < ps.size();i++){
            co = compareProduction(ps.get(i),p);
//            System.out.println(co);
            if (co == 1){
                leftSameIndex = i;
                break;
            }
            if (co == 2){
                break;
            }
        }
        if (co == 1){
            ps.get(leftSameIndex).addTags(p.tags);
            return ps;
        }else if (co ==2){
            return ps;
        }else{
            ps.add(p);
            return ps;
        }
    }

    public boolean compareCore(Core c1,Core c2){
        int count = 0;
        if (c1.cores.size() != c2.cores.size())
            return false;

        for (int i = 0;i < c1.cores.size();i++){
            for (int j = 0;j < c2.cores.size();j++){
                int co = compareProduction(c1.cores.get(i),c2.cores.get(j));
                if (co == 2){
                    count++;
                    break;
                }
            }
        }

        if (count != c1.cores.size())
            return false;

        return true;
    }

    public int coreIn(ArrayList<Core> cs, Core c){
        int in = -1;
        for (int i = 0;i < cs.size();i++){
            if (compareCore(cs.get(i),c)){
                in = i;
                break;
            }
        }
        return in;
    }
}

