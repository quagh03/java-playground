import java.util.*;

public class SortCharactersByFequency {
    public static String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        char[] charArray = s.toCharArray();
        for(char c : charArray){
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        List<Character> charList = new ArrayList<>(map.keySet());
        charList.sort((a, b) -> map.get(b) - map.get(a));

        StringBuilder result = new StringBuilder();
        for (char c : charList) {
            int frequency = map.get(c);
            for (int i = 0; i < frequency; i++) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static void main(String[] args){
        String input = "tree";
        String sortedString = frequencySort(input);
        System.out.println(sortedString);
    }
}
