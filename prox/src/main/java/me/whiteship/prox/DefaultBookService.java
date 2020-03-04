package me.whiteship.prox;

import org.springframework.stereotype.Service;

@Service
public class DefaultBookService implements BookService {

    /**
     * 프록시를 활용하여 rent 메세지 위 아래로 부가적인 메세지를 추가하는 경우
     * */
    @Override
    public void rent(Book book) {
        System.out.println("rent : " + book.getTitle());
    }

    @Override
    public void returnBook(Book book) {
        System.out.println("returnBook : " + book.getTitle());
    }
}
