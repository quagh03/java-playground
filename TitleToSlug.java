import java.text.Normalizer;

public class TitleToSlug {
    public static void main(String[] args) {
        String[] titles = {
                "Đó hàm kiểm tra dữ liệu",
                "Đường dẫn tới tài nguyên",
                "Điều hướng trong ứng dụng",
                "Đăng nhập vào hệ thống",
                "Đăng ký tài khoản mới",
                "Đặt lại mật khẩu",
                "Đánh giá sản phẩm",
                "Đặt hàng trực tuyến",
                "Điều khoản sử dụng",
                "Quan hệ công chúng",
                "@@@@",
                "Địa chỉ liên hệ"
        };

        for (String title : titles) {
            String slug = convertToSlug(title);
            System.out.println(slug);
        }

    }

    public static String convertToSlug(String title) {
        String slug = title.toLowerCase();
        slug = slug.replace("đ", "d");
        slug = slug.replace("Đ", "D");
        slug = Normalizer.normalize(slug, Normalizer.Form.NFD);
        slug = slug.replaceAll("[^\\p{ASCII}]", "");
        slug = slug.replaceAll("[`~!@#|$%^&*()=,./<>?'\":;_]", "");
        slug = slug.replaceAll(" ", "-");
        slug = slug.replaceAll("-{2,}", "-");
        slug = "@" + slug + "@";
        slug = slug.replaceAll("@-|-@|@", "");
        return slug;
    }
}