package movie.bw.com.movie.bean;

import java.util.List;

/**
 * 作者：夏洪武
 * 时间：2019/1/26.
 * 邮箱：
 * 说明：
 */
public class Pay_Chose_Film_Bean {


    /**
     * result : [{"address":"东城区滨河路乙1号雍和航星园74-76号楼","commentTotal":81,"distance":0,"followCinema":2,"id":1,"logo":"http://mobile.bwstudent.com/images/movie/logo/qcgx.jpg","name":"青春光线电影院"},{"address":"西城区前门大街大栅栏街36号","commentTotal":7,"distance":0,"followCinema":2,"id":2,"logo":"http://mobile.bwstudent.com/images/movie/logo/dgl.jpg","name":"大观楼电影院"},{"address":"北京市西城区天桥南大街3号楼一层、二层（天桥艺术大厦南侧）","commentTotal":26,"distance":0,"followCinema":2,"id":3,"logo":"http://mobile.bwstudent.com/images/movie/logo/sd.jpg","name":"首都电影院"},{"address":"北京市海淀区远大路1号B座5层魔影国际影城","commentTotal":13,"distance":0,"followCinema":2,"id":4,"logo":"http://mobile.bwstudent.com/images/movie/logo/mygj.jpg","name":"魔影国际影城"},{"address":"朝阳区湖景东路11号新奥购物中心B1(东A口)","commentTotal":11,"distance":0,"followCinema":2,"id":5,"logo":"http://mobile.bwstudent.com/images/movie/logo/CGVxx.jpg","name":"CGV星星影城"}]
     * message : 查询成功
     * status : 0000
     */

    /**
         * address : 东城区滨河路乙1号雍和航星园74-76号楼
         * commentTotal : 81
         * distance : 0
         * followCinema : 2
         * id : 1
         * logo : http://mobile.bwstudent.com/images/movie/logo/qcgx.jpg
         * name : 青春光线电影院
         */

        private String address;
        private int commentTotal;
        private int distance;
        private int followCinema;
        private int id;
        private String logo;
        private String name;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCommentTotal() {
            return commentTotal;
        }

        public void setCommentTotal(int commentTotal) {
            this.commentTotal = commentTotal;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getFollowCinema() {
            return followCinema;
        }

        public void setFollowCinema(int followCinema) {
            this.followCinema = followCinema;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

}
