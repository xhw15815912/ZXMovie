package movie.bw.com.movie.bean;

import java.util.List;

/**
 * 作者：夏洪武
 * 时间：2019/1/27.
 * 邮箱：
 * 说明：
 */
public class FilmCommentBean {

    /**
     * result : [{"commentContent":"数据","commentHeadPic":"http://172.17.8.100/images/head_pic/bwjy.jpg","commentId":8,"commentTime":1533716460000,"commentUserId":6,"commentUserName":"谁的益达","greatNum":0,"hotComment":1,"isGreat":0},{"commentContent":"影院环境很好","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2018-07-21/20180721120945.jpg","commentId":1,"commentTime":1532534400000,"commentUserId":5,"commentUserName":"你的益达","greatNum":0,"hotComment":0,"isGreat":1}]
     * message : 请求成功
     * status : 0000
     */


        /**
         * commentContent : 数据
         * commentHeadPic : http://172.17.8.100/images/head_pic/bwjy.jpg
         * commentId : 8
         * commentTime : 1533716460000
         * commentUserId : 6
         * commentUserName : 谁的益达
         * greatNum : 0
         * hotComment : 1
         * isGreat : 0
         */

        private String commentContent;
        private String commentHeadPic;
        private int commentId;
        private long commentTime;
        private int commentUserId;
        private String commentUserName;
        private int greatNum;
        private int hotComment;
        private int isGreat;

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

        public String getCommentHeadPic() {
            return commentHeadPic;
        }

        public void setCommentHeadPic(String commentHeadPic) {
            this.commentHeadPic = commentHeadPic;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public long getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(long commentTime) {
            this.commentTime = commentTime;
        }

        public int getCommentUserId() {
            return commentUserId;
        }

        public void setCommentUserId(int commentUserId) {
            this.commentUserId = commentUserId;
        }

        public String getCommentUserName() {
            return commentUserName;
        }

        public void setCommentUserName(String commentUserName) {
            this.commentUserName = commentUserName;
        }

        public int getGreatNum() {
            return greatNum;
        }

        public void setGreatNum(int greatNum) {
            this.greatNum = greatNum;
        }

        public int getHotComment() {
            return hotComment;
        }

        public void setHotComment(int hotComment) {
            this.hotComment = hotComment;
        }

        public int getIsGreat() {
            return isGreat;
        }

        public void setIsGreat(int isGreat) {
            this.isGreat = isGreat;
        }

}
