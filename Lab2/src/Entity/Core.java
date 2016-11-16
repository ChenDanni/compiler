package Entity;

import java.util.ArrayList;

/**
 * Created by user on 16/11/15.
 */
public class Core {
    public ArrayList<Production> cores;
    public int statementName;

    public Core(){
        cores = new ArrayList<>();
        statementName = -1;
    }

    public void setCores(ArrayList<Production> cores){
        for (int i = 0;i < cores.size();i++){
            this.cores.add(cores.get(i));
        }
    }
    public void addProduction(Production p){
        Production np = new Production(p.left,p.right,p.rightB,p.tags);
        cores.add(np);
    }
}
