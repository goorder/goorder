package net.goorder.app.presentation;

import java.util.List;
import lombok.Data;

/**
 * @author witoldsz
 */
@Data
public class OrderingGroupView {

    private List<OrderingTableView> tables;

    public OrderingGroupView(List<OrderingTableView> tables) {
        this.tables = tables;
    }

}
