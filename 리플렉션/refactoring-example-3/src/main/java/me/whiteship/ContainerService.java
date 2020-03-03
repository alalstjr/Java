package me.whiteship;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ContainerService {

    /**
     * 제네릭 메소드 메소드의 파라미터로 넘겨주는 Class<T> classType 으로 return 을 하고싶을 때 사용
     */
    public static <T> T getObject(Class<T> classType) {
        T instance = createinstance(classType);

        /**
         * classType 의 모든 필드를 돌면서 Inject 어노테이션이 존재하는지 확인합니다.
         * */
        Arrays.stream(classType.getDeclaredFields()).forEach(field -> {
            Inject annotation = field.getAnnotation(Inject.class);

            /* 어노테이션이 존재한다면 */
            if (annotation != null) {
                /**
                 *  BookService 의 Type 은 BookRepository 입니다.
                 *  BookRepository 인스턴스를 생성한 후 필드에 설정합니다.
                 */
                Object fieldInstance = createinstance(field.getType());
                field.setAccessible(true);
                try {
                    field.set(instance, fieldInstance);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return instance;
    }

    /**
     * 파라미터로 받은 Class<T> classType
     */
    private static <T> T createinstance(Class<T> classType) {
        try {
            return classType.getConstructor(null).newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
