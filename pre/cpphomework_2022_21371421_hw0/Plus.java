import java.util.Scanner;

public class Plus {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int a = input.nextInt();
        int b = input.nextInt();
        int ans = a + b;
        System.out.println(ans);
        input.close();
    }
}
