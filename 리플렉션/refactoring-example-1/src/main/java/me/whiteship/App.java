package me.whiteship;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws ClassNotFoundException {
        /**
         * Book.class, MyBook.class, MyInterface.interface 정보에 접근하려면 Class<T> 타입이 필요합니다.
         *
         * Book.class 로딩이 끝나면 class Type의 인스턴스를 만들어서 힙 영역에 넣습니다.
         * 이러한 인스턴스는 class 로딩만 해도 인스턴스가 생성이 됩니다.
         * 그래서 bookClass 를 바로 사용할 수 있습니다.
         * Class 타입의 인스턴스를 가져오는 방법
         * */
        Class<Book> bookClass = Book.class;

        /**
         * 이미 인스턴스가 존재하는 경우 getClass를 사용하여 가져올 수도 있습니다.
         * */
        Book                  book   = new Book();
        Class<? extends Book> aClass = book.getClass();

        /**
         * 아무것도 모르고 문자열만 아는 상태인 경우
         * */
        Class<?> aClass1 = Class.forName("me.whiteship.Book");

        /**
         * Book.class 의 Field 들을 불러온다면 getFields 메소드를 활용하여 public 값을 가져올 수 있습니다.
         * Arrays.stream 을 활용하여 해당 Class 의 Field 값을 출력하면
         * getFields 메소드는 결과는 "public java.lang.String me.whiteship.Book.d" 하나만 출력됩니다.
         * */
        System.out.println(" ");
        System.out.println("===== fields =====");
        Field[] fields = bookClass.getFields();
        Arrays.stream(fields).forEach(System.out::println);

        /**
         * 접근 지시자 상관없이 모든값을 가져오는 방법
         * */
        System.out.println(" ");
        System.out.println("===== declaredFields =====");
        Field[] declaredFields = bookClass.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(System.out::println);

        /**
         * 필드값의 값을 참조하는 방법
         * new 인스턴스는 필수 입니다.
         * */
        System.out.println(" ");
        System.out.println("===== declaredFields get field =====");
        Arrays.stream(declaredFields).forEach(field -> {
            try {
                /* setAccessible true 없이 실행하면 접근할 수 없는 값에 접근하려해서 에러가 발생합니다. */
                field.setAccessible(true);
                System.out.printf("%s %s\n", field, field.get(book));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        /**
         * 필드의 메소드를 가져오는 방법
         * 상속받은 정보까지 전부 불러옵니다.
         * */
        System.out.println(" ");
        System.out.println("===== getMethods =====");
        Method[] methods = bookClass.getMethods();
        Arrays.stream(methods).forEach(System.out::println);

        /**
         * 필드의 상속자를 가져오는 방법
         * */
        System.out.println(" ");
        System.out.println("===== getConstructors =====");
        Constructor<?>[] constructors = bookClass.getConstructors();
        Arrays.stream(constructors).forEach(System.out::println);

        /**
         * Super 상위 클래스 가져오는 방법
         * */
        System.out.println(" ");
        System.out.println("===== getConstructors =====");
        Class<? super MyBook> superclass = MyBook.class.getSuperclass();
        System.out.println(superclass);

        /**
         * Super 인터페이스 가져오는 방법
         * */
        System.out.println(" ");
        System.out.println("===== getInterfaces =====");
        Class<?>[] interfaces = MyBook.class.getInterfaces();
        Arrays.stream(interfaces).forEach(System.out::println);

        /**
         * 각각의 안에는 다양한 기능들이 존재합니다.
         * getModifiers 으로 값 검증이 가능합니다.
         * 필드권한을 확인하는 방법
         * */
        System.out.println(" ");
        System.out.println("===== getModifiers field =====");
        Arrays.stream(Book.class.getDeclaredFields()).forEach(field -> {
            /* Modifiers 제공하는 static 메소드를 사용하면 필드 권한을 확인할 수 있습니다. */
            int modifiers = field.getModifiers();
            System.out.println(field);
            System.out.println(Modifier.isPrivate(modifiers));
            System.out.println(Modifier.isStatic(modifiers));
        });

        /**
         * getModifiers
         * 메소드의 파라미터 타입, 갯수 등등 여러가지 정보를 알수 있습니다.
         * */
        System.out.println(" ");
        System.out.println("===== getModifiers method =====");
        Arrays.stream(Book.class.getMethods()).forEach(field -> {
            System.out.println(field.getParameterTypes());
        });

        /**
         * getAnnotation
         * */
        System.out.println(" ");
        System.out.println("===== getAnnotation =====");
        Arrays.stream(Book.class.getAnnotations()).forEach(System.out::println);

        /**
         * 상속받은 인터페이스 getAnnotation
         * */
        System.out.println(" ");
        System.out.println("===== 상속 getAnnotation =====");
        Arrays.stream(MyBook.class.getAnnotations()).forEach(System.out::println);

        /**
         * MyBook 인터페이스에만 붙어있는 getAnnotation
         * */
        System.out.println(" ");
        System.out.println("===== getDeclaredFields =====");
        Arrays.stream(MyBook.class.getDeclaredFields()).forEach(System.out::println);

        /**
         * 어노테이션 필드의 값을 참조하는 방법
         * */
        System.out.println(" ");
        System.out.println("===== 어노테이션 필드의 값을 참조하는 방법 =====");
        Arrays.stream(Book.class.getAnnotations()).forEach(annotation -> {
            /*
             * 해당 어노테이션이 개발자가 원하는 어노테이션이라면 타입을 변경합니다.
             * 다음 값을 직접 참조할 수 있습니다.
             */
            if (annotation instanceof MyAnnotation) {
                MyAnnotation myAnnotation = (MyAnnotation) annotation;
                System.out.println(myAnnotation.value());
                System.out.println(myAnnotation.number());
            }
        });
    }
}
