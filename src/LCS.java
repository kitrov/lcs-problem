/**
 * Created by Vik on 4/23/2016.
 */
public class LCS {
    Integer[][] table; // storing lcs problem table
    String string1, string2; // two strings for lcs search
    int len1, len2; // lengths of two strings
    int max;
    boolean filled = false; // true when table is filled

    public LCS(String str1, String str2) {
        this.string1 = str1;
        this.string2 = str2;
        this.max = 0;
        this.len1 = str1.length() + 1;
        this.len2 = str2.length() + 1;
        this.table = new Integer[len2][len1];
        // first row and column are 0s
        for (int i = 0; i < len1; i++) {
            table[0][i] = new Integer(0);
        }
        for (int i = 0; i < len2; i++) {
            table[i][0] = new Integer(0);
        }
    }

    public Integer[][] getTable() {
        return table;
    }

    public int getRowNum() {
        return len2;
    }

    public int getColNum() {
        return len1;
    }

    public void one_check(int i, int j){
        while (!( table[i - 1][j - 1] != null && table[i][j - 1] != null && table[i - 1][j] != null)) {
        } // waits if other threads working on needed table cells
        if (table[i][j] == null) {
            if (string2.charAt(i - 1) == string1.charAt(j - 1)) {
                table[i][j] = table[i - 1][j - 1] + 1;
            } else {
                table[i][j] = Math.max(table[i][j - 1], table[i - 1][j]);
            }
            if (table[i][j] > max) {
                max = table[i][j];
            }
        }
    }

    public int execute(){
        // change this to thread master-slave system
        RowSlave slave = new RowSlave(1, this);
        int max = 0;
        slave.start();
        try {
            slave.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*for (int i = 1; i < len2; i++) {
            for (int j = 1; j < len1; j++) {
                one_check(i, j);
            }
        }*/
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
