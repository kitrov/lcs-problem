import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Vik on 4/23/2016.
 */
public class LCS {

    Integer[][] table; // storing lcs problem table
    CountDownLatch[][] waitTable;
    String string1, string2; // two strings for lcs search
    int len1, len2, rowi; // lengths of two strings, row iter
    int max;
    int[][] iter;
    boolean filled = false; // true when table is filled

    public LCS(String str1, String str2) {
        this.string1 = str1;
        this.string2 = str2;
        this.max = 0;
        this.len1 = str1.length() + 1;
        this.len2 = str2.length() + 1;
        this.rowi = 1;
        this.table = new Integer[len2][len1];
        this.waitTable = new CountDownLatch[len2][len1];
        this.iter = new int[len2][2];
//        for (int i = 1; i < len2/2+0.5; i++) {
//            ,
//        }
        // first row and column are 0s
        for (int i = 0; i < len1; i++) {
            table[0][i] = new Integer(0);
            waitTable[0][i] = new CountDownLatch(0);
        }
        for (int i = 0; i < len2; i++) {
            table[i][0] = new Integer(0);
            waitTable[i][0] = new CountDownLatch(0);
        }
        for (int i = 1; i < len2; i++) {
            for (int j = 1; j < len1; j++) {
                table[i][j] = new Integer(-1);
                waitTable[i][j] = new CountDownLatch(1);
            }
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

    public int getRowi() {
        return rowi;
    }

    public int incRowi() {
        this.rowi++;
        return this.rowi;
    }

    public void one_check(int i, int j) {
        try {
            //        System.out.println(table_toString());
//        System.out.println(i+" "+j);
                        /* while (!(table[i - 1][j - 1] != -1 && table[i][j - 1] != -1 && table[i - 1][j] != -1)) {
                        try {
                        synchronized (waitTable[i - 1][j - 1]) {
                        if (table[i - 1][j - 1] == -1) {
                        waitTable[i - 1][j - 1].wait();
                        }
                        }
                        synchronized (waitTable[i - 1][j]) {
                        if (table[i - 1][j] == -1) {
                        waitTable[i - 1][j].wait();
                        }
                        }
                        synchronized (waitTable[i][j - 1]) {
                        if (table[i][j - 1] == -1) {
                        waitTable[i][j - 1].wait();
                        }
                        }
                        //                    this.wait(); // waits if other threads working on needed table cells
                        } catch (InterruptedException e) {
                        e.printStackTrace();
                        }
                        }*/
            if (table[i-1][j-1] == -1) {
                waitTable[i - 1][j - 1].await();
            }
            if (table[i -1][j] == -1) {
                waitTable[i - 1][j].await();
            }
            if (table[i][j - 1] == -1) {
                waitTable[i][j - 1].await();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
                 
                /*try {
                        while (!(table[i - 1][j - 1] != -1)) {
                                synchronized (waitTable[i - 1][j - 1]) {
                                        waitTable[i - 1][j - 1].wait();
                                }
                        }

                        while (!(table[i][j - 1] != -1)) {
                                synchronized (waitTable[i][j - 1]) {
                                        waitTable[i][j - 1].wait();
                                }
                        }

                        while (!(table[i - 1][j] != -1)) {
                                synchronized (waitTable[i - 1][j]) {
                                        waitTable[i - 1][j].wait();
                                }
                        }
                } catch (InterruptedException ex) {
                        ex.printStackTrace();
                }*/

        if (table[i][j] == -1) {
            if (string2.charAt(i - 1) == string1.charAt(j - 1)) {
                table[i][j] = table[i - 1][j - 1] + 1;
            } else {
                table[i][j] = Math.max(table[i][j - 1], table[i - 1][j]);
            }
            if (table[i][j] > max) {
                max = table[i][j];
            }
            waitTable[i][j].countDown();
                        /*synchronized (waitTable[i][j]) {
                                waitTable[i][j].notifyAll();
                        }*/
//                this.notify();
        }
    }

    public int execute(int thread_num) {
        // change this to thread master-slave system
        RowSlave[] slave = new RowSlave[thread_num];
        for (int i = 0; i < thread_num; i++) {
            slave[i] = new RowSlave(i + 1, this);
            slave[i].start();
        }
                /*for (int i = 1; i < len2; i++) {
                 for (int j = 1; j < len1; j++) {
                 one_check(i, j);
                 }
                 }*/
                /*synchronized (waitTable[len2 - 1][len1 - 1]) {
                        while (table[len2 - 1][len1 - 1] == -1) {
                                try {
                                        waitTable[len2 - 1][len1 - 1].wait();
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                                // waits for result cell
                        }
                }*/
        for (int i = 0; i < thread_num; i++) {
            try {
                slave[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        filled = true;
        return max;
    }

    public String solution_str() {
        String solution = new String("");
        if (!filled) {
            execute(1);
        }
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
                } else if (pos != table[ci - 1][ri]) {
                    ri--;
                } else {
                    ci--;
                }
            } else if (pos != table[ci][ri - 1]) {
                ci--;
            } else {
                ri--;
            }
            pos = table[ci][ri];
        }
//        System.out.println("End[" + ci + "]" + "[" + ri + "]");
        return solution;
    }

    public String table_toString() {
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