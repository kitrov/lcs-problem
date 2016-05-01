/**
 * Created by Viktor-PC on 2016-04-25.
 */
public class RowSlave extends Thread {
    int thread_num;
    LCS lcs;

    public RowSlave(int thread_num, LCS lcs) {
        this.thread_num = thread_num;
        this.lcs = lcs;
    }

    public void run() {
        super.run();
        int rowLen = lcs.getRowNum(), colLen = lcs.getColNum() , ri = lcs.getRowi();
        while (lcs.getRowi() < rowLen) {
            for (int j = 1; j < colLen; j++) {
                lcs.one_check(ri, j);
            }
            ri = lcs.incRowi();
        }
    }
}
