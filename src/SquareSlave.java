/**
 * Created by Vik on 4/30/2016.
 */
public class SquareSlave extends Thread {
    int thread_num;
    LCS lcs;

    public SquareSlave(int thread_num, LCS lcs) {
        this.thread_num = thread_num;
        this.lcs = lcs;
    }

    private void square(int x, int y) {
        int rowLen = lcs.getRowNum(), colLen = lcs.getColNum();
        if (x < colLen && y < rowLen) {
            lcs.one_check(x, y);
        }
        if (x + 1 < colLen && y < rowLen) {
            lcs.one_check(x + 1, y);
        }
        if (x < colLen && y + 1 < rowLen) {
            lcs.one_check(x, y + 1);
        }
        if (x + 1 < colLen && y + 1 < rowLen) {
            lcs.one_check(x + 1, y + 1);
        }
    }

    public void run() {
        super.run();

    }
}
