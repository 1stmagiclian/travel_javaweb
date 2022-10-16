package cn.itcast.travel.domain;

import java.io.Serializable;

/**
 * 写一个类，用于封装后端返回前端数据对象
 */
public class Result implements Serializable {
    //后端返回结果正常为true，发生异常返回false
    private boolean flag;
    //后端返回结果数据对象
    private Object data;
    //发生异常的错误消息
    private String errorMsg;

    //定义无参构造器
    public Result() {
    }

    //定义两个有参构造器【重载的方式定义】
    public Result(boolean flag) {
        this.flag = flag;
    }
    public Result(boolean flag, String errorMsg) {
        this.flag = flag;
        this.errorMsg = errorMsg;
    }
    public Result(boolean flag, Object data, String errorMsg) {
        this.flag = flag;
        this.data = data;
        this.errorMsg = errorMsg;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
