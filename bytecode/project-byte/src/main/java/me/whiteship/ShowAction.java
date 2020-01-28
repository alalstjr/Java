package me.whiteship;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;

import java.io.File;
import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class ShowAction {
    public ShowAction() {
    }

    public static void main(String[] args) {
       /*
        ByteBuddy byteBuddy = new ByteBuddy();
        byteBuddy.redefine(Moja.class)    // Moja class를 재 정의합니다.
                .method(named("pullOut")) // Moja class 선언된 pullOut 메소드를 선택
                .intercept(FixedValue.value("Action!!")) // pullOut 메소드의 값을 변경
                .make()
                .saveIn(new File("/Users/kimminseok/git-repository/Java/바이트코드 조작/project/build/classes/java/main/")); // 폴더를 지정합니다.
       */

        Moja moja = new Moja();
        System.out.println("Show Action : " + moja.pullOut());
    }
}
