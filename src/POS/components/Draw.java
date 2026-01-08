package components;

public class Draw
{
  Box box = new Box();
  AsciiCode ac = new AsciiCode();
  
  public String background;
  private String fullBackground;

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

  private String panelColor =  "\u001B[48;5;110m";
  private String backgroundColor = "\u001B[48;5;15m";
  private String borderColor = "\u001B[38;5;17m";

  private void CreateBoxes(int width, int height)
  {
    background = box.ConstructBox(
        width,
        height,
        2,
        borderColor,
        backgroundColor,
        "\u001B[49m",
        width
        );

    firstPanel = box.ConstructBox(
        firstPanelWidth,
        firstPanelHeight,
        2,
        borderColor,
        panelColor,
        backgroundColor,
        width
        );

    secondPanel = box.ConstructBox(
        secondPanelWidth,
        secondPanelHeight,
        2,
        borderColor,
        panelColor,
        backgroundColor,
        width
        );

    thirdPanel = box.ConstructBox(
        thirdPanelWidth,
        thirdPanelHeight,
        2,
        borderColor,
        panelColor,
        backgroundColor,
        width
        );

    inputBox = ac.CursorTo(inputBoxX, inputBoxY);
    inputBox+= box.ConstructBox(
        inputBoxWidth,
        inputBoxHeight,
        1,
        borderColor,
        inputBoxColor,
        backgroundColor,
        width
        );
    inputBox+= ac.MoveCursor(1, 1) + ac.RgbColor(true, 255, 255, 255) + ac.RgbColor(false, 0, 100, 255);
  }

  public void PreConstruct(int width, int height)
  {
    firstPanelWidth = width * 6 / 10 - 2;
    firstPanelHeight = height * 8 / 10 - 2;

    secondPanelWidth = width - firstPanelWidth - 3;
    secondPanelHeight = height - 2;
    secondPanelX = firstPanelWidth + 3;

    thirdPanelWidth = firstPanelWidth;
    thirdPanelHeight = height - firstPanelHeight - 3;
    thirdPanelY = firstPanelHeight + 3;

    inputBoxWidth = thirdPanelWidth * 6 / 10 - 2;
    inputBoxX = thirdPanelX + 1;
    inputBoxY = thirdPanelY + 1;

    CreateBoxes(width, height);

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
