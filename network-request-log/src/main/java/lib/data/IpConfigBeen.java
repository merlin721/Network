package lib.data;

import java.io.Serializable;
import java.util.HashMap;

/**
  *IpConfigBeen
  *@author ：daiwenbo
  *@Time   ：2018/6/22 下午4:16
  *@e-mail ：daiwwenb@163.com
  *@description ：ip实体类
  */
public class IpConfigBeen implements Serializable {
    /**环境名称*/
    private final String environmentName;
    /**每个环境对应一组url*/
    private final HashMap<String,String> urlMap;


    public IpConfigBeen(String environmentName, HashMap<String, String> urlMap) {
        this.environmentName = environmentName;
        this.urlMap = urlMap;
    }

    public String getKeyUrl(String key) {
        return urlMap.get(key);
    }
    public HashMap<String,String> getUrlMap(){
        return urlMap;
    }

    public String getEnvironmentName(){
        return environmentName;
    }
}
