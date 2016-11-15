/**
 * Created by chendanni on 16/11/15.
 */
package Entity;

public class Goto {
    char path;
    String gotoState;

    public Goto(char path, String gotoStateName){
        this.path = path;
        this.gotoState = gotoStateName;
    }
}
