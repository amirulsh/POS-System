import components.*;
import java.util.Scanner;
public class POS 
{
  public static void main(String[] args) 
  {
    Draw draw = new Draw();
    Scanner scanner = new Scanner(System.in);
    TextFormatter textRender = new TextFormatter();
    AsciiCode ac = new AsciiCode();

    draw.PreConstruct(120, 30);
    draw.DrawBackground();

    String input;
    String note;

out:
    while(true)
    {
      input = scanner.nextLine();

      switch(input)
      {
        case "q": break out;
        case "r": draw.DrawBackground();
        case "n": {
          System.out.print(draw.inputBox);
          note = scanner.nextLine();
          String text = textRender.TextWrapper(8, 3, note);
          System.out.print(ac.CursorTo(3, 3) + text);
          System.out.print(draw.inputBox);
        }
        default: System.out.print(draw.inputBox);
      }
       
    }
    scanner.close();
    System.out.print("\u001B[34;1H");
  }
}
