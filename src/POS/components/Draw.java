package components;

public class Draw
{
  AsciiCode ac = new AsciiCode();
  TextFormatter tf = new TextFormatter();
  Box box;


  public String startPanel;
  private int startPanelWidth;
  private int startPanelHeight;
  private int startPanelX = 2;
  private int startPanelY = 2;
  private String startPanelColor = ac.RgbColor(true, 121, 255, 181);
  private String startBorderColor = ac.RgbColor(false, 2, 147, 0);

  public String background;
  private String startBackground;
  private String orderBackground;
  private int backgroundWidth;
  private int backgroundHeight;

  public String firstPanel;
  private int firstPanelWidth;
  private int firstPanelHeight;
  private int firstPanelX = 2;
  private int firstPanelY = 2;

  public String secondPanel;
  private int secondPanelWidth;
  private int secondPanelHeight;
  private int secondPanelX;
  private int secondPanelY = 2;

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

  public String optionBox;
  private String focusedOptionBox;
  private int optionBoxWidth;
  private int optionBoxHeight;
  private int optionBoxWidthMin = 12;
  private int optionBoxHeightMin = 4;
  

  private String optionBoxColor = ac.RgbColor(true, 249, 255, 165);
  private String focusOptionBorderColor = ac.RgbColor(false, 255, 133, 0);
  private String defaultOptionBorderColor = ac.RgbColor(false, 255, 108, 0);

  private String panelColor = ac.RgbColor(true, 130, 200, 255);
  private String backgroundColor = ac.RgbColor(true, 255, 255, 255);
  private String borderColor = ac.RgbColor(false, 0, 0, 255);


  public Draw(int width, int height) {
    this.backgroundWidth = width;
    this.backgroundHeight = height;



    firstPanelWidth = backgroundWidth * 6 / 10 - 2;
    firstPanelHeight = backgroundHeight * 8 / 10 - 2;

    secondPanelWidth = backgroundWidth - firstPanelWidth - 3;
    secondPanelHeight = backgroundHeight - 2;
    secondPanelX = firstPanelWidth + 3;

    guidePanelWidth = firstPanelWidth;
    guidePanelHeight = backgroundHeight - firstPanelHeight - 3;
    guidePanelY = firstPanelHeight + 3;

    startPanelWidth = backgroundWidth - 2;
    startPanelHeight = backgroundHeight - 2 - guidePanelHeight;

    inputBoxWidth = guidePanelWidth * 6 / 10 - 2;
    inputBoxX = guidePanelX + 1;
    inputBoxY = guidePanelY + 1;



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

    String[] category = {"Order", "Sales"};
    startBackground = background
      + startPanel
      + startGuidePanel
      + CreateOptions(category, startPanelWidth, startPanelHeight)
      + inputBox;

    category = new String[]{"Food", "Beverage"};
    orderBackground = background
      + firstPanel
      + secondPanel
      + guidePanel
      + CreateOptions(category, firstPanelWidth, firstPanelHeight)
      + inputBox;

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

  public String CreateOptions(String[] optionList, int panelWidth, int panelHeight)
  { 
    optionBoxWidth = Math.min((panelWidth - 2) / 2 - 1, 20);
    optionBoxHeight = Math.min((panelHeight - 2) / 2 - 1, 5);
    int[] optionPosition = positioningOptionBox(optionList, panelWidth, panelHeight);

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
        optionBoxColor,
        backgroundColor
        );

    String options = "";

    for (int i = 1; i < optionPosition.length; i+=2)
    {
      options+= ac.CursorTo(optionPosition[i - 1], optionPosition[i]) + optionBox;
      String text = tf.AlignCenter(optionBoxWidth - 2, optionBoxHeight - 2, optionList[i / 2]);
     int textHeight = tf.GetTextHeight();
      options+= optionBoxColor + defaultOptionBorderColor
        + ac.MoveCursor(1, 1) + (i / 2 + 1) 
        + ac.MoveCursor(-1, (optionBoxHeight - 2 - textHeight) / 2) 
        + text;
    }
    return options;
  }

  public void DrawOptions(String options)
  {
    System.out.print(options);
  }

  public void DrawStart()
  {
    System.out.print(startBackground);
  }
  public void DrawOrder()
  {
    System.out.print(orderBackground);
  }
}
