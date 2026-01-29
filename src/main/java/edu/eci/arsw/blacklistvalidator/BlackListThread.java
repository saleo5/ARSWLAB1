package edu.eci.arsw.blacklistvalidator;

import java.util.LinkedList;
import java.util.List;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

/**
 * Hilo que busca una IP en un segmento de listas negras
 */
public class BlackListThread extends Thread {
    
    private int startIndex;
    private int endIndex;
    private String ipAddress;
    private int ocurrencesCount;
    private List<Integer> blackListOcurrences;
    private HostBlacklistsDataSourceFacade skds;
    private int checkedListsCount;
    
    public BlackListThread(int startIndex, int endIndex, String ipAddress, 
                           HostBlacklistsDataSourceFacade skds) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.ipAddress = ipAddress;
        this.skds = skds;
        this.ocurrencesCount = 0;
        this.blackListOcurrences = new LinkedList<>();
        this.checkedListsCount = 0;
    }
    
    @Override
    public void run() {
        // Buscar en el segmento asignado
        for (int i = startIndex; i <= endIndex; i++) {
            checkedListsCount++;
            
            if (skds.isInBlackListServer(i, ipAddress)) {
                blackListOcurrences.add(i);
                ocurrencesCount++;
            }
        }
    }
    
    public int getOcurrencesCount() {
        return ocurrencesCount;
    }

    public List<Integer> getBlackListOcurrences() {
        return blackListOcurrences;
    }
    
    public int getCheckedListsCount() {
        return checkedListsCount;
    }
}