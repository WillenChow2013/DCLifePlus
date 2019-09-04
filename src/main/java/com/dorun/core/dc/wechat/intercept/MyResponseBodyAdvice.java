package com.dorun.core.dc.wechat.intercept;

import com.dorun.core.dc.utils.RSAUtils;
import com.dorun.core.dc.wechat.annotation.SerializedField;
import net.sf.json.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice(basePackages = "com.dorun.core.dc.wechat.controller")
public class MyResponseBodyAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        SerializedField serializedField = methodParameter.getMethodAnnotation(SerializedField.class);

        return serializedField != null && serializedField.encode();
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        String reBody = RSAUtils.publicEncrypt(JSONObject.fromObject(body).toString());
        return reBody;
    }
}
