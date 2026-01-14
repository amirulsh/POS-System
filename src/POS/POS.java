import components.*;
import java.util.Scanner;

public class POS
{
  public static void main(String[] args)
  {
    // Basic setup
    int backgroundWidth  = 140;
    int backgroundHeight = 30;

    Item item        = new Item();
    AsciiCode ascii  = new AsciiCode();
    Draw draw        = new Draw(backgroundWidth, backgroundHeight, item);
    FrameCache fc    = new FrameCache(backgroundWidth, backgroundHeight, draw);
    Scanner scanner  = new Scanner(System.in);

    int state     = 0; // Current frame
    int nextState = 0; // Next frame

    // Maps logical state to frame index
    int[] frameState =
    {
      fc.indexStart,     // 0
      fc.indexOrder,     // 1
      fc.indexSale,      // 2
      fc.indexFood,      // 3
      fc.indexBeverage,  // 4
      fc.indexAddons,    // 5
      fc.indexPayment    // 6
    };

    // Parent state for "back" navigation
    int[] parentState =
    {
      0, // 0 Start has no parent
      0, // 1 Order -> Start
      0, // 2 Sale  -> Start
      1, // 3 Food  -> Order
      1, // 4 Beverage -> Order
      4, // 5 Addons -> Beverage
      0  // 6 Payment (assigned dynamically)
    };

    int input = 0; // input

    boolean selectFood = false;  // food is selected
    boolean voidingItem = false; // if void the item in order list
    boolean payment = false;     // open payment
    boolean cash = false;        // cash is selected
    boolean card = false;        // card is selected

    double amountEntered = 0;

    int selectedAddons = 0;
    int selectedItem   = 0;

    // Draw initial frame
    System.out.print(fc.frame[frameState[0]]);

out:
    while (true)
    {
      if (state != 0 && state != 2) // if not start and sale state
      {
        System.out.print(draw.displayItem(draw.orderPanel)); // prompt items in order panel
      }

      System.out.print(draw.inputPanel.panel); // prompt input panel

      String charInput = scanner.nextLine(); // read input

      if (payment)
      {
        if (charInput.equals("q")) // Quit program
        {
          break out;
        }
        else if (charInput.equals("b")) // Go back without voiding orders
        {
          // reset input
          cash = false;
          card = false;
          payment = false;
          amountEntered = 0;

          // set state to previous state and display the panel
          state = parentState[state];
          System.out.print(fc.frame[frameState[state]]);
          continue;
        }

        // Select payment method
        if (!cash && !card)
        {
          if (charInput.equals("c")) // select cash
          {
            cash = true;
            amountEntered = 0;
          }
          else if (charInput.equals("a")) // select card
          {
            card = true;
          }

          // re-render the payment panel after input
          System.out.print(draw.renderPayment(draw.paymentPanel, amountEntered, card, cash));
          continue;
        }

        // Confirm payment
        if (charInput.equals("Y"))
        {
          item.paid();
          item.voidItem();

          // reset input and exit payment panel
          state = parentState[state];
          amountEntered = 0;
          cash = false;
          card = false;
          payment = false;

          System.out.print(fc.frame[frameState[state]]);
          continue;
        }

        // Cash input
        if (cash && charInput.matches("\\d+(\\.\\d+)?")) // regex matching for digit and decimal string
        {
          amountEntered = Double.parseDouble(charInput); // parse string into double
        }

        // re-render the payment panel
        System.out.print(draw.renderPayment(draw.paymentPanel, amountEntered, card, cash));
        continue;
      }

      if (charInput.matches("\\d+")) // regex matching for digit string
      {
        input = Integer.parseInt(charInput); // parse string into int

        // Start / Order navigation
        if ((state == 0 || state == 1) && (input >= 0 || input <= 2)) // state is either start or order and input 1 and 2
        {
          nextState = input; // set next state as 1 for order and 2 for sale
          System.out.print(fc.frame[frameState[state] + input]); // switch focus of option to input

          continue;
        }
        // Food selection
        else if (state == 3 && (input >= 0 && input <= item.getFood().length))  // state food
        {
          System.out.print(fc.frame[fc.indexFood + input]); // change option focus
          selectFood = true;
          selectedItem = input;
          continue;
        }
        // Beverage selection
        else if (state == 4 && (input >= 0 && input <= item.getBeverage().length)) // state beverage
        {
          System.out.print(fc.frame[frameState[state] + input]); // change option focus
          nextState = state + 1;
          selectedItem = input;
          continue;
        }
        // Add-ons selection
        else if (state == 5 && (input >= 0 && input <= item.getAddons().length)) // state addon
        {
          System.out.print(fc.frame[frameState[state] + input]);
          selectedAddons = input;
        }
      }
      else // char input
      {
        switch (charInput)
        {
          case "q":  // quit
            break out;

          case "b":  // back
            // render state to parent
            state = parentState[state];
            System.out.print(fc.frame[frameState[state]]);
            break;

          case "p":  // payment
            parentState[6] = state; // set parent state as current state
            state = 6;  // set state to payment
            System.out.print(fc.frame[frameState[state]]);
            System.out.print(draw.renderPayment(draw.paymentPanel, amountEntered, false, false)); // initialize payment
            payment = true;
            continue;

          case "v":  // void items
            voidingItem = true;
          case "":  // confirmation
            if (voidingItem)  // confirm void
            {
              item.voidItem();
              voidingItem = false;
            }

            if (nextState > 0) // confirm change state
            {
              state = nextState;
              System.out.print(fc.frame[frameState[state]]);
              nextState = 0;

              if (state == 2) // confirm and render all items and total sale
              {
                System.out.print(draw.renderSale(draw.salePanel));
              }
              continue;
            }

            System.out.print(fc.frame[frameState[state]]);

            // Register item
            if (selectFood && selectedItem != 0)
            {
              item.registerItem(1, selectedItem - 1); // register food
            }
            else if (!selectFood && selectedItem != 0 && selectedAddons == 1)
            {
              item.registerItem(2, selectedItem - 1);  // register cold beverage
            }
            else if (!selectFood && selectedItem != 0 && selectedAddons == 2)
            {
              item.registerItem(3, selectedItem - 1);  // register hot beverage
            }

            // reset input
            selectFood   = false;
            selectedAddons = 0;
            continue;

          default:
            System.out.print(draw.inputPanel.panel);
        }
      }
    }

    // Cleanup
    scanner.close();
    System.out.print(ascii.eraseEntireScreen);
  }
}

