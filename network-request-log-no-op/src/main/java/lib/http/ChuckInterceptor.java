package lib.http;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/6/12
 * description   : xxxx描述
 */

public class ChuckInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            throw e;
        }
        return response;
    }
}
