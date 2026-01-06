package components;

public class Draw
{
  private String backgroundCache;
  public void BuildBackground(int width, int height)
  {
    Box box = new Box();

    int firstPanelWidth = (width - 2) * 6 / 10;
    int firstPanelHeight = (height - 2) * 8 / 10;


    String panelColor =  "\u001B[48;5;110m";
    String backgroundColor = "\u001B[48;5;15m";

    String borderColor = "\u001B[38;5;17m";
    backgroundCache = "\u001B[H\u001B[2J";
    backgroundCache+= box.ConstructBox(
        width,
        height,
        2,
        borderColor,
        backgroundColor,
        "\u001B[49m"
        );
    backgroundCache+="\u001B[2;2H";
    backgroundCache+= box.ConstructBox(
        firstPanelWidth,
        firstPanelHeight,
        2,
        borderColor,
        panelColor,
        backgroundColor
        );
    backgroundCache+="\u001B[2;" + (firstPanelWidth+3) +"H";
    backgroundCache+= box.ConstructBox(
        width - firstPanelWidth - 3,
        height - 2,
        2,
        borderColor,
        panelColor,
        backgroundColor
        );
    backgroundCache+="\u001B[" + (firstPanelHeight+3) + ";2H";
    backgroundCache+= box.ConstructBox(
        firstPanelWidth,
        height - firstPanelHeight - 3,
        2,
        borderColor,
        panelColor,
        backgroundColor
        );
  }
  public void DrawBackground()
  {
    System.out.print(backgroundCache);
  }

}
