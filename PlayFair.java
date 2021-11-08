import java.util.ArrayList;
import java.util.Arrays;

public class PlayFair {
    private char[][] key = new char[5][5];
    private final char bogus_character = 'Z';
    public PlayFair(String key)
    {
        if(key.length() >= 25)
            throw new RuntimeException("key must have length less than 25 characters");
        key = key.toUpperCase();
        char[] key_arr = key.toCharArray();
        for(char x : key_arr)
            if(x < 'A' || x > 'Z')
                throw new RuntimeException("only characters from a - z can be used in key generation");
        for(int i = 0; i < key_arr.length; i++)
            if(key_arr[i] == 'J')
                key_arr[i] = 'I';
        int iterator = 0;
        boolean flag = false;
        int i , j = 0;
        for(i = 0; i < 5; i++)
        {
            for(j = 0; j < 5; j++)
            {
                this.key[i][j] = key_arr[iterator];
                if(iterator == key_arr.length - 1) {
                    flag = true;
                    break;
                }
                else
                    iterator ++;
            }
            if(flag)
                break;
        }
        Arrays.sort(key_arr);
        j++;
        for(char current = '@'; i < 5; i++)
        {
            for (; j < 5; j++)
            {
                while(true)
                {
                    current ++;
                    if(current == 'J')
                        current ++;
                    if (key.indexOf(current) == -1) {
                        this.key[i][j] = current;
                        break;
                    }
                }
            }
            j = 0;
        }


    }

    public PlayFair(char[][] key)
    {
        if(key.length !=5 || key[0].length != 5)
            throw new RuntimeException("the 2D array must be 5x5");
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                if(key[i][j] < 65 ||key[i][j] > 90)
                    throw new RuntimeException("only characters from a - z can be used in key generation");
        this.key = key;
    }

    public char[][] getKey()
    {
        return this.key;
    }

    private static Boolean checkKey(char[][] key)
    {
        boolean flag;
        for(int i = 0; i < 5; i++)
            for(int j = 0;j < 5; j++)
                {
                    flag = false;
                    for(int k = 0; k < 5; k++)
                        for(int l=0; l <5; l++)
                            if(flag && key[i][j] == key[k][l])
                                return false;
                            else if(key[i][j] == key[k][l])
                                flag = true;
                }
        return true;
    }

    private static Boolean checkKey(String key)
    {
        boolean flag;
        char[] arr = key.toCharArray();
            for(char x: arr) {
                flag = false;
                for (char y : arr) {
                    if(flag && x == y)
                        return false;
                    else if(x == y)
                        flag = true;
                }
            }
        return true;
    }

    private ArrayList<String> split(String text)
    {
        ArrayList<String> strings = new ArrayList<>();
        if(text.length() % 2 != 0)
            text += this.bogus_character;
        char[] text_characters = text.toUpperCase().toCharArray();
        for(int i = 0; i < text_characters.length; i += 2)
            if(i == text_characters.length -1 || text_characters[i] == text_characters[i + 1])
            {
                strings.add(text_characters[i] + String.valueOf(this.bogus_character));
                i--;
            }
            else
                strings.add(
                    text_characters[i] + String.valueOf(text_characters[i + 1])
                    );
        String last = strings.get(strings.size() - 1);
        if(last.equals("XX"))
            strings.remove(strings.size() - 1);
        return strings;
    }

    private static int[] getIndex(char key, char[][] matrix)
    {
        int[] out = {-1,-1};
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                if(key == matrix[i][j])
                {
                    out[0] = i;
                    out[1] = j;
                    return out;
                }
        return out;
    }

    public String encrypt(String plainText)
    {
        ArrayList<String> strings = this.split(plainText);
        StringBuilder output = new StringBuilder();
        for(String x : strings)
        {
            char[] characters = x.toCharArray();
            int[] index1 = PlayFair.getIndex(characters[0], this.key);
            int[] index2 = PlayFair.getIndex(characters[1], this.key);
            if(index1[0] == index2[0])
                output.append(this.getKey()[index1[0]][(index1[1] + 1) % 5]).append(this.getKey()[index2[0]][(index2[1] + 1) % 5]);
            else if(index1[1] == index2[1])
                output.append(this.getKey()[(index1[0] + 1) % 5][index1[1]]).append(this.getKey()[(index2[0] + 1) % 5][index2[1]]);
            else
                output.append(this.getKey()[index1[0]][index2[1]]).append(this.getKey()[index2[0]][index1[1]]);
        }
        return output.toString();
    }

    public String decrypt(String cipher)
    {
        ArrayList<String> strings = this.split(cipher);
        StringBuilder output = new StringBuilder();
        for(String x : strings)
        {
            char[] characters = x.toCharArray();
            int[] index1 = PlayFair.getIndex(characters[0], this.key);
            int[] index2 = PlayFair.getIndex(characters[1], this.key);
            if(index1[0] == index2[0])
                output.append(this.getKey()[index1[0]][(index1[1] + 4) % 5]).append(this.getKey()[index2[0]][(index2[1] + 4) % 5]);
            else if(index1[1] == index2[1])
                output.append(this.getKey()[(index1[0] + 4) % 5][index1[1]]).append(this.getKey()[(index2[0] + 4) % 5][index2[1]]);
            else
                output.append(this.getKey()[index1[0]][index2[1]]).append(this.getKey()[index2[0]][index1[1]]);
        }

        return output.substring(0,output.length() - ((output.toString().toCharArray()[output.length() - 1] == this.bogus_character)? 1:0)).toLowerCase();
    }
}
