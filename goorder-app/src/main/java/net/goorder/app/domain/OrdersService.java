package net.goorder.app.domain;

import net.goorder.app.infrastructure.Update;
import javax.inject.Inject;
import net.goorder.app.infrastructure.Sequencer;
import static net.goorder.db.jooq.Tables.*;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.TableField;
import org.jooq.UpdatableRecord;

/**
 *
 * @author witoldsz
 */
public class OrdersService {

    @Inject
    private DSLContext jooq;

    @Inject
    private GroupIdGenerator groupIdGenerator;

    @Inject
    private Sequencer sequencer;

    public String createNewGroup() {
        return groupIdGenerator.next();
    }

    public Long addNewTable(String groupId, String label) {
        //TODO: check if groupId is valid
        Long id = sequencer.next();
        jooq.insertInto(ORDERING_TABLE, ORDERING_TABLE.ID, ORDERING_TABLE.GROUP_ID, ORDERING_TABLE.LABEL)
                .values(id, groupId, label)
                .execute();
        return id;
    }

    public void updateTable(OrderingTableUpdateCmd update) {
        Condition c = ORDERING_TABLE.ID.eq(update.getTableId()).and(ORDERING_TABLE.GROUP_ID.eq(update.getGroupId()));
        update(ORDERING_TABLE.LABEL, update.getLabel(), c);
        update(ORDERING_TABLE.PLACE, update.getPlace(), c);
        update(ORDERING_TABLE.COMMENTS, update.getComments(), c);
    }

    public Long addNewLine(String groupId, Long tableId) {
        //TODO: check if groupId is valid
        Long id = sequencer.next();
        jooq.insertInto(ORDER_LINE, ORDER_LINE.ID, ORDER_LINE.ORDERING_TABLE)
                .values(id, tableId)
                .execute();
        return id;
    }

    public void updateLine(OrderingLineUpdateCmd update) {
        //TODO: check if groupId is valid
        Condition c = ORDER_LINE.ID.eq(update.getLineId()).and(ORDER_LINE.ORDERING_TABLE.eq(update.getTableId()));
        update(ORDER_LINE.WHO, update.getWho(), c);
        update(ORDER_LINE.WHAT, update.getWhat(), c);
        update(ORDER_LINE.PRICE, update.getPrice(), c);
        update(ORDER_LINE.PAID, update.getPaid(), c);
    }

    private <R extends UpdatableRecord<R>, T> void update(TableField<R,T> f, Update<T> u, Condition c) {
        if (u != null) {
            jooq.update(f.getTable()).set(f, u.getValue()).where(c).execute();
        }
    }

}
