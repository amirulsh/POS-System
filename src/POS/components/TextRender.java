package components;

public class TextRender 
{

  AsciiCode ac = new AsciiCode();

  public String TextWrapper(int width, int height, String text)
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
        splitIndex = Math.max(i - splitOffsetIndex, 0);
        continue;
      }

      if (i - splitOffsetIndex >= width || i == text.length() - 1) 
      {
        if (i == text.length() - 1) 
        {
          splitIndex = text.length() - splitOffsetIndex;
        }

        if (splitIndex > 0) 
        {
          line = new String(textChar, splitOffsetIndex, splitIndex);
          splitOffsetIndex+= splitIndex + 1;
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
    return text;
  }
}
