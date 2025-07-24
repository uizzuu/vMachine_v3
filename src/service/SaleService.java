package service;

import repository.SaleRepository;
import vo.SaleVO;

import java.util.List;

public class SaleService {
    private SaleRepository sr = new SaleRepository();

    // ID 초기값 = 1
    private int SaleIdSeq = 1;
    // 자동 ID 생성기
    public int generateSaleId() {
        return SaleIdSeq++;
    }

    // 판매 기록 저장
    public void recordSale(SaleVO sale) {
        sr.save(sale);
    }

    // 제품별 판매 집계 결과 반환
    public List<String[]> getSalesByProduct() {
        return sr.getSalesByProduct();
    }

    // 회원별 판매 집계 결과 반환
    public List<String[]> getSalesByMember(){
        return sr.getSalesByMember();
    }

    // 전체 판매 목록 필요
    public List<SaleVO> listAllSales() {
        return sr.findAll();
    }
}
