package repository;

import db.DBConn;
import vo.SaleVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleRepository implements CrudInterfaceSale {
    // DB 연결
    private Connection conn = DBConn.getConnection();
    private PreparedStatement psmt;
    private ResultSet rs;
    private String sql;

    // 판매 기록 저장
    @Override
    public void save(SaleVO sale) {
        // System.out.println("[SaleRepository.save]");
        try {
            sql = "INSERT INTO sale(sid, mid, pid, quantity, total_price, sale_time)" +
                    "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, sale.getSaleId());
            psmt.setInt(2, sale.getMemberId());
            psmt.setInt(3, sale.getProductId());
            psmt.setInt(4, sale.getQuantity());
            psmt.setInt(5, sale.getTotalPrice());
            psmt.executeUpdate();
            psmt.close();
        } catch (SQLException e) {
            System.out.println("판매 기록 저장 실패 : " + e.toString());
        }
    }

    // 전체 판매 기록 조회
    @Override
    public List<SaleVO> findAll() {
        // System.out.println("[SaleRepository.findAll]");
        List<SaleVO> saleList = new ArrayList<>();
        try {
            sql = "SELECT * FROM sale";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs.next()) {
                SaleVO s = new SaleVO();
                s.setSaleId(rs.getInt("sid"));
                s.setMemberId(rs.getInt("mid"));
                s.setProductId(rs.getInt("pid"));
                s.setQuantity(rs.getInt("quantity"));
                s.setTotalPrice(rs.getInt("total_price"));
                s.setSaleTime(rs.getString("sale_time"));
                saleList.add(s);
            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            System.out.println("전체 판매 기록 조회 실패 : " + e.toString());
        }
        return saleList;
    }

    // 제품별 판매 집계 (이름, 수량, 총금액)
    @Override
    public List<String[]> getSalesByProduct() {
        // System.out.println("[SaleRepository.getSalesByProduct]");
        List<String[]> result = new ArrayList<>();
        try {
            sql = "SELECT p.name AS 제품명, SUM(s.quantity) AS 판매수량, " +
                    "SUM(s.total_price) AS 판매금액 " +
                    "FROM sale s JOIN product p ON s.pid = p.pid " +
                    "GROUP BY p.pid, p.name ORDER BY p.pid DESC";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs.next()) {
                result.add(new String[]{
                        rs.getString("제품명"),
                        String.valueOf(rs.getInt("판매수량")),
                        String.valueOf(rs.getInt("판매금액"))
                });
            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            System.out.println("제품별 판매 집계 실패 : " + e.toString());
        }
        return result;
    }

    // 회원별 판매 집계 (이름, 수량, 총금액)
    @Override
    public List<String[]> getSalesByMember() {
        // System.out.println("[SaleRepository.getSalesByMember]");
        List<String[]> result = new ArrayList<>();
        try {
            sql = "SELECT m.id AS 회원아이디, m.name AS 회원명, " +
                    "SUM(s.total_price) AS 구매금액, " +
                    "m.money AS 충전잔액 " +
                    "FROM sale s JOIN member m ON s.mid = m.mid " +
                    "GROUP BY m.mid, m.id, m.name, m.money ORDER BY m.mid DESC";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs.next()) {
                result.add(new String[]{
                        rs.getString("회원아이디"),
                        rs.getString("회원명"),
                        String.valueOf(rs.getInt("구매금액")),
                        String.valueOf(rs.getInt("충전잔액"))
                });
            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            System.out.println("회원별 판매 집계 실패 : " + e.toString());
        }
        return result;
    }
}
