import java.util.HashSet;
import java.util.Set;

public class FindUniqueBinaryString {
    public static String findDifferentBinaryString(String[] nums){
        Set<String> set = new HashSet<>();
        int n = nums.length;
        
        for (String num : nums) {
            set.add(num);
        }
        for (int i = 0; i < (1 << n); i++) {
            String bin = Integer.toBinaryString(i);
            while (bin.length() < n) {
                bin = "0" + bin;
            }
            if (!set.contains(bin)) {
                return bin;
            }
        }

        return "";

    }

    public static void main(String[] args){
        String[] testArray = {"01", "10"};
        System.out.println(findDifferentBinaryString(testArray));
    }
}
