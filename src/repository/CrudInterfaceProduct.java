package repository;

import vo.ProductVO;

import java.util.List;

public interface CrudInterfaceProduct {
    // 제품 저장 (아이디 중복 시 덮어씀)
    int save(ProductVO product);

    // 제품아이디로 제품 조회
    ProductVO findById(int productID);

    // 제품 수정
    void update(ProductVO product);

    // 제품 삭제
    void delete(int productID);

    // ID 자동 생성
    int generateId();

    // 제품 존재 여부 확인
    boolean existById(int productID);

    // 전체 제품 목록 반환
    List<ProductVO> findAll();
}
