package movie.bw.com.movie.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：夏洪武
 * 时间：2019/1/22.
 * 邮箱：
 * 说明：用户登录SessiondId,Bean
 */
@Entity
public class UserBean {
        private String sessionId;
        private int userId;
        private int register;

        @Generated(hash = 1078665005)
        public UserBean(String sessionId, int userId, int register) {
            this.sessionId = sessionId;
            this.userId = userId;
            this.register = register;
        }

        @Generated(hash = 1203313951)
        public UserBean() {
        }

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }



}
