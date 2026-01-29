package edu.eci.arsw.blacklistvalidator;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

/**
 * @author hcadavid
 */
public class HostBlackListsValidator {
    
    private static final int BLACK_LIST_ALARM_COUNT = 5;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is done in parallel using N threads.
     * @param ipaddress suspicious host's IP address.
     * @param N number of threads to use for the search
     * @return Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress, int N) {
        LinkedList<Integer> blackListOcurrences = new LinkedList<>();
        int ocurrencesCount = 0;
        HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();
        int checkedListsCount = 0;
        
        int totalServers = skds.getRegisteredServersCount();
        int segmentSize = totalServers / N;
        int remainder = totalServers % N;
        
        BlackListThread[] threads = new BlackListThread[N];
        
        int start = 0;
        for (int i = 0; i < N; i++) {
            int end = start + segmentSize - 1;
            if (i < remainder) {
                end++;
            }
            
            threads[i] = new BlackListThread(start, end, ipaddress, skds);
            threads[i].start();
            
            start = end + 1;
        }
        
        for (int i = 0; i < N; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        for (int i = 0; i < N; i++) {
            ocurrencesCount += threads[i].getOcurrencesCount();
            blackListOcurrences.addAll(threads[i].getBlackListOcurrences());
            checkedListsCount += threads[i].getCheckedListsCount();
        }
        
        if (ocurrencesCount >= BLACK_LIST_ALARM_COUNT) {
            skds.reportAsNotTrustworthy(ipaddress);
        } else {
            skds.reportAsTrustworthy(ipaddress);
        }
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", 
                new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
}
