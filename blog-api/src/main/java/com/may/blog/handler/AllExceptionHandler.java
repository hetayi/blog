package com.may.blog.handler;


import com.may.blog.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//对加了@Controller注解的方法进行拦截处理 AOP的实现
@RestControllerAdvice
public class AllExceptionHandler {
    //进行异常处理
    @ExceptionHandler(Exception.class)
    public Result doException(Exception e){
        e.printStackTrace();
        return Result.fail(-999,"系统吔屎啦");
    }

}
