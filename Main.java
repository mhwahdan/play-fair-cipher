

public class Main {
    public static void main(String[] args) {
        PlayFair cipherer = new PlayFair("monarchy");
        for (int i = 0; i < 5; i++) {
            for (int j =0; j < 5; j++)
                System.out.print(cipherer.getKey()[i][j] + " ");
            System.out.print("\n");
        }
        System.out.println(cipherer.encrypt("instruments"));
        System.out.println(cipherer.decrypt("GATLMZCLRQTX"));
    }
}
