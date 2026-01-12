package components;

public class FrameCache
{

  Draw draw;
  Item item = new Item();
  AsciiCode ac = new AsciiCode();
  public FrameCache(int backgroundWidth, int backgroundHeight, Draw draw)
  {
    this.draw = draw;
    ConstructFrame();
  }


  public String[] frame = new String[100];
  public int nextFrame = 0;
  public int indexStart;
  public int indexOrder;
  public int indexFood;
  public int indexBeverage;
  public int indexAddons;
  public int indexSale;
  public int indexPayment;

  private void ExpandFocus(String[] optionList, String panel, String background, String title, int width, int panelX, int panelY, String textColor, String panelColor)
  {
    String optionTitle = textColor + panelColor + ac.bold + ac.CursorTo((width - title.length()) / 2 + panelX, panelY) + title + ac.resetBold;
    frame[nextFrame] = background + optionList[0] + optionTitle;
    int i;
    for (i = 1; i < optionList.length; i++)
    {
      int index = nextFrame + i;
      frame[index] = panel + optionList[i] + optionTitle;
    }
    nextFrame = nextFrame + i;
  }

  private void ConstructFrame()
  {
    String[] category = item.GetCategory();
    String[] food = item.GetFood();
    String[] beverage = item.GetBeverage();
    String[] addons = item.GetAddons();
    String[] startOption = {"Order", "Sale"};


    startOption = draw.CreateOptions(startOption, draw.startPanelWidth, draw.firstPanelHeight);
    category = draw.CreateOptions(category, draw.firstPanelWidth, draw.firstPanelHeight);
    food = draw.CreateOptions(food, draw.firstPanelWidth, draw.firstPanelHeight);
    beverage = draw.CreateOptions(beverage, draw.firstPanelWidth, draw.firstPanelHeight);
    addons = draw.CreateOptions(addons, draw.firstPanelWidth, draw.firstPanelHeight);
    indexStart = nextFrame;

    String black = ac.RgbColor(false, 0, 0, 0);
    String gray = ac.RgbColor(false, 80, 80, 80);
    String generalBind = draw.panelColor + ac.CursorTo(draw.keybindsX, draw.inputBoxY) + black + ac.bold + "q" + gray + ac.resetBold + "uit"
      + ac.MoveCursor(4, 0) + black + ac.bold + "b" + gray + ac.resetBold + "ack";

    String voidBind = ac.CursorTo(draw.keybindsX, draw.inputBoxY + 2) + black + ac.bold + "v" + gray + ac.resetBold + "oid";
    String selectBind = ac.CursorTo(draw.keybindsX, draw.inputBoxY + 1) + gray + "select "  + black + "<Num>";
    String paymentBind =  ac.CursorTo(draw.keybindsX, draw.inputBoxY + 1) + black + ac.bold + "c" + gray + ac.resetBold + "ash"
       + ac.MoveCursor(4, 0) + gray + "c" + black + ac.bold + "a" + gray + ac.resetBold + "rd";


    ExpandFocus(startOption, draw.startPanel, draw.startBackground, "POS", draw.startPanelWidth, draw.startPanelX, draw.startPanelY, draw.startBorderColor, draw.startPanelColor);
    frame[0]+= draw.startGuidePanel + generalBind + selectBind;
    indexOrder = nextFrame;
    ExpandFocus(category, draw.firstPanel, draw.orderBackground, "Category", draw.firstPanelWidth, draw.firstPanelX, draw.firstPanelX, draw.borderColor, draw.panelColor);
    frame[indexOrder] += draw.guidePanel + generalBind + voidBind + selectBind;
    indexFood = nextFrame;
    ExpandFocus(food, draw.firstPanel, draw.orderBackground, "Food", draw.firstPanelWidth, draw.firstPanelX, draw.firstPanelY, draw.borderColor, draw.panelColor);
    frame[indexFood] += draw.guidePanel + generalBind + voidBind + selectBind;
    indexBeverage = nextFrame;
    ExpandFocus(beverage, draw.firstPanel, draw.orderBackground, "Beverage", draw.firstPanelWidth, draw.firstPanelX, draw.firstPanelY, draw.borderColor, draw.panelColor);
    frame[indexBeverage] += draw.guidePanel + generalBind + voidBind + selectBind;
    indexAddons = nextFrame;
    ExpandFocus(addons, draw.firstPanel, draw.orderBackground, "Addons", draw.firstPanelWidth, draw.firstPanelX, draw.firstPanelY, draw.borderColor, draw.panelColor);
     frame[indexAddons] += draw.guidePanel + generalBind + voidBind + selectBind;
   indexPayment = nextFrame;
    frame[nextFrame++] = draw.guidePanel + draw.firstPanel + draw.paymentPanel + generalBind + paymentBind;
    indexSale = nextFrame;
    frame[nextFrame++] = draw.startGuidePanel + draw.startBackground + generalBind;

  }

}
