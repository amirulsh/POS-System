package components;

public class Draw
{
  Box box = new Box();
  
  private String background;
  private String fullBackground;

  private String firstPanel;
  private int firstPanelWidth;
  private int firstPanelHeight;
  private int firstPanelX = 2;
  private int firstPanelY = 2;

  private String secondPanel;
  private int secondPanelWidth;
  private int secondPanelHeight;
  private int secondPanelX;
  private int secondPanelY = 2;

  private String thirdPanel;
  private int thirdPanelWidth;
  private int thirdPanelHeight;
  private int thirdPanelX = 2;
  private int thirdPanelY;

  private String panelColor =  "\u001B[48;5;110m";
  private String backgroundColor = "\u001B[48;5;15m";
  private String borderColor = "\u001B[38;5;17m";

  public void PreConstruct(int width, int height)
  {
    firstPanelWidth = (width - 2) * 6 / 10;
    firstPanelHeight = (height - 2) * 8 / 10;

    secondPanelWidth = width - firstPanelWidth - 3;
    secondPanelHeight = height - 2;
    secondPanelX = firstPanelWidth + 3;

    thirdPanelWidth = firstPanelWidth;
    thirdPanelHeight = height - firstPanelHeight - 3;
    thirdPanelY = firstPanelHeight + 3;

    background = box.ConstructBox(
        width,
        height,
        2,
        borderColor,
        backgroundColor,
        "\u001B[49m"
        );

    firstPanel = box.ConstructBox(
        firstPanelWidth,
        firstPanelHeight,
        2,
        borderColor,
        panelColor,
        backgroundColor
        );

    secondPanel = box.ConstructBox(
        secondPanelWidth,
        secondPanelHeight,
        2,
        borderColor,
        panelColor,
        backgroundColor
        );

    thirdPanel = box.ConstructBox(
        thirdPanelWidth,
        thirdPanelHeight,
        2,
        borderColor,
        panelColor,
        backgroundColor
        );

    fullBackground = "\u001B[2J\u001B[H"
      + background
      + "\u001B[" + firstPanelY + ";" + firstPanelX + "H"
      + firstPanel
      + "\u001B[" + secondPanelY + ";" + secondPanelX + "H"
      + secondPanel
      + "\u001B[" + thirdPanelY + ";" + thirdPanelX + "H"
      + thirdPanel;
  }

  public void DrawBackground()
  {
    System.out.print(fullBackground);
  }

}
