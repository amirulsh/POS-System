package components;

public class TextFormatter
{
  AsciiCode ac = new AsciiCode();


  public String TextWrapper(int width, int height, String text)
  {
    String fitText = "";
    char character;
    char[] textChar = text.toCharArray();
    String line = "";
    int lineCount = 1;
    int textLength  = text.length();


    int splitOffset = 0;
    int splitLength = 0;
    char splitChar = '\0';

    if (textLength < width) return text;

    for (int i = 0; i < textLength; i++) 
    {
      character = textChar[i];
      if (character == ' ' || character == '/' || character == '.' || character == ',')
      {
        splitLength = Math.max(i - splitOffset, 0);
        splitChar = character;
        continue;
      }


      if (i - splitOffset >= width || i == textLength - 1) 
      {
        if(lineCount == height)
        {
          if (textLength >= splitOffset + width + 2)
          {
            return fitText + new String(textChar, splitOffset, (width - 2)) + "...";
          }
          else
          {
            return fitText + new String(textChar, splitOffset, textLength - splitOffset);
          }
        }

        if (splitLength > 0) 
        {
          line = new String(textChar, splitOffset, splitLength);
          if (splitChar == ' ')
          {
            splitOffset+= 1;
          }
          splitOffset+= splitLength ;
        }
        else
        {
          line = new String(textChar, splitOffset, width);
          splitOffset+= width;
        }

        splitLength = 0;
        lineCount++;
        fitText+= line;
        fitText+= ac.MoveCursor(-(line.length()), 1);
      }
    }
    return fitText;
  }
}
