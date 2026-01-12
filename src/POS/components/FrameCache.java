package components;

public class FrameCache
{

  Draw draw;
  Item item = new Item();
  public FrameCache(int backgroundWidth, int backgroundHeight)
  {
    draw = new Draw(backgroundWidth, backgroundHeight);
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

  private void ExpandFocus(String[] optionList, String panel, String background)
  {
    frame[nextFrame] = background + optionList[0];
    int i;
    for (i = 1; i < optionList.length; i++)
    {
      int index = nextFrame + i;
      frame[index] = panel + optionList[i];
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

    ExpandFocus(startOption, draw.startPanel, draw.startBackground);
    indexOrder = nextFrame;
    ExpandFocus(category, draw.firstPanel, draw.orderBackground);
    indexFood = nextFrame;
    ExpandFocus(food, draw.firstPanel, draw.orderBackground);
    indexBeverage = nextFrame;
    ExpandFocus(beverage, draw.firstPanel, draw.orderBackground);
    indexAddons = nextFrame;
    ExpandFocus(addons, draw.firstPanel, draw.orderBackground);
    indexPayment = nextFrame;
    frame[nextFrame++] = draw.firstPanel + draw.paymentPanel;
    indexSale = nextFrame;
    frame[nextFrame++] = draw.startBackground;

  }

}
