package components;

public class Box
{
  AsciiCode ascii = new AsciiCode();

  private int backgroundWidth;

  public Box(int width) {
    this.backgroundWidth = width;
  }

  public String drawBox(int width, int height, int borderType, String borderColor, String fillColor, String backgroundColor)
  {
    String middlePart;
    String boxConstruct;
    String horizonLine = "";
    String fill = "";
    String backgroundBorderReset = ascii.moveCursor(0, 1) + ascii.resetColor(true, true);
    String backgroundBorder = ascii.cursorToColumn(backgroundWidth) + ascii.resetColor(true, true) + backgroundBorderReset;
    String moveBelow = ascii.moveCursor(-width, 1);


    for (int i = 0; i < width - 2; i++)
    {
      horizonLine+= ascii.boxBorderHorizon[borderType];
      fill+=" ";
    }

    middlePart = moveBelow 
      + fillColor + borderColor
      + ascii.boxBorderVertical[borderType]
      + fill
      + ascii.boxBorderVertical[borderType] 
      + backgroundColor;

    boxConstruct = ascii.saveCursor
      + fillColor + borderColor
      + ascii.boxBorderTopLeftCorner[borderType] 
      + horizonLine
      + ascii.boxBorderTopRightCorner[borderType]
      + backgroundColor;

    for (int i = 0; i < height - 2; i++)
    {
      boxConstruct+=middlePart;
      backgroundBorder+= backgroundBorderReset;
    }

    boxConstruct+= moveBelow
      + fillColor + borderColor
      + ascii.boxBorderBottomLeftCorner[borderType] 
      + horizonLine
      + ascii.boxBorderBottomRightCorner[borderType] 
      + backgroundColor
      + backgroundBorder
      + ascii.loadCursor;

    return boxConstruct;
  }
}

