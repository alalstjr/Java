package me.whiteship.prox;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.named;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.assertj.core.internal.bytebuddy.ByteBuddy;
import org.assertj.core.internal.bytebuddy.implementation.InvocationHandlerAdapter;
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
    BookService bookService1 = (BookService) Proxy
            .newProxyInstance(BookService.class.getClassLoader(), new Class[]{BookService.class},
                    new InvocationHandler() {
                        /* 리얼 서브젝트 */
                        BookService bookService = new DefaultBookService();

                        @Override
                        public Object invoke(Object o, Method method, Object[] args)
                                throws Throwable {
                            /* if rent() 메소드에만 부가적인 기능을 적용하고 싶다면 */
                            if (method.getName().equals("rent")) {
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
        bookService1.rent(book);

        bookService1.returnBook(book);
    }

    /**
     * 클래스의 프록시
     */
    @Test
    public void de() {
        /* 프록시 handler 생성 */
        MethodInterceptor handler = new MethodInterceptor() {
            BookService bookService = new DefaultBookService();

            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
                    throws Throwable {
                return method.invoke(bookService, args);
            }
        };

        BookService bookService = (BookService) Enhancer.create(BookService.class, handler);

        Book book = new Book();
        bookService.rent(book);
    }

    @Test
    public void da()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        /**
         * BookService 의 서브클레스를 만들겠다. -> subclass().make()
         * */
        Class<? extends DefaultBookService> proxyClass = new ByteBuddy()
                .subclass(DefaultBookService.class)
                .method(named("rent")) // rent 메소드에만 적용이 됩니다.
                .intercept(
                        InvocationHandlerAdapter.of(new InvocationHandler() {
                            DefaultBookService bookService = new DefaultBookService();

                            @Override
                            public Object invoke(Object o, Method method, Object[] args)
                                    throws Throwable {
                                return method.invoke(bookService, args);
                            }
                        }))
                .make()
                .load(DefaultBookService.class.getClassLoader())
                .getLoaded();

        DefaultBookService bookService = proxyClass.getConstructor(null).newInstance();
        Book        book        = new Book();
        book.setTitle("book");

        bookService.rent(book);
    }
}