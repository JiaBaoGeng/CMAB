package cn.jbg.cmab.common.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by jbg on 2018/3/27.
 */

public class MybatisParamUtil {


    /**
     * map 属性 添加到criteria对象条件中
     * @param mapParam
     * @param criteria
     */
    public static void addMapPropertyToCriteria(Map mapParam, Object criteria){

        Set<Map.Entry> entry = mapParam.entrySet();
        for(Map.Entry element: entry){
            if(element.getValue()==null)
                continue;

            String propertyName = element.getKey().toString();
            Method equipMehtod= getCriteriaEquiptMethod(propertyName,criteria);
            addEquiptCondition(criteria,equipMehtod,element.getValue());
        }

    }

    /**
     * 对象数据线，添加到criteria对象条件中
     * @param objParam
     * @param criteria
     */
    public static void addObjectPropertyToCriteria(Object objParam,Object criteria)throws Exception{
        Map paramMap = objectToMap(objParam);
        addMapPropertyToCriteria(paramMap,criteria);

    }

    /**
     * 根据对象，返回相等的方法
     * @param propertyName
     * @param criteria
     * @return
     */
    private static Method getCriteriaEquiptMethod(String propertyName,Object criteria){
        //andBusiSystemEqualTo
        propertyName = propertyName.substring(0,1).toUpperCase()+propertyName.substring(1,propertyName.length());
        String methodName = "and"+propertyName+"EqualTo";
        Method[] methods = criteria.getClass().getMethods();
        for(Method method: methods){
            if(method.getName().equals(methodName)){
                return method;
            }
        }
        return null;

    }

