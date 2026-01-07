import components.*;
import java.util.Scanner;
public class POS 
{
  public static void main(String[] args) 
  {
    Draw draw = new Draw();
    Scanner scanner = new Scanner(System.in);
    TextRender textRender = new TextRender();
    AsciiCode ac = new AsciiCode();

    draw.PreConstruct(120, 30);
    draw.DrawBackground();

    boolean running = true;
    String input;
    String note;
    while(running)
    {
      input = scanner.nextLine();

      switch(input)
      {
        case "q": running = false;
        case "r": draw.DrawBackground();
        case "n": {
          note = scanner.nextLine();
          String text = textRender.TextFitter(8, 3, note);
          System.out.print(ac.CursorTo(3, 3) + text);
        }
      }
       
    }
    scanner.close();
    System.out.print("\u001B[34;1H");
  }
}
