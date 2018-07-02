import java.math.BigInteger;
import java.util.Scanner;

public class task1 {
    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        while(true){
            System.out.println("Please write number");
            int num = scanner.nextInt();
            System.out.println(factorial(num));
            fact2(num);

        }
    }


    static void fact2(int n){
        /*this is the second version of the solutions,
         simplified, with the help of the standard,
         the standard way to find the factorial - counted the sum of the digits
        */
        BigInteger fact = BigInteger.valueOf(1);
        BigInteger sum = BigInteger.valueOf(0);
        for (int i = 1; i <= n; i++)
            fact = fact.multiply(BigInteger.valueOf(i));

        while(fact.compareTo(BigInteger.valueOf(1))>-1)
        {
            sum= sum.add(fact.remainder(BigInteger.valueOf(10)));
            fact = fact.divide(BigInteger.valueOf(10));
        }
        System.out.println(sum);
    }
    public static int factorial(int n) {
        int rez[] = new int[300];
        rez[0] = 1; // Initialize result
        int res_size = 1;
        for (int x = 2; x <= n; x++) {
            int carry = 0;

            // One by one multiply n with individual digits of rez.
            for (int i = 0; i < res_size; i++) {
                int prod = rez[i] * x + carry;
                rez[i] = prod % 10; // Store last digit of 'prod' in rez
                carry = prod / 10;  // Put rest in carry
            }

            // Put carry in rez
            while (carry != 0) {
                rez[res_size] = carry % 10;
                carry = carry / 10;
                res_size++;
            }
        }

        int rez2=0;
        for (int i = res_size - 1; i >= 0; i--) {
            rez2+=rez[i];
        }

        return rez2;
    }

}

