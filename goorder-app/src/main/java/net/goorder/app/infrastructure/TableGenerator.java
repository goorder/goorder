package net.goorder.app.infrastructure;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;


/**
 *
 * @author Witold Szczerba <witold.szczerba@espeo.pl>
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class TableGenerator implements Sequencer {

    private static final int FETCH_SIZE = 10;

    private static final String DEFAULT_SEQ_NAME = "default";

    @Inject
    private SequenceTable sequenceTable;

    private long left = 0;

    private long next = -1;

    @Override
    @Lock(LockType.WRITE)
    public Long next(String sequenceName) {
        if (left < 1) {
            next = sequenceTable.fetch(sequenceName, FETCH_SIZE);
            left = FETCH_SIZE;
        }
        left--;
        return next++;
    }

    @Override
    @Lock(LockType.WRITE)
    public Long next() {
        return next(DEFAULT_SEQ_NAME);
    }

}
