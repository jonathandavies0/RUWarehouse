package warehouse;

/*
 * Use this class to test the betterAddProduct method.
 */ 
public class BetterAddProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        
        // Use this file to test addProduct
    //create new warehouse object
    Warehouse w = new Warehouse();
    //store the number of items
    int totalProducts = StdIn.readInt();

    //loop through the entire input file / fill parameters to pass to addProduct()
    int count = 0;
    while(count < totalProducts){
        int day = StdIn.readInt(); //stores the day
        int id = StdIn.readInt(); //stores the ID
        String name = StdIn.readString(); //stores the ID
        int stock = StdIn.readInt(); //stores the stock
        int demand = StdIn.readInt(); //stores the demand

        //add each product from input file to warehouse
        w.betterAddProduct(id, name, stock, day, demand);
        //increment count to fulfil the base case
        count++;
    }
    StdOut.println(w); //print out warehouse
    }
}
