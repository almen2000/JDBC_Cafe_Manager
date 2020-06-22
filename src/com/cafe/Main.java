package com.cafe;


import com.cafe.management.Manager;
import com.cafe.management.Waiter;
import com.cafe.types.Gender;
import com.cafe.types.Status;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Manager manager = Manager.getManagerByUsername("manager");
        Waiter waiter = Waiter.getWaiterByUsername("vangay");
        System.out.println(manager);
        System.out.println(waiter);
//        manager.createWaiterInDB("Vanik", "Martirosyan", "vangay", "vanik111", "vanik@gmail.com", Gender.MALE);
//        manager.createManagerInDB("Gayane", "Arakelyan", "aragay", "gayane11", "gayane@gmail.com", Gender.FEMALE);
//        manager.createTableInDB(7);
//        manager.assignTableToWaiter(UUID.fromString("9a3916d2-4416-40d4-9267-fdfddff2235e"), "vangay");
//        manager.createProductInDB("Steak", 21, 300, "Steak File Minion", 25);
//        waiter.assignedTables();
//        waiter.createOrderForTable(UUID.fromString("9a3916d2-4416-40d4-9267-fdfddff2235e"));
//        waiter.createProductInOrder(UUID.fromString("e5d0e60f-4077-4621-a06f-a4b0a056595d"), UUID.fromString("792c7e7c-86e3-4582-8732-35e68e99db86"), 3);
//        waiter.editProductInOrderAmount(UUID.fromString("e5d0e60f-4077-4621-a06f-a4b0a056595d"), UUID.fromString("792c7e7c-86e3-4582-8732-35e68e99db86"), 7);
//        waiter.editOrderStatus(UUID.fromString("2afdde3f-b783-4498-bc2e-e8ba1754a2a2"), Status.CLOSED);
    }

}
