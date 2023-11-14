package christmas.util;

public class ViewConstants {
    public static final String SPACE = " ";
    public static final String NEW_LINE = "\n";

    public static final int ZERO = 0;
    public static final String WON = "원";
    public static final String ZERO_WON = ZERO + WON;

    public static final String COUNT = "개";
    public static final String NONE = "없음";

    public static final String NEGATIVE_VALUE_FORMA = "-%s";
    public static final String LABEL_WITH_NEGATIVE_VALUE_FORMAT = "%s: -%s";

    public static final String RESERVATION_MESSAGE_FRAME = """
            12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 s보기!
                        
            <주문 메뉴>
            %s
                        
            <할인 전 총주문 금액>
            %s
                        
            <증정 메뉴>
            %s
                        
            <혜택 내역>
            %s
                        
            <총혜택 금액>
            %s
                        
            <할인 후 예상 결제 금액>
            %s
                        
            <12월 이벤트 배지>
            %s
            """;
}
