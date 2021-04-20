package com.example.simpletv.NetworkRequestInterface.JavaBean;

import java.util.List;

public class MovieURL {

    /**
     * code : 0
     * data : {"m3u8List":[{"name":"BD1280⾼高清中英双字","url":"https://yong.yongjiu6.com/20171218/pTsOuU48/index.m3u8"},{"name":"第01集","url":"http://youku.com-youku.com/20171217/43WO0nz2/index.m3u8"}],"otherList":[{"name":"BD1280⾼高清中英双字","url":"https://yong.yongjiu6.com/share/8LSgNlZuNXjHiswv"},{"name":"第01集","url":"http://youku.com-youku.com/share/jhADiSHsNXuQJ3x7"}]}
     * msg : 执⾏行行成功
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private List<M3u8ListBean> m3u8List;
        private List<OtherListBean> otherList;

        public List<M3u8ListBean> getM3u8List() {
            return m3u8List;
        }

        public void setM3u8List(List<M3u8ListBean> m3u8List) {
            this.m3u8List = m3u8List;
        }

        public List<OtherListBean> getOtherList() {
            return otherList;
        }

        public void setOtherList(List<OtherListBean> otherList) {
            this.otherList = otherList;
        }

        public static class M3u8ListBean {
            /**
             * name : BD1280⾼高清中英双字
             * url : https://yong.yongjiu6.com/20171218/pTsOuU48/index.m3u8
             */

            private String name;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class OtherListBean {
            /**
             * name : BD1280⾼高清中英双字
             * url : https://yong.yongjiu6.com/share/8LSgNlZuNXjHiswv
             */

            private String name;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
