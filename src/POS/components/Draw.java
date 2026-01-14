
package components;

public class Draw
{
  private final AsciiCode ascii = new AsciiCode();
  private final TextFormatter formatter = new TextFormatter();
  private final Box box;
  private final Item item;


  public final Panel background;
  public final Panel startPanel;
  public final Panel menuPanel;
  public final Panel orderPanel;
  public final Panel guidePanel;
  public final Panel guidePanelStart;
  public final Panel inputPanel;
  public final Panel paymentPanel;
  public final Panel salePanel;

  public String startBackground;
  public String orderBackground;

  public int keybindsX;

  public Draw(int width, int height, Item item)
  {
    this.item = item;
    this.box = new Box(width);
    String panelColor = ascii.rgbColor(true, 178, 254, 255);
    String borderColor = ascii.rgbColor(false, 0, 0, 255);


    background = new Panel(null, box);
    background.width = width;
    background.height = height;
    background.fillColor = ascii.rgbColor(true, 255, 255, 255);
    background.borderColor = borderColor;


    startPanel = new Panel(background, box);
    startPanel.height = height - 8;
    startPanel.anchorTop = true;
    startPanel.anchorLeft = true;
    startPanel.anchorRight = true;
    startPanel.fillColor = ascii.rgbColor(true, 193, 255, 178);
    startPanel.borderColor = ascii.rgbColor(false, 2, 147, 0);
    startPanel.title = "Start";

    salePanel = new Panel(background, box);
    salePanel.height = height - 8;
    salePanel.anchorTop = true;
    salePanel.anchorLeft = true;
    salePanel.anchorRight = true;
    salePanel.fillColor = ascii.rgbColor(true, 193, 255, 178);
    salePanel.borderColor = ascii.rgbColor(false, 2, 147, 0);
    salePanel.title = "Sale";

    menuPanel = new Panel(background, box);
    menuPanel.width = width * 6 / 10 - 2;
    menuPanel.height = height - 8;
    menuPanel.anchorTop = true;
    menuPanel.anchorLeft = true;
    menuPanel.fillColor = panelColor;
    menuPanel.borderColor = borderColor;
    menuPanel.title = "Menu";


    orderPanel = new Panel(background, box);
    orderPanel.width = width - menuPanel.width - 3;
    orderPanel.anchorTop = true;
    orderPanel.anchorBottom = true;
    orderPanel.anchorRight = true;
    orderPanel.fillColor = panelColor;
    orderPanel.borderColor = borderColor;
    orderPanel.title = "Order";


    guidePanel = new Panel(background, box);
    guidePanel.width = menuPanel.width;
    guidePanel.height = 5;
    guidePanel.anchorBottom = true;
    guidePanel.anchorLeft = true;
    guidePanel.fillColor = panelColor;
    guidePanel.borderColor = borderColor;
    guidePanel.title = "Guide";

    guidePanelStart = new Panel(background, box);
    guidePanelStart.width = menuPanel.width;
    guidePanelStart.height = 5;
    guidePanelStart.anchorBottom = true;
    guidePanelStart.anchorLeft = true;
    guidePanelStart.anchorRight = true;
    guidePanelStart.fillColor = panelColor;
    guidePanelStart.borderColor = borderColor;
    guidePanelStart.title = "Guide";


    inputPanel = new Panel(guidePanel, box);
    inputPanel.width = 30;
    inputPanel.height = 3;
    inputPanel.anchorTop = true;
    inputPanel.anchorLeft = true;
    inputPanel.fillColor = ascii.rgbColor(true, 255, 255, 255);
    inputPanel.borderColor = borderColor;
    inputPanel.title = "Input";


    paymentPanel = new Panel(menuPanel, box);
    paymentPanel.anchorTop = true;
    paymentPanel.anchorBottom = true;
    paymentPanel.anchorRight = true;
    paymentPanel.anchorLeft = true;
    paymentPanel.fillColor = ascii.rgbColor(true, 255, 255, 255);
    paymentPanel.borderColor = borderColor;
    paymentPanel.title = "Payment";

    keybindsX = inputPanel.x + inputPanel.width + 4;

    redraw();
  }

  public void redraw()
  {
    background.redraw();
    startPanel.redraw();

    menuPanel.redraw();
    orderPanel.redraw();
    guidePanel.redraw();
    guidePanelStart.redraw();
    inputPanel.redraw();
    inputPanel.panel += ascii.cursorTo(inputPanel.x + 1, inputPanel.y + 1);
    paymentPanel.redraw();
    salePanel.redraw();

    startBackground =
      ascii.eraseEntireScreen
      + ascii.cursorHome
      + background.panel
      + startPanel.panel
      + guidePanelStart.panel;

    orderBackground =
      ascii.eraseEntireScreen
      + ascii.cursorHome
      + background.panel
      + menuPanel.panel
      + orderPanel.panel
      + guidePanel.panel
      + inputPanel.panel;
  }

