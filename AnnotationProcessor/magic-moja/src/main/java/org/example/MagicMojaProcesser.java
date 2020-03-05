package org.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

/**
 * AutoService 어노테이션 등록으로 자동으로 resources.META_INF.services/javax.annotation.processing.Processor 파일을
 * 등록해줍니다.
 */
@AutoService(Processor.class)
public class MagicMojaProcesser extends AbstractProcessor {

    /* 해당 프로세서가 처리할 어떤 어노테이션인지 명시해주는 메소드 */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Magic.class.getName());
    }

    /* 지원하는 소스코드의 버전을 결정하는 메소드 */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * true 를 return 한면 어노테이션 타입을 처리한것이 되므로 다음 프로세서들 한테 해당 어노테이션을 처리하라고 전달하지 않습니다. Magic 어노테이션을 해당
     * 프로세서에서만 처리할것이므로 true 를 반환합니다.
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        /* Magic 어노테이션을 가지고있는 엘리먼트를 를 가져옵니다. */
        Set<? extends Element> elements = roundEnvironment
                .getElementsAnnotatedWith(Magic.class);

        /*
         * 여러 TypeElement 돌면서 조건을 체크합니다.
         * Magic 어노테이션을 인터페이스에만 붙여서 사용하기를 원합니다.
         */
        for (Element element : elements) {
            /*
             * ElementKind 활용하면 element 의 타입을 알 수 있습니다.
             * 인터페이스가 아닌경우 에러처리를 합니다.
             */
            if (element.getKind() != ElementKind.INTERFACE) {
                processingEnv
                        .getMessager()
                        .printMessage(Kind.ERROR, "No Type" + element.getSimpleName());
            } else {
                processingEnv
                        .getMessager()
                        .printMessage(Kind.NOTE, "Processing" + element.getSimpleName());
            }

            TypeElement typeElement = (TypeElement) element;
            ClassName   className   = ClassName.get(typeElement);

            /**
             *  MethodSpec.methodBuilder() : 메소드를 하나 생성합니다.
             *  addModifiers(Modifier.) : 접근권한을 설정합니다.
             *  returns() : 반환 타입을 설정합니다.
             *  addStatement() : 메소드 내부의 Statement 추가하기
             */
            MethodSpec pullOut = MethodSpec.methodBuilder("pullOut")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String.class)
                    .addStatement("return $S", "Rabbit!")
                    .build();

            /**
             *  classBuilder() : 생성되는 class의 이름을 설정합니다.
             *  addModifiers() : 접근권한을 설정합니다.
             *  addMethod() : 메소드를 추가합니다.
             *  addSuperinterface() : 인터페이스를 추가합니다.
             * */
            TypeSpec magicMoja = TypeSpec.classBuilder("MagicMoja")
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(className)
                    .addMethod(pullOut)
                    .build();

            /**
             * 위 코드는 메모리상의 소스로만 클레스를 정의한것
             * Filer 인터페이스 를 사용하여 소스코드를 생성할 수 있습니다.
             * 이를 Javapoet을 사용하여 더 쉽게 생성할 수 있습니다.
             *
             * JavaFile.builder(p, c) : 패키지 네임의 위치에 클래스를 생성해라
             * */
            Filer filer = processingEnv.getFiler();

            try {
                JavaFile.builder(className.packageName(), magicMoja)
                        .build()
                        .writeTo(filer);
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Kind.ERROR, "FATAL ERROR " + e);
            }
        }

        return true;
    }
}
