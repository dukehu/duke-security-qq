package com.duke.aspect;

import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.lang.reflect.Field;
import java.util.Arrays;

@Aspect  // 声明这是一个切片
@Component // 加入到spring容器中
public class TimeAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeAspect.class);

    private static final String[] TYPES = {"java.lang.Integer", "java.lang.Double",
            "java.lang.Float", "java.lang.Long", "java.lang.Short",
            "java.lang.Byte", "java.lang.Boolean", "java.lang.Char",
            "java.lang.String", "int", "double", "long", "short", "byte",
            "boolean", "char", "float"};

    // @Before()  // 相当于拦截器中的preHandle


    // @After()  // 方法成功返回是调用

    // @AfterThrowing  // 方法抛出异常是调用

    @Around("execution(* com.duke.web.controller.*.*(..))")  // 覆盖了@Before、@After、@@AfterThrowing标注的方法
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        // ProceedingJoinPoint 拦截住的方法的一些信息
        LOGGER.info("time aspect start");

        // 类
        String classType = pjp.getTarget().getClass().getName();

        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        String clazzSimpleName = clazz.getSimpleName();
        // LOGGER.info("classType--->" + classType);
        // LOGGER.info("className--->" + clazzName);
        // LOGGER.info("classSimpleName--->" + clazzSimpleName);

        String methodName = pjp.getSignature().getName();
        // LOGGER.info("methodName--->" + methodName);

        LOGGER.info(classType + "." + methodName);

        String[] paramNames = getFiledName(clazz, clazzName, methodName);
//        for (String paramName : paramNames) {
//            LOGGER.info(paramName);
//        }
        String logContent = writeLogInfo(paramNames, pjp);
        LOGGER.info(logContent);
        Object object = pjp.proceed();
        LOGGER.info("time aspect end");
        return object;
    }

    private static String writeLogInfo(String[] paramNames, ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        StringBuilder sb = new StringBuilder();
        boolean clazzFlag = true;
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            sb.append(paramNames[i]).append(" ");
            // 获取对象类型
            String typeName = arg.getClass().getTypeName();

            // 处理简单对象
            for (String type : TYPES) {
                if (type.equals(typeName)) {
                    sb.append("=").append(arg).append("; ");
                    break;
                }
            }
            if (clazzFlag) {
                sb.append(getFieldsValue(arg));
            }
        }
        return sb.toString();
    }

    /**
     * 得到参数的值
     *
     * @param obj 参数
     */
    private static String getFieldsValue(Object obj) {
        String typeName = obj.getClass().getTypeName();

        if (Arrays.asList(TYPES).contains(typeName)) {
            return "";
        }

        if (obj instanceof BindingResult) {
            BindingResult errors = (BindingResult) obj;
            if (errors.hasErrors()) {
                errors.getAllErrors().forEach(error -> {
                    FieldError fieldError = (FieldError) error;
                    String message =
                            fieldError.getObjectName() + "."
                                    + fieldError.getField() + "："
                                    + fieldError.getRejectedValue() + "--->"
                                    + fieldError.getDefaultMessage();
                    LOGGER.error(message);
                });
            }
        }

        Field[] fields = obj.getClass().getDeclaredFields();
        // 处理自定义对象
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("【");
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                for (String type : TYPES) {
                    if (field.getType().getName().equals(type)) {
                        stringBuilder.append(field.getName()).append(" = ").append(field.get(obj)).append("; ");
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        stringBuilder.append("】");
        return stringBuilder.toString();
    }

    /**
     * 得到方法的参数名称
     *
     * @param clazz      那个类
     * @param clazzName  类名称
     * @param methodName 方法名称
     * @return 参数名称数组
     */
    private static String[] getFiledName(Class clazz, String clazzName, String methodName) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();

        ClassClassPath classPath = new ClassClassPath(clazz);
        pool.insertClassPath(classPath);

        CtClass ctClass = pool.getCtClass(clazzName);
        CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);

        org.apache.ibatis.javassist.bytecode.MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();

        LocalVariableAttribute localVariableAttribute = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);

        if (ObjectUtils.isEmpty(localVariableAttribute)) {
            // 异常
        }

        String[] paramNames = new String[ctMethod.getParameterTypes().length];
        int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++) {
            paramNames[i] = localVariableAttribute.variableName(i + pos); //paramNames即参数名
        }
        return paramNames;
    }
}
