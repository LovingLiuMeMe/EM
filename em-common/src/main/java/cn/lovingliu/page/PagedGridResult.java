package cn.lovingliu.page;

import java.io.Serializable;
import java.util.List;

/**
 * @Author：LovingLiu
 * @Description: 分页信息的封装类
 * @Date：Created in 2020-01-02
 */
public class PagedGridResult implements Serializable {
    private static final long serialVersionUID = 2006401223274663580L;

    private int page; // 当前页数
    private int total; // 总页数
    private long records; // 总记录数
    private List<?> rows; // 每行显示的内容

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
