package components;

public class Box
{
  private String[] boxBorderHorizon = {"\u2500", "\u2501", "\u2550"};
  private String[] boxBorderVertical = {"\u2502", "\u2503", "\u2551"};
  private String[] boxBorderTopLeftCorner = {"\u250C", "\u250F", "\u2554"};
  private String[] boxBorderTopRightCorner = {"\u2510", "\u2513", "\u2557"};
  private String[] boxBorderBottomLeftCorner = {"\u2514", "\u2517", "\u255A"};
  private String[] boxBorderBottomRightCorner = {"\u2519", "\u251B", "\u255D"};

  public String ConstructBox(int width, int height, int borderType, String borderColor, String fillColor, String backgroundColor)
  {
    String horizonLine = "";
    String fill = "";
    String middlePart;
    String boxConstruct;

    for (int i = 0; i < width - 2; i++)
    {
      horizonLine+=boxBorderHorizon[borderType];
      fill+=" ";
    }

    middlePart = "\u001B["+width+"D\u001B[1B" 
      + fillColor + borderColor
      + boxBorderVertical[borderType]
      + fill 
      + boxBorderVertical[borderType] 
      + backgroundColor;

    boxConstruct = fillColor + borderColor
      + boxBorderTopLeftCorner[borderType] 
      + horizonLine
      + boxBorderTopRightCorner[borderType]
      + backgroundColor;

    for (int i = 0; i < height - 2; i++)
    {
      boxConstruct+=middlePart;
    }

    boxConstruct+= "\u001B["+width+"D\u001B[1B" 
      + fillColor + borderColor
      + boxBorderBottomLeftCorner[borderType] 
      + horizonLine
      + boxBorderBottomRightCorner[borderType] 
      + backgroundColor
      + "\u001B["+width+"D" + "\u001B["+(height-1)+"A";

    return boxConstruct;
  }
}
