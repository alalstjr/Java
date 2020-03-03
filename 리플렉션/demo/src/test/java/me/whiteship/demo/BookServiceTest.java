package me.whiteship.demo;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    /**
     * BookService 는 사실 Null 값입니다.
     *
     * */
    @Autowired
    BookService bookService;

    @Test
    public void di() {
        /**
         * BookService 의 bookRepository 값이 존재하는지 확인합니다.
         * */
        Assert.assertNotNull(bookService);
        Assert.assertNotNull(bookService.bookRepository);
    }
}