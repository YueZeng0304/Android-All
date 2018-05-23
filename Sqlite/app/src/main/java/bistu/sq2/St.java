package bistu.sq2;

/**
 * Created by Administrator on 2018/5/9.
 */

public class St {

    private String num;
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }
    public void setNums(String num) {
        this.num = num;
    }

    public St(String num, String name) {
        super();
        this.num=num;
        this.name = name;

    }
}
