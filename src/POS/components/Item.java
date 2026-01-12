package components;

class Catalogue
{
  public final String[] category = {"Food", "Beverage"};
  public final String[] food = {"Roti Canai", "Nasi Kandar Ayam", "Maggie Goreng", "Mee Goreng Mamak"};
  public final double[] foodPrice = {1.60, 10.00, 7.00, 7.00};
  public final String[] beverage = {"Air Limau", "Teh", "Teh'O", "Kopi", "Kopi'O",};
  public final String[] addons = {"Cold", "Hot"};
  public final double[] beveragePriceCold = {2.00, 1.80, 1.70, 1.90, 1.80};
  public final double[] beveragePriceHot = {1.90, 1.70, 1.60, 1.80, 1.70};
}

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

  private String[] itemsName = new String[itemArrayLength];
  private int[] itemsCount = new int[itemArrayLength];
  private double[] itemsPrice = new double[itemArrayLength];

  private String[] itemListDisplay = new String[itemArrayLength];
  private double[] itemListPrice = new double[itemArrayLength];
  private int[] itemListId = new int[itemArrayLength];
  private int[] itemListCount = new int[itemArrayLength];
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


  public String[] GetCategory()
  {
    return catalogue.category;
  }

  public String[] GetFood()
  {
    return catalogue.food;
  }

  public String[] GetBeverage()
  {
    return catalogue.beverage;
  }

  public String[] GetAddons()
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
      lastIndex++;
    }
  }

  public String[] GetList()
  { 
    String[] list = new String[lastIndex];
    for (int i = 0; i < lastIndex; i++ )
    {
      list[i] = itemListDisplay[i];
    }
    return list;
  }

  public double[] GetPriceList()
  {
    double[] list = new double[lastIndex];
    for (int i = 0; i < lastIndex; i++ )
    {
      list[i] = itemListPrice[i];
    }
    return list;
  } 

  public int[] GetCountList()
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
  lastIndex = 0;
  }

  public void Paid()
  {
    for (int i = 0; i < itemListId.length; i++)
    {
      itemsCount[itemListId[i]] = itemListCount[i];
    }
  }
}
