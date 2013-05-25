package net.goorder.app.presentation;

import java.util.List;
import lombok.Data;
import org.joda.time.LocalDateTime;

/**
 * This is a single order of a specific {@link OrderGroup}.
 * @author witoldsz
 */
@Data
public class OrderingTableView {

    private Long id;

    private String label;

    private String place;

    private String comments;

    private List<OrderLineView> items;
}
