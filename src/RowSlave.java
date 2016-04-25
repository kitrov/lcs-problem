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
        for (int i = 1; i*thread_num < lcs.getRowNum(); i++) {
            for (int j = 1; j < lcs.getColNum(); j++) {
                lcs.one_check(i*thread_num, j);
            }
        }
    }
}
