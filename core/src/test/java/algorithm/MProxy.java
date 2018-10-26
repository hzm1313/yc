package algorithm;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by DT167 on 2018/7/16.
 */
public class MProxy implements InvocationHandler {
    private Object obj = null;

    public MProxy(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(this.obj,args);
        if (method.getName().equalsIgnoreCase("request")) {
                System.out.println("request");
        }
        return result;
    }
}
