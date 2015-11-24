package io.ganguo.chat.route.server.util;


import io.ganguo.chat.route.server.Bootstrap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;




/**
 * Created by James on 2015/11/13 0013.
 */
public class ApplicationContextUtil {

    private static ApplicationContext context = new AnnotationConfigApplicationContext(Bootstrap.class);

    public static Object getBean(String beanName){

        return context.getBean(beanName);

    }


}
