package christmas.domain;

public enum Gift {
    // imp. ㅠㅠ
    //  그냥 Menu에 있는 샴페인을 넘기면 되는건가
    //  증점품 안 줄 때도 생각해서 NONE을 만들긴했는데..흐음
    NONE("없음", 0, 0),
    CHAMPAGNE("샴페인", 25_000, 1);
    private final String giftName;
    private final int giftPrice;
    private final int quantity;

    Gift(String giftName, int giftPrice, int quantity) {
        this.giftName = giftName;
        this.giftPrice = giftPrice;
        this.quantity = quantity;
    }

    /**
     * @param menuItems 사용자가 입력한 메뉴목록과 총 개수
     * @return 증정 이벤트: 할인 전 총주문 금액이 12만 원 이상일 때, 샴페인 1개 증정
     */

    public static Gift from(MenuItems menuItems) {
        if (menuItems.getTotalPrice() >= 120_000) {
            return CHAMPAGNE;
        }
        return NONE;
    }

    public String getGiftName() {
        return giftName;
    }

    public int getGiftPrice() {
        return giftPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
