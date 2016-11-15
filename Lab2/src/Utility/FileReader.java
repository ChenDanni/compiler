/**
 * Created by chendanni on 16/11/15.
 */
package Utility;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileReader {

    public ArrayList<String> readByLine(String filename){
        ArrayList<String> res = new ArrayList<String>();

        try{
            File file = new File(filename);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(reader);

            String line = "";
            while((line = br.readLine()) != null){
                res.add(line);
            }
            br.close();


        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }
}
