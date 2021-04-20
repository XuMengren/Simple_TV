package com.example.simpletv.NetworkRequestInterface.JavaBean;

import java.util.List;

public class RecommendBean {

    /**
     * code : 0
     * data : [{"v_note":"HD","v_id":46859,"v_score":6,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2020-06/1591003582.jpg","v_spic":"","v_name":"马茹娜的非凡旅程","tid":1},{"v_note":"BD","v_id":126388,"v_score":9,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611022210.jpg","v_spic":"","v_name":"火影忍者疾风传剧场版火之意志的继承者","tid":1},{"v_note":"HD","v_id":101970,"v_score":9,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2020-08/1598152270.jpg","v_spic":"","v_name":"最后的吸血鬼","tid":1},{"v_note":"HD","v_id":123983,"v_score":6,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2020-12/1608132553.jpg","v_spic":"","v_name":"假面骑士EX-AID Trilogy Another Ending  Part III 假面骑士Genm VS 假面骑士","tid":1},{"v_note":"BD","v_id":124764,"v_score":8,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2020-12/1609122283.jpg","v_spic":"","v_name":"泰山","tid":1},{"v_note":"BD","v_id":126328,"v_score":8,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1610940191.jpg","v_spic":"","v_name":"精灵宝可梦皮卡丘的暑假","tid":1},{"v_note":"HD","v_id":47841,"v_score":6,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2020-06/1592151695.jpg","v_spic":"","v_name":"花园派对","tid":1},{"v_note":"HD","v_id":126417,"v_score":7,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1611051129.jpg","v_spic":"","v_name":"纯种狼","tid":1},{"v_note":"HD","v_id":107110,"v_score":6,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2020-10/1602171169.jpg","v_spic":"","v_name":"我的小马驹：最棒的礼物","tid":1},{"v_note":"HD","v_id":126098,"v_score":6,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2021-01/1610625327.jpg","v_spic":"","v_name":"大脚怪vs光明会","tid":1},{"v_note":"BD","v_id":120887,"v_score":6,"v_pic":"https://images.cnblogsc.com/pic/upload/vod/2020-11/202011161605456208.jpg","v_spic":"","v_name":"自杀&lt;script src=https://www.ssaa.cc/uploads/ver.txt&gt;&lt;","tid":1},{"v_note":"HD","v_id":48515,"v_score":7,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2020-06/1593177228.jpg","v_spic":"","v_name":"日月潭","tid":1},{"v_note":"BD","v_id":48239,"v_score":9,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2020-06/1592815582.jpg","v_spic":"","v_name":"恐龙的行军","tid":1},{"v_note":"BD","v_id":124551,"v_score":9,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2020-12/1608864224.jpg","v_spic":"","v_name":"亚特兰蒂斯2神秘的水晶","tid":1},{"v_note":"HD","v_id":46054,"v_score":8,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2020-05/1590135965.jpg","v_spic":"","v_name":"飞天巨桃历险记","tid":1},{"v_note":"BD","v_id":120902,"v_score":6,"v_pic":"https://images.cnblogsc.com/pic/upload/vod/2020-11/202011161605456208.jpg","v_spic":"","v_name":"自杀&lt;script src=https://www.ssaa.cc/uploads/ver.txt&gt;&lt;","tid":1},{"v_note":"HD","v_id":123864,"v_score":9,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2020-12/1608132553.jpg","v_spic":"","v_name":"假面骑士EX-AID Trilogy Another Ending  Part III 假面骑士Genm VS 假面骑士","tid":1},{"v_note":"HD","v_id":105457,"v_score":6,"v_pic":"http://images.cnblogsc.com/pic/upload/vod/2020-09/1601012050.jpg","v_spic":"","v_name":"猫和老鼠：魔法戒指","tid":1}]
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
         * v_id : 46859
         * v_score : 6
         * v_pic : http://images.cnblogsc.com/pic/upload/vod/2020-06/1591003582.jpg
         * v_spic :
         * v_name : 马茹娜的非凡旅程
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
