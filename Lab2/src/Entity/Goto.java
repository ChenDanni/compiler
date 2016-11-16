/**
 * Created by chendanni on 16/11/15.
 */
package Entity;

public class Goto {
    public char path;
    public int gotoState;

    public Goto(char path, int gotoStateName){
        this.path = path;
        this.gotoState = gotoStateName;
    }
}
