/**
 * Created by Vik on 4/23/2016.
 */
public class LCS {
    Integer[][] table; // storing lcs problem table
    String string1, string2; // two strings for lcs search
    int len1, len2; // lengths of two strings
    boolean filled = false; // true when table is filled

    public LCS(String str1, String str2) {
        this.string1 = str1;
        this.string2 = str2;
        this.len1 = str1.length() + 1;
        this.len2 = str2.length() + 1;
        this.table = new Integer[len1][len2];
        // first row and column are 0s
        for (int i = 0; i < len1; i++) {
            table[0][i] = new Integer(0);
        }
        for (int i = 0; i < len2; i++) {
            table[i][0] = new Integer(0);
        }
    }

    public int execute(){
        // table fill algorithm dynamic solution
        int max = 0;
        for (int i = 1; i < len2; i++) {
            for (int j = 1; j < len1; j++) {
                if(string1.charAt(i-1) == string2.charAt(j-1)) {
                    table[i][j] = table[i - 1][j - 1] + 1;
                } else {
                    table[i][j] = Math.max(table[i][j-1],table[i-1][j]);
                }
                if(table[i][j] > max){
                    max = table[i][j];
                }
            }
        }
        filled = true;
        return max;
    }

    public String solution_str() {
        String solution = new String("");
        if (!filled)
            execute();
        // answer search algorithm
        int ri = len1 - 1, // row iterator
                ci = len2 - 1, //column iterator
                pos = table[ci][ri];
        while (pos > 0) {
//            System.out.println("Start[" + ci + "]" + "[" + ri + "]");
            if (pos > table[ci][ri - 1]) {
                if (pos > table[ci - 1][ri]) {
                    ri--;
                    ci--;
                    solution = string1.charAt(ri) + solution;
                } else if (pos != table[ci - 1][ri]){
                    ri--;
                } else  {
                    ci--;
                }
            } else if (pos != table[ci][ri - 1]){
                ci--;
            } else {
                ri--;
            }
            pos = table[ci][ri];
        }
//        System.out.println("End[" + ci + "]" + "[" + ri + "]");
        return solution;
    }

    public String table_toString(){
        String temp = new String();
        temp = ("  " + string1 + "\n ");
        for (int i = 0; i < len2; i++) {
            if (i != 0) {
                temp += (string2.charAt(i - 1));
            }
            for (int j = 0; j < len1; j++) {
                temp += (table[i][j]);
            }
            temp += "\n";
        }
        return temp;
    }
}