  public String displayItem(Panel panel)
  {
    String[] itemList = item.getList();
    double[] priceList = item.getPriceList();
    int[] itemCount = item.getCountList();
    if(item.lastIndex == 0) return "";

    String[] itemLine = new String[itemList.length * 2];
    String[] priceLine = new String[priceList.length * 2];

    for (int i = 0; i < itemList.length; i++)
    {
      int ii = i * 2;
      double price = priceList[i];
      int count = itemCount[i];
      if (ii > panel.height - 7) continue;

      itemLine[ii] = itemList[i];
      itemLine[ii + 1] = " x " + count;
      priceLine[ii] = "RM" + String.format("%.2f", price) + "       ";
      priceLine[ii + 1] = "RM" + String.format("%.2f", price * count);
    }

    int totalPriceDisplayY = panel.y + panel.height - 5;
    int totalPriceDisplayX = panel.x + panel.width - 1;
    double sstPrice = item.orderTotalPrice * 0.06;
    double totalPriceAfterSst = item.orderTotalPrice + sstPrice;
    String formatedTotalPrice = String.format("%.2f", item.orderTotalPrice);
    String formatedSstPrice = String.format("%.2f", sstPrice);
    String formatedPriceAfterSst = String.format("%.2f", totalPriceAfterSst);

    String priceDisplay = formatter.alignRight(priceLine);
    int priceWidth = priceLine[0].length();
    String itemDisplay = ascii.cursorTo(panel.x + 1, panel.y + 1)
      + panel.fillColor + panel.borderColor
      + formatter.alignLeft(itemLine)
      + ascii.cursorTo(panel.x + panel.width - 2 - priceWidth, panel.y + 1)
      + priceDisplay
      + ascii.cursorTo(totalPriceDisplayX - 7 - formatedTotalPrice.length(), totalPriceDisplayY) 
      + "Total: " + formatedTotalPrice
      + ascii.moveCursor(-(5 + formatedSstPrice.length()), 1) 
      + "sst: " + formatedSstPrice
      + ascii.moveCursor(-(12 + formatedPriceAfterSst.length()), 1) 
      + "Full Price: " + formatedPriceAfterSst;

    return itemDisplay;
  }

  public String renderPayment(Panel panel, double input, boolean card, boolean cash)
  {
    String output =  panel.panel + ascii.cursorTo(panel.x + 1, panel.y + 1) 
      + "Total Price: RM" + String.format("%.2f", item.orderTotalPrice * 1.06);
    if (input > 0)
    {
      double balance = input - item.orderTotalPrice * 1.06;
      output+= ascii.cursorTo(panel.x + 1, panel.y + 2) 
        + "Entered: RM" + String.format("%.2f", input)
        + ascii.cursorTo(panel.x + 1, panel.y + 3)
        + "Balance: RM" + String.format("%.2f", balance)
        + ascii.cursorTo(panel.x + 1, panel.y + 5)
        + "Proceed? Y/N";
    }
    else if (cash)
    {
      output+= ascii.cursorTo(panel.x + 1, panel.y + 5)
        + "Enter cash amount.";
    }
    if (card)
    {
      output+= ascii.cursorTo(panel.x + 1, panel.y + 5)
        + "Proceed? Y/N";
    }
    output+= ascii.resetBold;
    return output;
  }

  public String renderSale(Panel panel)
  {
    String sale = panel.fillColor + panel.borderColor;

    int columnWidth = 40; 

    for (int i = 0; i < item.itemsName.length; i++)
    {
      int column = i / (panel.height - 2);
      int row = i % (panel.height - 2);

      int x = panel.x + 1 + (column * (columnWidth + 1));
      int y = panel.y + 1 + row;

      String id = (i + 1) + ". ";
      String leftLine = item.itemsName[i] + " x " + item.itemsCount[i];
      String price = "RM" + String.format("%.2f", item.itemsPrice[i]);

      int spacing = columnWidth - id.length() - leftLine.length() - price.length();

      sale += ascii.cursorTo(x, y)
        + id
        + leftLine
        + ascii.moveCursor(spacing, 0)
        + price;
    }

    String formattedTotalPrice = "RM" + String.format("%.2f", item.saleTotalPrice);
    String formattedTotalPriceAfterSst = "RM" + String.format("%.2f", item.saleTotalPrice * 1.06);
    sale += ascii.moveCursor(-formattedTotalPrice.length() - 7, 2) + "Total: "+ formattedTotalPrice
      + ascii.moveCursor(-formattedTotalPrice.length() - 17, 1) + "Total after sst: "+ formattedTotalPriceAfterSst;

    return sale;
  }
}
