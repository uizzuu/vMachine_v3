package service;

import exception.MyException;
import repository.MemberRepository;
import vo.MemberVO;

import java.util.List;

public class MemberService {
    private MemberRepository mr = new MemberRepository();

    // 회원가입
    public void signup(MemberVO member) throws MyException {
        if (mr.findByLoginId(member.getId()) != null) {
            throw new MyException("이미 존재하는 아이디입니다.");
        }
        mr.save(member);
    }

    // 로그인
    public MemberVO login(String id, String pw) throws MyException {
        MemberVO member = mr.findByLoginId(id);
        if (member == null || !member.getPw().equals(pw)) {
            throw new MyException("로그인 실패(아이디 또는 비밀번호 오류)");
        }
        return member;
    }

    // 금액 충전
    public void charge(int mid, int money) throws MyException {
        if (money <= 0 || money % 1000 != 0) {
            throw new MyException("충전 금액은 1000원 단위의 양수여야 합니다.");
        }
        MemberVO member = mr.findById(mid);
        if (member == null) {
            throw new MyException("존재하지 않는 회원입니다.");
        }
        member.setMoney(member.getMoney() + money);
        mr.update(member);
    }

    // 회원 삭제
    public void deleteMember(int mid) {
        mr.delete(mid);
    }

    // 회원 수정
    public void updateMember(MemberVO mid) {
        mr.update(mid);
    }

    // 회원 정보 목록 반환
    public List<MemberVO> listMembers() {
        return mr.findAll();
    }

    // ID 자동 생성 호출
    public int generateMemberId() {
        return mr.generateId();
    }

    //
    public MemberVO getMember(int mid) {
        return mr.findById(mid);
    }

    public void updateId(MemberVO member){
        mr.update(member);
    }
}
