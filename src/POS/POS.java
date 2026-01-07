import components.*;
import java.util.Scanner;
public class POS 
{
  public static void main(String[] args) 
  {
    Draw draw = new Draw();
    Scanner scanner = new Scanner(System.in);

    draw.PreConstruct(120, 30);
    draw.DrawBackground();

    boolean running = true;
    String input;
    while(running)
    {
      input = scanner.nextLine();

      switch(input)
      {
        case "q": running = false;
        case "r": draw.DrawBackground();
      }
       
    }
    scanner.close();
    System.out.print("\u001B[34;1H");
  }
}
