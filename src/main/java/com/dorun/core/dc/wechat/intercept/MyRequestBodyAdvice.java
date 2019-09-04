package com.dorun.core.dc.wechat.intercept;

import com.dorun.core.dc.utils.RSAUtils;
import com.dorun.core.dc.wechat.annotation.SerializedField;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@ControllerAdvice(basePackages = "com.dorun.core.dc.wechat.controller")
public class MyRequestBodyAdvice implements RequestBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        SerializedField serializedField = methodParameter.getMethodAnnotation(SerializedField.class);
        return serializedField != null && serializedField.decrypt();
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        return  new HttpInputMessage(){

            @Override
            public HttpHeaders getHeaders() {
                return inputMessage.getHeaders();
            }

            @Override
            public InputStream getBody() throws IOException {
                try {
                    String cipHex = IOUtils.toString(inputMessage.getBody(), StandardCharsets.UTF_8);
                    String plainText = RSAUtils.publicDecrypt(cipHex);
                    return new ByteArrayInputStream(plainText.getBytes(StandardCharsets.UTF_8));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return inputMessage.getBody();
            }
        };
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }
}
