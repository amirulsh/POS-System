package components;

public class Draw
{
  private String backgroundCache;
  public void BuildBackground()
  {
    Box box = new Box();

    String backgroundColor = "\u001B[48;5;15m";
    String borderColor = "\u001B[38;5;17m";
      backgroundCache = "\u001B[H\u001B[2J";
    backgroundCache+= box.ConstructBox(
        120,
        30,
        2,
        borderColor,
        backgroundColor,
        "\u001B[49m"
        );
    backgroundCache+="\u001B[2;2H";
    backgroundCache+= box.ConstructBox(
        69,
        20,
        2,
        borderColor,
        "\u001B[48;5;110m",
        backgroundColor
        );
    backgroundCache+="\u001B[2;72H";
    backgroundCache+= box.ConstructBox(
        48,
        28,
        2,
        borderColor,
        "\u001B[48;5;110m",
        backgroundColor
        );
    backgroundCache+="\u001B[23;2H";
    backgroundCache+= box.ConstructBox(
        69,
        7,
        2,
        borderColor,
        "\u001B[48;5;110m",
        backgroundColor
        );
  }
  public void DrawBackground()
  {
    System.out.print(backgroundCache);
  }

}
