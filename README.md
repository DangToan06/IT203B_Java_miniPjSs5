# DỰ ÁN: QUẢN LÝ CỬA HÀNG ĐỒ ĂN NHANH (FAST FOOD MANAGEMENT)

## I. THÔNG TIN CHUNG
- **Nhóm trưởng:** Đặng Quốc Toàn
- **Số lượng thành viên:** 10
- **Ngôn ngữ:** Java 8+
- **Kiến trúc:** Phân lớp (Model - Repository - Service - Controller)

---

## II. CẤU TRÚC THƯ MỤC DỰ ÁN
Mọi người tuân thủ cấu trúc package này để khi merge code không bị lỗi.

```text
src/main/java/com/fastfood/
│
├── model/                # Thực thể (Entities) - Thành viên 2, 3, 4
│   ├── MenuItem.java     (Abstract class)
│   ├── Food.java         (Kế thừa MenuItem)
│   ├── Drink.java        (Kế thừa MenuItem)
│   ├── Order.java        (Đơn hàng)
│   └── OrderItem.java    (Chi tiết món trong đơn)
│
├── repository/           # Lưu trữ dữ liệu - Thành viên 5
│   ├── MenuRepository.java
│   └── OrderRepository.java
│
├── service/              # Logic nghiệp vụ (Stream API) - Thành viên 6, 7, 8
│   ├── MenuService.java
│   ├── OrderService.java
│   └── StatisticsService.java
│
├── exception/            # Xử lý lỗi - Thành viên 9
│   ├── InsufficientStockException.java
│   └── InvalidOrderIdException.java
│
├── util/                 # Công cụ bổ trợ - Thành viên 9
│   └── Validator.java
│
└── Main.java             # Menu điều khiển - NHÓM TRƯỞNG (TOÀN)

src/test/java/com/fastfood/ # Thư mục chứa Unit Test (JUnit 5) ( Ai làm phần nào thì ghi test luôn )
```
## III. PHÂN CÔNG NHIỆM VỤ

| STT | Thành viên    | Nhiệm vụ chính   | Chi tiết                                                                         |
|:----|:--------------|:-----------------|:---------------------------------------------------------------------------------|
| 01  | Toàn (Leader) | Kiến trúc & Menu | Tạo cấu trúc Project, viết file Main.java điều hướng, Review và Merge code.      |
| 02  |               | Model: Food      | Tạo class MenuItem (abstract) và Food. Thuộc tính private + Getter/Setter.       | 
| 03  |               | Model: Drink     | Tạo class Drink. Override calculatePrice() dựa trên size (S, M, L).              | 
| 04  |               | Model: Order     | Tạo Order và OrderItem. Quản lý trạng thái đơn hàng (Enum: PENDING, PAID...).    | 
| 05  |               | Repository       | Tạo MenuRepo và OrderRepo bằng ArrayList. Cung cấp phương thức lấy/lưu data.     | 
| 06  |               | Service: Menu    | Viết hàm CRUD món ăn. Yêu cầu: Dùng Stream API lọc món theo giá/tên.             | 
| 07  |               | Service: Order   | Viết hàm tạo đơn, tính tổng tiền. Dùng Optional để xử lý khi không tìm thấy đơn. | 
| 08  |               | Service: Stats   | Thống kê doanh thu, tìm món bán chạy nhất. Yêu cầu: Dùng stream().collect().     | 
| 09  |               | Exception & Val  | Tạo các Custom Exception và class Validator kiểm tra dữ liệu đầu vào.            | 
| 10  |               | Unit Test        | Viết Test Case cho logic tính tiền và các hàm quan trọng bằng JUnit 5.           | 


