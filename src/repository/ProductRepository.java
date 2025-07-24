package repository;

import db.DBConn;
import vo.ProductVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository implements CrudInterfaceProduct {
    // 제품 정보 저장
    // productId : key, productVO : value
    // private Map<Integer, ProductVO> productMap = new HashMap<>();

    // DB 연결
    private Connection conn = DBConn.getConnection();
    private PreparedStatement psmt = null;
    private String sql;

    // 제품 저장 (아이디 중복 시 덮어씀)
    @Override
    public int save(ProductVO product) {
        // System.out.println("[ProductRepository.save]");
        try {
            sql = "INSERT INTO product(name, price, stock)" +
                    "VALUES (?, ?, ?)";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, product.getProduct());
            psmt.setInt(2, product.getPrice());
            psmt.setInt(3, product.getStock());
            int result = psmt.executeUpdate();
            psmt.close();
            System.out.println("제품 추가가 완료되었습니다.");
            return result;
        } catch (SQLException e) {
            System.out.println("제품 저장 실패 : " + e.toString());
        }
        return 0;
        // productMap.put(product.getProductID(), product);
    }

    // 제품아이디로 제품 조회
    @Override
    public ProductVO findById(int productID) {
        // System.out.println("[ProductRepository.findById]");
        ProductVO p = null;
        ResultSet rs = null;
        try {
            sql = "SELECT * FROM product WHERE pid = ? AND is_deleted = FALSE";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, productID);
            rs = psmt.executeQuery();
            if (rs.next()) {
                p = new ProductVO();
                p.setProductID(rs.getInt("pid"));
                p.setProduct(rs.getString("name"));
                p.setPrice(rs.getInt("price"));
                p.setStock(rs.getInt("stock"));
            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            System.out.println("제품 조회 실패 : " + e.toString());
        }
        return p;
        // return productMap.get(productID);
    }

    // 제품 수정
    @Override
    public void update(ProductVO product) {
        // System.out.println("[ProductRepository.update]");
        try {
            sql = "UPDATE product " +
                    "SET name = ?, price = ?, stock = ? " +
                    "WHERE pid = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, product.getProduct());
            psmt.setInt(2, product.getPrice());
            psmt.setInt(3, product.getStock());
            psmt.setInt(4, product.getProductID());
            int result = psmt.executeUpdate();
//            if (result > 0) {
//                System.out.println("제품이 성공적으로 수정되었습니다.");
//            } else {
//                System.out.println("수정할 제품을 찾을 수 없습니다.");
//            }
        } catch (SQLException e) {
            System.out.println("제품 수정 실패 : " + e.toString());
        }
        // productMap.put(product.getProductID(), product);
    }

    // 제품 삭제
    @Override
    public void delete(int productID) {
        // System.out.println("[ProductRepository.delete]");
        try {
            sql = "UPDATE product SET is_deleted = TRUE WHERE pid = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, productID);
            int result = psmt.executeUpdate();
            psmt.close();
        } catch (SQLException e) {
            System.out.println("제품 삭제 실패 : " + e.toString());
        }
        // productMap.remove(productID);
    }

    // ID 자동 생성
    private int productIdSeq = 1;
    @Override
    public int generateId() {
        return productIdSeq++;
    }

    // 제품 존재 여부 확인
    @Override
    public boolean existById(int productID) {
        // System.out.println("[ProductRepository.existById]");
        ResultSet  rs = null;
        try {
            sql = "SELECT COUNT(*) FROM product WHERE pid = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, productID);
            rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            System.out.println("제품 존재 여부 확인 실패 : " + e.toString());
        }
        return false;
        // return productMap.containsKey(productID);
    }

    // 전체 제품 목록 반환
    @Override
    public List<ProductVO> findAll() {
        // System.out.println("[ProductRepository.findAll]");
        List<ProductVO> productList = new ArrayList<>();
        ResultSet rs = null;
        try {
            sql = "SELECT * FROM product WHERE is_deleted = FALSE";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs.next()) {
                ProductVO p = new ProductVO();
                p.setProductID(rs.getInt("pid"));
                p.setProduct(rs.getString("name"));
                p.setPrice(rs.getInt("price"));
                p.setStock(rs.getInt("stock"));
                productList.add(p);
            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            System.out.println("전체 제품 조회 실패 : " + e.toString());
        }
        return productList;
        // return new ArrayList<>(productMap.values());
    }
}
