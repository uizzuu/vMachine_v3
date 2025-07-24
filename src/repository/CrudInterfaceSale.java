package repository;

import vo.SaleVO;

import java.util.List;

public interface CrudInterfaceSale {
    // 판매 기록 저장
    void save(SaleVO sale);

    // 전체 판매 기록
    List<SaleVO> findAll();

    // 제품별 판매 집계 (이름, 수량, 총금액)
    List<String[]> getSalesByProduct();

    // 회원별 판매 집계 (이름, 수량, 총금액)
    List<String[]> getSalesByMember();
}
