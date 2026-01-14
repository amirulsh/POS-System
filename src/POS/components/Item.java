package components;

public class Item
{

  Catalogue catalogue = new Catalogue();

  public Item()
  {
    nameItems(); //calls nameItems() method
  }

  private int indexFood = 0; // for index use at food[]
  private int indexColdBeverage = catalogue.food.length; //same array as food[]
  private int indexHotBeverage = indexColdBeverage + catalogue.beverage.length; //expand array with int of indexColdBeverage
  private int itemArrayLength = indexHotBeverage + catalogue.beverage.length; //expand the previous array with int of indexHotBeverage, also getting the total itemArrayLength

  public String[] itemsName = new String[itemArrayLength]; //array for storing String values
  public int[] itemsCount = new int[itemArrayLength]; //array for storing int values
  public double[] itemsPrice = new double[itemArrayLength]; //array for storing double values

  private String[] itemListDisplay = new String[itemArrayLength]; //storing display names of items in order
  private double[] itemListPrice = new double[itemArrayLength]; //storing price for each selected item
  private int[] itemListId = new int[itemArrayLength]; //used to link back to itemsName and itemsPrice
  private int[] itemListCount = new int[itemArrayLength]; //stores quantity of each selected item

  public double orderTotalPrice = 0;
  public double saleTotalPrice = 0;

  public int lastIndex = 0;

  private void nameItems()
  {
    double[] price; //array for storing prices
    int i = 0;
    for (int fd = 0; fd < catalogue.food.length; fd++) //loop through food.length array and setting assigning index with fd int, with fd going from 0 to 3 (length is 4)
    {
      itemsPrice[i] = catalogue.foodPrice[fd];
      itemsName[i++] = catalogue.food[fd];
    }
    for (int ad = 0; ad < catalogue.addons.length; ad++)  //loop through addons[], with 0 being "Cold", 1 being "Hot"
    {
      if(ad == 0) 
      {
        price = catalogue.beveragePriceCold;
      } 
      else
      {
        price = catalogue.beveragePriceHot;
      }

      for (int bv = 0; bv < catalogue.beverage.length; bv++) //loop through beverage[]
      {
        itemsPrice[i] = price[bv]; //adding the price of the item by using price[] array from earlier loop 
        itemsName[i++] = catalogue.beverage[bv] + " - " + catalogue.addons[ad]; //rename the string by adding the addon retrieved from the earlier loop
      }
    }
  }


  public String[] getCategory() //method to return array of category[]
  {
    return catalogue.category;
  }

  public String[] getFood() //method to return array of food[]
  {
    return catalogue.food;
  }

  public String[] getBeverage() //method to return array of beverage[]
  {
    return catalogue.beverage;
  }

  public String[] getAddons() //method to return array of addons[]
  {
    return catalogue.addons;
  }


  public void registerItem(int categoryId, int input)
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
    for (int i = 0; i < lastIndex; i++) //loop through to check for duplicates
    {
      if (itemListId[i] == index) //if found to be a duplicate
      {
        itemListCount[i]++; //increase quantity by 1 at the item's index
        orderTotalPrice+= itemListPrice[i]; //add new amount by refererring to itemListPrice[]

        duplicate = true;
        break;
      }
    }
    if (!duplicate) //if not a duplicate
    {
      itemListId[lastIndex] = index; //store item id
      itemListDisplay[lastIndex] = itemsName[index]; //store item's name
      itemListPrice[lastIndex] = itemsPrice[index]; //store item's price
      itemListCount[lastIndex] = 1; //create new quantity
      orderTotalPrice+= itemsPrice[index]; //update total price by referring to itemPrice[]
      lastIndex++; //move over to the next slot
    }
  }

  public String[] getList()
  { 
    String[] list = new String[lastIndex]; //new String array with the same length as lastIndex int
    for (int i = 0; i < lastIndex; i++ ) //loop by the amount of lastIndex and copying itemListDisplay[] array
    {
      list[i] = itemListDisplay[i];
    }
    return list;
  }

  public double[] getPriceList()
  {
    double[] list = new double[lastIndex]; //new double array with the same length as lastIndex int
    for (int i = 0; i < lastIndex; i++ ) //loop by the amount of lastIndex and copying itemListPrice[] array
    {
      list[i] = itemListPrice[i];
    }
    return list;
  } 

  public int[] getCountList()
  {
    int[] list = new int[lastIndex]; //new int array with the same length as lastIndex int
    for (int i = 0; i < lastIndex; i++ ) //loop by the amount of lastIndex and copying itemListCount[] array
    {
      list[i] = itemListCount[i];
    }
    return list;
  }


  public void voidItem() //method used to clear current order
  {
    itemListDisplay = new String[itemArrayLength];
    itemListPrice = new double[itemArrayLength];
    itemListId = new int[itemArrayLength];
    itemListCount = new int[itemArrayLength];
    orderTotalPrice = 0; //resetting back to default value
    lastIndex = 0; //resetting back to default value
  }

  public void paid() //method used to process after paying
  {
    for (int i = 0; i < itemListId.length; i++) //loop through length of itemListId 
    {
      itemsCount[itemListId[i]]+= itemListCount[i];
    }
    saleTotalPrice+= orderTotalPrice; //add total price of the order 
    orderTotalPrice = 0; //reset current order price
  }
}
