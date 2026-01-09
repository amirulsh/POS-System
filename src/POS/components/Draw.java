package components;

public class Draw
{
  AsciiCode ac = new AsciiCode();
  Box box;

  public String background;
  private String fullBackground;
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

  public String thirdPanel;
  private int thirdPanelWidth;
  private int thirdPanelHeight;
  private int thirdPanelX = 2;
  private int thirdPanelY;

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
  private String defaultOptionBorderColor = ac.RgbColor(false, 255, 208, 0);

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

    thirdPanelWidth = firstPanelWidth;
    thirdPanelHeight = backgroundHeight - firstPanelHeight - 3;
    thirdPanelY = firstPanelHeight + 3;

    inputBoxWidth = thirdPanelWidth * 6 / 10 - 2;
    inputBoxX = thirdPanelX + 1;
    inputBoxY = thirdPanelY + 1;

    optionBoxWidth = Math.min((firstPanelWidth - 2) / 2 - 1, 20);
    optionBoxHeight = Math.min((firstPanelHeight - 2) / 2 - 1, 5);

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

    thirdPanel = ac.CursorTo(thirdPanelX, thirdPanelY);
    thirdPanel+= box.DrawBox(
        thirdPanelWidth,
        thirdPanelHeight,
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

    fullBackground = background
      + firstPanel
      + secondPanel
      + thirdPanel
      + inputBox;
  }

  public int[] positioningOptionBox(int[] optionList)
  {
    int optionCount = optionList.length;
    int[] optionPosition = new int[optionCount * 2];
    int fitHeight;
    int fitWidth;
    
    if (firstPanelWidth - 2 < optionBoxWidthMin || firstPanelHeight - 2 < optionBoxHeightMin)
    {
      return new int[]{0};
    }
    else 
    {
      int maxFitWidth = firstPanelWidth / optionBoxWidthMin;
      int maxFitHeight = firstPanelHeight / optionBoxHeightMin;

      if (maxFitWidth * maxFitHeight >= optionCount)
      {
        fitWidth = (int) Math.ceil((double) Math.sqrt(optionCount));
        fitHeight = (int) Math.ceil((double) optionCount / fitWidth);
      }
      else return new int[]{0};
    }

    optionBoxWidth = (firstPanelWidth - 2) / fitWidth - 1;
    optionBoxHeight = (int) Math.round((double) optionBoxHeightMin / optionBoxWidthMin * optionBoxWidth);

    if (optionBoxHeight * fitHeight > firstPanelHeight - fitHeight - 1)
    {
      optionBoxHeight = (firstPanelHeight - 2) / fitHeight - 1;
      optionBoxWidth = (int) Math.round((double) optionBoxWidthMin / optionBoxHeightMin * optionBoxHeight);

    }

    optionPosition[0] = firstPanelX + (firstPanelWidth - optionBoxWidth * fitWidth - (fitWidth - 1)) / 2;
    optionPosition[1] = firstPanelY + (firstPanelHeight - optionBoxHeight * fitHeight - (fitHeight - 1)) / 2;

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

  public String CreateOptions(int[] optionList)
  { 
    int[] optionPosition = positioningOptionBox(optionList);

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
    }
    return options;
  }

  public void DrawOptions(String options)
  {
    System.out.print(options);
  }

  public void DrawBackground()
  {
    System.out.print(fullBackground);
  }
}
