/**
 * Created by chendanni on 16/11/15.
 */
package SyntaxAnalysis;

import Entity.Goto;
import Entity.LRStatement;
import Entity.Production;
import Entity.Sign;
import Utility.FileReader;
import Utility.PrintOut;

import java.util.ArrayList;
import java.util.Stack;

public class Analyst {
    FileReader reader = new FileReader();
    static String filename = "input/source.txt";
    PrintOut printOut = new PrintOut();

    Stack symbolStack = new Stack();

    Stack stateStack = new Stack();

    ArrayList<LRStatement> statements = new ArrayList<LRStatement>();

    public Analyst(ArrayList<LRStatement> statements){
//        System.out.println(statements.size());
        this.statements = new ArrayList<>(statements);
    }

    public void initalStack(){
        symbolStack.push('$');
        stateStack.push(0);
    }

    public void analyseLine(String line){
        char[] array = line.toCharArray();
        char currentC;
        int statePeek = -1;
        char symbolPeek = ' ';
        boolean err = true;

        initalStack();

        for (int i = 0;i < array.length;i++){
            err = true;
            printOut.printStateStack(stateStack);
            printOut.printSymbolStack(symbolStack);
            printOut.printInput(i,array);
            currentC = array[i];
            statePeek = (int) stateStack.peek();
            symbolPeek = (char) symbolStack.peek();
            LRStatement sn = statements.get(statePeek);
            ArrayList<Goto> shift = sn.shift;
            ArrayList<Goto> go_to = sn.go_to;
            Production reduce = sn.reduce;
            if (sn.accept != null){
                if (currentC == sn.accept.sign){
                    System.out.println();
                    System.out.println("----SUCCESS----");
                    return;
                }

            }

            for (int j = 0; j < shift.size(); j++) {
                if (shift.get(j).path == currentC){
                    symbolStack.push(currentC);
                    stateStack.push(shift.get(j).gotoState);
                    System.out.println("    移入 " + currentC);
                    err = false;
                    break;
                }
            }

            if (reduce != null){
                ArrayList<Character> tags = reduce.tags;
                for (int j = 0; j < tags.size(); j++) {
                    if (tags.get(j) == currentC){
                        err = false;
                        char left = reduce.left;
                        Production np = new Production(reduce.left,reduce.right,
                                reduce.rightB,reduce.tags);
                        np = np.clean(np);
                        ArrayList<Sign> right = np.right;
                        for (int k = 0; k < right.size(); k++) {
                            symbolStack.pop();
                            stateStack.pop();
                        }
                        System.out.print("    规约 ");
                        printOut.printProdection(np);
                        System.out.println();
                        symbolStack.push(left);
                        int stateNow = (int) stateStack.peek();
                        LRStatement sNow = statements.get(stateNow);
                        ArrayList<Goto> gotos = sNow.go_to;
                        for (int k = 0; k < gotos.size(); k++) {
                            if (gotos.get(k).path == left){
                                stateStack.push(gotos.get(k).gotoState);
                                i--;
                                break;
                            }
                        }
                    }
                }
            }
            if (err){
                System.out.println();
                System.out.println("ERROR!");
                return;
            }

        }
    }


    public void analyse(){

        ArrayList<String> lines = reader.readByLine(filename);

        for (int i = 0;i < lines.size();i++){
            analyseLine(lines.get(i) + '$');
        }

    }


}
