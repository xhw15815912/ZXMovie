package movie.bw.com.movie.bean;

import java.util.List;

/**
 * 作者：夏洪武
 * 时间：2019/1/25.
 * 邮箱：
 * 说明：
 */
public class MoviewCommentBean {

    /**
     * result : [{"commentId":3,"commentTime":1533117674000,"greatNum":0,"replyNum":0,"commentHeadPic":"http://172.17.8.100/images/head_pic/bwjy.jpg","hotComment":0,"isGreat":1,"movieComment":"恐龙界的霸主！","commentUserId":6,"commentUserName":"谁的益达"},{"commentId":1,"commentTime":1532681329000,"greatNum":0,"replyNum":0,"commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2018-07-21/20180721120945.jpg","hotComment":0,"isGreat":0,"movieComment":"电影很好看","commentUserId":5,"commentUserName":"你的益达"}]
     * message : 请求成功
     * status : 0000
     */


        /**
         * commentId : 3
         * commentTime : 1533117674000
         * greatNum : 0
         * replyNum : 0
         * commentHeadPic : http://172.17.8.100/images/head_pic/bwjy.jpg
         * hotComment : 0
         * isGreat : 1
         * movieComment : 恐龙界的霸主！
         * commentUserId : 6
         * commentUserName : 谁的益达
         */
        private String commentContent;
        private int commentId;
        private long commentTime;
        private int greatNum;
        private int replyNum;
        private String commentHeadPic;
        private int hotComment;
        private int isGreat;
        private String movieComment;
        private int commentUserId;
        private String commentUserName;

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
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

        public int getGreatNum() {
            return greatNum;
        }

        public void setGreatNum(int greatNum) {
            this.greatNum = greatNum;
        }

        public int getReplyNum() {
            return replyNum;
        }

        public void setReplyNum(int replyNum) {
            this.replyNum = replyNum;
        }

        public String getCommentHeadPic() {
            return commentHeadPic;
        }

        public void setCommentHeadPic(String commentHeadPic) {
            this.commentHeadPic = commentHeadPic;
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

        public String getMovieComment() {
            return movieComment;
        }

        public void setMovieComment(String movieComment) {
            this.movieComment = movieComment;
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

}
