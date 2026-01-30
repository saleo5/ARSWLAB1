package edu.eci.arsw.blacklistvalidator;

import java.util.List;

public class PerformanceTest {
    
    public static void main(String[] args) {
        
        HostBlackListsValidator hblv = new HostBlackListsValidator();
        String ipToTest = "202.24.34.55";
        
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Número de núcleos disponibles: " + cores);
        System.out.println("=========================================\n");
        
        int[] threadCounts = {1, cores, cores * 2, 50, 100, 200, 500};
        
        for (int numThreads : threadCounts) {
            System.out.println("*** Prueba con " + numThreads + " hilos ***");
            
            long startTime = System.currentTimeMillis();
            
            List<Integer> blackLists = hblv.checkHost(ipToTest, numThreads);
            
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            
            System.out.println("Tiempo de ejecución: " + duration + " ms");
            System.out.println("Listas negras encontradas: " + blackLists.size());
            System.out.println("=========================================\n");
            
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}