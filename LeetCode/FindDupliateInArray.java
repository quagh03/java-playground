import java.util.*;
class FindDupliateInArray{
    public static List<Integer> findDuplicates(int[] nums) {
        //Check if list is empty
        if(nums.length == 0){
            return new ArrayList<>();
        }
        
        //Map to store number and exist time
        Map<Integer, Integer> countMap = new HashMap<>();

        for(int num : nums){
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        List<Integer> duplicateNumbers = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry : countMap.entrySet()){
            if(entry.getValue() > 1){
                duplicateNumbers.add(entry.getKey());
            }
        }
        return duplicateNumbers;
    }

    public static void main(String[] args) {
        int[] inputArray = {4,3,2,7,8,2,3,1};
        List<Integer> result = findDuplicates(inputArray);
        System.out.println(result);
    }
}