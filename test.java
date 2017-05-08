
/**
 * Write a description of class test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class test
{
    public static void main(String[] args){
        for(int i = 0; i<50; i++){
           System.out.println( maybeNegative() );
        }
    }
    public static int maybeNegative(){
        return (int)(Math.random()*2)*2-1;
    }
}
