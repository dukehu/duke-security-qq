package com.duke.utils.impl;

import com.duke.domain.ValidateCode;
import com.duke.exception.ValidateCodeException;
import com.duke.utils.ValidateCodeGenerator;
import com.duke.utils.ValidateCodeProcessor;
import com.duke.utils.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * Created duke on 2018/1/7
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    /**
     * 模板方法（Template Method）
     *
     * @param request 封装请求和响应
     */
    @Override
    public void create(ServletWebRequest request) throws Exception {

        // 基本方法是实现算法各个步骤的方法，是模板方法的组成部分。
        // 基本方法分为三种：抽象方法（Abstract Method）、具体方法（Concrete Method）、钩子方法（Hook Method）
        C validateCode = generate(request);

        // 具体方法（Concrete Method）
        // 具体方法有一个抽象类或者是具体类声明并实现，子类可以进行覆盖也可以直接继承
        // 在此例中就是直接集成的，应为两种验证模式都是要将验证码信息报错到浏览器session中
        // 具体方法也可在子类中进行集体的实现
        save(request, validateCode);

        // 抽象方法（Abstract Method）
        // 一个抽象方法，有抽象类声明，有具体的子类实现
        // 就像此例中，在抽象方法中声明了发送验证码的抽象方法，而在其子类中具体的去实现此方法，怎么发是子类去实现的
        send(request, validateCode);

        // 钩子方法（Hoop Method）
        // 此例中没有用到钩子方法
        /**
         * 一个钩子方法由一个抽象类或者是具体的子类声明并实现，其子类可能会加以扩展。
         * 通常在父类中给出的实现是一个空实现（可使用virtual关键字将其定义为虚函数），
         * 并以该空实现作为方法的默认实现， 当然钩子方法也可以非空的默认实现。
         */
        /**
         * 在模板方法中，钩子方法有两种，第一类钩子方法可以与一些具体的步骤“挂钩”，以实现
         * 在不同条件下执行模板方法中的不同步骤，这类钩子方法返回值通常是Boolean类型，这类方法
         * 一般取名为isXXX();用与对某个条件进行判断，如果条件满足则执行某一步骤，否则将不执行，
         * 这类钩子函数可以控制方法的执行，对一个算法进行约束。
         * 示例代码如下：
         */

        /*// 抽象类
        public abstract class AbstractClass {
            // 模板方法
            public void templateMethod() {
                open();
                display();
                // 通过钩子方法来判断是否要执行某一步骤
                if(isPrint()) {
                    print();
                }
            }

            // 钩子方法，子类可覆盖
            public Boolean isPrint() {
                return true;
            }
        }*/

        /**
         * 还有一类钩子方法就是实现体为空的具体方法，子类可根据需求继承或者覆盖这些钩子方法，
         * 与抽象方法相比，这类钩子函数的函数在于，子类如果没有覆盖弗雷中定义的钩子方法，
         * 编译可以正常通过，但是如果没有覆盖父类中声明的抽象方法。编译报错。
         */
        /*abstract class AbstractClass {
            //模板方法
            public void TemplateMethod() {
                PrimitiveOperation1();
                PrimitiveOperation2();
                PrimitiveOperation3();
            }
            //基本方法—具体方法
            public void PrimitiveOperation1()   {      //实现代码}
            // 基本方法—抽象方法
            public abstract void PrimitiveOperation2();
            //基本方法—钩子方法
            public void PrimitiveOperation3(){}
        }*/
    }

    /**
     * 生成校验码
     *
     * @param request 封装响应和请求的一个对象
     * @return C
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        String type = getValidateCodeType(request).toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request 封装响应和请求的一个对象
     * @return ValidateCodeType
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    /**
     * 保存校验码
     *
     * @param request      封装响应和请求的一个对象
     * @param validateCode 验证码对象
     */
    private void save(ServletWebRequest request, C validateCode) {
        sessionStrategy.setAttribute(request, getSessionKey(request), validateCode);
    }

    /**
     * 构建验证码放入session时的key
     *
     * @param request 封装响应和请求的一个对象
     * @return String
     */
    private String getSessionKey(ServletWebRequest request) {
        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
    }

    /**
     * 发送校验码，由子类实现
     *
     * @param request      封装响应和请求的一个对象
     * @param validateCode 验证码对象
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType processorType = getValidateCodeType(request);
        String sessionKey = getSessionKey(request);

        C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(processorType + "验证码不存在");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidateCodeException(processorType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, sessionKey);
    }

}
