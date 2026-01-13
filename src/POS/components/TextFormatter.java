package components;

public class TextFormatter
{
  AsciiCode ac = new AsciiCode();

  private int textHeight;
  private int textWidth;

  public String[] wrapText(int width, int height, String text)
  {
    String[] textList = new String[height];
    char character;
    char[] textChar = text.toCharArray();
    String line = "";
    int lineCount = 1;
    int textLength  = text.length();
    int lineIndex = 0;

    int splitOffset = 0;
    int splitLength = 0;
    char splitChar = '\0';

    if (textLength <= width)
    {
      String[] oneLine = {text};
      return oneLine;
    }

    for (int i = 0; i < textLength; i++) 
    {
      character = textChar[i];
      if (character == ' ' || character == '/' || character == '.' || character == ',')
      {
        splitLength = Math.max(i - splitOffset, 0);
        splitChar = character;
      }


      if (i - splitOffset >= width || i == textLength - 1) 
      {
        if(lineCount == height)
        {
          textHeight = lineCount + 1;
          if (textLength >= splitOffset + width + 1)
          {
            textList[lineIndex] = new String(textChar, splitOffset, (width - 3)) + "...";
            return textList;
          }
          else
          {
            textList[lineIndex] = new String(textChar, splitOffset, textLength - splitOffset);
            return textList;
          }
        }

        if (splitLength > 0) 
        {
          if (splitChar != ' ')
          {
            splitLength++;
          }
          line = new String(textChar, splitOffset, splitLength);

          if (splitChar == ' ')
          {
            splitOffset++;
          }
          splitOffset+= splitLength ;
        }
        else
        {
          line = new String(textChar, splitOffset, (textLength - splitOffset));
          splitOffset+= width;
        }

        splitLength = 0;
        lineCount++;
        textList[lineIndex] = line;
        lineIndex++;
      }
    }
    String[] newTextList = new String[lineIndex];
    for (int i = 0; i < newTextList.length; i++)
    {
      newTextList[i] = textList[i];
    }
    textLength = lineCount;
    return newTextList;
  }

  public String alignCenter(int width, int height, String[] textList)
  {
    String line;
    String alignedLine = "";
    int textLength = textList[0].length();
    int padding = ((width - textLength) / 2);
    String lineStart = ac.moveCursor(padding, 0);
    alignedLine = lineStart + textList[0];
    int startNextLength = textLength + padding;
    int i;
    textWidth = Math.max(textWidth, textLength);
    for (i = 1; i < textList.length; i++)
    {
      line = textList[i];
      if (line == null) break;
      textLength = line.length();
      textWidth = Math.max(textWidth, textLength);

      padding = ((width - textLength) / 2);

      alignedLine+= ac.moveCursor(-(startNextLength) + padding, 1) + line;
      startNextLength = textLength + padding;
    }
    textHeight = i;
    return alignedLine;
  }

  public String alignLeft(String[] textList)
  {
    String line;
    String alignedLine = "";
    int i;
    for (i = 0; i < textList.length; i++)
    {
      line = textList[i];
      if (line == null) break;
      int textLength = line.length();
      textWidth = Math.max(textWidth, textLength);
      alignedLine+= line;
      if (i == textList.length - 1) return alignedLine;
      alignedLine+= ac.moveCursor(-(textLength), 1);
    }
    textHeight = i;
    return alignedLine;
  }

  public String alignRight(String[] textList)
  {
    String line = textList[0];
    String alignedLine = line;
    int textLength = line.length();
    textWidth = Math.max(textWidth, textLength);
    int i;
    for (i = 1; i < textList.length; i++)
    {
      
      line = textList[i]; 
      if (line == null) break;
      textLength = line.length();
      textWidth = Math.max(textWidth, textLength);
      String startLineAt = ac.moveCursor(-(line.length()), 1);
      alignedLine+= startLineAt + line;
    }
    textHeight = i;
    return alignedLine;
  }


  public int getTextHeight()
  {
    int returnHeight = textHeight;
    textHeight = 0;
    return returnHeight;
  }

  public int getTextWidth()
  {
    int returnWidth = textWidth;
    textWidth = 0;
    return returnWidth;
  }
}
