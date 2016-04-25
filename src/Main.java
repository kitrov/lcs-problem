/**
 * Created by Vik on 4/23/2016.
 */
public class Main {
    public static void main(String args[]){
        System.out.println("LCS problem implementation");
        LCS test1 = new LCS("ABCD", "AACBD");
        System.out.println(test1.execute(1) + " " + test1.solution_str());
        System.out.println(test1.table_toString());
    }
}