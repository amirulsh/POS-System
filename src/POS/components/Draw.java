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

    box = new Box(backgroundWidth);

    CreateBoxes(width, height);
  }  

  private void CreateBoxes(int width, int height)
  {
    background = box.DrawBox(
        width,
        height,
        2,
        borderColor,
        backgroundColor,
        ac.ResetColor(true, false)
        );

    firstPanel = box.DrawBox(
        firstPanelWidth,
        firstPanelHeight,
        2,
        borderColor,
        panelColor,
        backgroundColor
        );

    secondPanel = box.DrawBox(
        secondPanelWidth,
        secondPanelHeight,
        2,
        borderColor,
        panelColor,
        backgroundColor
        );

    thirdPanel = box.DrawBox(
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
        1,
        borderColor,
        inputBoxColor,
        backgroundColor
        );
    inputBox+= ac.MoveCursor(1, 1) + ac.RgbColor(true, 255, 255, 255) + ac.RgbColor(false, 0, 100, 255);

    fullBackground = ac.eraseEntireScreen + ac.cursorHome 
      + background
      + ac.CursorTo(firstPanelX, firstPanelY)
      + firstPanel
      + ac.CursorTo(secondPanelX, secondPanelY)
      + secondPanel
      + ac.CursorTo(thirdPanelX, thirdPanelY)
      + thirdPanel
      + inputBox;
  }

  public void DrawBackground()
  {
    System.out.print(fullBackground);
  }
}
