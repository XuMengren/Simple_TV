package com.example.simpletv.NetworkRequestInterface.JavaBean;

import java.util.List;

public class BannerBean {

    /**
     * code : 0
     * data : [{"v_note":"HD","v_id":30627,"v_score":9,"v_pic":"https://pic.szjal.cn/img/9f80d41b8b8306bdb8e8171e0e128a26.jpg","v_spic":"https://tva1.sinaimg.cn/large/0082zybply1gbt020qhhjj318f0lg4qp.jpg","v_name":"寄⽣生⾍虫","tid":12},{"v_note":"更更新⾄至24集","v_id":43753,"v_score":7,"v_pic":"https://pic.szjal.cn/img/164d55edf47aa8ce2dc107da5b4aa105.jpg","v_spic":"https://tva1.sinaimg.cn/large/007S8ZIlgy1gerdlfo1uzj315b0j6467.jpg","v_name":"我的刺刺猬⼥女女孩","tid":28},{"v_note":"更更新⾄至111集","v_id":96503,"v_score":8,"v_pic":"https://pic.szjal.cn/img/5a642aa486825.jpg","v_spic":"https://tva1.sinaimg.cn/large/006tNbRwly1gan2x9905kj312p0k6qv5.jpg","v_name":"⽃斗罗⼤大陆","tid":35},{"v_note":"12集全","v_id":93890,"v_score":6,"v_pic":"https://pic.szjal.cn/img/bd51eb468beaad86a38504b6d915375b.jpg","v_spic":"https://tva1.sinaimg.cn/large/007S8ZIlly1gfdxt7ujtjj31ag0luwib.jpg","v_name":"余罪 第⼀一季","tid":28}]
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
         * v_note : HD
         * v_id : 30627
         * v_score : 9
         * v_pic : https://pic.szjal.cn/img/9f80d41b8b8306bdb8e8171e0e128a26.jpg
         * v_spic : https://tva1.sinaimg.cn/large/0082zybply1gbt020qhhjj318f0lg4qp.jpg
         * v_name : 寄⽣生⾍虫
         * tid : 12
         */

        private String v_note;
        private int v_id;
        private int v_score;
        private String v_pic;
        private String v_spic;
        private String v_name;
        private int tid;

        public String getV_note() {
            return v_note;
        }

        public void setV_note(String v_note) {
            this.v_note = v_note;
        }

        public int getV_id() {
            return v_id;
        }

        public void setV_id(int v_id) {
            this.v_id = v_id;
        }

        public int getV_score() {
            return v_score;
        }

        public void setV_score(int v_score) {
            this.v_score = v_score;
        }

        public String getV_pic() {
            return v_pic;
        }

        public void setV_pic(String v_pic) {
            this.v_pic = v_pic;
        }

        public String getV_spic() {
            return v_spic;
        }

        public void setV_spic(String v_spic) {
            this.v_spic = v_spic;
        }

        public String getV_name() {
            return v_name;
        }

        public void setV_name(String v_name) {
            this.v_name = v_name;
        }

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }
    }
}
