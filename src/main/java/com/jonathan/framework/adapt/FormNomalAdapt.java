package com.jonathan.framework.adapt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jonathan.framework.entity.BaseModel;
import com.jonathan.framework.adapt.iface.ParamsAdapt;
import com.jonathan.framework.annotation.ParamName;
import com.jonathan.framework.constant.InsideTypeConstant;
import com.jonathan.framework.entity.BeanEntity;
import com.jonathan.framework.entity.MvcMapping;
import com.jonathan.framework.util.PropertUtil;
import com.jonathan.framework.util.RequestUtil;
import com.jonathan.framework.util.StringUtil;

/**
 * form装载到多个bean,以beanname为准
 * @author jonathan
 *
 */
public class FormNomalAdapt implements ParamsAdapt {

	@Override
	public Object[] doAdapt(MvcMapping mapping, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		if (StringUtil.isNullOrEmpty(mapping.getParamTypes())) {
			return null;
		}
		Object[] params = new Object[mapping.getParamTypes().size()];
		for (int i = 0; i < mapping.getParamTypes().size(); i++) {
			BeanEntity beanEntity = mapping.getParamTypes().get(i);
			if (beanEntity.getFieldType().isAssignableFrom(request.getClass())) {
				params[i] = request;
				continue;
			}
			if (beanEntity.getFieldType().isAssignableFrom(response.getClass())) {
				params[i] = response;
				continue;
			}
			if (beanEntity.getFieldType().isAssignableFrom(session.getClass())) {
				params[i] = session;
				continue;
			}
			if (BaseModel.class.isAssignableFrom(beanEntity.getFieldType())) {
				ParamName paramNameAnnotion=beanEntity.getFieldType().getAnnotation(ParamName.class);
				String paraName=beanEntity.getFieldName();
				if(paramNameAnnotion!=null){
					paraName=paramNameAnnotion.value();
				}
				params[i] = RequestUtil.getBeanAll(request, paraName, beanEntity.getFieldType());
				continue;
			}
			if (beanEntity.getFieldType().isPrimitive()||InsideTypeConstant.INSIDE_TYPES.contains(beanEntity.getFieldType())) {
				ParamName paramNameAnnotion=beanEntity.getFieldType().getAnnotation(ParamName.class);
				String paraName=beanEntity.getFieldName();
				if(paramNameAnnotion!=null){
					paraName=paramNameAnnotion.value();
				}
				String value=request.getParameter(paraName);
				params[i]=PropertUtil.parseValue(value, beanEntity.getFieldType());
				continue;
			}
		}
		return params;
	}

}
