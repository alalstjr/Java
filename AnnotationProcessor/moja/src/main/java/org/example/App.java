package org.example;

import java.util.ArrayList;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        /**
         * MojaFactory 에노테이션 프로세싱을 통해서 생성한 클레스
         * */
        Moja moja = new MagicMoja();
        System.out.println(moja.pullOut());

        ArrayList<String[]> lists = new ArrayList<>();

    }
}
