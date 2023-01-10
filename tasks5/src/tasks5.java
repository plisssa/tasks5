import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class tasks5 {

    public static int[] encrypt(String word) {
        int[] encrypt = new int[word.length()];//определяем массив
        encrypt[0] = word.charAt(0);
        int v = encrypt[0];
        for (int i = 1; i < word.length(); i++) {
            encrypt[i] = word.charAt(i) - v;
            v = word.charAt(i);
        }
        return encrypt;
    }


    public static String decrypt(int[] encrypt) {
        String decrypt = "" + (char) encrypt[0];//строка с первым символом
        int v = encrypt[0];
        for (int i = 1; i < encrypt.length; i++) {
            decrypt += (char) (encrypt[i] + v);
            v = encrypt[i] + v;
        }
        return decrypt;
    }


    public static boolean canMove(String figure, String start, String end) {
        int startR = start.charAt(0) - 'A';
        int startC = start.charAt(1) - '1';
        int endR = end.charAt(0) - 'A';
        int endC = end.charAt(1) - '1';
        if (startR < 0 || startR > 7 || startC < 0 || startC > 7 || endR < 0 || endR > 7 || endC < 0 || endC > 7)
            return false;
        switch (figure) {
            case "Pawn"://пешка
                return startR == endR && (endC - startC == 1 || (startC == 1 && endC - startC == 2));//+1 ход если первый ход и +2 если нет
            case "Rook"://ладья
                return startR == endR || startC == endC;//начальная буква равна конечной либо аналогично с цифрой
            case "Knight"://конь
                return Math.abs(startR - endR) == 2 && Math.abs(startC - endC) == 1 || Math.abs(startR - endR) == 1 && Math.abs(startC - endC) == 2;//ходит буквой г
            case "Bishop"://слон
                return Math.abs(startR - endR) == Math.abs(startC - endC);//наискосок разница букв равана разнице цифр
            case "Queen":
                return startR == endR || startC == endC || Math.abs(startR - endR) == Math.abs(startC - endC);//все условия движения по х по у(ладья) и как для слона на искосок
            case "King":
                return Math.abs(startR - endR) <= 1 && Math.abs(startC - endC) <= 1;//на одну клетку в любую сторону
            default:
                return false;
        }
    }


    public static boolean canComplete(String s, String word) {
        int Idx = 0;//индекс с которого начинаем
        for (int i = 0; i < s.length(); i++) {
            int idx = word.indexOf(s.charAt(i), Idx);
            if (idx == -1) return false;//если не находит символ
            Idx = idx + 1;
        }
        return true;
    }


    public static int sumDigProd(int... s) {
        int sum = 0;
        for (int i = 0; i < s.length; i++) {
            sum += s[i];
        }
        while (sum > 9) {//пока число не однозначное
            int p = 1;
            while (sum > 0) {
                p *= (sum % 10);
                sum /= 10;
            }
            sum = p;
        }
        return sum;
    }

    // Вспомогательная функция
    public static int countVowels(String word) {
        final String vowels = "aeiouy";
        StringBuilder unique = new StringBuilder();
        int sum = 0;
        for (char lit : word.toLowerCase().toCharArray()) {//берем каждую букву слова
            if (vowels.indexOf(lit) != -1 && unique.indexOf(lit + "") == -1) {
                unique.append(lit);
            }
        }
        return sum;
    }


    public static String[] sameVowelGroup(String[] words) {
        String[] result = new String[words.length];
        int n = 0;
        int baseVowels = countVowels(words[0]);
        for (int i = 0; i < words.length; i++) {
            if (baseVowels == countVowels(words[i])) {
                result[n] = words[i];
                n++;
            }
        }
        return Arrays.copyOf(result, n);//выводим слова
    }


    public static boolean validateCard(long number) {
        String num = number + "";
        if (num.length() < 14 || num.length() > 19) return false;
        int sum = 0;
        int last = Integer.parseInt(num.charAt(num.length() - 1) + "");
        for (int i = 0; i < num.length() - 1; i++) {
            int d = Integer.parseInt(num.charAt(i) + "");
            if (i % 2 == 0) {//если индекс четный
                d *= 2;
                if (d > 9) d -= 9;
            }
            sum += d;
        }
        return (10 - sum % 10) == last;

    }


    public static String numToEng(int number) {
        String[] digits = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};//0-9
        String[] teens = {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};//10-19
        String[] tens = {"twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
        String hundred = "hundred";
        StringBuilder a = new StringBuilder();
        if (number > 99) {
            a.append(digits[number / 100]).append(" ").append(hundred).append(" ");
            number %= 100;
        }
        if (number > 19) {
            a.append(tens[number / 10 - 2]).append(" ");
            number %= 10;
            a.append(digits[number]);
        } else {
            if (number > 9) {
                a.append(teens[number - 10]);
            } else {
                a.append(digits[number]);
            }
        }
        return a.toString();
    }


    public static String numToRus(int number) {
        String[] digits = {"ноль", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"};
        String[] teens = {"десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};
        String[] tens = {"двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто"};
        String[] hundreds = {"сто", "двести", "триста", "четыреста", "пятьсот", "семьсот", "восемьсот", "девятьсот"};
        StringBuilder answer = new StringBuilder();
        if (number > 99) {
            answer.append(hundreds[number / 100 - 1]).append(" ");
            number %= 100;
        }
        if (number > 19) {
            answer.append(tens[number / 10 - 2]).append(" ");
            number %= 10;
            answer.append(digits[number]);
        } else {
            if (number > 9) {
                answer.append(teens[number - 10]);
            } else {
                answer.append(digits[number]);
            }
        }
        return answer.toString();
    }


    public static String getSha256Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashInBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String correctTitle(String input){
        String[] words = input.split(" ");
        List<String> e = Arrays.stream(new String[]{"the", "of", "in", "and"}).toList();//в списке складываем слова
        for(int i = 0; i < words.length; i++){//идем по каждому слову
            String word = words[i].toLowerCase();
            if(e.contains(word)){
                words[i] = word;
            }
            else{
                words[i] = word.substring(0, 1).toUpperCase() + word.substring(1);//в каждом слове первый символ делает большим
            }
        }
        return String.join(" ", words);
    }


    public static String hexLattice(int n){
        if(n == 1)return " o ";
        int num = 1;
        int counter = 0;
        while(num != n){
            num = 3 * counter * (counter - 1) + 1;
            counter++;
        }
        if(num != n)return "Invalid";
        StringBuilder a = new StringBuilder();
        int side  = counter - 1;
        int mid = side * 2 + 1;
        for(int i = 0; i < counter - 1; i++){
            for(int j = 0; j < mid - side - 2; j++){
                a.append(" ");
            }
            for(int k = 0; k < side; k++){//выводим первый сиимвол в строке без пробела
                if(k == 0)a.append("o");
                else a.append(" o");
                     }
            for(int j = 0; j < (mid - side - 2); j++){
                a.append(" ");
            }
            a.append("\n");
            side++;
        }
        side -= 2;
        for(int i = 0; i < counter - 2; i++){
            for(int j = 0; j < (mid - side - 2); j++){
                a.append(" ");
            }
            for(int k = 0; k < side; k++){
                if(k == 0)a.append("o");
                else a.append(" o");
            }
            for(int j = 0; j < (mid - side - 2); j++){
                a.append(" ");
            }
            a.append("\n");
            side--;
        }
        return a.toString().trim();
    }
    public static void main(String[] args) {
        System.out.println("Задача 1");
        System.out.println("encrypt(\"Hello\") = " + Arrays.toString(encrypt("Hello")));
        System.out.println("decrypt([ 72, 33, -73, 84, -12, -3, 13, -13, -68 ]) = " + decrypt(new int[]{72, 33, -73, 84, -12, -3, 13, -13, -68}));
        System.out.println("encrypt(\"Sunshine\") = " + Arrays.toString(encrypt("Sunshine")));
        System.out.println("\nЗадача 2");
        System.out.println("canMove(\"Rook\", \"A8\", \"H8\") = " + canMove("Rook", "A8", "H8"));
        System.out.println("canMove(\"Bishop\", \"A7\", \"G1\") = " + canMove("Bishop", "A7", "G1"));
        System.out.println("canMove(\"Queen\", \"C4\", \"D6\") = " + canMove("Queen", "C4", "D6"));
        System.out.println("\nЗадача 3");
        System.out.println("canComplete(\"butl\", \"beautiful\") = " + canComplete("butl", "beautiful"));
        System.out.println("canComplete(\"butlz\", \"beautiful\") = " + canComplete("butlz", "beautiful"));
        System.out.println("canComplete(\"tulb\", \"beautiful\") = " + canComplete("tulb", "beautiful"));
        System.out.println("canComplete(\"bbutl\", \"beautiful\") = " + canComplete("bbutl", "beautiful"));
        System.out.println("\nЗадача 4");
        System.out.println("sumDigProd(16, 28) = " + sumDigProd(16, 28));
        System.out.println("sumDigProd(0) = " + sumDigProd(0));
        System.out.println("sumDigProd(1, 2, 3, 4, 5, 6) = " + sumDigProd(1, 2, 3, 4, 5, 6));
        System.out.println("\nЗадача 5");
        System.out.println("sameVowelGroup([\"toe\", \"ocelot\", \"maniac\"]) = " + Arrays.toString(sameVowelGroup(new String[]{"toe", "ocelot", "maniac"})));
        System.out.println("sameVowelGroup([\"many\", \"carriage\", \"emit\", \"apricot\", \"animal\"]) = " + Arrays.toString(sameVowelGroup(new String[]{"many", "carriage", "emit", "apricot", "animal"})));
        System.out.println("sameVowelGroup([\"hoops\", \"chuff\", \"bot\", \"bottom\"]) = " + Arrays.toString(sameVowelGroup(new String[]{"hoops", "chuff", "bot", "bottom"})));
        System.out.println("\nЗадача 6");
        System.out.println("validateCard(1234567890123456) = " + validateCard(1234567890123456L));
        System.out.println("validateCard(1234567890123452) = " + validateCard(1234567890123452L));
        System.out.println("\nЗадача 7");
        System.out.println("numToEng(18) = " + numToEng(18));
        System.out.println("numToEng(117) = " + numToEng(117));
        System.out.println("numToEng(0) = " + numToEng(0));
        System.out.println("numToRus(18) = " + numToRus(18));
        System.out.println("numToRus(356) = " + numToRus(356));
        System.out.println("numToRus(0) = " + numToRus(0));
        System.out.println("\nЗадача 8");
        System.out.println("getSha256Hash(\"password123\") = " + getSha256Hash("password123"));
        System.out.println("getSha256Hash(\"Fluffy@home\") = " + getSha256Hash("Fluffy@home"));
        System.out.println("getSha256Hash(\"Hey dude!\") = " + getSha256Hash("Hey dude!"));
        System.out.println("\nЗадача 9");
        System.out.println("correctTitle(\"jOn SnoW, kINg IN thE noRth.\") = " + correctTitle("jOn SnoW, kINg IN thE noRth."));
        System.out.println("correctTitle(\"sansa stark, lady of winterfell.\") = " + correctTitle("sansa stark, lady of winterfell."));
        System.out.println("correctTitle(\"TYRION LANNISTER, HAND OF THE QUEEN.\") = " + correctTitle("TYRION LANNISTER, HAND OF THE QUEEN."));
        System.out.println("\nЗадача 10");
        System.out.println("hexLattice(1) = " + hexLattice(1));
        System.out.println("hexLattice(7) = " + hexLattice(7));
        System.out.println("hexLattice(19) = " + hexLattice(19));
        System.out.println("hexLattice(37) = " + hexLattice(37));

    }
}

