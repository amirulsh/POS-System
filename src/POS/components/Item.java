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
  private String[] itemsCount = new String[itemArrayLength];

  private String[] itemListDisplay = new String[itemArrayLength];
  private int[] itemListId = new int[itemArrayLength];
  private int[] itemListCount = new int[itemArrayLength];
  private String[] itemPricesDisplay = new String[200];
  private int lastIndex = 0;
  
  private void nameItems()
  {
    int i = 0;
    for (String item: catalogue.food)
    {
      itemsName[i++] = item;
    }
    for (String addon: catalogue.addons)
    {
      for (String item: catalogue.beverage)
      {
        itemsName[i++] = item + " - " + addon;
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

  private void addBeverage()
  {

  }

  public String[] RegisterItem(int categoryId, int input)
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
      lastIndex++;
    }
    String[] list = new String[lastIndex];
    for (int i = 0; i < lastIndex; i++ )
    {
      list[i] = itemListDisplay[i];
    }
    return list;
  }
}
