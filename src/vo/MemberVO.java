package vo;

public class MemberVO {
    private int mid;
    private String id;
    private String pw;
    private String name;
    private String phone;
    private int money;
    private String cardNo;

    // 생성자
    public MemberVO(){}

    public MemberVO(String id, String pw, String name, String phone, int money, String cardNo) {
        // this.mid = mid;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.money = money;
        this.cardNo = cardNo;
    }

    // toString()
    @Override
    public String toString() {
        return "[회원ID=" + mid +
                ", 아이디=" + id +
                ", 비밀번호=" + pw +
                ", 회원명=" + name +
                ", 전화번호=" + phone +
                ", 보유금액=" + money +
                ", 카드번호=" + cardNo +
                ']';
    }

    // Getter, Setter

    public int getMId() {
        return mid;
    }

    public void setMId(int mid) {
        this.mid = mid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
