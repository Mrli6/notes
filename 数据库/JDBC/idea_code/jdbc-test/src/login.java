public class login {
    public static void main(String[] args) {

        int x = 121;
        int cmp = 0;

        while(x != 0){
            cmp = cmp*10 + x%10;
            x /= 10;
        }

        System.out.println(cmp);
        System.out.println(x);
        System.out.println(cmp == x);

    }
}