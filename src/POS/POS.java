import components.*;
import java.util.Scanner;
public class POS 
{
  public static void main(String[] args) 
  {
    int backgroundWidth = 120;
    int backgroundHeight = 30;
    Draw draw = new Draw(backgroundWidth, backgroundHeight);
    Scanner scanner = new Scanner(System.in);
    TextFormatter textRender = new TextFormatter();
    AsciiCode ac = new AsciiCode();
    int[] list = {1, 2, 3, 4, 5, 6};

    draw.DrawBackground();
    String options = draw.CreateOptions(list);
    draw.DrawOptions(options);

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
          String text = textRender.AlignRight(8, 4, note);
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
