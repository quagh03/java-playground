public class AddTwoNumbers {
    public class ListNode{
        int val;
        ListNode next;
        ListNode(){}
        ListNode(int val){this.val = val;}
        ListNode(int val, ListNode next){
            this.val = val;
            this.next = next;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2){
        // 2ms
        // ListNode dummy = new ListNode(0);
        // ListNode current = dummy;
        // int carry = 0;

        // while(l1 != null || l2 != null){
        //     int x = (l1 != null) ? l1.val : 0;
        //     int y = (l1 != null) ? l1.val : 0;

        //     int sum = x + y + carry;
        //     carry = sum / 10;

        //     current.next = new ListNode(sum % 10);
        //     current = current.next;

        //     if (l1 != null) l1 = l1.next;
        //     if (l2 != null) l2 = l2.next;

        // }
        // if (carry > 0) {
        //     current.next = new ListNode(carry);
        // }
    
        // return dummy.next;


        // 1ms
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        int carry = 0;

        while (l1 != null || l2 != null || carry != 0) {
            int digit1 = (l1 != null) ? l1.val : 0;
            int digit2 = (l2 != null) ? l2.val : 0;

            int sum = digit1 + digit2 + carry;
            int digit = sum % 10;
            carry = sum / 10;

            ListNode newNode = new ListNode(digit);
            tail.next = newNode;
            tail = tail.next;

            l1 = (l1 != null) ? l1.next : null;
            l2 = (l2 != null) ? l2.next : null;
        }

        ListNode result = dummyHead.next;
        dummyHead.next = null;
        return result;
    }

    public static void main(String[] args) {
        
    }
}

