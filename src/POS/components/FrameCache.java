package components;

public class FrameCache
{

  Draw draw;
  Item item = new Item();
  AsciiCode ascii = new AsciiCode();
  Box box;
  Option option;
  TextFormatter formatter = new TextFormatter();
  public FrameCache(int backgroundWidth, int backgroundHeight, Draw draw)
  {
    this.draw = draw;
    this.box = new Box(backgroundWidth);
    this.option = new Option(
      box,
      formatter,
      draw.menuPanel.fillColor,
      ascii.rgbColor(true, 255, 243, 178),
      ascii.rgbColor(true, 255, 168, 128),
      ascii.rgbColor(false, 255, 153, 0),
      ascii.rgbColor(false, 255, 54, 0)
      );
    ConstructFrame();
  }


  public String[] frame = new String[100];
  private int nextFrame = 0;
  public int indexStart;
  public int indexOrder;
  public int indexFood;
  public int indexBeverage;
  public int indexAddons;
  public int indexSale;
  public int indexPayment;

  private void expandFocus(String[] optionList, String background)
  {
    frame[nextFrame] = background + optionList[0];
    int i;
    for (i = 1; i < optionList.length; i++)
    {
      int index = nextFrame + i;
      frame[index] = optionList[i];
    }
    nextFrame = nextFrame + i;
  }

  private void ConstructFrame()
  {
    String[] category = item.getCategory();
    String[] food = item.getFood();
    String[] beverage = item.getBeverage();
    String[] addons = item.getAddons();
    String[] startOption = {"Order", "Sale"};




    String black = ascii.rgbColor(false, 0, 0, 0);
    String gray = ascii.rgbColor(false, 80, 80, 80);
    String generalBind = draw.guidePanel.fillColor + ascii.cursorTo(draw.keybindsX, draw.inputPanel.y) + black + ascii.bold + "q" + gray + ascii.resetBold + "uit"
      +ascii.moveCursor(4, 0) + black + ascii.bold + "b" + gray + ascii.resetBold + "ack";

    String voidBind = ascii.cursorTo(draw.keybindsX, draw.inputPanel.y + 2) + black + ascii.bold + "v" + gray + ascii.resetBold + "oid";
    String selectBind = ascii.cursorTo(draw.keybindsX, draw.inputPanel.y + 1) + gray + "select "  + black + "<Num>";
    String paymentBind = ascii.cursorTo(draw.keybindsX, draw.inputPanel.y + 1) + black + ascii.bold + "c" + gray + ascii.resetBold + "ash"
      +ascii.moveCursor(4, 0) + gray + "c" + black + ascii.bold + "a" + gray + ascii.resetBold + "rd";


    expandFocus(option.createOptions(draw.startPanel, startOption), draw.startBackground);
    frame[0]+= draw.guidePanelStart.panel + generalBind + selectBind;
    indexOrder = nextFrame;
    expandFocus(option.createOptions(draw.menuPanel, category), draw.orderBackground);
    frame[indexOrder] += draw.guidePanel.panel + generalBind + voidBind + selectBind;
    indexFood = nextFrame;
    expandFocus(option.createOptions(draw.menuPanel, food), draw.orderBackground);
    frame[indexFood] += draw.guidePanel.panel + generalBind + voidBind + selectBind;
    indexBeverage = nextFrame;
    expandFocus(option.createOptions(draw.menuPanel, beverage), draw.orderBackground);
    frame[indexBeverage] += draw.guidePanel.panel + generalBind + voidBind + selectBind;
    indexAddons = nextFrame;
    expandFocus(option.createOptions(draw.menuPanel, addons), draw.orderBackground);
    frame[indexAddons] += draw.guidePanel.panel + generalBind + voidBind + selectBind;
    indexPayment = nextFrame;
    frame[++nextFrame] = draw.guidePanel.panel + draw.menuPanel.panel + draw.paymentPanel.panel + generalBind + paymentBind;
    indexSale = nextFrame;
    frame[nextFrame++] =  draw.startBackground + draw.salePanel.panel + generalBind;

  }

}
