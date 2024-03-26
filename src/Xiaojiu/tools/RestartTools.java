package Xiaojiu.tools;

public class RestartTools {
    public static int ProcessingTime(String unit,int number){
        int num = 0;
        switch (unit){
            case "d":
                num=number*24*60*60;
                break;
            case "h":
                num=number*60*60;
                break;
        }
        return num;
    }
    public static boolean isNumber(String str){
        try {
            Integer.parseInt(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
