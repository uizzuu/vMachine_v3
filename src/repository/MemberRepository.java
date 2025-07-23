package repository;

import vo.MemberVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {
    // 회원정보 저장
    // Map:key 는 회원아이디(id)
    private Map<Integer, MemberVO> memberMap = new HashMap<>();

    // 회원 저장 (아이디 중복시 덮어씀)
    public void save(MemberVO member) {
        memberMap.put(member.getMId(), member);
    }

    // 회원아이디로 회원 정보 조회
    public MemberVO findById(int mid) {
        return memberMap.get(mid);
    }

    // 로그인용 id로 조회
    public MemberVO findByLoginId(String id) {
        for (MemberVO m : memberMap.values()) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }

    // 회원 정보 수정
    public void update(MemberVO member) {
        memberMap.put(member.getMId(), member);
    }

    // 회원 삭제
    public void delete(int mid) {
        memberMap.remove(mid);
    }

    // 회원 존재 여부 확인
    public boolean existById(int mid) {
        return memberMap.containsKey(mid);
    }

    // 전체 회원 목록 반환
    public List<MemberVO> findAll() {
        return new ArrayList<>(memberMap.values());
    }

    // ID 자동 생성
    private int memberIdSeq = 1;
    public int generateId() {
        return memberIdSeq++;
    }
}
