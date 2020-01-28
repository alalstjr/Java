--------------------
# Java
--------------------

# 목차

- [1. 자바 JVM JDK 그리고 JRE](#자바-JVM-JDK-그리고-JRE)
    - [1. JVM](#JVM)
    - [2. JRE](#JRE)
    - [3. JDK](#JDK)
    - [4. JAVA](#JAVA)
- [2. JVM 구조](#JVM-구조)
    - [1. 클래스 로더 시스템](#클래스-로더-시스템)
    - [2. 메모리](#메모리)
        - [1. 스택](#스택)
        - [2. 네이티브 메소드](#네이티브-메소드)
        - [3. 실행 엔진](#실행-엔진)
        - [4. 참고](#참고)

# 자바 JVM JDK 그리고 JRE

- JDK
    - JRE
        - JVM
        - Library
    - 개발 툴
        - appletviewer
        - apt
        - jar...

## JVM

Java Virtual Machine

- 자바 가상 머신으로 자바 바이트 코드(.class 파일)를 `OS에 특화된 코드로 변환(인터프리터와 JIT 컴파일러)하여 실행`한다. 그러므로 OS에 의존적이므로 종속적을 가집니다.
- `바이트 코드를 실행하는 표준`(JVM 자체는 표준)이자 구현체(특정 밴더가 구현한 JVM)다.
- JVM스팩: https://docs.oracle.com/javase/specs/jvms/se11/html/
- JVM 밴터: 오라클, 아마존, Azul, ...
- 특정 플랫폼에 `종속적`

최초의 JVM은 JAVA만 지원해주기 위해서 만들어 졌지만 JAVA언어와 직접적인 연관 관계가 있는것이 아니라 중간에 클레스 파일만 있으면 실행을 해주는(JRE) 존재해서 연관성이 타이트하지 않습니다. 
즉 어떠한 다른 프로그래밍 코드로 코딩을 하더라도 해당 언어로 컴파일 했을때 class 파일이 만들어 지거나 Java 파일 만들어 주기만하면 JVM을 활용할 수 있습니다. 

## JRE

Java Runtime Environment

JVM은 혼자 배포할 수 없습니다. 
`최소 배포 단위는 JRE` 입니다.

- `자바 애플리케이션을 실행`할 수 있도록 구성된 배포판.
- JVM과 핵심 라이브러리 및 자바 런타임 환경에서 사용하는 프로퍼티 세팅이나 리소스 파일을 가지고 있다.(JVM + 라이브러리)
- 개발 관련 도구는 포함하지 않는다. (JDK에서 제공)

## JDK

Java Development Kit

- JRE + 개발에 필요한 툴
- 소스 코드를 작성할 때 사용하는 자바 언어는 플랫폼에 독립적
- 오라클은 자바 11부터는 `JDK만 제공하며 JRE를 따로 제공하지 않는다.`

## JAVA

- 프로그래밍 언어
- JDK에 들어있는 자바 컴파일러(javac)를 사용하여 바이트코드(.class 파일)로 컴파일 할 수 있다.
- 오라클에서 만든 Oracle JDK 11 버전부터 상용으로 사요할 때 유료.

JIT 컴파일러: https://aboullaite.me/understanding-jit-compiler-just-in-time-compiler/
JDK, JRE 그리고 JVM: https://howtodoinjava.com/java/basics/jdk-jre-jvm/
https://en.wikipedia.org/wiki/List_of_JVM_languages

# JVM 구조

![JVM 구조](./images/jvm.png)

## 클래스 로더 시스템

JAVA 바이트 코드를 읽어 들여서 `메모리에 적절하게 배치`하는게 클래스 로더가 하는일입니다.

- .class 에서 바이트코드를 읽고 메모리에 저장
- 로딩: 클래스 읽어오는 과정
- 링크: 레퍼런스를 연결하는 과정
- 초기화: static 값들 초기화 및 변수에 할당

> static 값 예제

~~~
public class Application {
    public Application() { }

    static String myName;
    static {
        myName = "jjunpro";
    }
}

public class WhiteShip {
    public WhiteShip() { }

    public void hello() {
        String myName = Application.myName;
        // static 선언으로 다른 class 에서 불러올 수 있습니다.
    }
}
~~~

## 메모리

- 메모스 영역에는 `클래스 수준의 정보 (클래스 이름, 부모 클래스 이름, 메소드, 변수) 저장.` 공유 자원이다.
- 힙 영역에는 `객체를 저장.` 공유 자원이다.
- 스택 영역에는 `쓰레드 마다 런타임 스택`을 만들고, 그 안에 `메소드 호출을 스택 프레임이라 부르는 블럭으로 쌓는다.` 쓰레드 `종료하면 런타임 스택도 사라진다.`
- PC(Program Counter) 레지스터: `쓰레드 마다 쓰레드 내 현재 실행할 스택 프레임을 가리키는 포인터가 생성`s된다.
- 네이티브 메소드 스택
- https://javapapers.com/core-java/java-jvm-run-time-data-areas/#Program_Counter_PC_Register

아무것도 상속받지 않은 클래스도 기본적으로 Object 클래스를 상속받습니다. 

~~~
public class Application {
    public static void main(String[] args) {
        System.out.println("Super Class : " + Application.class.getSuperclass().toString());
    }
}

- 결과

Super Class : class java.lang.Object
~~~

Application.class 에 들어있는 메소드, 변수들 모드 메소드 영역에 저장이 되고 공유하는 자원이 됩니다. 다른 영역에서도 참조할 수 있는 정보들입니다.

힙, 메소드를 제외한 다른것들은 쓰레드에서만 공유하는 자원

### 스택

쓰레드마다 런타임 스택이라는것을 만들고 그 안에 스택 프레임(메소드 콜)을 쌓습니다.
예를 들어서 에러 메세지를 볼때 메서드가 쌓여서 출력되는것을 확인합니다. 
이런 스택은 쓰레드 마다 하나씩 만들어 집니다.
만들어진 스택에 메소드를 쌓은 위치를 가르키는 PC registers 가 생깁니다.
이것또한 해당 쓰레드마다 생기고 국한 됩니다.

### 네이티브 메소드

메소드에 네이티브라는 키워드가 붙어 있고 그 구현을 자바가 아닌 C,C++.. 로 한것을 의미

- JNI(Java Native Interface)
    - 자바 애플리케이션에서 C, C++, 어셈블리로 작성된 함수를 사용할 수 있는 방법 제공
    - Native 키워드를 사용한 메소드 호출
    - https://medium.com/@bschlining/a-simple-java-native-interface-jni-example-in-java-and-scala-68fdafe76f5f  

실제 구현된 그 자체를 `네이티브 메소드 라이브러리` 라고 부르고 있습니다.
해당 라이브러리는 `네이티브 메소드 인터페이스(JNI)`를 통해서 사용해야 합니다.
네이티브 메소드를 사용하는 코드가 존재한다면 `네이티브 메소드 스택`이 생기고 JNI 를 불러오는 스택 프레임이 하나 쌓이게 됩니다.

간단 예제

~~~
Thread.currentThread();
>
public static native Thread currentThread();
~~~  

## 실행 엔진

실행을 하면서 스택을 사용합니다.
한줄씩 하는것이 비효율적이므로 JIT 사용하고 메모리도 최적화 해줘야 하니까 남는 레퍼런스 인스턴스를 찾아서 정리도 해줍니다.

- 인터프리터: 바이크 코드를 한줄 씩 실행.
- JIT(just-in-time compilation) 컴파일러: 인터프리터 효율을 높이기 위해, `인터프리터가 반복되는 코드`를 발견하면 JIT 컴파일러로 반복되는 코드를 `모두 네이티브 코드로 바꿔둔다.` 그 다음부터 인터프리터는 네이티브 코드로 컴파일된 코드를 바로 사용한다.
- GC(Garbage Collector): 더이상 `참조되지 않는 객체`를 모아서 `정리한다.`

## 참고
- https://www.geeksforgeeks.org/jvm-works-jvm-architecture/
- https://dzone.com/articles/jvm-architecture-explained
- http://blog.jamesdbloom.com/JVMInternals.html
