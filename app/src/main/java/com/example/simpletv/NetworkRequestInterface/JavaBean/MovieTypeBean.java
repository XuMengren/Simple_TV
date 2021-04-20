package com.example.simpletv.NetworkRequestInterface.JavaBean;

import java.util.List;

public class MovieTypeBean {

    /**
     * code : 0
     * data : [{"tname":"电影","tid":1},{"tname":"电视剧","tid":2},{"tname":"综艺","tid":3},{"tname":"动漫","tid":4},{"tname":"动作片","tid":5},{"tname":"爱情片","tid":6},{"tname":"科幻片","tid":7},{"tname":"恐怖片","tid":8},{"tname":"战争片","tid":9},{"tname":"喜剧片","tid":10},{"tname":"纪录片","tid":11},{"tname":"剧情片","tid":12},{"tname":"港台剧","tid":14},{"tname":"欧美剧","tid":15},{"tname":"日韩剧","tid":16},{"tname":"国内","tid":17},{"tname":"国际","tid":18},{"tname":"社会","tid":19},{"tname":"军事","tid":20},{"tname":"娱乐","tid":21},{"tname":"八卦","tid":22},{"tname":"科技","tid":23},{"tname":"财经","tid":24},{"tname":"公益","tid":25},{"tname":"评论","tid":26},{"tname":"时尚","tid":27},{"tname":"国产剧","tid":28},{"tname":"香港剧","tid":29},{"tname":"台湾剧","tid":30},{"tname":"日本剧","tid":31},{"tname":"韩国剧","tid":32},{"tname":"微电影","tid":33},{"tname":"伦理片","tid":34},{"tname":"国产动漫","tid":35},{"tname":"日韩动漫","tid":36},{"tname":"欧美动漫","tid":37},{"tname":"内地综艺","tid":38},{"tname":"港台综艺","tid":39},{"tname":"日韩综艺","tid":40},{"tname":"欧美综艺","tid":41},{"tname":"港台动漫","tid":42},{"tname":"海外动漫","tid":43},{"tname":"电影解说","tid":44},{"tname":"泰国剧","tid":45}]
     * msg : 执行成功
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
         * tname : 电影
         * tid : 1
         */

        private String tname;
        private int tid;

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }
    }
}
