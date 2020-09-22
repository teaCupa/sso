package org.yh.ssoclient.annotation;

import java.lang.annotation.*;

/**
 * @Author: yh
 * @Date: 2020/9/3
 * @Description:
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Documented
public @interface CurrentUser {
    //当前用户在request中的名字
    String value() default "user";
}
