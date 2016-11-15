/**
 * Created by chendanni on 16/11/15.
 */
package Entity;

import java.util.ArrayList;

public class First {
    public Sign sign;
    public ArrayList<Character> firsts;

    public First(Sign sign){
        this.sign = sign;
        firsts = new ArrayList<>();
    }

    public void setFirsts(ArrayList<Character> cs){
        for (int i = 0;i < cs.size();i++){
            firsts.add(cs.get(i));
        }
    }
}
