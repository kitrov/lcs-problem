/**
 * Created by Vik on 4/30/2016.
 */
public class SquareSlave extends Thread {
    int thread_num,x ,y;
    LCS lcs;

    public SquareSlave(int thread_num, LCS lcs, int x , int y) {
        this.thread_num = thread_num;
        this.lcs = lcs;
        this.x = x;
        this.y = y;
    }

    public void run() {
        super.run();
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
}
