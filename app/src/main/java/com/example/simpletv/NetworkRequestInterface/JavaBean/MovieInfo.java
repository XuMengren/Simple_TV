package com.example.simpletv.NetworkRequestInterface.JavaBean;

import java.util.List;

public class MovieInfo {

    /**
     * code : 0
     * data : [{"v_publisharea":"美国","v_pic":"https://pic.szjal.cn/img/a50bcbd76a4f3abfead62034967cedaa.jpg","v_spic":"","body":"1898年年，银矿⼯工丹丹尼尔普莱恩惟尤(丹丹尼尔戴刘易易斯 Daniel Day Lewis 饰)因井 下作业事故摔断了了腿，但他因祸得福了了解到秘密的⽯石油信息。他利利⽤用失聪孩童HW(狄⻰龙弗雷雷泽尔 Dillon Freasier 饰)欺骗乡⺠民赢得了了⽯石油地盘，从此⻜飞⻩黄腾达。但是，成为 ⽯石油⼤大亨的他并不不快乐， HW对他 的怨恨与⽇日俱增，他唯⼀一认亲的兄弟居然也是冒牌货。传教⼠士伊莱桑迪不不过是个借宗教蛊惑⼈人⼼心的⼩小⼈人。在 ⼀一次采矿事故中，丹丹尼尔的⼯工⼈人不不幸丧⽣生。正当他希望伊莱(保罗达诺 Paul Dano 饰)施以援⼿手时，对 ⽅方羞辱了了他，两⼈人从此开始明争暗⽃斗...... 本⽚片根据⼩小说《⽯石油》改编，导演保罗托⻢马斯安德森凭借此⽚片摘得 第58届柏林林电影节最佳导演银熊奖。丹丹尼尔戴刘易易斯摘得第80届奥斯卡⾦金金像奖影帝桂冠。","v_actor":"丹丹尼尔·戴-刘易易斯,保罗·达诺,凯⽂文·J·奥康纳,巴⾥里里·德尔·舍曼,狄⻰龙·弗雷雷泽 尔","tid":12,"v_note":"HD","v_id":5432,"v_score":9,"v_publishyear":2007,"v_director":"保罗·托⻢马斯·安德森","v_name":"⾎血⾊色将⾄至","v_lang":"英语"}]
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
         * v_publisharea : 美国
         * v_pic : https://pic.szjal.cn/img/a50bcbd76a4f3abfead62034967cedaa.jpg
         * v_spic :
         * body : 1898年年，银矿⼯工丹丹尼尔普莱恩惟尤(丹丹尼尔戴刘易易斯 Daniel Day Lewis 饰)因井 下作业事故摔断了了腿，但他因祸得福了了解到秘密的⽯石油信息。他利利⽤用失聪孩童HW(狄⻰龙弗雷雷泽尔 Dillon Freasier 饰)欺骗乡⺠民赢得了了⽯石油地盘，从此⻜飞⻩黄腾达。但是，成为 ⽯石油⼤大亨的他并不不快乐， HW对他 的怨恨与⽇日俱增，他唯⼀一认亲的兄弟居然也是冒牌货。传教⼠士伊莱桑迪不不过是个借宗教蛊惑⼈人⼼心的⼩小⼈人。在 ⼀一次采矿事故中，丹丹尼尔的⼯工⼈人不不幸丧⽣生。正当他希望伊莱(保罗达诺 Paul Dano 饰)施以援⼿手时，对 ⽅方羞辱了了他，两⼈人从此开始明争暗⽃斗...... 本⽚片根据⼩小说《⽯石油》改编，导演保罗托⻢马斯安德森凭借此⽚片摘得 第58届柏林林电影节最佳导演银熊奖。丹丹尼尔戴刘易易斯摘得第80届奥斯卡⾦金金像奖影帝桂冠。
         * v_actor : 丹丹尼尔·戴-刘易易斯,保罗·达诺,凯⽂文·J·奥康纳,巴⾥里里·德尔·舍曼,狄⻰龙·弗雷雷泽 尔
         * tid : 12
         * v_note : HD
         * v_id : 5432
         * v_score : 9
         * v_publishyear : 2007
         * v_director : 保罗·托⻢马斯·安德森
         * v_name : ⾎血⾊色将⾄至
         * v_lang : 英语
         */

        private String v_publisharea;
        private String v_pic;
        private String v_spic;
        private String body;
        private String v_actor;
        private int tid;
        private String v_note;
        private int v_id;
        private int v_score;
        private int v_publishyear;
        private String v_director;
        private String v_name;
        private String v_lang;

        public String getV_publisharea() {
            return v_publisharea;
        }

        public void setV_publisharea(String v_publisharea) {
            this.v_publisharea = v_publisharea;
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

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getV_actor() {
            return v_actor;
        }

        public void setV_actor(String v_actor) {
            this.v_actor = v_actor;
        }

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

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

        public int getV_publishyear() {
            return v_publishyear;
        }

        public void setV_publishyear(int v_publishyear) {
            this.v_publishyear = v_publishyear;
        }

        public String getV_director() {
            return v_director;
        }

        public void setV_director(String v_director) {
            this.v_director = v_director;
        }

        public String getV_name() {
            return v_name;
        }

        public void setV_name(String v_name) {
            this.v_name = v_name;
        }

        public String getV_lang() {
            return v_lang;
        }

        public void setV_lang(String v_lang) {
            this.v_lang = v_lang;
        }
    }
}
