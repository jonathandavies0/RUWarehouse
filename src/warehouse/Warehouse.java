package warehouse;

/*
 *
 * This class implements a warehouse on a Hash Table like structure, 
 * where each entry of the table stores a priority queue. 
 * Due to your limited space, you are unable to simply rehash to get more space. 
 * However, you can use your priority queue structure to delete less popular items 
 * and keep the space constant.
 * 
 * @author Ishaan Ivaturi
 */ 
public class Warehouse {
    private Sector[] sectors;
    
    // Initializes every sector to an empty sector
    public Warehouse() {
        sectors = new Sector[10];

        for (int i = 0; i < 10; i++) {
            sectors[i] = new Sector();
        }
    }
    
    /**
     * Provided method, code the parts to add their behavior
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void addProduct(int id, String name, int stock, int day, int demand) {
        evictIfNeeded(id);
        addToEnd(id, name, stock, day, demand);
        fixHeap(id);
    }

    /**
     * Add a new product to the end of the correct sector
     * Requires proper use of the .add() method in the Sector class
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    private void addToEnd(int id, String name, int stock, int day, int demand) {
        //creates the product object to add to end of the correct sector
        //Product p = new Product(id, name, stock, day);
        Product p = new Product(id, name, stock, day, demand);

        // get sector location from ID
        int s = p.getId(); //stores the ID
        s = s % 10; //restores the last digit = sector # 

        // add the product object(p) to the sector array at index (s)
        sectors[s].add(p); 

    }

    /**
     * Fix the heap structure of the sector, assuming the item was already added
     * Requires proper use of the .swim() and .getSize() methods in the Sector class
     * @param id The id of the item which was added
     */
    private void fixHeap(int id) {
        int s = id % 10; //store the sector that we are fixing
        sectors[s].swim(sectors[s].getSize()); //swim the last product in that sector
    }

    /**
     * Delete the least popular item in the correct sector, only if its size is 5 while maintaining heap
     * Requires proper use of the .swap(), .deleteLast(), and .sink() methods in the Sector class
     * @param id The id of the item which is about to be added      
     */
    private void evictIfNeeded(int id) {
        int s = id % 10; //store the sector 

       //delte the least popular product if the sector.size = 5
        if(sectors[s].getSize() == 5){
            sectors[s].swap(1, 5); //swap the first and last node 
            sectors[s].deleteLast(); //deletes least popular product
            sectors[s].sink(1); //heapifies the array
        }
    }

    /**
     * Update the stock of some item by some amount
     * Requires proper use of the .getSize() and .get() methods in the Sector class
     * Requires proper use of the .updateStock() method in the Product class
     * @param id The id of the item to restock
     * @param amount The amount by which to update the stock
     */
    public void restockProduct(int id, int amount) {
        int s = id % 10; //store sector 
        int count = 1; //create a variable to increment loop
        //while we haven't checked all the products 
        while(count < sectors[s].getSize() + 1){
            int ident = sectors[s].get(count).getId();
            //if item exists in the sector then add amount to stock
            if(ident == id){
                sectors[s].get(count).updateStock(amount);//updates the stock
            }
            count++;
        }
    }
    
    /**
     * Delete some arbitrary product while maintaining the heap structure in O(logn)
     * Requires proper use of the .getSize(), .get(), .swap(), .deleteLast(), .sink() and/or .swim() methods
     * Requires proper use of the .getId() method from the Product class
     * @param id The id of the product to delete
     */
    public void deleteProduct(int id) {
        //get the sector
        int s = id % 10; //store sector
        int count = 1;
        // find / swap id product with last product

        while(count < sectors[s].getSize()+1){
            int ident = sectors[s].get(count).getId();
            //if item exists in the sector then swap, delete, and swim swapped product
            if(ident == id){
                sectors[s].swap(sectors[s].getSize(), count); //swap (last index, id index)
                sectors[s].deleteLast(); //deletes the last product 
                sectors[s].sink(count);
            }
            count++;
        }
        //delete the last product 
        //sink down the swapped product left
    }
    
    /**
     * Simulate a purchase order for some product
     * Requires proper use of the getSize(), sink(), get() methods in the Sector class
     * Requires proper use of the getId(), getStock(), setLastPurchaseDay(), updateStock(), updateDemand() methods
     * @param id The id of the purchased product
     * @param day The current day
     * @param amount The amount purchased
     */
    public void purchaseProduct(int id, int day, int amount) {
        
        int s = id % 10; //get the sector
        //search for the item by id 
        int count = 1;
        while(count < sectors[s].getSize()+1){
            int ident = sectors[s].get(count).getId();
            //compare each item id with our target id 
            if(ident == id){
                if(sectors[s].get(count).getStock() > amount){
                    //update the LPD for item
                    sectors[s].get(count).setLastPurchaseDay(day); 
                    //decrease stock of item by amount
                    sectors[s].get(count).updateStock(-1* amount);  
                    //demand is increased by amount purchased
                    sectors[s].get(count).updateDemand(amount);
                    //sink the updated product to maintain min heap structure
                    sectors[s].sink(count);
                } 

            }count++;
        }

        //update items last purchase day to the current day
        //decrease stock of item by amount
        //demand is increased by amount purchased
    }
    
    /**
     * Construct a better scheme to add a product, where empty spaces are always filled
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void betterAddProduct(int id, String name, int stock, int day, int demand) {
        int s = id % 10;
        //
        if (max(s)) { 
            addToEnd(id, name, stock, day, demand);
            fixHeap(id);
        } else { // original is full

            int j = 0; 
            if (s != 9) j = s + 1; 

            while (j < 10) { 

                if (max(j)) {
                    addToEnd(j, name, stock, day, demand);
                    fixHeap(j);
                    break;
                } 

                if (j == s) {
                    evictIfNeeded(id);
                    addToEnd(id, name, stock, day, demand);
                    fixHeap(id);
                    break;
                }

                if (j == 9) j = 0; // [0,10)
                else j++;
            }
        }
    }
    
    private boolean max(int sectorId) {
        return sectors[sectorId].getSize() < 5;
    }
    //     Product p = new Product(id, name, stock, day, demand); //create the new item to insert
        
    //     //determine where you are putting this product
    //     int s = id % 10; //stores the original sector
    //     int count = s; // store the current sector 
    //     boolean wasStored = false;
    //     Product x = new Product(id, name, stock, day, demand);

    //     if(sectors[s].getSize() != 5){
    //         addProduct(id, name, stock, day, demand);
    //     }
    //     else{

    //     //sector is full and the current sector is not the original
    //     while(wasStored = false){
    //         //sector is full so continue | search for the next open space
    //         if(sectors[s].getSize() == 5){
    //             if(count > 9){
    //                 count = 0;
    //             }
    //             else{
    //                 count++; //increment to next sector
    //                 continue;
    //             }
                
    //         }
    //         else{
    //             //store the product at open space in that current sector
    //             sectors[count].add(x);
    //             wasStored = true;
    //         }
            
    //     }
    //     evictIfNeeded(s);
    //     addProduct(id, name, stock, day, demand);
    // }
        

        

        

        //base case: current sector == original || product added to a heap true

    /*
     * Returns the string representation of the warehouse
     */
    public String toString() {
        String warehouseString = "[\n";

        for (int i = 0; i < 10; i++) {
            warehouseString += "\t" + sectors[i].toString() + "\n";
        }
        
        return warehouseString + "]";
    }

    /*
     * Do not remove this method, it is used by Autolab
     */ 
    public Sector[] getSectors () {
        return sectors;
    }
}
