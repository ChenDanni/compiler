/**
 * Created by chendanni on 16/11/15.
 */
package Entity;
import java.util.ArrayList;

import Utility.Helper;

public class Production {
    public char left;
    public ArrayList<Sign> right;
    public ArrayList<Character> tags;
    public Sign dot;
    public String rightB;

    public Production(char left,String right){
        this.rightB = right;
        this.right = new ArrayList<Sign>();
        this.tags = new ArrayList<Character>();
        String[] r = right.split("");
        this.left = left;
        dot = new Sign('.',3);
        this.right.add(dot);
        for (int i = 0;i < r.length;i++){
            char c = r[i].charAt(0);
            Sign temp = new Sign(c, Helper.getType(c));
            this.right.add(temp);
        }
        Sign finish = new Sign('#',4);
        this.right.add(finish);
    }
    public void setTags(ArrayList<Character> tags){
        this.tags = new ArrayList<Character>();
        for (int i = 0;i < tags.size();i++){
            this.tags.add(tags.get(i));
        }
    }

    public Production(char left,ArrayList<Sign> rights,String rightB,ArrayList<Character> tags){
        this.rightB = rightB;
        this.left = left;
        this.right = new ArrayList<Sign>();
        this.tags = new ArrayList<Character>();

        for (int i = 0;i < rights.size();i++){
            Sign s = new Sign(rights.get(i).sign,rights.get(i).type);
            this.right.add(s);
        }
        this.tags = new ArrayList<>(tags);
    }

    public void addTag(char tag){
        this.tags.add(tag);
    }

    public boolean tagIn(char tag){
        for (int i = 0;i < tags.size();i++){
            if (tags.get(i) == tag)
                return true;
        }
        return false;
    }

    public void addTags(ArrayList<Character> tags){
        for (int i = 0;i < tags.size();i++){
            if (!tagIn(tags.get(i))){
                this.tags.add(tags.get(i));
            }
        }
    }

    public int getDotIndex(){
        for (int i = 0;i < right.size();i++){
            if (right.get(i).type == 3){
                return i;
            }
        }
        return -1;
    }

    public Sign getDotNext(){
        int dotIndex = getDotIndex();
        return right.get(dotIndex+1);
    }

    public ArrayList<Sign> getDotAfter(){
        ArrayList<Sign> ret = new ArrayList<Sign>();
        int dotIndex = getDotIndex();
        int len = right.size();
        for (int i = dotIndex + 2;i < len;i++){
            ret.add(right.get(i));
        }
        return ret;
    }
    public void moveDot(){
        int index = getDotIndex();
        char befores = right.get(index).sign;
        int beforet = right.get(index).type;
        char afters = right.get(index+1).sign;
        int aftert = right.get(index+1).type;

        right.get(index).sign = afters;
        right.get(index).type = aftert;

        right.get(index+1).sign = befores;
        right.get(index+1).type = beforet;

    }

    public Production clean(Production p){
        char left = p.left;
        ArrayList<Sign> newRight = new ArrayList<>();

        for (int i = 0;i < p.right.size();i++){
            if ((p.right.get(i).type == 3)||(p.right.get(i).type == 4))
                continue;
            Sign s = new Sign(p.right.get(i).sign,p.right.get(i).type);
            newRight.add(s);
        }
        Production ret = new Production(left,newRight,"",new ArrayList<Character>());
        return ret;
    }

}
