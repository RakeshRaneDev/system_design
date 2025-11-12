package org.byte_beast.in_memory_database;

import java.util.List;

public class RadisDatabaseApplication {
    //inililze database
    public static void main(String[] args) {
        System.out.println("Radis data batabse demo");
        RadisDatabase db = new RadisDatabase();
        db.start();
        basicCurdOperation(db);

        advanceSeracrchAndFilter(db);

        ttlOperations(db);
    }

    private static void ttlOperations(RadisDatabase db) {
        System.out.println("============ TTl operation ===========");
        db.create("user1", "hope form", 10l);
        db.create("user2", "hope form", 20l);

        System.out.println("user2 " + db.getRemainingTtl("user2")/60);
        System.out.println("user1 " + db.getRemainingTtl("user1")/60);

        try{
            Thread.sleep(30000);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        System.out.println("after 30 sec");
        System.out.println("user2 " + db.getRemainingTtl("user2")/60);
        System.out.println("user1 " + db.getRemainingTtl("user1")/60);

        System.out.println(" user1  " +  db.read("user1"));
    }

    private static void advanceSeracrchAndFilter(RadisDatabase db) {
        List<DataBaseEntry> dataBaseEntries = db.find(new QueryCondition("key", "r", ComparisonOperator.START_WITH));
        System.out.println(dataBaseEntries);
    }

    private static void basicCurdOperation(RadisDatabase db) {
        // create opreation
        db.create("rakesh", 100);
        db.create("Rani", 500);
        db.create("Rohan", 100);
        db.create("hema", 500);
        // read opration
        System.out.println("[create] rakesh   "+ db.read("rakesh"));

        db.upsert("Mohan", 100);
        System.out.println("[upsert] Mohan   "+ db.read("Mohan"));

        db.update("rakesh", 10000);

        // update opration
        System.out.println("[update] rakesh   "+ db.read("rakesh"));

        //delete

        db.delete("hema");

        // delete opration
        System.out.println("[delete] hema   "+ db.read("hema"));
    }
}
