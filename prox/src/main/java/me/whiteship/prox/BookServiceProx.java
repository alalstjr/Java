package me.whiteship.prox;

public class BookServiceProx implements BookService {

    BookService bookService;

    public BookServiceProx(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void rent(Book book) {
        System.out.println("=======");
        bookService.rent(book);
        System.out.println("=======");
    }
}
