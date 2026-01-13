package components;
public class Item
{

  Catalogue catalogue = new Catalogue();

  public Item()
  {
    nameItems();
  }

  private int indexFood = 0;
  private int indexColdBeverage = catalogue.food.length;
  private int indexHotBeverage = indexColdBeverage + catalogue.beverage.length;
  private int itemArrayLength = indexHotBeverage + catalogue.beverage.length;

  public String[] itemsName = new String[itemArrayLength];
  public int[] itemsCount = new int[itemArrayLength];
  public double[] itemsPrice = new double[itemArrayLength];

  private String[] itemListDisplay = new String[itemArrayLength];
  private double[] itemListPrice = new double[itemArrayLength];
  private int[] itemListId = new int[itemArrayLength];
  private int[] itemListCount = new int[itemArrayLength];

  public double allItemTotalPrice = 0;
  public double allListTotalPrice = 0;

  public int lastIndex = 0;

  private void nameItems()
  {
    double[] price;
    int i = 0;
    for (int fd = 0; fd < catalogue.food.length; fd++)
    {
      itemsPrice[i] = catalogue.foodPrice[fd];
      itemsName[i++] = catalogue.food[fd];
    }
    for (int ad = 0; ad < catalogue.addons.length; ad++)
    {
      if(ad == 0)
      {
        price = catalogue.beveragePriceCold;
      } 
      else
      {
        price = catalogue.beveragePriceHot;
      }

      for (int bv = 0; bv < catalogue.beverage.length; bv++)
      {
        itemsPrice[i] = price[bv];
        itemsName[i++] = catalogue.beverage[bv] + " - " + catalogue.addons[ad];
      }
    }
  }


  public String[] getCategory()
  {
    return catalogue.category;
  }

  public String[] getFood()
  {
    return catalogue.food;
  }

  public String[] getBeverage()
  {
    return catalogue.beverage;
  }

  public String[] getAddons()
  {
    return catalogue.addons;
  }


  public void RegisterItem(int categoryId, int input)
  {
    int index = 0;
    if (categoryId == 1)
    {
      index = indexFood + input;
    }
    else if (categoryId == 2)
    {
      index = indexColdBeverage + input;
    }
    else if (categoryId == 3)
    {
      index = indexHotBeverage + input;
    }

    boolean duplicate = false;
    for (int i = 0; i < lastIndex; i++)
    {
      if (itemListId[i] == index) 
      {
        itemListCount[i]++;
        allListTotalPrice+= itemListPrice[i];

        duplicate = true;
        break;
      }
    }
    if (!duplicate)
    {
      itemListId[lastIndex] = index;
      itemListDisplay[lastIndex] = itemsName[index];
      itemListPrice[lastIndex] = itemsPrice[index];
      itemListCount[lastIndex] = 1;
      allListTotalPrice+= itemsPrice[index];
      lastIndex++;
    }
  }

  public String[] getList()
  { 
    String[] list = new String[lastIndex];
    for (int i = 0; i < lastIndex; i++ )
    {
      list[i] = itemListDisplay[i];
    }
    return list;
  }

  public double[] getPriceList()
  {
    double[] list = new double[lastIndex];
    for (int i = 0; i < lastIndex; i++ )
    {
      list[i] = itemListPrice[i];
    }
    return list;
  } 

  public int[] getCountList()
  {
    int[] list = new int[lastIndex];
    for (int i = 0; i < lastIndex; i++ )
    {
      list[i] = itemListCount[i];
    }
    return list;
  }


  public void voidItem()
  {
    itemListDisplay = new String[itemArrayLength];
    itemListPrice = new double[itemArrayLength];
    itemListId = new int[itemArrayLength];
    itemListCount = new int[itemArrayLength];
    allListTotalPrice = 0;
    lastIndex = 0;
  }

  public void Paid()
  {
    for (int i = 0; i < itemListId.length; i++)
    {
      itemsCount[itemListId[i]]+= itemListCount[i];
    }
    allItemTotalPrice+= allListTotalPrice;
    allListTotalPrice = 0;
  }
}
