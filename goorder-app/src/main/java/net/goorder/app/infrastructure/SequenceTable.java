package net.goorder.app.infrastructure;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import net.goorder.db.jooq.tables.records.SequenceRecord;
import static net.goorder.db.jooq.Tables.*;
import org.jooq.DSLContext;
/**
 *
 * @author Witold Szczerba <witold.szczerba@espeo.pl>
 */
@Stateless
public class SequenceTable {

    @Inject
    private DSLContext jooq;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long fetch(String sequenceName, long fetchSize) {
        Long result;
        SequenceRecord sequence = jooq.selectFrom(SEQUENCE)
                .where(SEQUENCE.SEQ_NAME.eq(sequenceName))
                .forUpdate()
                .fetchOne();
        if (sequence != null) {
            result = sequence.getSeqCount();
        } else {
            result = 1L;
            sequence = jooq.newRecord(SEQUENCE);
            sequence.setSeqName(sequenceName);
        }
        sequence.setSeqCount(result + fetchSize);
        sequence.store();
        return result;
    }
}
