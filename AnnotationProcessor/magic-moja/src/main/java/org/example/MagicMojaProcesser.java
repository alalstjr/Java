package org.example;

import com.google.auto.service.AutoService;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
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
        }

        return true;
    }
}
