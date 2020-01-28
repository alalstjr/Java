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

    public static void main(String[] args) {
        // Super Class 출력 예제
        System.out.println("Super Class : " + Application.class
                .getSuperclass()
                .toString());

        // 네이티브 메소드 예제
        Thread.currentThread();
    }
}
