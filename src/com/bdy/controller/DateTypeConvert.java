package com.bdy.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

public class DateTypeConvert extends StrutsTypeConverter {
	SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
	@Override
	public Object convertFromString(Map context, String[] values, Class arg2) {
		for(String value:values){
			if(value!=null&&value.trim().length()!=0){
				try {
				return	sdf.parse(value);
				} catch (ParseException e) {
					throw new TypeConversionException(e);
				}
			}
		}
		return null;
	}

	@Override
	public String convertToString(Map context, Object value) {
		if(value!=null&&value instanceof java.util.Date){
			return sdf.format(value);
		}else{
			return null;
		}
		
	}

}
