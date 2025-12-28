/*
* Demonstration of String immutability and String pool in Java.
*/
public class StringImmutableDemo {
  public static void main(String[] args) {
    /*
     * original and copyOfOriginal
     * both is String literal refer to the same String object in the String pool
     * using '==' to compare -> true
     */
    String original = "Hello";
    String copyOfOriginal = "Hello";
    System.out.println(original == copyOfOriginal);

    /*
     * Why String should be immutable?
     * 3 keywords: Security, Memory, Thread Safe
     * Memory: Using String Pool to store Objects -> check if existed then pass the references instead of creating new one (Demonstrated before)
     * Security: Imangine we're using String for a path to a file. If String coulde be changed? what happend?
     * Thread Safe: Imagine we have a GlobalConfig that shared resource and being used in many threads of our service
     */
    //Memory (Reference to same String object)
    System.out.println(System.identityHashCode(original) == System.identityHashCode(copyOfOriginal));

    //Security

    //Thread Safe
    String immutabelStringConfig = "org.postgresql.Driver";
    StringBuilder nonImmutableStringConfig = new StringBuilder("org.postgresql.Driver");
    Runnable task = () -> {
      //Non Immutable can be changed
      nonImmutableStringConfig.append("modified by").append(Thread.currentThread().getName());
      System.out.println(Thread.currentThread().getName() + ": (Immutable) Connecting to database using " + immutabelStringConfig);
      System.out.println(Thread.currentThread().getName() + ": (NON-Immutable) Connecting to database using " + nonImmutableStringConfig);
    };

    for (int i = 0; i < 10; i++) {
      new Thread(task).start();
    }
  }
}