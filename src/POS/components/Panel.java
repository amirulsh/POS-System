package components;

public class Panel
{
  Box box;

  // Parent panel used for relative positioning (anchoring)
  Panel parentPanel;

  AsciiCode ascii = new AsciiCode();

  public String panel; // Fully rendered panel (ANSI string)

  // Top-left position of the panel (1-based terminal coords)
  public int x = 1;
  public int y = 1;

  // Panel size implicit if anchor not ovewrite
  public int width = 0;
  public int height = 0;

  // Optional title displayed on the top border
  public String title = "";

  // Colors used when drawing the panel
  public String borderColor;
  public String fillColor;

  // Anchor flags control how the panel positions itself
  public boolean anchorCenter = false;
  public boolean anchorRight = false;
  public boolean anchorLeft = false;
  public boolean anchorTop = false;
  public boolean anchorBottom = false;

  // Border style index used by Box
  public int borderType = 2;

  // Create a panel relative to a parent panel
  public Panel(Panel parentPanel, Box box)
  {
    this.parentPanel = parentPanel;
    this.box = box;
  }

  /*
   * Recalculates position and size based on anchors,
   * then redraws the panel as an ANSI string.
   *
   * This must be called whenever:
   * - parent panel changes size
   * - anchor flags change
   * - panel dimensions change
   */
  public void redraw()
  {
    String backgroundColor; // Background color behind the panel border
    if (parentPanel != null)
    {
      // Nested panels inherit parent fill color
      backgroundColor = parentPanel.fillColor;
      // Center panel within parent
      if (anchorCenter)
      {
        x = (parentPanel.width  - 2 - width)  / 2 + parentPanel.x;
        y = (parentPanel.height - 2 - height) / 2 + parentPanel.y;
      }

      // Stretch horizontally if anchored to both sides
      if (anchorLeft && anchorRight)
      {
        x = parentPanel.x + 1;
        width = parentPanel.width - 2;
      }
      else if (anchorLeft)
      {
        x = parentPanel.x + 1; // Stick to left edge
      }
      else if (anchorRight)
      {
        x = parentPanel.x + parentPanel.width - width - 1; // Stick to right edge
      }

      // Stretch vertically if anchored to both top and bottom
      if (anchorTop && anchorBottom)
      {
        y = parentPanel.y + 1;
        height = parentPanel.height - 2;
      }
      else if (anchorBottom)
      {
        y = parentPanel.y + parentPanel.height - height - 1; // Stick to bottom edge
      }
      else if (anchorTop)
      {
        y = parentPanel.y + 1; // Stick to top edge
      }
    }
    else
    {
      // background panel uses terminal background
      backgroundColor = ascii.resetColor(true, false);
    }

    // Draw panel box at computed position
    panel =
      ascii.cursorTo(x, y)
      + box.drawBox(
          width,
          height,
          borderType,
          borderColor,
          fillColor,
          backgroundColor
          );

    // Draw title centered on top border if present
    if (!title.isBlank())
    {
      panel +=
        ascii.bold
        + fillColor
        + borderColor
        + ascii.moveCursor((width - title.length()) / 2, 0)
        + title
        + ascii.resetBold;
    }
  }
}
