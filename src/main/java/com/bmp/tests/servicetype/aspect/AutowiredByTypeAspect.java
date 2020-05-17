package com.bmp.tests.servicetype.aspect;

import com.bmp.tests.servicetype.annotation.AutowiredByType;
import com.bmp.tests.servicetype.annotation.ServiceByType;
import com.bmp.tests.servicetype.annotation.ServiceType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

import static com.bmp.tests.servicetype.annotation.ServiceType.DOG;

@Aspect
@Component
@Slf4j
public class AutowiredByTypeAspect {

    @Autowired
    private ApplicationContext applicationContext;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void beanAnnotatedWithRestController() {
    }

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {
    }

    @Pointcut("publicMethod() && beanAnnotatedWithRestController()")
    public void publicMethodInsideAClassMarkedWithRestController() {
    }

    @Before("publicMethodInsideAClassMarkedWithRestController()")
    public void before(JoinPoint joinPoint) throws Throwable {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ServiceByType.class);
        log.debug("The beans [{}]", beans);
        Object bean = beans.entrySet().stream()
                .filter(this::filterBeanImplementation)
                .map(e -> e.getValue())
                .findFirst()
                .orElseThrow(RuntimeException::new);
        log.debug("The bean to inject [{}]", bean);

        Object targetObject = joinPoint.getTarget();
        log.debug("The target object {}", targetObject);
        Arrays.stream(targetObject.getClass().getDeclaredFields())
                .filter(this::filterParametersAnnotated)
                .forEach(f -> this.injectBean(f, targetObject, bean));
    }

    private void injectBean(Field field, Object targetObject, Object bean) {
        try {
            field.setAccessible(true);
            field.set(targetObject, bean);
            log.debug("Set field {} of target object {} with value {}",
                    field, targetObject, bean);
        } catch (Exception e) {
            log.error("Error setting value", e);
        }
    }

    private boolean filterParametersAnnotated(Field field) {
        return field.getAnnotationsByType(AutowiredByType.class).length > 0;
    }

    private boolean filterBeanImplementation(Map.Entry<String, Object> mapEntry) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String type = request.getHeader("type");
        return Arrays.stream(ServiceType.values())
                .filter(e -> e.name().equals(type)).findFirst()
                .orElse(DOG)
                .equals(mapEntry.getValue().getClass().getAnnotation(ServiceByType.class).value());
    }
}
