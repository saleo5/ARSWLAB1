package edu.eci.arsw.blacklistvalidator;

import java.util.List;

public class Main {
    
    public static void main(String a[]) {
        HostBlackListsValidator hblv = new HostBlackListsValidator();
        
        // Número de hilos a usar
        int numberOfThreads = 12;
        
        System.out.println("===== TEST 1: IP dispersa (202.24.34.55) =====");
        List<Integer> blackLists1 = hblv.checkHost("200.24.34.55", numberOfThreads);
        System.out.println("The host was found in the following blacklists: " + blackLists1);
        
        System.out.println("\n===== TEST 2: IP no reportada (212.24.24.55) =====");
        List<Integer> blackLists2 = hblv.checkHost("212.24.24.55", numberOfThreads);
        System.out.println("The host was found in the following blacklists: " + blackLists2);
        
        System.out.println("\n===== TEST 3: IP con muchos reportes (200.24.34.55) =====");
        List<Integer> blackLists3 = hblv.checkHost("200.24.34.55", numberOfThreads);
        System.out.println("The host was found in the following blacklists: " + blackLists3);
    }
}