package movie.bw.com.movie.core;


import movie.bw.com.movie.core.exception.ApiException;

/**
 * 作者：夏洪武
 * 时间：2019/1/18.
 * 邮箱：
 * 说明：
 */
public interface DataCall<T> {
    void success(T data);
    void fail(ApiException e);
}
