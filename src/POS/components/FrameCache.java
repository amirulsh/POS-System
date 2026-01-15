package components;

public class FrameCache
{
  Draw draw;
  Item item = new Item();
  AsciiCode ascii = new AsciiCode();
  Box box; // for create option

  Option option;

  TextFormatter formatter = new TextFormatter();

  public FrameCache(int backgroundWidth, int backgroundHeight, Draw draw)
  {
    this.draw = draw;

    // Box is initialized with terminal width for cursor cleanup
    this.box = new Box(backgroundWidth);

    // Option renderer configuration
    this.option = new Option(
      box, // pass box with background width
      formatter, // pass TextFormatter instead of construct new
      draw.menuPanel.fillColor,
      ascii.rgbColor(true, 255, 243, 178), // default color
      ascii.rgbColor(true, 255, 168, 128), // focus color
      ascii.rgbColor(false, 255, 153, 0), // default border color
      ascii.rgbColor(false, 255, 54, 0) // focus border color
    );

    // Build all frames once at startup
    ConstructFrame();
  }

  // Cache of all rendered frames
  public String[] frame = new String[100];

  // Pointer to the next free index in the frame array
  private int nextFrame = 0;

  // Entry points into the frame cache for each state, each index point to full-screen frame
  public int indexStart;
  public int indexOrder;
  public int indexFood;
  public int indexBeverage;
  public int indexAddons;
  public int indexSale;
  public int indexPayment;

  /*
   * Stores a sequence of frames representing focus movement.
   *
   * optionList[0] is the default (no focus movement).
   * optionList[1..n] are variations with focused options.
   *
   * background is added only to the first frame in the sequence.
   */
  private void expandFocus(String[] optionList, String background)
  {
    // First frame includes full background
    frame[nextFrame] = background + optionList[0];

    int i; // initialized here to pass to next frame
    for (i = 1; i < optionList.length; i++)
    {
      int index = nextFrame + i; // ignore first frame of options and start from nextFrame
      frame[index] = optionList[i]; 
    }

    // Advance frame pointer past this group
    nextFrame = nextFrame + i;
  }

  /*
   * Constructs every static UI frame used by the program.
   *
   * Frames are built once and reused by index for performance.
   * Each logical state points to a specific index range.
   */
  private void ConstructFrame()
  {
    // Menu data fetched from Item
    String[] category = item.getCategory();
    String[] food = item.getFood();
    String[] beverage = item.getBeverage();
    String[] addons = item.getAddons();

    // Start screen options
    String[] startOption = {"Order", "Sale"};

    // Common colors for keybind hints
    String black = ascii.rgbColor(false, 0, 0, 0);
    String gray  = ascii.rgbColor(false, 80, 80, 80);

    // Global keybinds shown on most screens
    String generalBind = draw.guidePanel.fillColor
      + ascii.cursorTo(draw.keybindsX, draw.inputPanel.y)
      + black + ascii.bold + "q" + gray + ascii.resetBold + "uit" // quit
      + ascii.moveCursor(4, 0)
      + black + ascii.bold + "b" + gray + ascii.resetBold + "ack"; // back

    // Void item keybind
    String voidBind = ascii.cursorTo(draw.keybindsX, draw.inputPanel.y + 2)
      + black + ascii.bold + "v" + gray + ascii.resetBold + "oid" // void
      + ascii.moveCursor(5, 0)
      + black + ascii.bold + "p" + gray + ascii.resetBold + "ay"; // pay

    // Numeric selection hint
    String selectBind = ascii.cursorTo(draw.keybindsX, draw.inputPanel.y + 1)
      + gray + "select " + black + "<Num>"; // select <Num>

    // Payment method keybinds
    String paymentBind = ascii.cursorTo(draw.keybindsX, draw.inputPanel.y + 1)
      + black + ascii.bold + "c" + gray + ascii.resetBold + "ash" // cash
      + ascii.moveCursor(4, 0)
      + gray + "c" + black + ascii.bold + "a" + gray + ascii.resetBold + "rd"; // card

    // Start screen frames
    expandFocus(
      option.createOptions(draw.startPanel, startOption),
      draw.startBackground
    );
    frame[0] += draw.guidePanelStart.panel + generalBind + selectBind;

    // Order category frames
    indexOrder = nextFrame;
    expandFocus(
      option.createOptions(draw.menuPanel, category),
      draw.orderBackground
    );
    frame[indexOrder] += draw.guidePanel.panel + generalBind + voidBind + selectBind;

    // Food frames
    indexFood = nextFrame;
    expandFocus(
      option.createOptions(draw.menuPanel, food),
      draw.orderBackground
    );
    frame[indexFood] += draw.guidePanel.panel + generalBind + voidBind + selectBind;

    // Beverage frames
    indexBeverage = nextFrame;
    expandFocus(
      option.createOptions(draw.menuPanel, beverage),
      draw.orderBackground
    );
    frame[indexBeverage] += draw.guidePanel.panel + generalBind + voidBind + selectBind;

    // Add-on frames
    indexAddons = nextFrame;
    expandFocus(
      option.createOptions(draw.menuPanel, addons),
      draw.orderBackground
    );
    frame[indexAddons] += draw.guidePanel.panel + generalBind + voidBind + selectBind;

    // Payment frame (single, no focus expansion)
    indexPayment = nextFrame;
    frame[nextFrame++] = draw.guidePanel.panel
      + draw.menuPanel.panel
      + draw.paymentPanel.panel
      + generalBind
      + paymentBind;

    // Sale screen frame
    indexSale = nextFrame;
    frame[nextFrame++] =
      draw.startBackground
      + draw.salePanel.panel
      + generalBind;
  }
}

