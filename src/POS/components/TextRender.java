package components;

public class TextRender 
{

  AsciiCode ac = new AsciiCode();

  public String TextFitter(int width, int height, String text)
  {
    String fitText = "";
    char character;
    char[] textChar = text.toCharArray();
    String line = "";
    int lineCount = 1;


    int splitOffsetIndex = 0;
    int splitIndex = 0;

    for (int i = 0; i < text.length(); i++) 
    {
      character = textChar[i];
      if (character == ' ' || character == '/' || character == '.' || character == ',')
      {
        splitIndex = i - splitOffsetIndex;


        continue;
      }

      if (i - splitOffsetIndex >= width) 
      {
        if (splitIndex != 0) 
        {
          if (splitIndex >= width){
            line = new String(textChar, splitOffsetIndex, splitIndex);
            splitOffsetIndex+= splitIndex;
          }
          else
          {
            line = new String(textChar, splitOffsetIndex, width);
            splitOffsetIndex+= width;
          }
          fitText+= line;


          splitIndex = 0;
          if (lineCount == height)
          {
            if (i > splitIndex)
            {
              int gap = width - splitIndex;
              if (gap > 3)
              {
                fitText+= "...";
              }
              else
              {
                fitText+= ac.MoveCursor((gap), 0) + "...";
              }
            }
            return fitText;
          }

          lineCount++;
          fitText+= ac.MoveCursor(-(line.length()), 1);
        }
      }
    }
    if (fitText != "")
    {
      return fitText;
    }
    return fitText;
  }
}
