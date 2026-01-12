package components;

public class Draw
{
  AsciiCode ac = new AsciiCode();
  TextFormatter tf = new TextFormatter();
  Box box;

  public String startPanel;
  public int startPanelWidth;
  private int startPanelHeight;
  private int startPanelX = 2;
  private int startPanelY = 2;
  private String startPanelColor = ac.RgbColor(true, 121, 255, 181);
  private String startBorderColor = ac.RgbColor(false, 2, 147, 0);

  public String background;
  public String startBackground;
  public String orderBackground;
  private int backgroundWidth;
  private int backgroundHeight;

  public String firstPanel;
  public int firstPanelWidth;
  public int firstPanelHeight;
  private int firstPanelX = 2;
  private int firstPanelY = 2;

  public String secondPanel;
  public int secondPanelWidth;
  public int secondPanelHeight;
  public int secondPanelX;
  public int secondPanelY = 2;

  public String guidePanel;
  public String startGuidePanel;
  private int guidePanelWidth;
  private int guidePanelHeight;
  private int guidePanelX = 2;
  private int guidePanelY;

  public String inputBox;
  private int inputBoxWidth;
  private int inputBoxHeight = 3;
  private int inputBoxX;
  private int inputBoxY;
  private String inputBoxColor = ac.RgbColor(true, 255, 255, 255);

  private int optionBoxWidth;
  private int optionBoxHeight;
  private int optionBoxWidthMin = 12;
  private int optionBoxHeightMin = 4;
  public String[] startOptions;
  
  public String paymentPanel;
  private int paymentPanelWidth;
  private int paymentPanelHeight;
  private int paymentPanelX;
  private int paymentPanelY;

  private String focusOptionColor = ac.RgbColor(true, 255, 168, 128);
  private String optionBoxColor = ac.RgbColor(true, 249, 255, 165);
  private String focusOptionBorderColor = ac.RgbColor(false, 255, 54, 0);
  private String defaultOptionBorderColor = ac.RgbColor(false, 255, 108, 0);

  private String panelColor = ac.RgbColor(true, 130, 200, 255);
  private String backgroundColor = ac.RgbColor(true, 255, 255, 255);
  private String borderColor = ac.RgbColor(false, 0, 0, 255);

  public double totalPriceAfterSst;

  public Draw(int width, int height) {
    this.backgroundWidth = width;
    this.backgroundHeight = height;

    firstPanelWidth = backgroundWidth * 6 / 10 - 2;

    firstPanelHeight = backgroundHeight - 8;

    secondPanelWidth = backgroundWidth - firstPanelWidth - 3;
    secondPanelHeight = backgroundHeight - 2;
    secondPanelX = firstPanelWidth + 3;

    guidePanelWidth = firstPanelWidth;
    guidePanelHeight = backgroundHeight - firstPanelHeight - 3;
    guidePanelY = backgroundHeight - guidePanelHeight;

    startPanelWidth = backgroundWidth - 2;
    startPanelHeight = backgroundHeight - 2 - guidePanelHeight;

    inputBoxWidth = guidePanelWidth * 6 / 10 - 2;
    inputBoxX = guidePanelX + 1;
    inputBoxY = guidePanelY + 1;

    paymentPanelHeight = firstPanelHeight - 4;
    paymentPanelWidth = firstPanelWidth - 4;
    paymentPanelY = firstPanelY + 2;
    paymentPanelX = firstPanelX + 2;



    box = new Box(backgroundWidth);

    CreateBoxes(width, height);
  }  

