package me.whiteship;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        /**
         * Book.class 존재하면 리플렉션이 시작합니다.
         * 인스턴스를 생성하는 방법 getConstructor 기본생성자를 가져온 후 newInstance 인스턴스를 생성합니다.
         * */
        Class<?> aClass = Class.forName("me.whiteship.Book");
        /* 기본 생성자 */
        Constructor<?> constructor = aClass.getConstructor(null);
        Book           book        = (Book) constructor.newInstance();
        System.out.println(book);

        /* 파라미터값을 받는 생성자 */
        Constructor<?> constructor1 = aClass.getConstructor(String.class);
        Book           book1        = (Book) constructor1.newInstance("myBook");
        System.out.println(book1);

        /**
         * 특정한 인스턴스가 필요한 Field
         * */
        Field b = Book.class.getDeclaredField("B");
        b.setAccessible(true);
        System.out.println(b.get(book));
        b.set(book, "BBBBBB");
        System.out.println(b.get(book));

        /**
         * Field 를 가져와서 value 를 가져올 때 해당 Field 가 특정한 인스턴스에 해당하는 Field 면
         * get() 메소드에 인스턴스를 넘겨줄 수 있는데 Field a 는 static 한 필드이므로
         * 인스턴스마다 다른것이 아니라 모든 클레스에서 공유되는 static 한 변수 이므로 특정한 Object 를 넘겨줄것이 없습니다.
         * 그러므로 get(null) null 값을 넘겨주면 됩니다.
         * */
        Field a = Book.class.getDeclaredField("A");
        System.out.println(a.get(null));

        /* 변경하는 경우 */
        a.set("null", "AAAAAA");
        System.out.println(a.get(null));

        /**
         * 특정한 인스턴스가 필요한 Method
         * */
        Method c = Book.class.getDeclaredMethod("c");
        /* Method 특정한 인스턴스인 경우 */
        c.setAccessible(true);
        c.invoke(book);
        System.out.println(c);

        /**
         * 파라미터값이 존재하는 Method
         * */
        Method sum = Book.class.getMethod("sum", int.class, int.class);
        int invoke = (int) sum.invoke(book, 10, 10);
        System.out.println(invoke);
    }
}
