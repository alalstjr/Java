package me.whiteship;

public class WhiteShip {
    public WhiteShip() {
    }

    public void hello() {
        String myName = Application.myName;
    }

    // 클래스 로더 Class 객체를 생성하여 “힙" 영역에 저장 예제
    public void work() {
        /*
         * 예제로 WhiteShip.class 가 로딩되면 class 타입의 인스턴스가 WhiteShip.class 저장이 됩니다.
         * 그래서 static 하게 접근이 가능합니다.
         * */
        //WhiteShip.class;

        /*
         * 아니면 인스턴스가 존재한다면 whiteShip.getClass() 해서 접근할 수 있습니다.
         * */
        WhiteShip whiteShip = new WhiteShip();
        whiteShip.getClass();

        /*
         * Class 객체도 만들어져서 접근이 가능합니다.
         * */
        Class<WhiteShip> whiteShipClass;
        whiteShip.getClass();
    }
}
