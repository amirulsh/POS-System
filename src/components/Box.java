package components;

public class Box
{
  public String ConstructBox(int width, int height, int borderType, String borderColor, String fillColor, String backgroundColor)
  {

    String[] boxBorderHorizon = {"\u2500", "\u2501", "\u2550"};
    String[] boxBorderVertical = {"\u2502", "\u2503", "\u2551"};
    String[] boxBorderTopLeftCorner = {"\u250C", "\u250F", "\u2554"};
    String[] boxBorderTopRightCorner = {"\u2510", "\u2513", "\u2557"};
    String[] boxBorderBottomLeftCorner = {"\u2514", "\u2517", "\u255A"};
    String[] boxBorderBottomRightCorner = {"\u2519", "\u251B", "\u255D"};

    String horizonLine = "";
    String fill = "";
    String middlePart;
    String boxConstruct;


    for (int i = 0; i < width - 2; i++)
    {
      horizonLine+=boxBorderHorizon[borderType];
      fill+=" ";
    }
    middlePart = "\u001B["+width+"D\u001B[1B" + fillColor + 
      borderColor + boxBorderVertical[borderType] + 
      fill + boxBorderVertical[borderType] + backgroundColor;
    
    boxConstruct = fillColor + borderColor + 
      boxBorderTopLeftCorner[borderType] + horizonLine + 
      boxBorderTopRightCorner[borderType] + backgroundColor;

    for (int i = 0; i < height - 2; i++)
    {
      boxConstruct+=middlePart;
    }
    boxConstruct+= "\u001B["+width+"D\u001B[1B" + fillColor + borderColor + 
      boxBorderBottomLeftCorner[borderType] + horizonLine +
      boxBorderBottomRightCorner[borderType] + backgroundColor + 
      "\u001B["+width+"D" + "\u001B["+height+"A";

    return boxConstruct;
  }
}
