package components;

public class Box
{
  AsciiCode ascii = new AsciiCode();

  // Width of the full terminal background
  // Used to reset cursor and colors after drawing a box
  private int backgroundWidth;

  public Box(int width)
  {
    // Store terminal width so box drawing does not leak styles
    this.backgroundWidth = width;
  }

  /*
   * Draws a rectangular box using ASCII border characters.
   *
   * width  : box width including borders
   * height : box height including borders
   * borderType : index for selecting border character set
   * borderColor : ANSI color applied to border characters
   * fillColor   : ANSI background color for box interior
   * backgroundColor : ANSI color to restore after drawing
   */
  public String drawBox(
    int width,
    int height,
    int borderType,
    String borderColor,
    String fillColor,
    String backgroundColor
  )
  {
    String middlePart; // One interior line of the box (vertical borders + fill)
    String boxConstruct; // Final string containing the entire box drawing
    String horizonLine = ""; // Horizontal line used for top and bottom borders
    String fill = ""; // Fill content between vertical borders


    // Moves cursor down one line and resets both fg/bg colors
    String backgroundBorderReset = ascii.moveCursor(0, 1) + ascii.resetColor(true, true);

    // Moves cursor to far right edge and resets colors
    // Prevents color leak into terminal background
    String backgroundBorder = ascii.cursorToColumn(backgroundWidth)
      + ascii.resetColor(true, true)
      + backgroundBorderReset;

    // Moves cursor left by box width and down one line
    String moveBelow = ascii.moveCursor(-width, 1);

    // Build horizontal border and interior fill
    for (int i = 0; i < width - 2; i++)
    {
      horizonLine += ascii.boxBorderHorizon[borderType];
      fill += " ";
    }

    // One middle row of the box
    middlePart = moveBelow
      + fillColor
      + borderColor
      + ascii.boxBorderVertical[borderType]
      + fill
      + ascii.boxBorderVertical[borderType]
      + backgroundColor;

    // Top border
    boxConstruct = ascii.saveCursor
      + fillColor
      + borderColor
      + ascii.boxBorderTopLeftCorner[borderType]
      + horizonLine
      + ascii.boxBorderTopRightCorner[borderType]
      + backgroundColor;

    // Middle rows
    for (int i = 0; i < height - 2; i++)
    {
      boxConstruct += middlePart;
      backgroundBorder += backgroundBorderReset;
    }

    // Bottom border
    boxConstruct += moveBelow
      + fillColor
      + borderColor
      + ascii.boxBorderBottomLeftCorner[borderType]
      + horizonLine
      + ascii.boxBorderBottomRightCorner[borderType]
      + backgroundColor
      + backgroundBorder
      + ascii.loadCursor;

    return boxConstruct;
  }
}

