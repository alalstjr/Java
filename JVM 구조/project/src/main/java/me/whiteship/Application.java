package me.whiteship;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

public class Application {
    public Application() {
    }

    static String myName;

    static {
        myName = "jjunpro";
    }

    // 심볼릭 레퍼런스 WhiteShip
    WhiteShip whiteShip = new WhiteShip();

    public static void main(String[] args) {
        // Super Class 출력 예제
        String superClass = Application.class
                .getSuperclass()
                .toString();
        System.out.println("Super Class : " + superClass);

        // 네이티브 메소드 예제
        Thread.currentThread();

        // Application에서 사용중인 클래스로더 확인하기
        ClassLoader classloader = Application.class.getClassLoader();
        System.out.println("Class Loader : " + classloader.toString());

        // 클래스로더 계층형 구조 확인하기
        System.out.println("Class Loader Parent : " + classloader.getParent());
        System.out.println("Class Loader Parent Parent: " + classloader
                .getParent()
                .getParent());
    }
}
