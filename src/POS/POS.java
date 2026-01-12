import components.*;
import java.util.Scanner;
public class POS 
{
  public static void main(String[] args) 
  {  
    int backgroundWidth = 140;
    int backgroundHeight = 30;
    Item item = new Item();
    Draw draw = new Draw(backgroundWidth, backgroundHeight, item);
    FrameCache fc = new FrameCache(backgroundWidth, backgroundHeight, draw);
    Scanner scanner = new Scanner(System.in);

    int state = 0;
    int nextState = 0;   
    int[] frameState = {fc.indexStart, fc.indexOrder, fc.indexSale, fc.indexFood, fc.indexBeverage, fc.indexAddons, fc.indexPayment};
    int[] parentState = {
      0, // 0 Start has no parent
      0, // 1 Order -> Start
      0, // 2 Sale  -> Start
      1, // 3 Food  -> Order
      1, // 4 Beverage -> Order
      4,  // 5 Addons -> Beverage
      0,  // 6 Payment -- assignable
    };

    int input = 0;
    boolean selectFood = false;
    boolean voidingItem = false;
    boolean payment = false;
    boolean cash = false;
    boolean card = false;
    double amountEntered = 0;
    int selectedAddons = 0;
    int selectedItem = 0;
    System.out.print(fc.frame[frameState[0]]);

out:
    while(true)
    {
      if (state != 0 && state != 2) System.out.print(draw.DisplayItem());
      System.out.print(draw.inputBox);
      String charInput = scanner.nextLine();

      if (payment)
      {

        if (charInput.equals("q"))
        {
          break out;
        }
        else if (charInput.equals("b"))
        {
          cash = false;
          card = false;
          payment = false;
          amountEntered = 0;
          state = parentState[state];
          System.out.print(fc.frame[frameState[state]]);
          continue;
        }


        if (!cash && !card)
        {
          if (charInput.equals("c"))
          {
            cash = true;
            amountEntered = 0;
          }
          else if (charInput.equals("a"))
          {
            card = true;
          }
          System.out.print(draw.RenderPayment(amountEntered, card, cash));
          continue;
        }

        if (charInput.equals("Y"))
        {
          item.Paid();
          item.voidItem();

          state = parentState[state];
          amountEntered = 0;
          cash = false;
          card = false;
          payment = false;

          System.out.print(fc.frame[frameState[state]]);
          continue;
        }

        if (cash && charInput.matches("\\d+(\\.\\d+)?"))
        {
          amountEntered = Double.parseDouble(charInput);
        }

        System.out.print(draw.RenderPayment(amountEntered, card, cash));
        continue;
      }

      if (charInput.matches("\\d+"))
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
          case "p":
            parentState[6] = state;
            state = 6;
            System.out.print(fc.frame[frameState[state]]);
            System.out.print(draw.RenderPayment(amountEntered, false, false));
            payment = true;
            continue;
          case "v":
            voidingItem = true;
          case "":
            if (voidingItem)
            {
              item.voidItem();
              voidingItem = false;
            }
            if (nextState > 0)
            {
              state = nextState;
              System.out.print(fc.frame[frameState[state]]);
              nextState = 0;
              if (state == 2) System.out.print(draw.RenderSale());
              continue;
            }
            System.out.print(fc.frame[frameState[state]]);
            if (selectFood && selectedItem != 0)
            {
              item.RegisterItem(1, selectedItem - 1);
            } 
            else if (!selectFood && selectedItem != 0 && selectedAddons == 1)
            {
              item.RegisterItem(2, selectedItem - 1);
            } 
            else if (!selectFood && selectedItem != 0 && selectedAddons == 2)
            {
              item.RegisterItem(3, selectedItem - 1);
            }
            selectFood = false;
            selectedAddons = 0;

            continue;
          default: System.out.print(draw.inputBox);
        } 
    }
    scanner.close();
    System.out.print("\u001B[34;1H");
  }
}
