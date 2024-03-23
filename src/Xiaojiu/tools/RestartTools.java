package Xiaojiu.tools;

public class RestartTools {
    public static int ProcessingTime(String unit,int number){
        switch (unit){
            case "d":
                number*=24*60*60;
                break;
            case "h":
                number*=60*60;
                break;
        }
        return number;
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
