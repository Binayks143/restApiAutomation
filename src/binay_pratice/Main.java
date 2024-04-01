package binay_pratice;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        for(int i=1;i<=10;i++){
            l1.add(i);
        }
        for(int i=5;i<=15;i++){
            l2.add(i);
        }

        l1 = l1.stream().filter(val -> !l2.contains(val)).toList();
        System.out.println(l1);
    }
}
