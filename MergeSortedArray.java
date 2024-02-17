public class MergeSortedArray {
    
    public static void merge(int[] nums1, int m, int[] nums2, int n){
        //Using 2 pointers 
        int i = m - 1, j = n - 1, k = (m + n) - 1;
        
        while (k >= 0) {
            if(j < 0){
                nums1[k] = nums1[i];
                i--;
            }else if(i < 0){
                nums1[k] = nums2[j];
                j--;
            }else if(nums1[i] > nums2[j]){
                nums1[k] = nums1[i];
                i--;
            }else{
                nums1[k] = nums2[j];
                j--;
            }
            k--;
        }
    }

    public static void main(String[] args){

    }

}
