package algorithm;

import org.apache.log4j.lf5.util.Resource;
import org.mockito.invocation.Invocation;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * Created by DT167 on 2018/7/16.
 */
public enum SingletonClass
{
    INSTANCE;

    public void test()
    {
        System.out.println("The Test!");
    }
}


class Client2 {

    public static void main(String[] args) throws Exception {
        SingletonClass singletonClass = SingletonClass.INSTANCE;
        Class<SingletonClass> clazz = SingletonClass.class;
        Constructor<SingletonClass> c = clazz.getDeclaredConstructor();
        c.setAccessible(true); // 跳过权限检查
        SingletonClass sc3 = c.newInstance();

    }


}