    private static void addEquiptCondition(Object criteria,Method equipMehtod, Object value){
        if(equipMehtod==null)
            return;

        try {
            equipMehtod.invoke(criteria,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * MAP 类型转换对象
     * @param map
     * @param beanClass
     * @param <T>
     * @return
     */
    public static  <T> T mapToObject(Map<String, Object> map, Class<T> beanClass)   {
        T obj = null;
        if (map == null)
            return null;
        try {
            obj = beanClass.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            Map<String,Boolean> insertedValue = new HashMap<String, Boolean>(); //已经插入的部分，不需要调用get/set方法
            for (Field field : fields) {
                int mod = field.getModifiers();
                if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                    continue;
                }
                field.setAccessible(true);
                Class cl = field.getType();

                if(map.get(field.getName()) != null && map.get(field.getName()) != "" &&
                        StringUtils.isNotBlank(String.valueOf(map.get(field.getName())))){
                    if(cl.getName().equals("java.lang.Long") || cl.getName().equals("long")){
                        Object o =  map.get(field.getName());
                        if(o instanceof String){
                            if(!o.equals("null")) {
                                field.set(obj, Long.parseLong((String) o));
                            }
                        }else{
                            field.set(obj, Long.parseLong(o.toString()));
                        }

                    }else if(cl.getName().equals("java.lang.Integer") ||cl.getName().equals("int")){
                        Object o =  map.get(field.getName());
                        if(o instanceof String){
                            field.set(obj, Integer.parseInt((String)o));
                        }else {
                            field.set(obj, Integer.parseInt(o.toString()));
                        }

                    }else if(cl.getName().equals("java.util.Date") || cl.getName().equals("Date")){
                        Object o =  map.get(field.getName());

                        if(o instanceof Long){
                            field.set(obj, new Date(Long.valueOf(o.toString())));
                        }

                        if(o instanceof String){
                            field.set(obj, DateUtils.getFormatedDateTime(String.valueOf(o)));
                        }
                    }else if(cl.getName().equals("java.math.BigDecimal") || cl.getName().equals("BigDecimal")){
                        Object o = map.get(field.getName());
                        if(o instanceof  String){
                            field.set(obj, new BigDecimal(String.valueOf(o)));
                        }else if(o instanceof Long){
                            int j =0;
                        }else if(o instanceof Integer){
                            field.set(obj, new BigDecimal(String.valueOf(o)));
                        }
                    } else{
                        field.set(obj, map.get(field.getName()));
                    }
                    insertedValue.put(field.getName(),true);
                }


            }

            //调用set的方法，设置父类的属性.
            Method[] methods=obj.getClass().getMethods();
            for(Method method: methods){
                String methodName=method.getName();
                if(!methodName.startsWith("get"))
                    continue;
                String currentFieldName=methodName.substring(3,methodName.length());
                currentFieldName=currentFieldName.substring(0,1).toLowerCase()+currentFieldName.substring(1,currentFieldName.length());
                if(insertedValue.get(currentFieldName)!=null  ||map.get(currentFieldName)==null)
                    continue; //已经在上面进行处理,获取属性不存在
                //获取set方法
                Method setMethod= getSetMethodByName(methods,currentFieldName);
                if(setMethod==null)
                    continue;

                //获取字段类型，进行转换
                doSetMethodValue(setMethod,map.get(currentFieldName),obj);

            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return obj;
    }

    /**
     * 调用SET方法复制
     * @param setMethod
     * @param setValue
     * @param desObject
     * @throws Exception
     */
    private static void doSetMethodValue(Method setMethod, Object setValue, Object desObject) throws Exception {
        Class type= setMethod.getParameterTypes()[0];
        if(type.getName().equals("java.lang.Long") || type.getName().equals("long")){
            Long longVal = 0l;
            try{
                if(setValue instanceof String){
                    longVal =Long.parseLong((String)setValue);
                }else{
                    longVal =Long.parseLong(setValue.toString());
                }
            }catch (Exception e){

            }
            setMethod.invoke(desObject,longVal);

        }else if(type.getName().equals("java.lang.Integer") ||type.getName().equals("int")){
            Integer intVal = 0;
            try{
                if(setValue instanceof String){
                    intVal =Integer.parseInt((String)setValue);
                }else {
                    intVal = Integer.parseInt(setValue.toString());
                }

            }catch (Exception e){

            }
            setMethod.invoke(desObject,intVal);

        }else{
            setMethod.invoke(desObject,setValue);
        }


    }


    /**
     * 获取SET方法
     * @param methods
     * @param name
     * @return
     */
    private static Method getSetMethodByName(Method[] methods,String name){
        String methodName="set"+name.substring(0,1).toUpperCase()+name.substring(1,name.length());
        for(Method method: methods){

            if(method.getName().equals(methodName))
                return method;

        }
        return null;

    }

    public static String getStrValue(Map<String, Object> map, String key) {
        if (map == null || map.isEmpty() || StringUtils.isBlank(key)) {
            return "";
        }
        Object t = map.get(key);
        if (t != null) {
            return t.toString();
        } else {
            for (Object o : map.keySet()) {
                String name = (String) o;
                if (name.toLowerCase().equals(key.toLowerCase())) {
                    Object value =  map.get(o);
                    return value==null ? "":value.toString();
                }
            }
        }

        return "";
    }

    /**
     * 对象转MAP
     * @param input
     * @return
     */
    public static Map objectToMap(Object input)throws Exception{
        return objectToMap(input,false);

    }

    public static Map objectToMap(Object input,boolean keyUpper)throws Exception{
        Map result = new HashMap();
        Field[] fields= input.getClass().getDeclaredFields();
        for(Field field:fields){
            try {
                field.setAccessible(true);
                Object value= field.get(input);
                String key = keyUpper?field.getName().toUpperCase():field.getName();
                result.put(key,value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new Exception(e.getMessage());
            }
        }
        return result;

    }

    public static List objectListToMapList(List list)throws Exception{
        return objectListToMapList(list,true);
    }

    public static List objectListToMapList(List list,boolean keyUpper)throws Exception{
        List<Map> mapList = new ArrayList<Map>();
        for(Object o:list){
            mapList.add(objectToMap(o,keyUpper));
        }
        return mapList;
    }


    public static List mapListToObjectList(List<Map> mapList, Class<?> objectClass) {
        if (mapList ==  null || mapList.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        List objectList = new ArrayList();
        for (Map map: mapList) {
            objectList.add(MybatisParamUtil.mapToObject(map,objectClass));
        }
        return objectList;
    }




}

