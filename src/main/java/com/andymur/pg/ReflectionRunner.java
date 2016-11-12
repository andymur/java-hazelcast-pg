package com.andymur.pg;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by muraand.
 */
public class ReflectionRunner {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Holder defaultHolder = new Holder();
        Holder olderHolder = new Holder();

        olderHolder.setAge(10);

        String paramMethod = "sayYourAge";

        Class<?> clz = defaultHolder.getClass();
        Method m = clz.getDeclaredMethod(paramMethod, Integer.class);

        m.invoke(olderHolder, 20);
        m.invoke(defaultHolder, 10);
    }

    static class Holder {
        private int age = 5;

        public void setAge(int age) {
            this.age = age;
        }

        public Holder() {

        }

        public void sayYourAge(Integer age) {
            System.out.println("Hello, my age is ".concat(Integer.toString(age)));
        }

        public void sayHi() {
            System.out.println("Hi");
        }
    }
}
