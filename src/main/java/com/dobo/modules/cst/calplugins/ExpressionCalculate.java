package com.dobo.modules.cst.calplugins;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.dobo.common.service.ServiceException;

public class ExpressionCalculate {

	static ScriptEngineManager factory = new ScriptEngineManager();  
    static ScriptEngine engine = factory.getEngineByName("JavaScript");  
      
    public static String getExpressionValue(HashMap<String, String> map, String option){  
        if (option == null){
        	return null;
        }

        //System.out.println("------:"+option);
        if (map != null && map.size() != 0){
        	for (String para : map.keySet()){
        		option = option.replaceAll(para, map.get(para));
        	}
        }
        //System.out.println("------:"+option);
        return option;
    }
    
    public static BigDecimal getMathValue(HashMap<String, String> map, String option, int scale){  
        if (option == null){
        	return null;
        }
        
        double result = 0;
        String optionValue = "";
        
        /*if (map != null && map.size() != 0){
            for (String para : map.keySet()){
                option = option.replaceAll(para, map.get(para));
            }
    	}*/
        
        try{
        	optionValue = getExpressionValue(map, option);
            Object ob = engine.eval(optionValue); 
            result = Double.parseDouble(ob.toString()); 
            return BigDecimal.valueOf(result).setScale(scale, BigDecimal.ROUND_HALF_UP); 
        } catch (ScriptException e) {  
            System.out.println("无法识别表达式："+option+"="+optionValue+",异常："+e.getMessage());
            throw new ServiceException("无法识别表达式："+option+"="+optionValue+",异常："+e.getMessage());
        } catch (NumberFormatException ne) {  
            System.out.println("公式计算结果数字格式化错误："+option+"="+optionValue+"="+result+",异常："+ne.getMessage());  
            throw new ServiceException("公式计算结果数字格式化错误："+option+"="+optionValue+"="+result+",异常："+ne.getMessage());
        }
    }
    
    public static String getStringValue(HashMap<String, String> map, String option, int scale){
    	return getMathValue(map, option, scale) +"";
    }
    
    public static void main(String[] args) throws ScriptException {
		String str = "4.0/*0.2/8/250*12";
        Object ob = engine.eval(str); 
        System.out.println(Double.parseDouble(ob.toString())); 
	}
}
