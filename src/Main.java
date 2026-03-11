import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("===== HỆ THỐNG QUẢN LÝ CỬA HÀNG ĐỒ ĂN NHANH =====");
            System.out.println("1. Quản lý danh mục món ăn");
            System.out.println("2. Tạo và quản lý đơn hàng");
            System.out.println("3. Thống kê doanh thu & Tìm kiếm");
            System.out.println("4. Thoát");
            System.out.print("Chọn chức năng (1-4): ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    showMenuManagement();
                    break;
                case 2:
                    showOrderManagement();
                    break;
                case 3:
                    showStatisticsMenu();
                    break;
                case 4:
                    System.out.println("Đang thoát hệ thống...");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (choice != 4);
    }

    private static void showMenuManagement() {
        System.out.println("--- Chức năng Quản lý món ăn ---");
    }

    private static void showOrderManagement() {
        System.out.println("--- Chức năng Quản lý đơn hàng ---");
    }

    private static void showStatisticsMenu() {
        System.out.println("--- Chức năng Thống kê ---");
    }
}