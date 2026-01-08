package components;

public class Box
{
  AsciiCode ac = new AsciiCode();

  private int backgroundWidth;

  public Box(int width) {
    this.backgroundWidth = width;
  }

  public String DrawBox(int width, int height, int borderType, String borderColor, String fillColor, String backgroundColor)
  {
    String middlePart;
    String boxConstruct;
    String horizonLine = "";
    String fill = "";
    String backgroundBorderReset = ac.MoveCursor(0, 1) + ac.ResetColor(true, true);
    String backgroundBorder = ac.CursorToColumn(backgroundWidth) + ac.ResetColor(true, true) + backgroundBorderReset;
    String moveBelow = ac.MoveCursor(-width, 1);


    for (int i = 0; i < width - 2; i++)
    {
      horizonLine+=ac.boxBorderHorizon[borderType];
      fill+=" ";
    }

    middlePart = moveBelow 
      + fillColor + borderColor
      + ac.boxBorderVertical[borderType]
      + fill
      + ac.boxBorderVertical[borderType] 
      + backgroundColor;

    boxConstruct = ac.saveCursor
      + fillColor + borderColor
      + ac.boxBorderTopLeftCorner[borderType] 
      + horizonLine
      + ac.boxBorderTopRightCorner[borderType]
      + backgroundColor;

    for (int i = 0; i < height - 2; i++)
    {
      boxConstruct+=middlePart;
      backgroundBorder+= backgroundBorderReset;
    }

    boxConstruct+= moveBelow
      + fillColor + borderColor
      + ac.boxBorderBottomLeftCorner[borderType] 
      + horizonLine
      + ac.boxBorderBottomRightCorner[borderType] 
      + backgroundColor
      + backgroundBorder
      + ac.loadCursor;

    return boxConstruct;
  }
}
