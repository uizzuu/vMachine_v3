package repository;

import vo.MemberVO;
import vo.ProductVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {
    // 회원정보 저장
    // Map:key 는 회원아이디(id)
    private Map<String, MemberVO> memberMap = new HashMap<>();

    // 회원 저장 (아이디 중복시 덮어씀)
    public void save(MemberVO member) {
        memberMap.put(member.getId(), member);
    }

    // 회원아이디로 회원 정보 조회
    public MemberVO findById(String id) {
        return memberMap.get(id);
    }

    // 회원 정보 수정
    public void update(MemberVO member) {
        memberMap.put(member.getId(), member);
    }

    // 회원 삭제
    public void delete(String id) {
        memberMap.remove(id);
    }

    // 회원 존재 여부 확인
    public boolean existById(String id) {
        return memberMap.containsKey(id);
    }

    // 전체 회원 목록 반환
    public List<MemberVO> findAll() {
        return new ArrayList<>(memberMap.values());
    }
}
