package service;

import exception.MyException;
import repository.ProductRepository;
import vo.ProductVO;

import java.util.List;

public class ProductService {
    private ProductRepository pr = new ProductRepository();

    // 제품 등록
    public void addProduct(ProductVO product) {
        pr.save(product);
    }

    // 제품아이디로 제품 정보 조회
    public ProductVO getProduct(int id) {
        return pr.findById(id);
    }

    // 제품 구매
    public void buyProduct(int id) throws MyException {
        ProductVO p = pr.findById(id);
        if (p == null || p.getStock() <= 0) {
            throw new MyException("해당 제품이 없거나 재고가 부족합니다.");
        }
        p.setStock(p.getStock() - 1);
        pr.update(p);
    }

    // 제품 삭제
    public void deleteProduct(int id) {
        pr.delete(id);
    }

    // 제품 수정
    public void updateProduct(ProductVO id) {
        pr.update(id);
    }

    // ID 자동 생성 호출
    public int generateProductId() {
        return pr.generateId();
    }

    // 저장된 모든 제품 목록 반환
    public List<ProductVO> listProducts() {
        return pr.findAll();
    }
}
