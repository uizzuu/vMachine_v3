package vo;

public class SaleVO {
    private int saleId;
    private int memberId;
    private int productId;
    private int quantity;
    private int totalPrice;
    private String saleTime;

    // 생성자
    public SaleVO(){};

    public SaleVO(int saleId, int memberId, int productId, int quantity, int totalPrice, String saleTime) {
        this.saleId = saleId;
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.saleTime = saleTime;
    }

    // Getter, Setter

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(String saleTime) {
        this.saleTime = saleTime;
    }
}
