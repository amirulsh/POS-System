package components;
public class AsciiCode
{
  private final String escapeCode = "\u001B[";

  public final String[] boxBorderHorizon = {"\u2500", "\u2501", "\u2550"};
  public final String[] boxBorderVertical = {"\u2502", "\u2503", "\u2551"};
  public final String[] boxBorderTopLeftCorner = {"\u250C", "\u250F", "\u2554"};
  public final String[] boxBorderTopRightCorner = {"\u2510", "\u2513", "\u2557"};
  public final String[] boxBorderBottomLeftCorner = {"\u2514", "\u2517", "\u255A"};
  public final String[] boxBorderBottomRightCorner = {"\u2518", "\u251B", "\u255D"};

  public final String cursorHome = "\u001B[H";
  public final String eraseEntireScreen = "\u001B2[J";

  public final String saveCursor = "\u001B[s";
  public final String loadCursor = "\u001B[u";
  public final String bold = "\u001B[1m";
  public final String resetBold = "\u001B[22m";

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
