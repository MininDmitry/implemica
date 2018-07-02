import java.util.Scanner;

public class task3 {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        while(true){
            System.out.println("Please write number of parentheses");
            int num = scanner.nextInt();
            System.out.println(parentheses(num));
        }
    }
    static long parentheses (int n) {

        long [] C = new long [n+1];//initialize an array that will contain the entire sequence of numbers

        C[0]=1;
        for (int i=1; i<=n; i++)
        {
            C[i]=0;
            for (int j=0; j<i; j++)
                C[i]+=C[j]*C[i-1-j];//Each successive term is equal to the sum of the product of all previous members(C[j]*C[i-1-j])
            // or can it be better to say the "recursive relation"(перебор элементов)
        }

        return C[n];
    }
}
