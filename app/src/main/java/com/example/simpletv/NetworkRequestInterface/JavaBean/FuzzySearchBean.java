package com.example.simpletv.NetworkRequestInterface.JavaBean;

import java.util.List;

public class FuzzySearchBean {

    /**
     * code : 0
     * data : [{"v_name":"微微⼀一笑很倾城"},{"v_name":"微微⼀一笑很倾城电视剧"},{"v_name":"微微的猩红"},{"v_name":"微微⼀一笑很倾城"},{"v_name":"微微⼀一笑很诡异"},{"v_name":"微微⼀一笑很诡异"},{"v_name":"死侍迷途"},{"v_name":"微微⼀一笑很倾城"}]
     * msg : 执⾏行行成功
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * v_name : 微微⼀一笑很倾城
         */

        private String v_name;

        public String getV_name() {
            return v_name;
        }

        public void setV_name(String v_name) {
            this.v_name = v_name;
        }
    }
}
