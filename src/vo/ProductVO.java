package vo;

public class ProductVO {
    private int productID;
    private String product;
    private int price;
    private int stock;

    // 생성자
    public ProductVO(){}

    public ProductVO(int productID, String product, int price, int stock) {
        this.productID = productID;
        this.product = product;
        this.price = price;
        this.stock = stock;
    }

    // toString()
    @Override
    public String toString() {
        return "[제품ID=" + productID +
                ", 제품명=" + product +
                ", 가격=" + price +
                ", 재고=" + stock +
                ']';
    }

    // Getter, Setter

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
