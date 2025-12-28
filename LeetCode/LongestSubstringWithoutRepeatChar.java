import java.util.HashMap;
import java.util.Map;

public class LongestSubstringWithoutRepeatChar{
    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();

        char[] charArray = s.toCharArray();
        int repeatLength = 0; 
        int highestRepeatLength = 0;
        
        for (int i = 0; i < charArray.length; i++) {
            char temp = charArray[i];
            if (!map.containsKey(temp) || map.get(temp) < i - repeatLength) {
                repeatLength++;
            } else {
                repeatLength = i - map.get(temp);
            }

            map.put(temp, i);
            highestRepeatLength = Math.max(highestRepeatLength, repeatLength);
        }
        return highestRepeatLength;
    }

    public static void main(String[] args){
        System.out.println(
            lengthOfLongestSubstring("abcabcbb")
        );
        System.out.println(
            lengthOfLongestSubstring("bbbbb")
        );
        System.out.println(
            lengthOfLongestSubstring("pwwkew")
        );
    }
}