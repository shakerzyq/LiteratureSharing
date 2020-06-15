package Utils;

/**
 * @author 周杨清
 * @time 2020/6/5
 */
public interface HttpCallbackListener {
    void onfinish(String response);//表示服务器成功响应我们请求的时候调用,参数代表服务器返回的数据
    void onError(Exception e);//表示当网络操作出现错误时调用,参数记录错误的详细信息
}
