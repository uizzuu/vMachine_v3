package vo;

public class MemberVO {
    private String id;
    private String pw;
    private String name;
    private String phone;
    private int money;

    // 생성자
    public MemberVO(){}

    public MemberVO(String id, String pw, String name, String phone, int money) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.money = money;
    }

    // toString()
    @Override
    public String toString() {
        return "MemberVO{" +
                "id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", money=" + money +
                '}';
    }

    // Getter, Setter

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
}
