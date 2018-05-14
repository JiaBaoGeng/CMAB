package cn.jbg.cmab.common.util;

import java.util.Map;

/**
 * Created by jbg on 2018/3/24.
 * 提取MAP值工具类，安全提取
 */
public class MapParamUtil {

    public static String getStringValue(Map paramMap,String key){
        Object value = paramMap.get(key);
        if(value==null)
            return "";

        if(value instanceof String)
            return (String)value;
        else
            return value.toString();

    }

    public static Integer getIntValue(Map paramMap,String key){
        Object value = paramMap.get(key);
        if(value==null)
            return 0;

        if(value instanceof Integer)
            return (Integer)value;
        else if(value instanceof String)
            return Integer.valueOf((String)value);

        return 0;
    }


    public static Long getLongValue(Map paramMap,String key){
        Object value = paramMap.get(key);
        if(value==null)
            return 0l;

        if(value instanceof Long )
            return (Long)value;
        else if(value instanceof Integer )
            return Long.valueOf((Integer)value);
        else if(value instanceof String)
            return Long.valueOf((String)value);

        return 0l;
    }

    public static Double getDoubleValue(Map paramMap,String key){
        Object value = paramMap.get(key);
        if(value==null)
            return 0.0;

        if(value instanceof Double)
            return (Double)value;
        else if(value instanceof String)
            return Double.valueOf((String)value);

        return 0.0;
    }
}
