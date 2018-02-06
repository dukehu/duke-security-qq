package com.duke.web.controller.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("/order")
public class AsyncController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncController.class);

    /**
     * 标准的同步处理
     *
     * @return String
     */
    @RequestMapping(method = RequestMethod.GET)
    public String order() throws InterruptedException {
        LOGGER.info("主线程开始");
        // 这里相当于执行要处理的业务逻辑
        Thread.sleep(1000);
        LOGGER.info("主线程返回");
        return "success";
    }

    /**
     * 异步处理请求，在副线程里面处理下单的业务逻辑，主线程就可以处理其他的业务逻辑
     *
     * @return Callable<String>
     */
    @RequestMapping(value = "/async", method = RequestMethod.GET)
    public Callable<String> asyncOrder() {
        LOGGER.info("主线程开始");

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws InterruptedException {
                LOGGER.info("副线程开始");
                Thread.sleep(1000);
                LOGGER.info("副线程结束");
                return "success";
            }
        };
        LOGGER.info("主线程结束");

        return callable;
    }
}
