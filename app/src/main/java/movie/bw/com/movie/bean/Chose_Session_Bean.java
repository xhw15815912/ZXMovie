package movie.bw.com.movie.bean;

import java.util.List;

/**
 * 作者：夏洪武
 * 时间：2019/1/26.
 * 邮箱：
 * 说明：查询影院此影片的排场bean
 */
public class Chose_Session_Bean {

    /**
     * result : [{"beginTime":"18:30","duration":"117分钟","endTime":"19:55","id":24,"price":0.22,"screeningHall":"5号厅","seatsTotal":160,"seatsUseCount":50,"status":1}]
     * message : 查询成功
     * status : 0000
     */


        /**
         * beginTime : 18:30
         * duration : 117分钟
         * endTime : 19:55
         * id : 24
         * price : 0.22
         * screeningHall : 5号厅
         * seatsTotal : 160
         * seatsUseCount : 50
         * status : 1
         */

        private String beginTime;
        private String duration;
        private String endTime;
        private int id;
        private double price;
        private String screeningHall;
        private int seatsTotal;
        private int seatsUseCount;
        private int status;

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getScreeningHall() {
            return screeningHall;
        }

        public void setScreeningHall(String screeningHall) {
            this.screeningHall = screeningHall;
        }

        public int getSeatsTotal() {
            return seatsTotal;
        }

        public void setSeatsTotal(int seatsTotal) {
            this.seatsTotal = seatsTotal;
        }

        public int getSeatsUseCount() {
            return seatsUseCount;
        }

        public void setSeatsUseCount(int seatsUseCount) {
            this.seatsUseCount = seatsUseCount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

}
