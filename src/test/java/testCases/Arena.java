package testCases;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Arena {
    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Date date = new Date();
        String currentDateStamp = simpleDateFormat.format(date);
        System.out.println(currentDateStamp);
    }
}
