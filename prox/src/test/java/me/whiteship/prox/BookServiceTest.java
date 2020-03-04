package me.whiteship.prox;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BookServiceTest {

    // BookService bookService = new BookServiceProx(new DefaultBookService());

    /**
     * 프록시를 런타임시 만들기 newProxyInstance() 메소드에 클레스로더 -> BookService.class.getClassLoader() Class[] 배열
     * 인터페이스 목록이 필요합니다. 만들려는 프록시 인터페이스가 어떠한 인터페이스 타입의 구현체인지 알려줍니다. -> new Class[]{BookService.class}
     * 해당 프록시의 메소드가 호출이 될 때 메소드 호출을 어떻게 처리할 것인지의 설명 -> InvocationHandler()
     * <p>
     * Object 타입으로 return 을 하므로 타입 케스팅이 필요합니다.
     */
    BookService bookService = (BookService) Proxy
            .newProxyInstance(BookService.class.getClassLoader(), new Class[]{BookService.class},
                    new InvocationHandler() {
                        /* 리얼 서브젝트 */
                        BookService bookService = new DefaultBookService();

                        @Override
                        public Object invoke(Object o, Method method, Object[] args)
                                throws Throwable {
                            /* if rent() 메소드에만 부가적인 기능을 적용하고 싶다면 */
                            if(method.getName().equals("rent")) {
                                System.out.println("======1");
                                /* 기본적으로 실행되는 메소드가 실행됩니다. */
                                Object invoke = method.invoke(bookService, args);
                                System.out.println("======2");
                                return invoke;
                            }

                            /* 기본적으로 실행되는 메소드가 실행됩니다. */
                            return method.invoke(bookService, args);
                        }
                    });

    @Test
    public void di() {
        Book book = new Book();
        book.setTitle("spring");
        bookService.rent(book);

        bookService.returnBook(book);
    }
}