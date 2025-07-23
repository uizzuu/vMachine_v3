package repository;

import vo.ProductVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {
    // 제품 정보 저장
    // productId : key, productVO : value
    private Map<Integer, ProductVO> productMap = new HashMap<>();

    // 제품 저장 (아이디 중복 시 덮어씀)
    public void save(ProductVO product) {
        productMap.put(product.getProductID(), product);
    }

    // 제품아이디로 제품 조회
    public ProductVO findById(int productID) {
        return productMap.get(productID);
    }

    // 제품 수정
    public void update(ProductVO product) {
        productMap.put(product.getProductID(), product);
    }

    // 제품 삭제
    public void delete(int productID) {
        productMap.remove(productID);
    }

    // ID 자동 생성
    private int productIdSeq = 1;
    public int generateId() {
        return productIdSeq++;
    }

    // 제품 존재 여부 확인
    public boolean existById(int productID) {
        return productMap.containsKey(productID);
    }

    // 전체 제품 목록 반환
    public List<ProductVO> findAll() {
        return new ArrayList<>(productMap.values());
    }
}
