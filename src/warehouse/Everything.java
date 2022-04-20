package warehouse;

/*
 * Use this class to put it all together.
 */ 
public class Everything {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        
        //create warehouse
        Warehouse w = new Warehouse();
        //store the total # of queries 
        int totalQuery = StdIn.readInt();

        //loop through an execute the queries 
        int count = 1;
        while(StdIn.hasNextLine()){
            String line = StdIn.readLine(); //store the query 

            //separate the parameters passed for the query and store them to be used
            String[] sep = line.split(" "); 

            //check the query values
            if(sep[0].equals("add")){
                //parse to proper data type and create product object
                int day = Integer.parseInt(sep[1]);
                int id = Integer.parseInt(sep[2]);
                String name = sep[3];
                int stock = Integer.parseInt(sep[4]);
                int demand = Integer.parseInt(sep[5]);
                //Product a = new Product(id, name, stock, day, demand); //creates the product to add
                w.addProduct(id, name, stock, day, demand);
            }
            else if(sep[0].equals("purchase")){
                int day = Integer.parseInt(sep[1]);
                int id = Integer.parseInt(sep[2]);
                int amount = Integer.parseInt(sep[3]);
                System.out.println("     "+day + " " + " " + id + " " + amount);
                w.purchaseProduct(id, day, amount);; // updates the values for item
            }
            else if(sep[0].equals("delete")){
                int id = Integer.parseInt(sep[1]);
                System.out.println(id);
                w.deleteProduct(id); //delete the id at this location (id)
                
            }
            else if(sep[0].equals("restock")){
                int id = Integer.parseInt(sep[1]);
                int amount = Integer.parseInt(sep[2]);
                System.out.println(id + " " + amount);
                w.restockProduct(id, amount);
            }
            count++;
        }StdOut.println(w);

    }
}
