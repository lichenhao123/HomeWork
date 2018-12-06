package org.westos.day21.dig;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class TreasureDig {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, InvocationTargetException {
// 加载类的字节
        byte[] bytes = Files.readAllBytes(Paths.get("Treasure.class"));
// 自定义类加载器
        ClassLoader cl = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                return super.defineClass(name, bytes, 0, bytes.length);
            }
        };
// 获取类对象
        Class<?> clazz = cl.loadClass("com.westos.Treasure");
// 访问私有构造方法，创建对象实例
        Constructor<?> cons = clazz.getDeclaredConstructor();
        cons.setAccessible(true);
        Object obj = cons.newInstance();
// 找到有注解的方法，并反射调用
        Arrays.stream(clazz.getMethods()).filter(m -> m.getAnnotations().length > 0).findFirst().ifPresent((method) -> {
            try {
                method.invoke(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
