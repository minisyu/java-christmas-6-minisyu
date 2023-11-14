package christmas.util;

public class ViewConstants {
    public static final String START_EVENT_PLANNER_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    public static final String VISIT_DATE_MESSAGE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    public static final String MENU_ITEMS_MESSAGE = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    public static final String ERROR_PREFIX = "[ERROR] ";

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
