package components;
public class AsciiCode
{
  private String escapeCode = "\u001B[";

  public String[] boxBorderHorizon = {"\u2500", "\u2501", "\u2550"};
  public String[] boxBorderVertical = {"\u2502", "\u2503", "\u2551"};
  public String[] boxBorderTopLeftCorner = {"\u250C", "\u250F", "\u2554"};
  public String[] boxBorderTopRightCorner = {"\u2510", "\u2513", "\u2557"};
  public String[] boxBorderBottomLeftCorner = {"\u2514", "\u2517", "\u255A"};
  public String[] boxBorderBottomRightCorner = {"\u2519", "\u251B", "\u255D"};

  public String cursorHome = "\u001B[H";
  public String eraseEntireScreen = "\u001B2[J";

  public String saveCursor = "\u001B[s";
  public String loadCursor = "\u001B[u";

  public String CursorTo(int x, int y)
  {
    return escapeCode + y + ";" + x + "H";
  }

  public String CursorToColumn(int x)
  {
    return escapeCode + x + "G";
  }

  public String MoveCursor(int x, int y)
  {
    String move = "";
    if (x > 0)
    {
      move+= escapeCode + x + "C";
    }
    else if (x < 0)
    {
      move+= escapeCode + (x * -1) + "D";
    }

    if (y > 0)
    {
      move+= escapeCode + y + "B";
    }
    else if (y < 0)
    {
      move+= escapeCode + (y * -1) + "A";
    }

    return move;
  }

  public String RgbColor(boolean background, int r, int g, int b)
  {
    String rgb = r + ";" + g + ";" + b + "m";
    if (background) 
    {
      return escapeCode + "48;2;" + rgb;
    }
    else 
    {
      return escapeCode + "38;2;" + rgb;
    }
  }

  public String ResetColor(boolean backgroundColor, boolean foregroundColor)
  {
    if (backgroundColor && foregroundColor)
    {
      return escapeCode + "49;39m";
    }
    else if (backgroundColor)
    {
      return escapeCode + "49m";
    }
    else
    {
      return escapeCode + "49m";
    }
  }
}
