package com.dorun.core.dc.wechat.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
public @interface SerializedField {

    /**
     * 	需要加密的数据
     * @return
     */
    boolean encode() default true;


    /**
     * 需要解密
     * @return
     */
    boolean decrypt() default true;

}
