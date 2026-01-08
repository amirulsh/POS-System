package components;

public class TextFormatter
{
  AsciiCode ac = new AsciiCode();


  public String[] WrapText(int width, int height, String text)
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
    return newTextList;
  }

  public String AlignCenter(int width, int height, String text)
  {
    String[] wrappedText = WrapText(width, height, text);
    String line;
    String alignedLine = "";

    int padding = ((width - wrappedText[0].length()) / 2);
    String lineStart = ac.MoveCursor(padding, 0);
    alignedLine = lineStart + wrappedText[0];
    
    int startNextLength = wrappedText[0].length() + padding;

    for (int i = 1; i < wrappedText.length; i++)
    {
      line = wrappedText[i];
      int length = line.length();
      padding = ((width - length) / 2);

      alignedLine+= ac.MoveCursor(-(startNextLength) + padding, 1) + line;
      startNextLength = length + padding;
    }
    return alignedLine;
  }

  public String AlignLeft(int width, int height, String text)
  {
    String[] wrappedText = WrapText(width, height, text);
    String line;
    String alignedLine = "";
    for (int i = 0; i < wrappedText.length; i++)
    {
      line = wrappedText[i];
      alignedLine+= line;
      if (i == wrappedText.length - 1) return alignedLine;
      alignedLine+= ac.MoveCursor(-(line.length()), 1);
    }
    return alignedLine;
  }

  public String AlignRight(int width, int height, String text)
  {
    String[] wrappedText = WrapText(width, height, text);
    String line = wrappedText[0];
    String alignedLine = line;


    for (int i = 1; i < wrappedText.length; i++)
    {
      line = wrappedText[i];
      String startLineAt = ac.MoveCursor(-(line.length()), 1);
      alignedLine+= startLineAt + line;
    }
    return alignedLine;
  }
}
