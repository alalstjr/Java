package me.whiteship;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation 은 주석과 마찬가지 입니다.
 * 기본적으로 class, 소스 까지는 정보가 남습니다.
 * 바이트 코드를 로딩 했을 때 메모리 상에는 남아있지 않습니다.
 *
 * 하지만 RunTime 까지도 정보를 유지하고 싶다면 Retention 사용하면 됩니다.
 * 기본값은 CLASS 입니다.
 *
 * 확인하려면 javap -c -v [class 경로]
 * */
@Retention(RetentionPolicy.RUNTIME)
/**
 * Target 사용하면 Annotation 사용 위치를 제어할 수 있습니다.
 * */
@Target({ElementType.TYPE, ElementType.FIELD})
/**
 * 상속이 되는 어노테이션으로 선언
 * */
@Inherited
public @interface MyAnnotation {
    /**
     * default 값이 선언되지 않은 경우 값 선언은 필수로 받아야 합니다.
     * */
    int number();

    String name() default "jjunpro";

    /**
     * 만약 key 값의 이름을 value 라고 주면 어노테이션 선언시
     * @MyAnnotation(100) 따로 key 이름값을 주지않고 선언할 수 있습니다.
     * 여러개의 속성을 선언할 때는 key 값을 무조건 주어야 합니다.
     * */
    String value() default "value";
}