  private void CreateBoxes(int width, int height)
  {
    background =  ac.eraseEntireScreen + ac.cursorHome;
    background+= box.DrawBox(
        width,
        height,
        2,
        borderColor,
        backgroundColor,
        ac.ResetColor(true, false)
        );

    guidePanel = ac.CursorTo(guidePanelX, guidePanelY);
    guidePanel+= box.DrawBox(
        guidePanelWidth,
        guidePanelHeight,
        2,
        borderColor,
        panelColor,
        backgroundColor
        );

    startGuidePanel = ac.CursorTo(guidePanelX, guidePanelY);
    startGuidePanel+= box.DrawBox(
        startPanelWidth,
        guidePanelHeight,
        2,
        borderColor,
        panelColor,
        backgroundColor
        );

    startPanel = ac.CursorTo(startPanelX, startPanelY);
    startPanel+= box.DrawBox(
        startPanelWidth, 
        startPanelHeight,
        2, 
        startBorderColor, 
        startPanelColor, 
        backgroundColor);

    firstPanel =  ac.CursorTo(firstPanelX, firstPanelY);
    firstPanel+= box.DrawBox(
        firstPanelWidth,
        firstPanelHeight,
        2,
        borderColor,
        panelColor,
        backgroundColor
        );

    secondPanel =  ac.CursorTo(secondPanelX, secondPanelY);
    secondPanel+= box.DrawBox(
        secondPanelWidth,
        secondPanelHeight,
        2,
        borderColor,
        panelColor,
        backgroundColor
        );

    inputBox = ac.CursorTo(inputBoxX, inputBoxY);
    inputBox+= box.DrawBox(
        inputBoxWidth,
        inputBoxHeight,
        0,
        borderColor,
        inputBoxColor,
        backgroundColor
        );
    inputBox+= ac.MoveCursor(1, 1) + ac.RgbColor(true, 255, 255, 255) + ac.RgbColor(false, 0, 100, 255);

    paymentPanel = ac.CursorTo(paymentPanelX, paymentPanelY);
    paymentPanel+= box.DrawBox(
        paymentPanelWidth,
        paymentPanelHeight,
        1,
        borderColor,
        inputBoxColor,
        backgroundColor
        );
    

    startBackground = background
      + startPanel
      + startGuidePanel;

    orderBackground = background
      + firstPanel
      + secondPanel
      + guidePanel;

  }

  public int[] positioningOptionBox(String[] optionList, int panelWidth, int panelHeight)
  {
    int optionCount = optionList.length;
    int[] optionPosition = new int[optionCount * 2];
    int fitHeight;
    int fitWidth;
    
    if (panelWidth - 2 < optionBoxWidthMin || panelHeight - 2 < optionBoxHeightMin)
    {
      return new int[]{0};
    }
    else 
    {
      int maxFitWidth = panelWidth / optionBoxWidthMin;
      int maxFitHeight = panelHeight / optionBoxHeightMin;

      if (maxFitWidth * maxFitHeight >= optionCount)
      {
        fitWidth = (int) Math.ceil((double) Math.sqrt(optionCount));
        fitHeight = (int) Math.ceil((double) optionCount / fitWidth);
      }
      else return new int[]{0};
    }

    optionBoxWidth = (panelWidth - 2) / fitWidth - 1;
    optionBoxHeight = (int) Math.round((double) optionBoxHeightMin / optionBoxWidthMin * optionBoxWidth);

    if (optionBoxHeight * fitHeight > panelHeight - fitHeight - 1)
    {
      optionBoxHeight = (panelHeight - 2) / fitHeight - 1;
      optionBoxWidth = (int) Math.round((double) optionBoxWidthMin / optionBoxHeightMin * optionBoxHeight);
    }

    optionPosition[0] = 2 + (panelWidth - optionBoxWidth * fitWidth - (fitWidth - 1)) / 2;
    optionPosition[1] = 2 + (panelHeight - optionBoxHeight * fitHeight - (fitHeight - 1)) / 2;

    for (int row = 0; row < fitHeight; row++) {
      for (int column = 0; column < fitWidth; column++) {
        int index = row * fitWidth + column;
        if (index >= optionCount) break;

        optionPosition[index * 2] = optionPosition[0] + column * (optionBoxWidth + 1);
        optionPosition[index * 2 + 1] = optionPosition[1] + row * (optionBoxHeight + 1);
      }
    }

    return optionPosition;
  }

