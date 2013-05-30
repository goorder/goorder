package net.goorder.app.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import static net.goorder.db.jooq.Tables.*;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author witoldsz
 */
public class OrderingGroupRepo {

    @Inject
    private DSLContext jooq;

    public OrderingGroupView orderingGroup(String groupId) {
        Result<Record> records = jooq.select()
                .from(ORDERING_TABLE)
                .leftOuterJoin(ORDER_LINE).on(ORDER_LINE.ORDERING_TABLE.eq(ORDERING_TABLE.ID))
                .orderBy(ORDERING_TABLE.ID)
                .fetch();

        List<OrderingTableView> tables = new ArrayList<>();
        for (Record r : records) {
            Long tableId = r.getValue(ORDERING_TABLE.ID);
            OrderingTableView table = tables.isEmpty() ? null : tables.get(tables.size() - 1);
            if (table == null || table.getId().compareTo(tableId) != 0) {
                table = orderingTableView(r);
                tables.add(table);
            }
            table.addLine(orderLineView(r));
        }

        return new OrderingGroupView(tables);
    }

    private OrderingTableView orderingTableView(Record r) {
        OrderingTableView v = new OrderingTableView();
        v.setId(r.getValue(ORDERING_TABLE.ID));
        v.setComments(r.getValue(ORDERING_TABLE.COMMENTS));
        v.setLabel(r.getValue(ORDERING_TABLE.LABEL));
        v.setPlace(r.getValue(ORDERING_TABLE.PLACE));
        return v;
    }

    private OrderLineView orderLineView(Record r) {
        OrderLineView v = new OrderLineView();
        v.setId(r.getValue(ORDER_LINE.ID));
        v.setPaid(r.getValue(ORDER_LINE.PAID));
        v.setPrice(r.getValue(ORDER_LINE.PRICE));
        v.setWhat(r.getValue(ORDER_LINE.WHAT));
        v.setWho(r.getValue(ORDER_LINE.WHO));
        return v;
    }

}
