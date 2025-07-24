package repository;

import vo.MemberVO;

import java.util.List;

public interface CrudInterfaceMember {
    // 회원 저장 (아이디 중복시 덮어씀)
    int save(MemberVO member);

    // 회원아이디로 회원 정보 조회
    MemberVO findById(int mid);

    // 로그인용 id로 조회
    MemberVO findByLoginId(String id);

    // 회원 정보 수정
    void update(MemberVO member);

    // 회원 삭제
    void delete(int mid);

    // 회원 존재 여부 확인
    boolean existById(int mid);

    // 전체 회원 목록 반환
    List<MemberVO> findAll();

    // ID 자동 생성
    int generateId();
}
