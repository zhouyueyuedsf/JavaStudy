import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        char[] A= {'a','b','E','C','g'};
        int maxKey = 'Z';

        ArrayList<Character> B[] =  new ArrayList[maxKey];
//        char[] B = new char[52];
        for(int i = 0; i < A.length; i++){
            int Ai = 0;

            if(A[i] >= 'a'){
                int a = 'a';
                Ai = A[i];
                Ai = Ai - a;
                if(B[Ai] == null){
                    ArrayList<Character> a1 = new ArrayList<>();
                    B[Ai] =  a1;
                }
                B[Ai].add(A[i]);
            }else{
                Ai = A[i];
                if(B[Ai] == null){
                    ArrayList<Character> a1 = new ArrayList<>();
                    B[Ai] =  a1;
                }
                B[Ai].add(A[i]);
            }

        }
        System.out.println(B);
    }
}