  public String[] CreateOptions(String[] optionList, int panelWidth, int panelHeight)
  { 
    optionBoxWidth = Math.min((panelWidth - 2) / 2 - 1, 20);
    optionBoxHeight = Math.min((panelHeight - 2) / 2 - 1, 5);
    int[] optionPosition = positioningOptionBox(optionList, panelWidth, panelHeight);
    String[] optionBoxList = new String[optionList.length + 1];

    String optionBox;
    String focusedOptionBox;

    optionBox = box.DrawBox(
        optionBoxWidth,
        optionBoxHeight,
        1,
        defaultOptionBorderColor,
        optionBoxColor,
        backgroundColor
        );

    focusedOptionBox = box.DrawBox(
        optionBoxWidth,
        optionBoxHeight,
        1,
        focusOptionBorderColor,
        focusOptionColor,
        backgroundColor
        );

    
    for (int focus = 0; focus <= optionList.length; focus++)
    {
    String options = "";
    for (int i = 1; i <= optionPosition.length; i+=2)
    {
      String[] textList = tf.WrapText(optionBoxWidth - 2, optionBoxHeight - 2, optionList[i / 2]);
      String text = tf.AlignCenter(optionBoxWidth - 2, optionBoxHeight - 2, textList);
      int textHeight = tf.GetTextHeight();
      if (i / 2 + 1 != focus)
      {
        options+= ac.CursorTo(optionPosition[i - 1], optionPosition[i]) + optionBox;
        options+= optionBoxColor + defaultOptionBorderColor
          + ac.MoveCursor(1, 1) + (i / 2 + 1) 
          + ac.MoveCursor(-1, (optionBoxHeight - 2 - textHeight) / 2) 
          + text;
      }
      else
      {
        options+= ac.CursorTo(optionPosition[i - 1], optionPosition[i]) + focusedOptionBox;
        options+= focusOptionColor + focusOptionBorderColor
          + ac.MoveCursor(1, 1) + (i / 2 + 1) 
          + ac.MoveCursor(-1, (optionBoxHeight - 2 - textHeight) / 2) 
          + text;
      }
    }
    optionBoxList[focus] = options;
    }
    return optionBoxList;
  }



  public String DisplayItem(Item item)
  {
    String[] itemList = item.GetList();
    double[] priceList = item.GetPriceList();
    int[] itemCount = item.GetCountList();
    if(item.lastIndex == 0) return "";

    String[] itemLine = new String[itemList.length * 2];
    String[] priceLine = new String[priceList.length * 2];

    double totalPrice = 0;
    for (int i = 0; i < itemList.length; i++)
    {
      int ii = i * 2;
      double price = priceList[i];
      int count = itemCount[i];
      double totalItemPrice = price * count;
      totalPrice+= price * count;
      if (ii > secondPanelHeight - 7) continue;
      
      itemLine[ii] = itemList[i];
      itemLine[ii + 1] = " x " + count;
      priceLine[ii] = "RM" + String.format("%.2f", price) + "       ";
      priceLine[ii + 1] = "RM" + String.format("%.2f", totalItemPrice);
      
      
    }
    int totalPriceDisplayY = secondPanelY + secondPanelHeight - 5;
    int totalPriceDisplayX = secondPanelX + secondPanelWidth - 1;
    double sstPrice = totalPrice * 0.06;
    totalPriceAfterSst = totalPrice + sstPrice;
    String formatedTotalPrice = String.format("%.2f", totalPrice);
    String formatedSstPrice = String.format("%.2f", sstPrice);
    String formatedPriceAfterSst = String.format("%.2f", totalPriceAfterSst);

    String priceDisplay = tf.AlignRight(priceLine);
    int priceWidth = priceLine[0].length();
    String itemDisplay = ac.CursorTo(secondPanelX + 1, secondPanelY + 1)
      + panelColor + borderColor
      + tf.AlignLeft(itemLine)
      + ac.CursorTo(secondPanelX + secondPanelWidth - 2 - priceWidth, secondPanelY + 1)
      + priceDisplay
      + ac.CursorTo(totalPriceDisplayX - 7 - formatedTotalPrice.length(), totalPriceDisplayY) 
      + "Total: " + formatedTotalPrice
      + ac.MoveCursor(-(5 + formatedSstPrice.length()), 1) 
      + "sst: " + formatedSstPrice
      + ac.MoveCursor(-(12 + formatedPriceAfterSst.length()), 1) 
      + "Full Price: " + formatedPriceAfterSst;
    
    return itemDisplay;
  }

  public String RenderPayment(double input)
  {
    String output = paymentPanel + ac.MoveCursor(1, 1) 
      + "Total Price: RM" + String.format("%.2f", totalPriceAfterSst);
    if (input > 0)
    {
      double balance = input - totalPriceAfterSst;
      output+= ac.CursorTo(paymentPanelX + 1, paymentPanelY + 2) 
        + "Entered: RM" + String.format("%.2f", input)
        + ac.CursorTo(paymentPanelX + 1, paymentPanelY + 3)
        + "Balance: RM" + String.format("%.2f", balance)
        + ac.CursorTo(paymentPanelX + 1, paymentPanelY + 5)
        + "Proceed? Y/N";
      
    }
    return output;
  }
}
