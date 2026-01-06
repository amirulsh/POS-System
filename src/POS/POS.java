import components.*;

public class POS 
{
  public static void main(String[] args) 
  {
    Draw draw = new Draw();
    draw.BuildBackground();
    draw.DrawBackground();
    System.out.print("\u001B[34;1H");
  }
}
