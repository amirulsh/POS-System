
package components;

public class TextFormatter
{
  AsciiCode ac = new AsciiCode();

  private int textHeight; // Stores the height (number of lines) of the last formatted text

  /* wrapText()
   * Wraps a string into multiple lines that fit within a given width and height.
   *
   * Functions:
   * - Tries to split at spaces or punctuation when possible
   * - Falls back to hard cut if no split point exists
   * - Adds "..." if text exceeds available height
   *
   * Parameters:
   * width  - max characters per line
   * height - max number of lines
   * text - input string
   *
   * Returns: String[] of wrapped lines
   */
  public String[] wrapText(int width, int height, String text)
  {
    String[] textList = new String[height];
    char[] textChar = text.toCharArray();

    int textLength = text.length();
    int lineIndex = 0; // index in textList
    int lineCount = 1; // line count literally, will stored in textHeight

    int splitOffset = 0; // where current line starts in textList
    int splitLength = 0;// how many char in line it will split for new line
    char splitChar = '\0'; // character used for splitting

    // exit early if already fit 
    if (textLength <= width)
    {
      return new String[]{ text };
    }

    for (int i = 0; i < textLength; i++)
    {
      char character = textChar[i];

      // Record valid split points
      if (character == ' ' || character == '/' || character == '.' || character == ',')
      {
        splitLength = Math.max(i - splitOffset, 0); 
        splitChar = character;
      }

      // Line overflow or end of text
      if (i - splitOffset >= width || i == textLength - 1)
      {
        // Height limit reached then truncate
        if (lineCount == height)
        {
          textHeight = lineCount + 1;

          // Add ellipsis if text still remains
          if (textLength >= splitOffset + width + 1)
          {
            textList[lineIndex] =
              new String(textChar, splitOffset, width - 3) + "...";
          }
          else // if no remains then no ellipsis
          {
            textList[lineIndex] =
              new String(textChar, splitOffset, textLength - splitOffset);
          }
          return textList;
        }

        String line;

        // Split at recorded split point
        if (splitLength > 0) // split length is assigned
        {
          if (splitChar != ' ') // not space
          {
            splitLength++; // keep punctuation
          }

          line = new String(textChar, splitOffset, splitLength);

          // Skip space
          if (splitChar == ' ')
          {
            splitOffset++;
          }

          splitOffset += splitLength;
        }
        else // split length not assigned then cut relative to width
        {
          line = new String(textChar, splitOffset, textLength - splitOffset);
          splitOffset += width;
        }

        splitLength = 0;
        textList[lineIndex++] = line;
        lineCount++;
      }
    }

    // Trim unused array space
    String[] newTextList = new String[lineIndex];
    for (int i = 0; i < newTextList.length; i++)
    {
      newTextList[i] = textList[i];
    }

    textHeight = lineCount;
    return newTextList;
  }

  /* alignCenter()
   * Centers wrapped text inside a given width and height.
   *
   * Uses cursor movement instead of spaces.
   *
   * Updates:
   *   textWidth  - widest line
   *   textHeight - number of lines rendered
   */
  public String alignCenter(int width, int height, String[] textList)
  {
    String alignedLine = "";

    int textLength = textList[0].length();
    int padding = (width - textLength) / 2;

    // First line
    alignedLine = ac.moveCursor(padding, 0) + textList[0];
    int startNextLength = textLength + padding;

    int i; // init here to assign to textHeight
    for (i = 1; i < textList.length; i++) // iterate line start from 1
    {
      String line = textList[i];
      if (line == null) break;

      textLength = line.length();
      padding = (width - textLength) / 2; // find start position for each line

      alignedLine += ac.moveCursor(-(startNextLength) + padding, 1)
        + line;

      startNextLength = textLength + padding; 
      // store current length of text + padding here so next line can move left relative to last cursor position
    }

    textHeight = i;
    return alignedLine;
  }

  /*
   * Left-aligns text vertically.
   *
   * Each line starts at the same column.
   */
  public String alignLeft(String[] textList)
  {
    String alignedLine = "";
    int i;

    for (i = 0; i < textList.length; i++)
    {
      String line = textList[i];
      if (line == null) break;

      int textLength = line.length();

      alignedLine += line;

      // Move cursor down while staying aligned left
      if (i < textList.length - 1)
      {
        alignedLine += ac.moveCursor(-textLength, 1);
      }
    }

    textHeight = i;
    return alignedLine;
  }

  /*
   * Right-aligns text.
   *
   * Each new line starts aligned to the right edge
   * of the previous line.
   */
  public String alignRight(String[] textList)
  {
    String alignedLine = textList[0];
    int textLength = textList[0].length();


    int i;
    for (i = 1; i < textList.length; i++)
    {
      String line = textList[i];
      if (line == null) break;

      textLength = line.length(); // text length of current line

      alignedLine += ac.moveCursor(-textLength, 1) + line; // move cursor to left from last position to line of current length
    }

    textHeight = i;
    return alignedLine;
  }

  /*
   * Returns and resets last computed text height.
   */
  public int getTextHeight()
  {
    int returnHeight = textHeight;
    textHeight = 0;
    return returnHeight;
  }
}

