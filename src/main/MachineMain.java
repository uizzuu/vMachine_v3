package main;

import exception.MyException;
import service.MemberService;
import service.ProductService;
import vo.MemberVO;
import vo.ProductVO;

import java.util.Scanner;

public class MachineMain {
    private static MemberService ms = new MemberService();
    private static ProductService ps = new ProductService();

    private static Scanner sc = new Scanner(System.in);

    private static MemberVO loginMember = null;

    public static void main(String[] args) {
        while (true) {
            if (loginMember == null) {
                showLoginMenu();
            } else {
                showUserMenu();
            }
        }
    }

    private static void showLoginMenu() {
        System.out.println("=== 자판기(회원제) === ");
        System.out.println("1.회원가입 2.로그인 3.종료 0.관리자");
        int input = sc.nextInt();

        switch (input) {
            case 0:
                showAdminMenu();
                break;
            case 1:
                // 회원가입
                signup();
                break;
            case 2:
                // 로그인
                login();
                break;
            case 3:
                // 종료
                System.out.println("프로그램 종료");
                return;
            default:
                System.out.println("잘못된 입력입니다.");
        }
    }

    private static void signup() {
        try {
            addMember();
            System.out.println("회원가입 성공");
        } catch (MyException e) {
            System.out.println("회원가입 실패 : " + e.getMessage());
        }
    }

    private static void addMember() throws MyException {
        System.out.println("아이디 : ");
        String id = sc.next();
        System.out.println("비밀번호 : ");
        String pw = sc.next();
        System.out.println("회원명 : ");
        String name = sc.next();
        System.out.println("전화번호 : ");
        String phone = sc.next();
        System.out.println("카드번호 : ");
        String cardNo = sc.next();
        int mid = ms.generateMemberId();
        // 초기 충전 0
        MemberVO member = new MemberVO(mid, id, pw, name, phone, 0, cardNo);
        ms.signup(member);
    }

    private static void login() {
        try {
            System.out.println("아이디 : ");
            String id = sc.next();
            System.out.println("비밀번호 : ");
            String pw = sc.next();
            loginMember = ms.login(id, pw);
            System.out.println(loginMember.getName() + "님 환영합니다.");
            showUserMenu();
        } catch (MyException e) {
            System.out.println("로그인 실패 : " + e.getMessage());
        }
    }

    private static void showAdminMenu() {
        System.out.println("=== 관리자(admin) 메뉴 ===");
        System.out.println("1.자판기관리 2.회원관리 3.판매관리 4.로그아웃");
        int input = sc.nextInt();

        switch (input) {
            case 1:
                manageVmachine();
                break;
            case 2:
                manageMembers();
                break;
            case 3:
                manageSales();
                break;
            case 4:
                loginMember = null;
                System.out.println("관리자 프로그램이 로그아웃 되었습니다.");
                return;
            default:
                System.out.println("잘못된 입력입니다.");
        }
    }

