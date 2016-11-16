/**
 * Created by chendanni on 16/11/15.
 */
import Entity.Production;
import SyntaxAnalysis.Analyst;
import SyntaxAnalysis.TableBuilder;
import Utility.FileReader;
import Utility.PrintOut;

import java.util.ArrayList;

public class starter {

    public static void main(String[] args) {
        PrintOut printOut = new PrintOut();
//        Production p = new Production('E',"ABC");
//        printOut.printProdection(p);
//        p.moveDot();
//        printOut.printProdection(p);

        TableBuilder tableBuilder = new TableBuilder();
        tableBuilder.buildLRTable();
        Analyst analyst = new Analyst(tableBuilder.statements);
        analyst.analyse();
    }
}
