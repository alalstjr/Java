package me.whiteship;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class Application {
    public static void premain(String agentArgs, Instrumentation inst) {
        new AgentBuilder.Default() // AgentBuilder 기본 빌더를 가져옵니다.
                .type(ElementMatchers.any()) // 아무 타입을 가져옵니다.
                .transform((builder, typeDescription, classLoader, module) -> builder
                        .method(named("pullOut"))
                        .intercept(FixedValue.value("Action!!")))
                .installOn(inst); // 인스트루먼테이션(instrumentation) 저장
    }
}
