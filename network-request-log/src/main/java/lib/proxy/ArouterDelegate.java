package lib.proxy;

import android.content.Context;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/7/26
 * description   : 路由配置
 */

public class ArouterDelegate {

  public HashMap readProperties(final Context context) {
    HashMap<String, String> propertiesmap = new HashMap<String, String>();
    Properties properties = new Properties();
    try {
      //assets目录下的config.properties配置文件
      InputStream inputStream =
          context.getClassLoader().getResourceAsStream("assets/router_configure.properties");
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      properties.load(bufferedReader);
      Iterator iterator = properties.entrySet().iterator();
      while (iterator.hasNext()) {
        Map.Entry entry = (Map.Entry) iterator.next();
        Object key = entry.getKey();
        Object value = entry.getValue();
        //把value值转换为utf-8编码的字符串，避免乱码
        value = new String(value.toString().getBytes("UTF-8"));
        propertiesmap.put(key.toString().trim(), value.toString().trim());
      }

      inputStream.close();
      return propertiesmap;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