    private static void manageVmachine() {
        while (true) {
            System.out.println("=== 자판기 관리 ===");
            System.out.println("1.제품추가 2.제품수정 3.제품삭제 4.제품목록 5.돌아가기");
            int input = sc.nextInt();

            switch (input) {
                case 1:
                    // 제품 추가
                    System.out.println("=== 제품 추가 ===");
                    System.out.println("이름 : ");
                    String name = sc.next();
                    System.out.println("가격 : ");
                    int price = sc.nextInt();
                    System.out.println("재고 : ");
                    int stock = sc.nextInt();
                    int pid = ps.generateProductId();
                    ProductVO p = new ProductVO(pid, name, price, stock);
                    ps.addProduct(p);
                    System.out.println("제품 추가가 완료되었습니다.");
                    break;
                case 2:
                    // 제품 수정
                    showProduct();
                    System.out.println("수정할 제품 ID : ");
                    int updateId = sc.nextInt();
                    ProductVO pv = ps.getProduct(updateId);
                    if (pv != null) {
                        System.out.println("새 이름 : ");
                        pv.setProduct(sc.next());
                        System.out.println("새 가격 : ");
                        pv.setPrice(sc.nextInt());
                        System.out.println("새 재고 : ");
                        pv.setStock(sc.nextInt());
                        ps.updateProduct(pv);
                        System.out.println("제품 수정이 완료되었습니다.");
                    } else {
                        System.out.println("제품을 찾을 수 없습니다.");
                    }
                    break;
                case 3:
                    // 제품 삭제
                    System.out.println("삭제할 제품 ID : ");
                    int deleteId = sc.nextInt();
                    ps.deleteProduct(deleteId);
                    System.out.println("제품 삭제가 완료되었습니다.");
                    break;
                case 4:
                    // 제품 목록
                    showProduct();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private static void showProduct() {
        System.out.println("=== 전체 제품 목록 ===");
        for (ProductVO pvo : ps.listProducts()) {
            System.out.println(pvo);
        }
    }

    private static void manageMembers() {
        while (true) {
            System.out.println("=== 회원 관리 ===");
            System.out.println("1.회원추가 2.회원수정 3.회원삭제 4.회원목록 5.돌아가기");
            int input = sc.nextInt();

            switch (input) {
                case 1:
                    // 회원 추가
                    try {
                        addMember();
                    } catch (MyException e) {
                        System.out.println("회원추가 오류 : " + e.getMessage());
                    }
                    System.out.println("회원 추가가 완료되었습니다.");
                    break;
                case 2:
                    // 회원 수정
                    showMember();
                    System.out.println("수정할 회원 ID : ");
                    int updateId = sc.nextInt();
                    MemberVO mv = ms.getMember(updateId);
                    if (mv != null) {
                        System.out.println("새 아이디 : ");
                        mv.setId(sc.next());
                        System.out.println("새 비밀번호 : ");
                        mv.setPw(sc.next());
                        System.out.println("새 회원명 : ");
                        mv.setName(sc.next());
                        System.out.println("새 전화번호 : ");
                        mv.setPhone(sc.next());
                        System.out.println("새 보유금액 : ");
                        mv.setMoney(sc.nextInt());
                        System.out.println("새 카드번호 : ");
                        mv.setCardNo(sc.next());
                        ms.updateMember(mv);
                        System.out.println("회원 수정이 완료되었습니다.");
                    } else {
                        System.out.println("회원을 찾을 수 없습니다.");
                    }
                    break;
                case 3:
                    System.out.println("삭제할 회원 ID : ");
                    int deleteId = sc.nextInt();
                    ms.deleteMember(deleteId);
                    System.out.println("회원 삭제가 완료되었습니다.");
                    break;
                case 4:
                    showMember();
                    break;
                case 5:
                    return;
            }
        }
    }

    private static void showMember() {
        System.out.println("=== 회원 목록 ===");
        for (MemberVO m : ms.listMembers()) {
            System.out.println(m);
        }
    }

    private static void manageSales() {
        while (true) {
            System.out.println("=== 판매 관리 ===");
            System.out.println("1.제품별 판매 현황 2.회원별 판매 현황");
        }
    }

    private static void showUserMenu() {
        System.out.println("=== 사용자 메뉴 ===");
        System.out.println("1.금액충전 2.제품구매 3. 보유금액조회 4.로그아웃");
        int input = sc.nextInt();

        switch (input) {
            case 1:
                chargeMoney();
                break;
            case 2:
                buyProduct();
                break;
            case 3:
                showMoney();
                break;
            case 4:
                loginMember = null;
                System.out.println("로그아웃 되었습니다.");
                return;
            default:
                System.out.println("잘못된 입력입니다.");
        }
    }

    private static void chargeMoney() {
        while (true) {
            try {
                System.out.println("충전금액(1000원 단위)");
                int money = sc.nextInt();
                ms.charge(loginMember.getMId(), money);
                // 최신 금액 다시 조회
                showMoney();
                break;
            } catch (MyException e) {
                System.out.println("입력오류 : " + e.getMessage());
            }
        }
    }

    private static void buyProduct() {
        try {
            System.out.println("구매할 제품 ID 입력: ");
            int productID = sc.nextInt();
            ProductVO product = ps.getProduct(productID);
            if (product == null) {
                System.out.println("제품이 존재하지 않습니다.");
                return;
            }
            if (loginMember.getMoney() < product.getPrice()) {
                System.out.println("잔액이 부족합니다.");
                return;
            }
            // 제품 재고 차감
            ps.buyProduct(productID);
            // 잔액 차감
            loginMember.setMoney(loginMember.getMoney() - product.getPrice());
            ms.updateMember(loginMember);
            // 최신 잔액 다시 조회
            System.out.println(product.getProduct() + "구매 완료" + "\n");
            showMoney();
        } catch (MyException e) {
            System.out.println("구매 실패 : " + e.getMessage());
        }
    }

    private static void showMoney() {
        System.out.println("현재 보유 금액 : " + loginMember.getMoney() + "원");
    }


    private static boolean isAdmin(MemberVO member) {
        return "admin".equals(member.getId());
    }
}
