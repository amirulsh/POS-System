package components;

public class Option
{
  private final AsciiCode ascii = new AsciiCode();
  private final TextFormatter formatter;
  private final Box box;
  private final String backgroundColor;

  // Fill color of normal option boxes
  private final String optionBoxColor;

  // Fill color of the focused option box
  private final String focusOptionColor;

  // Border color for normal option boxes
  private final String defaultOptionBorderColor;

  // Border color for focused option box
  private final String focusOptionBorderColor;

  // Minimum dimensions allowed for option boxes
  private final int optionBoxWidthMin = 12;
  private final int optionBoxHeightMin = 4;

  // Actual computed dimensions for current layout
  private int optionBoxWidth;
  private int optionBoxHeight;

  public Option( // parameter
      Box box,
      TextFormatter formatter,
      String backgroundColor,
      String optionBoxColor,
      String focusOptionColor,
      String defaultOptionBorderColor,
      String focusOptionBorderColor
      )
  {
    this.box = box;
    this.formatter = formatter;
    this.backgroundColor = backgroundColor;
    this.optionBoxColor = optionBoxColor;
    this.focusOptionColor = focusOptionColor;
    this.defaultOptionBorderColor = defaultOptionBorderColor;
    this.focusOptionBorderColor = focusOptionBorderColor;
  }

  /*
   * Calculates (x, y) positions for each option box so they:
   * - Fit inside the panel
   * - Are evenly distributed in a grid
   * - Are centered horizontally and vertically
   *
   * Returns: ascii cursor to x,y position in string
   */
  private String[] positioningOptionBox(Panel panel, int optionCount)
  {
    int panelWidth = panel.width;
    int panelHeight = panel.height;

    String[] cursorList = new String[optionCount];

    // Abort if panel cannot even fit one minimum-size option
    if (panelWidth - 2 < optionBoxWidthMin || panelHeight - 2 < optionBoxHeightMin)
    {
      return null;
    }

    // Maximum number of boxes that can fit horizontally and vertically
    int maxFitWidth = (panelWidth - 2) / optionBoxWidthMin;
    int maxFitHeight = (panelHeight - 2) / optionBoxHeightMin;

    // Abort if total capacity is smaller than number of options
    if (maxFitWidth * maxFitHeight < optionCount)
    {
      return null;
    }

    // Try to form a roughly square grid
    int fitWidth = (int) Math.ceil(Math.sqrt(optionCount));
    int fitHeight = (int) Math.ceil((double) optionCount / fitWidth);

    // Compute option box width based on available panel space
    optionBoxWidth = (panelWidth - 2) / fitWidth - 1;

    // Maintain aspect ratio using minimum width/height ratio
    optionBoxHeight =
      (int) Math.round(
          (double) optionBoxHeightMin / optionBoxWidthMin * optionBoxWidth
          );

    // If height overflows panel, recompute using height as base
    if (optionBoxHeight * fitHeight > panelHeight - fitHeight - 1)
    {
      optionBoxHeight = (panelHeight - 2) / fitHeight - 1;
      optionBoxWidth =
        (int) Math.round(
            (double) optionBoxWidthMin / optionBoxHeightMin * optionBoxHeight
            );
    }

    // Clamp box size to prevent oversized options
    optionBoxWidth = Math.min(optionBoxWidth, optionBoxWidthMin * 3);
    optionBoxHeight = Math.min(optionBoxHeight, optionBoxHeightMin * 3);

    // Center the grid horizontally
    int startX = panel.x + (panelWidth - optionBoxWidth * fitWidth - (fitWidth - 1)) / 2;

    // Center the grid vertically
    int startY = panel.y + (panelHeight - optionBoxHeight * fitHeight - (fitHeight - 1)) / 2;

    // Assign positions row by row
    for (int row = 0; row < fitHeight; row++)
    {
      for (int column = 0; column < fitWidth; column++)
      {
        int index = row * fitWidth + column;
        if (index >= optionCount) break;

        cursorList[index] =
          ascii.cursorTo(
              startX + column * (optionBoxWidth + 1),
              startY + row * (optionBoxHeight + 1)
              );
      }
    }
    return cursorList;
  }

  /*
   * Builds all option states.
   *
   * Returns an array where:
   * - index 0 = no focus
   * - index N = option N focused
   *
   * Each element is a full ANSI-rendered frame segment.
   */
  public String[] createOptions(Panel panel, String[] optionList)
  {
    String[] cursorList = positioningOptionBox(panel, optionList.length);
    String[] optionBoxList = new String[optionList.length + 1];  // +1 for extra no focus

    // Layout failed → return empty frames
    if (cursorList == null)
    {
      return optionBoxList;
    }

    // Normal option box
    String optionBox =
      box.drawBox(
          optionBoxWidth,
          optionBoxHeight,
          1,
          defaultOptionBorderColor,
          optionBoxColor,
          backgroundColor
          );

    // Focused option box
    String focusedOptionBox =
      box.drawBox(
          optionBoxWidth,
          optionBoxHeight,
          1,
          focusOptionBorderColor,
          focusOptionColor,
          backgroundColor
          );


    for (int focus = 0; focus <= optionList.length; focus++)
    {
      String options = "";

      for (int i = 0; i < cursorList.length; i++)
      {
        // Draw box
        options += cursorList[i];

        boolean isFocused = (i + 1 == focus);

        options += isFocused ? focusedOptionBox : optionBox;

        // prepare text
        String text = formatter.alignCenter(
            optionBoxWidth - 2,
            optionBoxHeight - 2,
            formatter.wrapText(
              optionBoxWidth - 2,
              optionBoxHeight - 2,
              optionList[i])
            );

        int textHeight = formatter.getTextHeight(); // get the height so text fix the box height

        // Draw text and index
        options +=
          (isFocused ? focusOptionColor : optionBoxColor)
          + (isFocused ? focusOptionBorderColor : defaultOptionBorderColor)
          + ascii.moveCursor(1, 1)
          + (i + 1)
          + ascii.moveCursor(-1, (optionBoxHeight - 2 - textHeight) / 2)
          + text;
      }

      optionBoxList[focus] = options;
    }
    return optionBoxList;
  }
}

