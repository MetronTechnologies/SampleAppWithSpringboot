package com.fcmb.audit_lib.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auditable {
    String action(); // e.g., "USER_LOGIN", "CREATE_ORDER"
}

