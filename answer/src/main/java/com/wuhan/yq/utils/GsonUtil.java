package com.wuhan.yq.utils;


import java.sql.Timestamp;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 * 
 * json解析工具类 <功能详细描述>
 * 
 * @author zhangxiaoyou
 * @version [版本号, 2012-4-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GsonUtil
{
    private static SerializeConfig mapping = new SerializeConfig();
    
    // 日期格式化
    static
    {
        mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        mapping.put(Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    }
    
    /**
     * 把json串转为指定的对象
     * 
     * @param <T>
     */
    public static <T> T fromJson(String str, Class<T> clazz)
    {
    	str=str.replaceAll("\ufeff", "");
        return (T)JSON.parseObject(str, clazz);//服务端返回数据前有bom报头，java读的时候把报头直接当作文件内容读，然后就会出错
    }
    
    /**
     * 把对象转为json串
     */
    public static String toJson(Object obj)
    {
        return JSON.toJSONString(obj, mapping);
    }
    
    /**
     * 把对象转为json串[禁止循环引用检测，避免json串中出现$ref]
     *
     * @param obj
     * @return[参数、异常说明]
     * @return String [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static String toJsonWithoutCRD(Object obj)
    {
        return JSON.toJSONString(obj, mapping, SerializerFeature.DisableCircularReferenceDetect);
    }
}
