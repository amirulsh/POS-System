
package components;

public class Option
{
  private final AsciiCode ascii = new AsciiCode();
  private final TextFormatter formatter;
  private final Box box;

  private final String backgroundColor;
  private final String optionBoxColor;
  private final String focusOptionColor;
  private final String defaultOptionBorderColor;
  private final String focusOptionBorderColor;

  private final int optionBoxWidthMin = 12;
  private final int optionBoxHeightMin = 4;

  private int optionBoxWidth;
  private int optionBoxHeight;

  public Option(
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



  private int[] positioningOptionBox(Panel panel, String[] optionList)
  {
    int panelWidth = panel.width;
    int panelHeight = panel.height;

    int optionCount = optionList.length;
    int[] optionPosition = new int[optionCount * 2];

    if (panelWidth - 2 < optionBoxWidthMin || panelHeight - 2 < optionBoxHeightMin)
    {
      return new int[]{0};
    }

    int maxFitWidth = (panelWidth - 2)/ optionBoxWidthMin;
    int maxFitHeight = (panelHeight  - 2)/ optionBoxHeightMin;

    if (maxFitWidth * maxFitHeight < optionCount)
    {
      return new int[]{0};
    }

    int fitWidth = (int) Math.ceil(Math.sqrt(optionCount));
    int fitHeight = (int) Math.ceil((double) optionCount / fitWidth);

    optionBoxWidth = (panelWidth - 2) / fitWidth - 1;
    optionBoxHeight = (int) Math.round((double) optionBoxHeightMin / optionBoxWidthMin * optionBoxWidth);

    if (optionBoxHeight * fitHeight > panelHeight - fitHeight - 1)
    {
      optionBoxHeight = (panelHeight - 2) / fitHeight - 1;
      optionBoxWidth = (int) Math.round((double) optionBoxWidthMin / optionBoxHeightMin * optionBoxHeight);
    }

    optionBoxWidth = Math.min(optionBoxWidth, optionBoxWidthMin * 3);
    optionBoxHeight = Math.min(optionBoxHeight, optionBoxHeightMin * 3);

    int startX = panel.x + (panelWidth - optionBoxWidth * fitWidth - (fitWidth - 1)) / 2;

    int startY = panel.y + (panelHeight - optionBoxHeight * fitHeight - (fitHeight - 1)) / 2;

    for (int row = 0; row < fitHeight; row++)
    {
      for (int column = 0; column < fitWidth; column++)
      {
        int index = row * fitWidth + column;
        if (index >= optionCount) break;

        optionPosition[index * 2] =
          startX + column * (optionBoxWidth + 1);
        optionPosition[index * 2 + 1] =
          startY + row * (optionBoxHeight + 1);
      }
    }

    return optionPosition;
  }



  public String[] createOptions(Panel panel, String[] optionList)
  {
    int[] optionPosition = positioningOptionBox(panel, optionList);
    String[] optionBoxList = new String[optionList.length + 1];

    if (optionPosition.length == 1)
    {
      return optionBoxList;
    }

    String optionBox =
      box.drawBox(
          optionBoxWidth,
          optionBoxHeight,
          1,
          defaultOptionBorderColor,
          optionBoxColor,
          backgroundColor
          );

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
      for (int i = 1; i <= optionPosition.length; i+=2)
      {
        String[] textList = formatter.wrapText(optionBoxWidth - 2, optionBoxHeight - 2, optionList[i / 2]);
        String text = formatter.alignCenter(optionBoxWidth - 2, optionBoxHeight - 2, textList);
        int textHeight = formatter.getTextHeight();
        if (i / 2 + 1 != focus)
        {
          options+= ascii.cursorTo(optionPosition[i - 1], optionPosition[i]) + optionBox;
          options+= optionBoxColor + defaultOptionBorderColor
            + ascii.moveCursor(1, 1) + (i / 2 + 1) 
            + ascii.moveCursor(-1, (optionBoxHeight - 2 - textHeight) / 2) 
            + text;
        }
        else
        {
          options+= ascii.cursorTo(optionPosition[i - 1], optionPosition[i]) + focusedOptionBox;
          options+= focusOptionColor + focusOptionBorderColor
            + ascii.moveCursor(1, 1) + (i / 2 + 1) 
            + ascii.moveCursor(-1, (optionBoxHeight - 2 - textHeight) / 2) 
            + text;
        }
      }
      optionBoxList[focus] = options;
    }
    return optionBoxList;
  }
}

