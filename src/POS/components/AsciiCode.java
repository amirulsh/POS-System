package components;
public class AsciiCode
{
  // these are ascii unicode of escape sequance to control cursor position, text color, etc

  private final String esc = "\u001B["; // escape

  public final String[] boxBorderHorizon = {"\u2500", "\u2501", "\u2550"}; // {"─", "━", "═"}
  public final String[] boxBorderVertical = {"\u2502", "\u2503", "\u2551"}; // {"│", "┃", "║"}
  public final String[] boxBorderTopLeftCorner = {"\u250C", "\u250F", "\u2554"}; // {"┌", "┏", "╔"}
  public final String[] boxBorderTopRightCorner = {"\u2510", "\u2513", "\u2557"}; // {"┐", "┓", "╗"}
  public final String[] boxBorderBottomLeftCorner = {"\u2514", "\u2517", "\u255A"}; // {"└", "┗", "╚"}
  public final String[] boxBorderBottomRightCorner = {"\u2518", "\u251B", "\u255D"}; // {"┘", "┛", "╝"}

  public final String cursorHome = "\u001B[H";       // moves cursor to top-left
  public final String eraseEntireScreen = "\u001B2[J"; // clears entire terminal
  public final String saveCursor = "\u001B[s";      // save current cursor position
  public final String loadCursor = "\u001B[u";      // restore saved cursor position
  public final String bold = "\u001B[1m";           // bold text
  public final String resetBold = "\u001B[22m";     // reset bold

  public String cursorToColumn(int x) // move cursor relative y position and absolute x position
  {
    return esc + x + "G";
  }

  public String cursorTo(int x, int y) // move cursor to absolute position in screen
  {
    return esc + y + ";" + x + "H";
  }

  public String moveCursor(int x, int y) // move cursor relative to current position
  {
    String move = "";
    if (x > 0) // move column
    {
      move+= esc + x + "C"; // move right
    }
    else if (x < 0)
    {
      move+= esc + (x * -1) + "D"; // move left
    }

    if (y > 0) // move row
    {
      move+= esc + y + "B"; // move bottom
    }
    else if (y < 0)
    {
      move+= esc + (y * -1) + "A"; // move top
    }

    return move;
  }

  public String rgbColor(boolean background, int r, int g, int b)
  {
    String rgb = r + ";" + g + ";" + b + "m"; // rgb combined
    if (background) 
    {
      return esc + "48;2;" + rgb; // background color
    }
    else 
    {
      return esc + "38;2;" + rgb; // foreground color | font color
    }
  }

  public String resetColor(boolean backgroundColor, boolean foregroundColor)
  {
    if (backgroundColor && foregroundColor)
    {
      return esc + "49;39m"; // reset both color
    }
    else if (backgroundColor)
    {
      return esc + "49m"; // reset background color
    }
    else
    {
      return esc + "39m"; // reset font color
    }
  }
}

