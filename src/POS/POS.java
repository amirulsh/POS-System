import components.*;
import java.util.Scanner;
public class POS 
{


  public static void main(String[] args) 
  {
    int backgroundWidth = 120;
    int backgroundHeight = 30;
    int state = 0;
    int nextState = 0;   
    FrameCache fc = new FrameCache(backgroundWidth, backgroundHeight);
    int[] frameState = {fc.indexStart, fc.indexOrder, 0, fc.indexFood, fc.indexBeverage, fc.indexAddons};
    int[] parentState = {
      0, // 0 Start has no parent
      0, // 1 Order -> Start
      0, // 2 Sale  -> Start
      1, // 3 Food  -> Order
      1, // 4 Beverage -> Order
      4  // 5 Addons -> Beverage
    };

    Draw draw = new Draw(backgroundWidth, backgroundHeight);
    Scanner scanner = new Scanner(System.in);
    TextFormatter tf = new TextFormatter();
    Item item = new Item();
    AsciiCode ac = new AsciiCode();

    int input = 0;
    boolean selectFood = false;
    boolean selectBeverage = false;
    int selectedAddons = 0;
    int selectedItem = 0;
    System.out.print(fc.frame[frameState[0]]);

out:
    while(true)
    {
      System.out.print(draw.inputBox);
      String charInput = scanner.nextLine();

      if (charInput.matches("\\d"))
      {
        input = Integer.parseInt(charInput);

        if ((state == 0 || state == 1) && (input >= 0 || input <= 2))
        {
          nextState = state * 2 + input;
          System.out.print(fc.frame[frameState[state] + input]);
          continue;
        }
        else if (state == 3 && (input >= 0 && input <= item.GetFood().length))
        {
          System.out.print(fc.frame[fc.indexFood + input]);
          selectFood = true;
          selectedItem = input;
          continue;
        }
        else if (state == 4 && (input >= 0 && input <= item.GetBeverage().length))
        {
          System.out.print(fc.frame[frameState[state] + input]);
          nextState = state + 1;
          selectBeverage = true;
          selectedItem = input; 
          continue;
        }
        else if (state == 5 && (input >= 0 && input <= item.GetAddons().length))
        {
          System.out.print(fc.frame[frameState[state] + input]);
          selectedAddons = input;
        }
      }
      else
        switch(charInput)
        {
          case "q": 
            break out;
          case "b":
            state = parentState[state];
            System.out.print(fc.frame[frameState[state]]);
            break;
          case "":
            if (selectFood && selectedItem != 0)
            {
              System.out.print(
                  ac.CursorTo(draw.secondPanelX + 1, draw.secondPanelY + 1)
                  + tf.AlignCenter(draw.secondPanelWidth - 2, draw.secondPanelHeight - 2, item.RegisterItem(1, selectedItem - 1)));
            } 
            else if (selectBeverage && selectedItem != 0 && selectedAddons == 1)
            {
              System.out.print(
                  ac.CursorTo(draw.secondPanelX + 1, draw.secondPanelY + 1)
                  + tf.AlignCenter(draw.secondPanelWidth - 2, draw.secondPanelHeight - 2, item.RegisterItem(2, selectedItem - 1)));
            } 
            else if (selectFood && selectedItem != 0 && selectedAddons == 2)
            {
              System.out.print(
                  ac.CursorTo(draw.secondPanelX + 1, draw.secondPanelY + 1)
                  + tf.AlignCenter(draw.secondPanelWidth - 2, draw.secondPanelHeight - 2, item.RegisterItem(3, selectedItem - 1)));
            }
            if (nextState > 0)
            {
              state = nextState;
              System.out.print(fc.frame[frameState[state]]);
              nextState = 0;
            }
              continue;
          default: System.out.print(draw.inputBox);
        } 
    }
    scanner.close();
    System.out.print("\u001B[34;1H");
  }
}
