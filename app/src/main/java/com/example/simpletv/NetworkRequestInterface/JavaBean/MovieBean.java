package com.example.simpletv.NetworkRequestInterface.JavaBean;

import java.util.List;

public class MovieBean {

    /**
     * code : 0
     * data : [{"v_note":"HD","v_id":126818,"v_score":9,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611622296.jpg","v_spic":"","v_name":"佐格","tid":1},{"v_note":"HD","v_id":126727,"v_score":7,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611470759.jpg","v_spic":"","v_name":"漫威崛起幽灵蜘蛛","tid":1},{"v_note":"HD","v_id":126708,"v_score":7,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611455547.jpg","v_spic":"","v_name":"DC超级英雄美少女星际游戏","tid":1},{"v_note":"HD","v_id":126637,"v_score":9,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611369444.jpg","v_spic":"","v_name":"时钟里的天使","tid":1},{"v_note":"HD","v_id":126597,"v_score":6,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611296092.jpg","v_spic":"","v_name":"虹色萤火虫永远的暑假","tid":1},{"v_note":"HD","v_id":126560,"v_score":9,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611253931.jpg","v_spic":"","v_name":"所罗门国王传奇","tid":1},{"v_note":"HD","v_id":126512,"v_score":7,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611207963.jpg","v_spic":"","v_name":"DC超级英雄美少女年度英雄","tid":1},{"v_note":"BD","v_id":126513,"v_score":8,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611208092.jpg","v_spic":"","v_name":"DC超级英雄美少女超级英雄中学","tid":1},{"v_note":"BD","v_id":126500,"v_score":8,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611198607.jpg","v_spic":"","v_name":"龙珠Z剧场版7极限之战三大超级赛亚人","tid":1},{"v_note":"BD","v_id":126501,"v_score":8,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611198750.jpg","v_spic":"","v_name":"龙珠Z剧场版5最强对最强","tid":1},{"v_note":"HD","v_id":126417,"v_score":7,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611051129.jpg","v_spic":"","v_name":"纯种狼","tid":1},{"v_note":"BD","v_id":126384,"v_score":8,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611021875.jpg","v_spic":"","v_name":"火影忍者疾风传剧场版羁绊","tid":1},{"v_note":"BD","v_id":126388,"v_score":9,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611022210.jpg","v_spic":"","v_name":"火影忍者疾风传剧场版火之意志的继承者","tid":1},{"v_note":"BD","v_id":126390,"v_score":9,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611022642.jpg","v_spic":"","v_name":"牧羊女大冒险","tid":1},{"v_note":"BD","v_id":126375,"v_score":8,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611020776.jpg","v_spic":"","v_name":"火影忍者剧场版大激突幻之地底遗迹","tid":1},{"v_note":"BD","v_id":126376,"v_score":9,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611021136.jpg","v_spic":"","v_name":"火影忍者剧场版大兴奋三日月岛的动物骚动","tid":1},{"v_note":"BD","v_id":126379,"v_score":8,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611021316.jpg","v_spic":"","v_name":"火影忍者疾风传剧场版鸣人之死","tid":1},{"v_note":"HD","v_id":126366,"v_score":9,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611020171.jpg","v_spic":"","v_name":"邻家小侦探","tid":1}]
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
         * v_note : HD
         * v_id : 126818
         * v_score : 9
         * v_pic : http://images.cnblogsc.com/pic/upload/vod/2021-01/1611622296.jpg
         * v_spic :
         * v_name : 佐格
         * tid : 1
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
