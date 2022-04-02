import java.util.ArrayList;
import java.util.Arrays;

public class Lab9 {
    private static ArrayList<String> newStrArr() {
        return new ArrayList<>(Arrays.asList(
                "tomato", "cheese", "chips", "fruit", "pie", "butter", "tea", "buns"
        ));
    }

    private static ArrayList<Integer> newIntArr() {
        return new ArrayList<>(Arrays.asList(
                5,12,4,16,4,2,2
        ));
    }

    public static void main(String[] args) {
        R1(newStrArr(), 4);
        System.out.println();
        R2(newStrArr(), 4);
        System.out.println();
        R3Destructive(newIntArr(), 4, 7);
        System.out.println();
        R3Constructive(newIntArr(), 4, 7);
    }

    public static void R1(ArrayList<String> a, int length) {
        System.out.println("[R1] Original length: " + a.size());
        a.removeIf(s -> s.length() < length);
        System.out.println("[R1] New length: " + a.size());
    }

    public static ArrayList<String> R2(ArrayList<String> a, int length) {
        System.out.println("[R2] Original length: " + a.size());
        ArrayList<String> newA = new ArrayList<>();
        for (String s : a)
            if (s.length() >= length)
                newA.add(s);
        System.out.println("[R2] New length: " + a.size());
        System.out.println("[R2] New list's length: " + newA.size());
        return newA;
    }

    public static <E> void R3Destructive(ArrayList<E> a, E target, E replace) {
        System.out.println("=====R3 Destructive=====");
        int i = 0;
        while (i < a.size()) {
            E current = a.get(i);
            if (current.equals(target)) {
                System.out.printf("[R3] Original list %sth item: %s\n", i, current);
                a.set(i, replace);
                System.out.printf("[R3] Original list %sth new item: %s\n", i, replace);
                break;
            }
            i++;
        }
        System.out.printf("[R3] Original list %sth item: %s\n", i, a.get(i));
    }

    public static <E> ArrayList<E> R3Constructive(ArrayList<E> a, E target, E replace) {
        System.out.println("=====R3 Constructive=====");
        int i = 0;
        ArrayList<E> array = new ArrayList<>();
        while (i < a.size()) {
            E current = a.get(i);
            if (current.equals(target)) {
                System.out.printf("[R3] New list %sth item: %s\n", i, replace);
                array.add(replace);
                break;
            } else {
                array.add(current);
            }
            i++;
        }
        System.out.printf("[R3] Original list %sth item: %s\n", i, a.get(i));
        return array;
    }
}
