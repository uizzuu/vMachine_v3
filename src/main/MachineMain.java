package main;

import db.DBConn;
import exception.MyException;
import service.MemberService;
import service.ProductService;
import service.SaleService;
import vo.MemberVO;
import vo.ProductVO;
import vo.SaleVO;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MachineMain {

    private static MemberService ms = new MemberService();
    private static ProductService ps = new ProductService();
    private static SaleService ss = new SaleService();

    private static Scanner sc = new Scanner(System.in);

    private static MemberVO loginMember = null;

    public static void main(String[] args) throws MyException {
        // DB연결
        Connection conn = DBConn.getConnection();

        while (true) {
            if (loginMember == null) {
                if (!showLoginMenu()) {
                    break;
                }
            } else {
                showUserMenu();
            }
        }
    }

    private static boolean showLoginMenu() throws MyException {
            System.out.println("\n=== 자판기(회원제) === ");
            System.out.println("1.회원가입 2.로그인 3.종료 0.관리자");
            int input = -1;
        try {
            input = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
            sc.nextLine();
            return true;
        }
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
                    return false;
                default:
                    System.out.println("잘못된 입력입니다.");
                    break;
            }
            return true;
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
        String id = sc.nextLine();
        System.out.println("비밀번호 : ");
        String pw = sc.nextLine();
        System.out.println("회원명 : ");
        String name = sc.nextLine();
        System.out.println("전화번호 : ");
        String phone = sc.nextLine();
        String cardNo;
        while (true) {
            System.out.println("카드번호 : ");
            String input = sc.nextLine();
            cardNo = input.replaceAll("[-\\s.]", "");
            if (checkCardNo(cardNo)) {
                break;
            } else {
                throw new MyException("유효하지 않은 카드 번호입니다. 다시 입력해주세요.");
            }
        }
        int mid = ms.generateMemberId();
        // 초기 충전 0
        MemberVO member = new MemberVO(mid, id, pw, name, phone, 0, cardNo);
        ms.signup(member);
    }

    public static boolean checkCardNo(String cardNo) {
        // 16자리인지 확인
        if (cardNo == null || cardNo.length() != 16) {
            return false;
        }
        // 숫자만 있는지 확인
        for (int i = 0; i < 16; i++) {
            if (!Character.isDigit(cardNo.charAt(i))) {
                return false;
            }
        }
        // 오른쪽부터 숫자 정렬
        int sum = 0;
        for (int i = 0; i < 16; i++) {
            int digit = cardNo.charAt(15 - i) - '0';
            // 짝수번째자리라면
            if (i % 2 == 1) {
                digit *= 2;
                // 10보다 크다면
                if (digit > 9) {
                    digit -= 9;
                }
            }
            // 변환된 숫자 전부 더하기
            sum += digit;
        }
        // 총합이 10의 배수이면 유효
        return (sum % 10 == 0);
    }

    private static void login() {
        try {
            System.out.println("아이디 : ");
            String id = sc.nextLine();
            System.out.println("비밀번호 : ");
            String pw = sc.nextLine();
            loginMember = ms.login(id, pw);
            System.out.println(loginMember.getName() + "님 환영합니다.");
            showUserMenu();
        } catch (MyException e) {
            System.out.println("로그인 실패 : " + e.getMessage());
        }
    }

    private static void showAdminMenu() throws MyException {
        while (true) {
            System.out.println("\n=== 관리자(admin) 메뉴 ===");
            System.out.println("1.자판기관리 2.회원관리 3.판매관리 4.로그아웃");
            int input = -1;
            try {
                input = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                sc.nextLine();
                continue;
            }
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
                    break;
            }
        }
    }

    private static void manageVmachine() {
        while (true) {
            System.out.println("\n=== 자판기 관리 ===");
            System.out.println("1.제품추가 2.제품수정 3.제품삭제 4.제품목록 5.돌아가기");
            int input = -1;
            try {
                input = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                sc.nextLine();
                continue;
            }

            switch (input) {
                case 1:
                    // 제품 추가
                    System.out.println("\n=== 제품 추가 ===");
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
                    break;
            }
        }
    }

    private static void showProduct() {
        System.out.println("\n=== 전체 제품 목록 ===");
        for (ProductVO pvo : ps.listProducts()) {
            System.out.println(pvo);
        }
    }

    private static void manageMembers() throws MyException {
        while (true) {
            System.out.println("\n=== 회원 관리 ===");
            System.out.println("1.회원추가 2.회원수정 3.회원삭제 4.회원목록 5.돌아가기");
            int input = -1;
            try {
                input = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                sc.nextLine();
                continue;
            }

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
                    int updateId = -1;
                    try {
                        updateId = sc.nextInt();
                        sc.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("잘못된 회원 ID입니다.");
                        sc.nextLine();
                        break;
                    }
                    MemberVO mv = ms.getMember(updateId);
                    if (mv != null) {
                        System.out.println("새 아이디 : ");
                        mv.setId(sc.nextLine());
                        System.out.println("새 비밀번호 : ");
                        mv.setPw(sc.nextLine());
                        System.out.println("새 회원명 : ");
                        mv.setName(sc.nextLine());
                        System.out.println("새 전화번호 : ");
                        mv.setPhone(sc.nextLine());
                        System.out.println("새 보유금액 : ");
                        try {
                            mv.setMoney(sc.nextInt());
                            sc.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("잘못된 보유금액 입력입니다.");
                            sc.nextLine();
                            break;
                        }
                        System.out.println("새 카드번호 : ");
                        String newCardNo;
                        // 카드 번호 유효성 검사
                        while(true) {
                            newCardNo = sc.nextLine();
                            String cleanedCardNo = newCardNo.replaceAll("[-\\s.]", "");
                            if(checkCardNo(cleanedCardNo)) {
                                mv.setCardNo(cleanedCardNo);
                                break;
                            } else {
                                System.out.println("유효하지 않은 카드 번호입니다. 다시 입력해주세요.");
                            }
                        }
                        ms.updateMember(mv);
                        System.out.println("회원 수정이 완료되었습니다.");
                    } else {
                        System.out.println("회원을 찾을 수 없습니다.");
                    }
                    break;
                case 3:
                    showMember();
                    System.out.println("삭제할 회원 ID : ");
                    int deleteId = -1;
                    try {
                        deleteId = sc.nextInt();
                        sc.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("잘못된 회원 ID입니다.");
                        sc.nextLine();
                        break;
                    }
                    ms.deleteMember(deleteId);
                    System.out.println("회원 삭제가 완료되었습니다.");
                    break;
                case 4:
                    showMember();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
                    break;
            }
        }
    }

    private static void showMember() {
        System.out.println("\n=== 회원 목록 ===");
        List<MemberVO> members = ms.listMembers();
        if (members.isEmpty()) {
            System.out.println("등록된 회원이 없습니다.");
            return;
        }
        for (MemberVO m : members) {
            System.out.println(m);
        }
    }

    private static void manageSales() {
        while (true) {
            System.out.println("\n=== 판매 관리 ===");
            System.out.println("1.제품별 판매 현황 2.회원별 판매 현황 3.돌아가기");
            int input = -1;
            try {
                input = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                sc.nextLine();
                continue;
            }
            switch (input) {
                case 1:
                    // 제품별 판매 현황 출력
                    List<String[]> productStats = ss.getSalesByProduct();
                    // System.out.println("조회된 제품 판매 수: " + productStats.size());
                    System.out.println("\n[제품별 판매 현황]");
                    System.out.printf("%-1s | %-1s | %-1s\n", "제품명", "판매수량", "판매금액");
                    System.out.println("------------------------------------");
                    if (productStats.isEmpty()) {
                        System.out.println("판매된 제품이 없습니다.");
                    } else {
                        for (String[] row : productStats) {
                            System.out.printf("%-10s | %-10s | %-10s\n", row[0], row[1], row[2]);
                        }
                    }
                    break;
                case 2:
                    // 회원별 판매 현황 출력
                    List<String[]> memberStats = ss.getSalesByMember();
                    // System.out.println("조회된 제품 판매 수: " + memberStats.size());
                    System.out.println("\n[회원별 판매 현황]");
                    System.out.printf("%-1s | %-1s | %-1s | %-1s\n", "회원ID", "회원명", "구매금액", "충전잔액");
                    System.out.println("----------------------------------------------");
                    if (memberStats.isEmpty()) {
                        System.out.println("구매 기록이 있는 회원이 없습니다.");
                    } else {
                        for (String[] row : memberStats) {
                            System.out.printf("%-10s | %-10s | %-10s | %-10s\n", row[0], row[1], row[2], row[3]);
                        }
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
                    break;
            }
        }
    }

    private static void showUserMenu() {
        while (true) {
            System.out.println("=== 사용자 메뉴 ===");
            System.out.println("1.금액충전 2.제품구매 3. 보유금액조회 4.로그아웃");
            int input = -1;
            try {
                input = sc.nextInt();
                sc.nextLine(); // !!! nextInt() 뒤에 줄바꿈 문자 소비
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                sc.nextLine();
                continue;
            }
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
                    break;
            }
        }
    }

    private static void chargeMoney() {
        while (true) {
            try {
                System.out.println("충전금액(1000원 단위)");
                int money = sc.nextInt();
                sc.nextLine();
                // 1000원 단위 검사
                if (money <= 0 || money % 1000 != 0) {
                    throw new MyException("충전 금액은 1000원 단위 양수로 입력해주세요.");
                }
                ms.charge(loginMember.getMId(), money);
                // 최신 금액 다시 조회
                loginMember = ms.getMember(loginMember.getMId());
                showMoney();
                // 충전 완료 메시지
                System.out.println(money + "원 충전이 완료되었습니다.");
                break;
            } catch (InputMismatchException e) {
                System.out.println("잘못 입력하셨습니다. 숫자를 입력하세요.");
            } catch (MyException e) {
                System.out.println("충전 실패 : " + e.getMessage());
            }
        }
    }

    private static void buyProduct() {
        try {
            showProduct();
            System.out.println("구매할 제품 ID 입력: ");
            int productID = -1;
            try {
                productID = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("잘못된 제품 ID입니다.");
                sc.nextLine();
                return;
            }
            ProductVO product = ps.getProduct(productID);
            if (product == null || product.getStock() < 1) {
                System.out.println("제품이 존재하지 않거나 재고가 없습니다.");
                return;
            }
            if (loginMember.getMoney() < product.getPrice()) {
                System.out.println("잔액이 부족합니다.");
                System.out.println("현재 잔액 : " + loginMember.getMoney());
                return;
            }
            // 제품 재고 차감
            ps.buyProduct(productID);
            // 잔액 차감
            loginMember.setMoney(loginMember.getMoney() - product.getPrice());
            ms.updateMember(loginMember);
            // 판매기록 저장
            int sid = ss.generateSaleId();
            SaleVO sale = new SaleVO(sid, loginMember.getMId(), productID, 1, product.getPrice(), null);
            ss.recordSale(sale);
            // 최신 잔액 다시 조회
            System.out.println(product.getProduct() + "구매 완료" + "\n");
            showMoney();
        } catch (MyException e) {
            System.out.println("구매 실패 : " + e.getMessage());
        }
    }

    private static void showMoney() {
        loginMember = ms.getMember(loginMember.getMId());
        System.out.println("현재 보유 금액 : " + loginMember.getMoney() + "원");
    }


    private static boolean isAdmin(MemberVO member) {
        return "admin".equals(member.getId());
    }
}
