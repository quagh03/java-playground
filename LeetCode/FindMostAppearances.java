import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindMostAppearances {
    public static List<Integer> findMostAppearances(int[] nums) {
        // Kiểm tra nếu mảng đầu vào trống
        if (nums.length == 0) {
            return new ArrayList<>();
        }

        // Map để lưu số lần xuất hiện của mỗi số
        Map<Integer, Integer> countMap = new HashMap<>();

        // Duyệt qua mảng để đếm số lần xuất hiện của mỗi số
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        // Tìm số lần xuất hiện tối đa
        int maxCount = 0;
        for (int count : countMap.values()) {
            maxCount = Math.max(maxCount, count);
        }

        // Danh sách để lưu số có số lần xuất hiện tối đa
        List<Integer> mostAppearances = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == maxCount) {
                mostAppearances.add(entry.getKey());
            }
        }

        return mostAppearances;
    }

    public static void main(String[] args) {
        // Ví dụ sử dụng:
        int[] inputArray = {2, 3, 2, 5, 5, 5, 3, 4, 4, 4, 4};
        List<Integer> result = findMostAppearances(inputArray);
        System.out.println(result);
    }
}
