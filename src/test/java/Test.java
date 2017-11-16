import java.util.Arrays;
import java.util.Random;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/16 13:44
 */
public class Test {

    public static void main(String[] args) {
        Random random = new Random();
        int[] a = new int[10];
        for (int i = 0; i < 10; i++) {
            int i1 = random.nextInt(100);
            a[i] = i1;
        }
        System.out.println(Arrays.toString(a));
    }
}
