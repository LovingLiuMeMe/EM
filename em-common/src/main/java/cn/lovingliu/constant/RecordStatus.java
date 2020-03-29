package cn.lovingliu.constant;

/**
 * @Author：LovingLiu
 * @Description: 借还记录状态
 * @Date：Created in 2020-02-07
 */
public class RecordStatus { //  0:借记待审核(默认)1:待归还2:归还待审核3:待处理4:已完结
    public static final int CHECK_NO_PASS = -1;
    public static final int WAIT_DECREASE_CHECK = 0;
    public static final int WAIT_RETURN = 1;
    public static final int WAIT_RETURN_CHECK = 2;
    public static final int WAIT_DEAL = 3;
    public static final int WAIT_DEAL_CHECK = 4;
    public static final int FINISAH = 5;

}
