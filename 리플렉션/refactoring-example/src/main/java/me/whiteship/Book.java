package me.whiteship;

@MyAnnotation(number = 100)
public class Book {
    private static String a = "A";

    private static final String b = "B";

    private String c = "C";

    public String d = "D";

    protected String e = "E";

    public Book() {
    }

    public Book(String c, String d, String e) {
        this.c = c;
        this.d = d;
        this.e = e;
    }

    private void f() {
        System.out.println("F");
    }

    public void g() {
        System.out.println("G");
    }

    public int h() {
        return 1000;
    }
}
