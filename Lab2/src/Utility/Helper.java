package Utility;

import Entity.Production;

import java.util.ArrayList;

/**
 * Created by user on 16/11/15.
 */
public class Helper {
    static String filename = "input/cfg.txt";
    public static int getType(char c){
        if ((c>='A')&&(c<='Z'))
            return 0;

        if ((c>='a')&&(c<='z'))
            return 1;

        if (c == '.')
            return 3;

        return 2;
    }

    public static ArrayList<Production> initialCFGs(){
        ArrayList<Production> cfgs = new ArrayList<Production>();
        FileReader reader = new FileReader();
        ArrayList<String> cfg = reader.readByLine(filename);
        for (int i = 0;i < cfg.size();i++){
            String[] its = cfg.get(i).split("=");
            char left = its[0].charAt(0);
            String right = its[1];
            Production t = new Production(left,right);
            cfgs.add(t);
        }
        cfgs.get(0).addTag('$');
        return cfgs;
    }

}
