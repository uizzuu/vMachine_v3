package repository;

import db.DBConn;
import vo.MemberVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository implements CrudInterfaceMember {
    // DB 연결
    Connection conn = DBConn.getConnection();
    PreparedStatement psmt = null;
    String sql;
    // 회원정보 저장
    // Map:key 는 회원아이디(id)
    // private Map<Integer, MemberVO> memberMap = new HashMap<>();

    // 회원 저장 (아이디 중복시 덮어씀)
    @Override
    public int save(MemberVO member) {
        // System.out.println("[MemberRepository.save]");
        try {
            sql = "INSERT INTO member(mid, id, pw, name, phone, money, card_no)";
            sql = sql + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            psmt = conn.prepareStatement(sql);
            // 각 ? 자리에 매핑
            psmt.setInt(1, member.getMId());
            psmt.setString(2, member.getId());
            psmt.setString(3, member.getPw());
            psmt.setString(4, member.getName());
            psmt.setString(5, member.getPhone());
            psmt.setInt(6, member.getMoney());
            psmt.setString(7, member.getCardNo());
            // 쿼리 실행하기
            int result = psmt.executeUpdate();
            psmt.close();
            return result;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return 0;
    }

    // 회원아이디로 회원 정보 조회
    @Override
    public MemberVO findById(int mid) {
        // System.out.println("[MemberRepository.findById]");
        MemberVO m = null;
        ResultSet rs = null;
        try {
            sql = "SELECT * FROM member WHERE mid = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, mid);
            rs = psmt.executeQuery();
            if (rs.next()) {
                m = new MemberVO();
                m.setMId(rs.getInt("mid"));
                m.setId(rs.getString("id"));
                m.setPw(rs.getString("pw"));
                m.setName(rs.getString("name"));
                m.setPhone(rs.getString("phone"));
                m.setMoney(rs.getInt("money"));
                m.setCardNo(rs.getString("card_no"));
            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            System.out.println("회원 정보 조회 실패 : " + e.toString());
        }
        return m;
        // return memberMap.get(mid);
    }

    // 로그인용 id로 조회
    @Override
    public MemberVO findByLoginId(String id) {
        // System.out.println("[MemberRepository.findByLoginId]");
        MemberVO m = null;
        ResultSet rs = null;
        try {
            sql = "SELECT * FROM member WHERE id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, id);
            rs = psmt.executeQuery();
            if (rs.next()) {
                m = new MemberVO();
                m.setMId(rs.getInt("mid"));
                m.setId(rs.getString("id"));
                m.setPw(rs.getString("pw"));
                m.setName(rs.getString("name"));
                m.setPhone(rs.getString("phone"));
                m.setMoney(rs.getInt("money"));
                m.setCardNo(rs.getString("card_no"));
            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            System.out.println("로그인 ID 조회 실패 : " + e.toString());
        }
        return m;
    }

    // 회원 정보 수정
    @Override
    public void update(MemberVO member) {
        // System.out.println("[MemberRepository.update]");
        try {
            sql = "UPDATE member " +
                    "SET id = ?, pw = ?, name = ?, " +
                    "phone = ?, money = ?, card_no = ? " +
                    "WHERE mid = ? ";
            psmt = conn.prepareStatement(sql);
            // 각 ? 자리에 매핑
            psmt.setString(1, member.getId());
            psmt.setString(2, member.getPw());
            psmt.setString(3, member.getName());
            psmt.setString(4, member.getPhone());
            psmt.setInt(5, member.getMoney());
            psmt.setString(6, member.getCardNo());
            psmt.setInt(7, member.getMId());
            // 쿼리 실행하기
            int result = psmt.executeUpdate();
//            if (result > 0) {
//                System.out.println("회원 정보가 성공적으로 수정되었습니다.");
//            } else {
//                System.out.println("수정 대상 회원을 찾을 수 없습니다.");
//            }
            psmt.close();
        } catch (SQLException e) {
            System.out.println("회원 정보 수정 실패 : " + e.toString());
        }
        // memberMap.put(member.getMId(), member);
    }

    // 회원 삭제
    @Override
    public void delete(int mid) {
        // System.out.println("[MemberRepository.delete]");
        try {
            sql = "DELETE FROM member WHERE mid = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, mid);
            int result = psmt.executeUpdate();
//            if (result > 0) {
//                System.out.println("회원이 성공적으로 삭제되었습니다.");
//            } else {
//                System.out.println("삭제할 회원을 찾을 수 없습니다.");
//            }
            psmt.close();
        } catch (SQLException e) {
            System.out.println("회원 삭제 실패 : " + e.toString());
        }
        // memberMap.remove(mid);
    }

    // 회원 존재 여부 확인
    @Override
    public boolean existById(int mid) {
        // System.out.println("[MemberRepository.existById]");
        ResultSet rs = null;
        try {
            sql = "SELECT COUNT(*) FROM member WHERE mid = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, mid);
            rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            System.out.println("회원 존재 여부 확인 실패 : " + e.toString());
        }
        return false;
        // return memberMap.containsKey(mid);
    }

    // 전체 회원 목록 반환
    @Override
    public List<MemberVO> findAll() {
        // System.out.println("[MemberRepository.findAll]");
        // DB 에서 select한 결과를 담을 리스트 선언
        List<MemberVO> memberList = new ArrayList<>();
        ResultSet rs = null;
        try {
            sql = "SELECT * FROM member";
            psmt = conn.prepareStatement(sql);
            // SQL 구문 실행
            rs = psmt.executeQuery();
            // rs에 들어온 레코드들을 하나씩 뽑아서 memberList에 담기
            while (rs.next()) {
                MemberVO m = new MemberVO();
                m.setMId(rs.getInt("mid"));
                m.setId(rs.getString("id"));
                m.setPw(rs.getString("pw"));
                m.setName(rs.getString("name"));
                m.setPhone(rs.getString("phone"));
                m.setMoney(rs.getInt("money"));
                m.setCardNo(rs.getString("card_no"));
                // 리스트에 담기
                memberList.add(m);
            }
            // 닫아주기
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return memberList;
        // return new ArrayList<>(memberMap.values());
    }

    // ID 자동 생성
    private int memberIdSeq = 1;

    @Override
    public int generateId() {
        return memberIdSeq++;
    }
}
