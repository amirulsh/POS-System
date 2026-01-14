
package components;

class Catalogue
{
  public final String[] category = {"Food", "Beverage"}; //one-dimensional array for 2 selected categories
  public final String[] food = {"Roti Canai", "Nasi Kandar Ayam", "Maggie Goreng", "Mee Goreng Mamak"}; //one-dimensional array for Food category, has 4 items
  public final double[] foodPrice = {1.60, 10.00, 7.00, 7.00}; //one-dimensional array for food[] pricing, follows the same index 
  public final String[] beverage = {"Air Limau", "Teh", "Teh'O", "Kopi", "Kopi'O",}; //one-dimensional array for Beverage category, has 5 items
  public final String[] addons = {"Cold", "Hot"}; //2 options for beverage
  public final double[] beveragePriceCold = {2.00, 1.80, 1.70, 1.90, 1.80}; //one-dimensional array for beverage[] pricing with Cold option, follows the same index
  public final double[] beveragePriceHot = {1.90, 1.70, 1.60, 1.80, 1.70}; //one-dimensional array for beverage[] pricing with Hot option, follows the same index
}


