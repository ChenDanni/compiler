/**
 * Created by chendanni on 16/11/15.
 */
package SyntaxAnalysis;

import Entity.*;
import Utility.Compare;
import Utility.FileReader;
import Utility.Helper;
import Utility.PrintOut;

import java.util.ArrayList;

public class TableBuilder {
    PrintOut printOut = new PrintOut();
    Compare compare = new Compare();
    static String filename = "input/cfg.txt";
    public ArrayList<LRStatement> statements = new ArrayList<LRStatement>();
    ArrayList<Production> cfgs = new ArrayList<Production>(Helper.initialCFGs());
    ArrayList<First> firsts = new ArrayList<>();
    ArrayList<Sign> allSigns = new ArrayList<>();
    ArrayList<Core> cores = new ArrayList<>();

    int stateCount = 0;

    public ArrayList<Sign> addElements(ArrayList<Sign> signs,ArrayList<Character> tags){
        for (int i = 0;i < tags.size();i++){
            Sign temp = new Sign(tags.get(i),1);
            signs.add(temp);
        }
        return signs;
    }

    public boolean signIn(Sign s){
        for (int i = 0;i < allSigns.size();i++){
            if (allSigns.get(i).sign == s.sign)
                return true;
        }
        return false;
    }

    public boolean charIn(ArrayList<Character> cs, char c){
        for (int i = 0;i < cs.size();i++){
            if (c == cs.get(i)){
                return  true;
            }
        }
        return false;
    }

    public void getAllSigns(){
        for (int i = 1;i < cfgs.size();i++){
            Production p = cfgs.get(i);
            Sign l = new Sign(p.left,0);
            if (!signIn(l))
                allSigns.add(l);
            for (int j = 0;j < p.right.size();j++){
                if (!signIn(p.right.get(j)))
                    allSigns.add(p.right.get(j));
            }
        }
    }

    public boolean inFirst(char c){
        for (int i = 0;i < firsts.size();i++){
            if (c == firsts.get(i).sign.sign)
                return true;
        }
        return false;
    }

    public ArrayList<Character> initFirst(Sign sign){
        ArrayList<Character> ret = new ArrayList<>();
        if ((sign.type == 3)||(sign.type == 4))
            return ret;
        if ((sign.type == 1)||(sign.type == 2)){
            ret.add(sign.sign);
            return ret;
        }
        char l = sign.sign;

        for (int i = 1; i < cfgs.size();i++){
            Production t = cfgs.get(i);
            if (l == t.left){
                if (t.right.get(1).sign != l){
                    ArrayList<Character> tf = initFirst(t.right.get(1));
                    for (int k = 0;k < tf.size();k++){
                        if (!charIn(ret,tf.get(k)))
                            ret.add(tf.get(k));
                    }
                }
            }
        }
        return ret;
    }

    public ArrayList<Character> getFirst(ArrayList<Sign> signs){
        ArrayList<Character> ret = new ArrayList<>();
        if (signs.size() == 0){
            System.out.println("!!!!err!!!!");
            return ret;
        }
        Sign s = signs.get(0);

        if (s.type == 4){
            for (int i = 1;i < signs.size();i++){
                ret.add(signs.get(i).sign);
            }
            return ret;
        }
        for (int i = 0;i < firsts.size();i++){
            if (s.sign == firsts.get(i).sign.sign){
                return firsts.get(i).firsts;
            }
        }
        return ret;
    }

    public ArrayList<Production> getExtend(Sign start){
        ArrayList<Production> ret = new ArrayList<>();
        for (int i = 0;i < cfgs.size();i++){
            char t = cfgs.get(i).left;
//            printOut.printSign(t);
            if (start.sign == t){
                Production p = new Production(cfgs.get(i).left,cfgs.get(i).rightB);
                ret.add(p);
            }
        }
        return ret;
    }

    public int getIndexOfC(ArrayList<Character> cs, char c){
        int index = -1;
        for (int i = 0;i < cs.size();i++){
            if (cs.get(i) == c)
                return i;
        }
        return index;
    }

    public boolean statementExist(Core core){
        int cname = core.statementName;
        for (int i = 0;i < statements.size();i++){
            int sname = statements.get(i).name;
            if (sname == cname)
                return true;
        }
        return false;
    }

    public void getStatement(Core core){
        if(statementExist(core)) return;
        ArrayList<Production> statesp = new ArrayList<Production>(core.cores);
        ArrayList<Core> newCore = new ArrayList<>();
        ArrayList<Character> paths = new ArrayList<>();
        ArrayList<Goto> gts = new ArrayList<>();
        LRStatement state = new LRStatement(core.statementName);

        for (int i = 0;i < statesp.size();i++){
            Production temp = statesp.get(i);
            Sign dotNext = temp.getDotNext();
            if (dotNext.type == 0){
                ArrayList<Sign> dotAfter = temp.getDotAfter();
                dotAfter = addElements(dotAfter,temp.tags);
                ArrayList<Character> firsts = getFirst(dotAfter);
                ArrayList<Production> extend = getExtend(dotNext);
                for (int j = 0;j < extend.size();j++){
                    extend.get(j).setTags(firsts);
                    statesp = compare.addProduction(statesp,extend.get(j));
                }
            }
        }
        for (int i = 0;i < statesp.size();i++){
            Production t = statesp.get(i);
            Sign dotN = t.getDotNext();
            if (dotN.type != 4){
                Production np = new Production(t.left,t.right,t.rightB,t.tags);
                np.moveDot();
                int index = getIndexOfC(paths,dotN.sign);
                if (index > -1){
                    newCore.get(index).addProduction(np);
                }else{
                    paths.add(dotN.sign);
                    Core nc = new Core();
                    nc.addProduction(np);
                    newCore.add(nc);
                }
            }
        }
        for (int i = 0;i < newCore.size();i++){
            int index = compare.coreIn(cores,newCore.get(i));
            if (index == -1){
                newCore.get(i).statementName = stateCount;
                stateCount++;
                cores.add(newCore.get(i));
                Goto gt = new Goto(paths.get(i),newCore.get(i).statementName);
                gts.add(gt);
            }else{
                Goto gt = new Goto(paths.get(i),cores.get(index).statementName);
                gts.add(gt);
            }
        }
        state.setGoto(gts);
        state.setProductions(statesp);

        statements.add(state);
    }

    public void improveStates(){
        for (int i = 0;i < statements.size();i++){
            statements.get(i).handlegotos();
            statements.get(i).handleReduce();
            statements.get(i).handleAccept();
        }
    }

    public void buildLRTable(){
        getAllSigns();
        for (int i = 0;i < allSigns.size();i++){
            ArrayList<Character> cs = initFirst(allSigns.get(i));
            First f = new First(allSigns.get(i));
            f.setFirsts(cs);
            firsts.add(f);
        }
//        初始化第一个内核
        ArrayList<Production> coreStart = new ArrayList<>();
        coreStart.add(cfgs.get(0));
        Core s = new Core();
        s.setCores(coreStart);
        s.statementName = stateCount;
        stateCount++;
        cores.add(s);

        for (int i = 0;i < cores.size();i++){
            getStatement(cores.get(i));
        }

        improveStates();
        printOut.printStatements(statements);
    }
}
