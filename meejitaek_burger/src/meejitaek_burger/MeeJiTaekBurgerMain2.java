package meejitaek_burger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class MeeJiTaekBurgerMain2 {
	public static Scanner sc = new Scanner(System.in);

	static ArrayList<Burger> burgerInfoList = new ArrayList<>();
	static ArrayList<Side> sideInfoList = new ArrayList<>();
	static ArrayList<MyBurger> myBurgers = new ArrayList<>();
	static ArrayList<MySide> mySides = new ArrayList<>();
	static int burgerOrderNum;
	static int sideOrderNum;

	public static void main(String[] args) {

		int menuSelect = 0; // 메뉴번호 선택

		boolean quit = false;

		while (!quit) {
			mainMenuPrint();
			System.out.print("메뉴를 선택 하세요 : ");
			menuSelect = sc.nextInt();
			sc.nextLine();

			if (menuSelect < 1 || menuSelect > 9) {
				System.out.println("1~10까지의 숫자를 입력하세요.");
			} else {
				switch (menuSelect) {
				case 1: // 햄버거 선택
					chooseBurger(burgerInfoList);
					burgerInfoList = new ArrayList<Burger>();
					break;
				case 2: // 햄버거 삭제
					removeBurger();
					break;
				case 3: // 사이드 선택
					chooseSide(sideInfoList);
					sideInfoList = new ArrayList<Side>();
					break;
				case 4: // 사이드 삭제
					removeSide();
					break;
				case 5: // 선택 상품 목록 보기
					selectProductList();
					break;
				case 6: // 전체 비우기
					productListClear();
					break;
				case 7: // 영수증 표시하기
					menuBill();
					break;
				case 8: // 종료
					System.out.println("메뉴주문을 종료합니다. 감사합니다.");
					quit = true;
					break;
				case 9: // 관리자 로그인
					adminLogin();
					break;
				}
			}

		} // end of while

		System.out.println("The end");

	}

	// 관리자 로그인
	// 추가 1 : 아이디 비번 3번까지 잘못 입력시 초기화면으로 돌아가기
	// 추가 2 : 메뉴 저장/취소여부 묻기
	// 추가 3 : 메뉴 삭제하기
	public static void adminLogin() {
		Admin admin = new Admin();

		System.out.println("관리자 정보를 입력하세요.");
		System.out.print("아이디 : ");
		String adminId = sc.nextLine();
		System.out.print("비밀번호 : ");
		String adminPw = sc.nextLine();

		if (admin.getId().equals(adminId) && admin.getPassword().equals(adminPw)) {

			String[] writeBurger = new String[4];
			String[] writeSide = new String[3];
			boolean regiFlag = false;

			while (!regiFlag) {

				System.out.println("1. 버거상품등록   2. 사이드상품등록  3. 종료");
				int menu = sc.nextInt();
				sc.nextLine();
				switch (menu) {
				case 1:
					registerBurger(writeBurger);
					break;
				case 2:
					registerSide(writeSide);
					break;
				case 3:
					System.out.println("수고하셨습니다. 관리를 종료합니다.");
					regiFlag = true;
					break;
				default:
					System.out.println("잘못선택하셨습니다.");
					break;
				}
			}
		} else {
			System.out.println("아이디 또는 비밀번호가 잘못 입력되어 초기화면으로 돌아갑니다.");
		}
	}

	// 새로운 사이드메뉴를 등록하여 파일에 저장
	public static void registerSide(String[] writeSide) {
		System.out.print("사이드상품 코드 : ");
		String bgCode = sc.nextLine();
		writeSide[0] = bgCode;
		System.out.print("사이드상품 이름 : ");
		String bgName = sc.nextLine();
		writeSide[1] = bgName;
		System.out.print("사이드상품 가격 : ");
		String bgPrice = sc.nextLine();
		writeSide[2] = bgPrice;

		try (FileWriter fw = new FileWriter("side.txt", true)) {
			for (int i = 0; i < 3; i++) {
				fw.write(writeSide[i] + "\n");
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	// 새로운 버거를 등록하여 파일에 저장
	public static void registerBurger(String[] writeBurger) {
		System.out.print("버거 코드 : ");
		String bgCode = sc.nextLine();
		writeBurger[0] = bgCode;
		System.out.print("버거 이름 : ");
		String bgName = sc.nextLine();
		writeBurger[1] = bgName;
		System.out.print("버거 가격 : ");
		String bgPrice = sc.nextLine();
		writeBurger[2] = bgPrice;
		System.out.print("버거 정보 : ");
		String bgInfo = sc.nextLine();
		writeBurger[3] = bgInfo;

		try (FileWriter fw = new FileWriter("burger.txt", true)) {
			for (int i = 0; i < 4; i++) {
				fw.write(writeBurger[i] + "\n");
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	// 영수증 표시하는 메소드
	public static void menuBill() {
		LocalDate date = LocalDate.now();
		LocalTime now = LocalTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm");
		String formDate = date.format(dtf);
		String formNow = now.format(dtf2);

		System.out.println();
		System.out.println("    ==|| MeeJiTaek Burger ||==");
		System.out.println("========================================= ");

		if ((myBurgers.isEmpty() && mySides.isEmpty())) {
			System.out.println("---------------------------------------");
			System.out.println("   >>고객님 주문항목이 존재하지 않습니다.<<");
			System.out.println();
			System.out.println("                " + formDate + " " + formNow);
			System.out.println("---------------------------------------");
			System.out.println();

		} else {

			if (!myBurgers.isEmpty()) {
				System.out.println(">>>> 버거 <<<<");
				System.out.println("---------------------------------------");
				myBurgers.forEach(System.out::println);
				System.out.println("---------------------------------------");
				System.out.println();
			}
			if (!mySides.isEmpty()) {
				System.out.println();
				System.out.println(">>>> 사이드 <<<<");
				System.out.println("---------------------------------------");
				mySides.forEach(System.out::println);
				System.out.println("---------------------------------------");
				System.out.println();
			}

			int totalPrice = 0;
			for (MyBurger data : myBurgers) {
				totalPrice += data.getPrice();
			}
			for (MySide data : mySides) {
				totalPrice += data.getPrice();
			}
			System.out.println("========================================= ");
			System.out.println(formDate + " " + formNow + "     계산할 총 금액 : " + totalPrice);
			System.out.println();
			System.out.println("                                  ♡감사합니다.♡\n");
		}
	}

	// 선택메뉴 모두 제거하기
	public static void productListClear() {
		if (myBurgers.isEmpty() && mySides.isEmpty()) {
			System.out.println("삭제할 항목이 없습니다.");
		} else {
			System.out.print("모든 메뉴를 삭제하겠습니까? Y | N : ");
			String str = sc.nextLine();
			if (str.toUpperCase().equals("Y")) {
				myBurgers.clear();
				mySides.clear();
				System.out.println("모든 메뉴를 삭제했습니다.");
			}
		}
	}

	// 사이드메뉴 삭제 메소드
	public static void removeSide() {
		if (mySides.isEmpty()) {
			System.out.println(">>> 삭제할 사이드 메뉴가 없습니다. <<<");
			System.out.println();
		} else {
			System.out.println(">>>> 고객님께서 선택한 사이드 메뉴 목록입니다. <<<<");
			mySides.forEach(System.out::println);

			boolean exitFlag = false;
			while (!exitFlag) {
				System.out.print("\n삭제할 주문 번호를 입력하세요 : ");
				int num = sc.nextInt();
				sc.nextLine();
				int delNum = 0;
				boolean flag = false;

				for (int i = 0; i < mySides.size(); i++) {
					if (myBurgers.get(i).getOrderNum() == num) {
						delNum = i;
						flag = true;
						break;
					}
				}

				if (flag) {
					System.out.print(mySides.get(delNum).getOrderNum() + "번 메뉴를 삭제하겠습니까? Y | N ");
					String str = sc.nextLine();
					if (str.toUpperCase().equals("Y")) {
						System.out.println(mySides.get(delNum).getOrderNum() + ". " + mySides.get(delNum).getSdName()
								+ "메뉴가 선택목록에서 삭제되었습니다.\n");
						mySides.remove(delNum);
						System.out.println(">>>> 고객님께서 선택한 사이드 메뉴 목록입니다. <<<<");
						mySides.forEach(System.out::println);
					}
					exitFlag = true;
				} else {
					System.out.println("주문번호를 잘못 입력하였습니다. 다시 입력해 주세요.");
				}
			} // end of while
		}
	}
	// 사이드메뉴 선택메소드
	public static void chooseSide(ArrayList<Side> sideInfoList) {
		// sideList(sideInfoList); 라인삭제예정
		setFileToSideList(sideInfoList);
		sideInfoList.forEach(System.out::println);
		boolean quit = false;
		MySide mySide = null;

		while (!quit) {
			System.out.print("주문할 사이드의 코드를 선택하세요.(뒤로가기 -1) : ");
			int inputCode = sc.nextInt();
			sc.nextLine();
			if (inputCode == -1) {
				System.out.println();
				return;
			}
			boolean codeFlag = false; // 주문 상품 일치 여부
			int inNum = -1;

			for (int i = 0; i < sideInfoList.size(); i++) {
				if (inputCode == sideInfoList.get(i).getCode()) {
					inNum = i;
					codeFlag = true;
					break;
				}
			}

			// 코드 일치할때 주문 목록에 추가 여부 선택
			if (codeFlag) {
				System.out.print("선택한 상품을 주문 목록에 담겠습니까? Y | N : ");
				sideOrderNum++;
				String inputStr = sc.nextLine();
				if (inputStr.toUpperCase().equals("Y")) {

					mySide = new MySide(sideOrderNum, sideInfoList.get(inNum).getCode(),
							sideInfoList.get(inNum).getSdName(), sideInfoList.get(inNum).getPrice());

					System.out.println(sideInfoList.get(inNum).getSdName() + "가 주문목록에 추가되었습니다.");

					mySides.add(mySide);
				}
				quit = true;
			} else {
				System.out.println("상품선택을 잘못했습니다. 다시 입력해주세요.");
			}
		} // end of while
	}

	// 사이드 정보를 파일에서 읽고 ArrayList에 저장하는 메소드
	public static void setFileToSideList(ArrayList<Side> sideInfoList) {
		try (BufferedReader reader = new BufferedReader(new FileReader("side.txt"))) {
			String sideCode = null;
			String[] readSide = new String[3];

			while ((sideCode = reader.readLine()) != null) {
				readSide[0] = sideCode;
				readSide[1] = reader.readLine();
				readSide[2] = reader.readLine();

				Side side = new Side(Integer.parseInt(readSide[0]), readSide[1], Integer.parseInt(readSide[2]));
				sideInfoList.add(side);
			} // end of while
		} catch (FileNotFoundException e) {
			System.out.println("파일을 찾지 못했습니다. " + e.getMessage());

		} catch (Exception e) {
			System.out.println("오류 발생 " + e.getMessage());
		}
	}

	// 선택 상품목록 표시
	public static void selectProductList() {
		if ((myBurgers.isEmpty() && mySides.isEmpty())) {
			System.out.println("---------------------------------------");
			System.out.println(">>고객님 주문항목이 존재하지 않습니다.<<");
			System.out.println("---------------------------------------");
			System.out.println();
		}

		if (!myBurgers.isEmpty()) {
			System.out.println(">>>> 고객님께서 선택하신 버거 목록입니다. <<<<");
			System.out.println("---------------------------------------");
			myBurgers.forEach(System.out::println);
			System.out.println("---------------------------------------");
			System.out.println();
		}
		if (!mySides.isEmpty()) {
			System.out.println();
			System.out.println(">>>> 고객님께서 선택하신 사이드 목록입니다. <<<<");
			System.out.println("---------------------------------------");
			mySides.forEach(System.out::println);
			System.out.println("---------------------------------------");
			System.out.println();
		}
	}

	//선택 버거삭제
	public static void removeBurger() {
		if (myBurgers.isEmpty()) {
			System.out.println(">>> 삭제할 버거가 없습니다. <<<");
			System.out.println();
		} else {
			System.out.println(">>>> 고객님께서 선택한 버거 목록입니다. <<<<");
			myBurgers.forEach(System.out::println);

			boolean exitFalg = false;
			while (!exitFalg) {
				System.out.print("\n삭제할 주문 번호를 입력하세요. : ");
				int num = sc.nextInt();
				sc.nextLine();
				int delNum = 0;
				boolean flag = false;

				for (int i = 0; i < myBurgers.size(); i++) {
					if (myBurgers.get(i).getOrderNum() == num) {
						delNum = i;
						flag = true;
						break;
					}
				}

				if (flag) {
					System.out.print(myBurgers.get(delNum).getOrderNum() + "번 버거를 삭제하겠습니까? Y | N ");
					String str = sc.nextLine();
					if (str.toUpperCase().equals("Y")) {
						System.out.println(myBurgers.get(delNum).getOrderNum() + ". "
								+ myBurgers.get(delNum).getBgName() + "버거가 선택목록에서 삭제되었습니다.\n");
						myBurgers.remove(delNum);
						System.out.println(">>>> 고객님께서 선택한 버거 목록입니다. <<<<");
						myBurgers.forEach(System.out::println);
					}
					exitFalg = true;

				} else {
					System.out.println("주문번호를 잘못 입력하였습니다. 다시 입력해 주세요.");
				}

			} // end of while
		}
	}

	//버거 메뉴 고르기(선택하기)
	public static void chooseBurger(ArrayList<Burger> burgerInfoList) {
		setFileToBurgerList(burgerInfoList);
		burgerInfoList.forEach(System.out::println);
		boolean quit = false;
		MyBurger myBurger = null;

		while (!quit) {
			System.out.print("주문할 버거의 코드를 선택하세요(뒤로가기 -1) : ");
			int inputCode = sc.nextInt();
			sc.nextLine();
			if (inputCode == -1) {
				System.out.println();
				return;
			}

			boolean codeFlag = false; // 주문 상품 일치 여부
			int inNum = -1;

			for (int i = 0; i < burgerInfoList.size(); i++) {
				if (inputCode == burgerInfoList.get(i).getCode()) {
					inNum = i;
					codeFlag = true;
					break;
				}
			}

			// 일치할때 주문목록에 추가 여부 선택
			if (codeFlag) {
				myBurger = new MyBurger(burgerOrderNum, burgerInfoList.get(inNum).getCode(),
						burgerInfoList.get(inNum).getBgName(), burgerInfoList.get(inNum).getPrice(),
						burgerInfoList.get(inNum).getInfo());

				System.out.println("추가 옵션을 선택하세요.");
				System.out.print("1.버거 사이즈업   2. 세트메뉴   3. 세트메뉴 + 버거사이즈업   4. 선택안함 : ");
				int subMenu = sc.nextInt();
				sc.nextLine();
				switch (subMenu) {
				case 1:
					myBurger.sizeUp();
					break;
				case 2:
					myBurger.set();
					break;
				case 3:
					myBurger.sizeUpNSet();
					break;
				case 4:
					break;
				default:
					System.out.println("잘못 입력하였습니다. 다시 선택하세요. ");
				}

				System.out.print("선택한 상품을 주문목록에 담겠습니까? Y | N : ");
				String inputStr = sc.nextLine();
				if (inputStr.toUpperCase().equals("Y")) {
					burgerOrderNum++;
					myBurger.setOrderNum(burgerOrderNum);
					System.out.println(burgerInfoList.get(inNum).getBgName() + "가 주문목록에 추가되었습니다. ");
					myBurgers.add(myBurger); // 주문목록에 쌓아두기
				}

				quit = true;
			} else {
				System.out.println("상품선택을 잘못했습니다. 다시 입력해주세요.");
			}
		} // end of while
	}

	// 버거 정보를 파일에서 읽고 ArrayList에 저장하는 메소드
	public static void setFileToBurgerList(ArrayList<Burger> burgerInfoList) {
		// BufferedReader reader = null;
		try (BufferedReader reader = new BufferedReader(new FileReader("burger.txt"))) {
			String bugCode = null;
			String[] readBurger = new String[4];

			while ((bugCode = reader.readLine()) != null) {
				readBurger[0] = bugCode;
				readBurger[1] = reader.readLine();
				readBurger[2] = reader.readLine();
				readBurger[3] = reader.readLine();
				Burger burger = new Burger(Integer.parseInt(readBurger[0]), readBurger[1],
						Integer.parseInt(readBurger[2]), readBurger[3]);
				burgerInfoList.add(burger);
			}
		} catch (FileNotFoundException e) {
			System.out.println("파일을 찾지 못했습니다. " + e.getMessage());
		} catch (Exception e) {
			System.out.println("오류 발생" + e.getMessage());
		}
	}

	// 메인메뉴를 출력하는 메소드
	public static void mainMenuPrint() {

		System.out.print("");
		System.out.println("********************************************");
		System.out.println("       \tMeeJiTaek Burger Menu");
		System.out.println("********************************************");
		System.out.println("    1. 햄버거 선택        \t2. 햄버거 삭제");
		System.out.println("    3. 사이드 추가        \t4. 사이드 삭제");
		System.out.println("    5. 선택 상품 목록     \t6. 전체 비우기");
		System.out.println("    7. 영수증 표시하기     \t8. 종료");
		System.out.println("    9. 관리자 로그인");
		System.out.println("********************************************");

	}

}
