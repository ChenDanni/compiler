/**
 * Created by chendanni on 16/11/15.
 */
package Entity;


import Utility.Helper;

import java.util.ArrayList;

public class LRStatement {
    public ArrayList<Production> productions;
    public ArrayList<Goto> gotos;
    public ArrayList<Goto> shift;
    public ArrayList<Goto> go_to;
    public Production reduce;
    public Sign accept;

    public int name;

    public LRStatement(int name){
        productions = new ArrayList<Production>();
        gotos = new ArrayList<Goto>();
        shift = new ArrayList<>();
        go_to = new ArrayList<>();
        reduce = null;
        accept = null;
        this.name = name;
    }
    public void addProductions(Production p){
        this.productions.add(p);
    }

    public void setGoto(ArrayList<Goto> gts){
        for (int i = 0;i < gts.size();i++){
            Goto gt = new Goto(gts.get(i).path,gts.get(i).gotoState);
            gotos.add(gt);
        }
    }

    public void setProductions(ArrayList<Production> ps){
        this.productions = new ArrayList<>(ps);
    }

    public void handlegotos(){
        for (int i = 0;i < gotos.size();i++){
            char c = gotos.get(i).path;
            int type = Helper.getType(c);
            Goto t = new Goto(gotos.get(i).path,gotos.get(i).gotoState);
            if (type == 0){
                go_to.add(t);
            }else if ((type == 1)||(type == 2)){
                shift.add(t);
            }
        }
    }
    public void handleReduce(){
        for (int i = 0;i < productions.size();i++){
            int len = productions.get(i).right.size();
            int dotIndex = productions.get(i).getDotIndex();
            if (dotIndex + 2 == len){
                Production t = productions.get(i);
                reduce = new Production(t.left,t.right,t.rightB,t.tags);
//                p = p.clean(p);

            }

        }
    }
    public void handleAccept(){
        for (int i = 0; i < productions.size(); i++) {
            Production p = productions.get(i);
            if ((p.left == '@')&&((p.right.get(p.right.size() - 2)).sign == '.')){
                accept = new Sign('$',5);
                break;
            }
        }
    }
}
