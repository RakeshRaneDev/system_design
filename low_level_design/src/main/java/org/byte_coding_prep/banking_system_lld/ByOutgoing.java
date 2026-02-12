package org.byte_coding_prep.banking_system_lld;

import java.util.Comparator;

public class ByOutgoing implements Comparator<AccountSummary> {
    @Override
    public int compare(AccountSummary a, AccountSummary b) {
        if (a.outgoing == b.outgoing) {
            return a.accountId.compareTo(b.accountId);
        }
        return Integer.compare(b.outgoing, a.outgoing);
    }
}
