package warehouse;

public class Restock {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        //create a warehouse object
        Warehouse w = new Warehouse();

        //store the amount of queries 
        int queryTotal = StdIn.readInt();
        //System.out.println(StdIn.readString());
        //loop through the values and execute each query
        int count = 0; //incrementer through loop
        //String line = StdIn.readLine();//get rid of blank line

        while(StdIn.hasNextLine()){
           String line = StdIn.readLine(); //store the string for the first query 
            //System.out.println(count + " " + line);

            //separate the parameters passed for the query and store them to be used
            String[] sep = line.split(" ");
            //System.out.println(count + " "+ sep[0]);
            // check if query is "add" or "restock"

            for(int i =0; i < sep.length; i++){
            System.out.println(count +" " + sep[i] );}

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
