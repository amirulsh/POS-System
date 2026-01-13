package components;

public class Panel
{
  Box box;
  Panel parentPanel;
  AsciiCode ascii = new AsciiCode();
  public String panel;
  public int x = 1;
  public int y = 1;
  public int width = 0;
  public int height = 0;
  public String title = "";
  public String borderColor;
  public String fillColor;
  public boolean anchorCenter = false;
  public boolean anchorRight = false;
  public boolean anchorLeft = false;
  public boolean anchorTop = false;
  public boolean anchorBottom = false;
  public int borderType = 2;

  public Panel(Panel parentPanel, Box box)
  {
    this.parentPanel = parentPanel;
    this.box = box;
  }

  public void redraw()
  {
    if (anchorCenter)
    {
      x = (parentPanel.width - 2 - width) / 2 + parentPanel.x;
      y = (parentPanel.height - 2 - height) / 2 + parentPanel.y;
    }

    if (anchorLeft && anchorRight)
    {
      x = parentPanel.x + 1;
      width = parentPanel.width - 2;
    }
    else if (anchorLeft)
    {
      x = parentPanel.x + 1;
    }
    else if (anchorRight)
    {
      x = parentPanel.x + parentPanel.width - width - 1;
    }

    if (anchorTop && anchorBottom)
    {
      y = parentPanel.y + 1;
      height = parentPanel.height - 2;
    }
    else if (anchorBottom)
    {
      y = parentPanel.y + parentPanel.height - height - 1;
    }
    else if (anchorTop)
    {
      y = parentPanel.y + 1;
    }
    String backgroundColor;
    if (parentPanel == null)
    {
      backgroundColor = ascii.resetColor(true, false);
    }
    else
    {
      backgroundColor = parentPanel.fillColor;
    }

    panel = ascii.cursorTo(x,y) + box.drawBox(width, height, 2, borderColor, fillColor, backgroundColor);
    if (!title.isBlank()) panel += ascii.bold + fillColor + borderColor + ascii.moveCursor((width - title.length()) / 2, 0) + title + ascii.resetBold;
  }
}


