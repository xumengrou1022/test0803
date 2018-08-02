package com.haomostudio.Util;


import java.util.List;


public class Util {

    public static String dealFieldsValuesDataBaseOR(List<String> fields, List<String> values, String column){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("( ");
        if (fields == null || values == null
                || (fields != null && fields.size() == 0)
                || (values != null && fields.size() == 0) ){
            return "1=1";
        }
        if (fields.size() > 1 && values.size() > 1){
            int i =0;
            for (String value:values){
                if (column.contains("like")){
                    stringBuffer.append(fields.get(i)+" like '%"+value+"%'  or ");
                }else {
                    stringBuffer.append(fields.get(i)+" = '"+value+"'  or ");
                }
                i++;
            }
        }else {
            boolean fieldsOrValue = fields.size() == 1;
            List<String> lists = fieldsOrValue?values:fields;
            lists.stream().forEach(list->{
                if (column.contains("like")){
                    stringBuffer.append((fieldsOrValue?fields.get(0):list)+" like '%"+(fieldsOrValue?list:values.get(0))+"%'   or ");
                }else {
                    stringBuffer.append((fieldsOrValue?fields.get(0):list)+" = '"+(fieldsOrValue?list:values.get(0))+"'   or ");
                }

            });

        }
        stringBuffer.delete(stringBuffer.length()-4,stringBuffer.length()-1);
        stringBuffer.append(") ");
        if (fields.size() == 1 && values.size() == 1){
            return stringBuffer.toString().replace(" or","");
        }
        return stringBuffer.toString();
    }


}