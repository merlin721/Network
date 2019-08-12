package lib.data;

import java.util.ArrayList;
import java.util.List;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/7/24
 * description   : 路由bean
 */

public class ArouterBean {
    /*路由路径*/
    public String arouterPath;
    /*路由路径*/
    public String arouterPageName;
    /*路由参数*/
    public List<ArouterValue> arouterParams = new ArrayList<>();
}
